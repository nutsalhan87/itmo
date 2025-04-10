use std::{cmp, ops::BitXor};

use crate::{block::{Block, BlockEndec}, util::{to_u32_extended, ExtendPolicy}};

const LOG_W: u32 = 5;
const P32: u32 = 3084996963;
const Q32: u32 = 2654435769;
const ROUNDS: usize = 20;
const ROUNDS_EXTENDED: usize = 2 * ROUNDS + 4;

pub struct RC6 {
    keys: [u32; ROUNDS_EXTENDED],
}

impl RC6 {
    pub fn new(main_key: &[u8]) -> Self {
        let mut main_key = to_u32_extended(main_key, ExtendPolicy::Fill(0));
        
        let mut keys: [u32; ROUNDS_EXTENDED] = {
            let mut keys = [0; ROUNDS_EXTENDED];
            keys[0] = P32;
            let mut i = 1;
            while i < ROUNDS_EXTENDED {
                keys[i] = keys[i - 1].wrapping_add(Q32);
                i += 1;
            }
            keys
        };

        let (mut a, mut b, mut i, mut j) = (0u32, 0u32, 0usize, 0usize);
        for _ in 0..3 * cmp::max(main_key.len(), ROUNDS_EXTENDED) {
            (a, keys[i]) = {
                let value = (keys[i].wrapping_add(a).wrapping_add(b)).rotate_left(3);
                (value, value)
            };
            (b, main_key[j]) = {
                let value = (main_key[j].wrapping_add(a).wrapping_add(b)).rotate_left(a.wrapping_add(b));
                (value, value)
            };
            i = (i + 1) % ROUNDS_EXTENDED;
            j = (j + 1) % main_key.len();
        }

        Self { keys }
    }
}

impl BlockEndec for RC6 {
    fn encode_block(&self, block: Block) -> Block {
        let [mut a, mut b, mut c, mut d] = block;

        b = b.wrapping_add(self.keys[0]);
        d = d.wrapping_add(self.keys[1]);
        for i in 1..=ROUNDS {
            let t = b
                .wrapping_mul(b.wrapping_mul(2).wrapping_add(1))
                .rotate_left(LOG_W);
            let u = d
                .wrapping_mul(d.wrapping_mul(2).wrapping_add(1))
                .rotate_left(LOG_W);
            a = a.bitxor(t).rotate_left(u).wrapping_add(self.keys[2 * i]);
            c = c
                .bitxor(u)
                .rotate_left(t)
                .wrapping_add(self.keys[2 * i + 1]);
            (a, b, c, d) = (b, c, d, a);
        }
        a = a.wrapping_add(self.keys[ROUNDS_EXTENDED - 2]);
        c = c.wrapping_add(self.keys[ROUNDS_EXTENDED - 1]);

        [a, b, c, d]
    }

    fn decode_block(&self, block: Block) -> Block {
        let [mut a, mut b, mut c, mut d] = block;

        c = c.wrapping_sub(self.keys[ROUNDS_EXTENDED - 1]);
        a = a.wrapping_sub(self.keys[ROUNDS_EXTENDED - 2]);
        for i in (1..=ROUNDS).rev() {
            (a, b, c, d) = (d, a, b, c);
            let u = d
                .wrapping_mul(d.wrapping_mul(2).wrapping_add(1))
                .rotate_left(LOG_W);
            let t = b
                .wrapping_mul(b.wrapping_mul(2).wrapping_add(1))
                .rotate_left(LOG_W);
            c = c
                .wrapping_sub(self.keys[2 * i + 1])
                .rotate_right(t)
                .bitxor(u);
            a = a.wrapping_sub(self.keys[2 * i]).rotate_right(u).bitxor(t);
        }
        d = d.wrapping_sub(self.keys[1]);
        b = b.wrapping_sub(self.keys[0]);

        [a, b, c, d]
    }
}

#[cfg(test)]
mod test {
    use crate::block::BlockEndec;

    use super::RC6;

    #[test]
    fn cycle() {
        let main_key = "secret";
        let rc6 = RC6::new(main_key.as_bytes());
        let block = [10, 100, 1000, 100000];
        let encoded = rc6.encode_block(block);
        let decoded = rc6.decode_block(encoded);
        assert_eq!(block, decoded);
    }

    #[test]
    fn repeatability() {
        let main_key = "secret";
        let rc6 = RC6::new(main_key.as_bytes());
        let block = [10, 100, 1000, 100000];
        assert_eq!(rc6.encode_block(block), rc6.encode_block(block));
    }
}
