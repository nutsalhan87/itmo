use std::cell::LazyCell;

use rand::{distributions::Uniform, prelude::Distribution, thread_rng};

use crate::util::stable_hash;

const ALPHABET: LazyCell<Box<[char]>> = LazyCell::new(|| {
    ('a'..='z')
        .chain('A'..='Z')
        .chain('а'..='я')
        .chain('А'..='Я')
        .chain(".,-+_—%;:?!@#&()*~\"' \n".chars())
        .collect()
});
const THREE_LOWER_BITS_MASK: u16 = 0x0007;

fn random_symbol() -> char {
    const BETWEEN: LazyCell<Uniform<usize>> = LazyCell::new(|| Uniform::new(0, ALPHABET.len()));
    let mut rng = thread_rng();
    ALPHABET[BETWEEN.sample(&mut rng)]
}

enum Quarter {
    None,
    First,
    Second,
    Third,
    Fourth,
}

impl Quarter {
    const fn new(value: u16) -> Self {
        match value & THREE_LOWER_BITS_MASK {
            0b000..0b100 => Quarter::None,
            0b100 => Quarter::First,
            0b101 => Quarter::Second,
            0b110 => Quarter::Third,
            0b111 => Quarter::Fourth,
            _ => panic!("Quarter must be in [0; 7]"),
        }
    }
}

struct Grille {
    top_left: Quarter,
    top_right: Quarter,
    bottom_left: Quarter,
    bottom_right: Quarter,
}

impl Grille {
    fn new(secret_key: &str) -> Self {
        let hash = stable_hash(secret_key);
        let hash = ((hash % ((1u128 << 12) + 1)) - 1) as u16;

        Grille {
            top_left: Quarter::new(hash),
            top_right: Quarter::new(hash >> 3),
            bottom_left: Quarter::new(hash >> 6),
            bottom_right: Quarter::new(hash >> 9),
        }
    }

    fn cypher_block(&self, chars: &mut impl Iterator<Item = char>) -> String {
        let mut block = [None::<char>; 16];

        match self.top_left {
            Quarter::First => block[0] = chars.next(),   // 1 - - 2
            Quarter::Second => block[3] = chars.next(),  // - - - -
            Quarter::Third => block[15] = chars.next(),  // - - - -
            Quarter::Fourth => block[12] = chars.next(), // 4 - - 3
            _ => (),
        }
        match self.top_right {
            Quarter::First => block[1] = chars.next(),  // - 1 - -
            Quarter::Second => block[7] = chars.next(), // - - - 2
            Quarter::Third => block[14] = chars.next(), // 4 - - -
            Quarter::Fourth => block[8] = chars.next(), // - - 3 -
            _ => (),
        }
        match self.bottom_left {
            Quarter::First => block[4] = chars.next(),   // - - 2 -
            Quarter::Second => block[2] = chars.next(),  // 1 - - -
            Quarter::Third => block[11] = chars.next(),  // - - - 3
            Quarter::Fourth => block[13] = chars.next(), // - 4 - -
            _ => (),
        }
        match self.bottom_right {
            Quarter::First => block[5] = chars.next(),  // - - - -
            Quarter::Second => block[6] = chars.next(), // - 1 2 -
            Quarter::Third => block[10] = chars.next(), // - 4 3 -
            Quarter::Fourth => block[9] = chars.next(), // - - - -
            _ => (),
        }

        block
            .into_iter()
            .map(|v| v.unwrap_or_else(random_symbol))
            .collect()
    }

    fn decypher_block(&self, block: &[char; 16]) -> String {
        let mut part = [None::<char>; 4];

        part[0] = match self.top_left {
            Quarter::First => Some(block[0]),   // 1 - - 2
            Quarter::Second => Some(block[3]),  // - - - -
            Quarter::Third => Some(block[15]),  // - - - -
            Quarter::Fourth => Some(block[12]), // 4 - - 3
            _ => None,
        };
        part[1] = match self.top_right {
            Quarter::First => Some(block[1]),  // - 1 - -
            Quarter::Second => Some(block[7]), // - - - 2
            Quarter::Third => Some(block[14]), // 4 - - -
            Quarter::Fourth => Some(block[8]), // - - 3 -
            _ => None,
        };
        part[2] = match self.bottom_left {
            Quarter::First => Some(block[4]),   // - - 2 -
            Quarter::Second => Some(block[2]),  // 1 - - -
            Quarter::Third => Some(block[11]),  // - - - 3
            Quarter::Fourth => Some(block[13]), // - 4 - -
            _ => None,
        };
        part[3] = match self.bottom_right {
            Quarter::First => Some(block[5]),  // - - - -
            Quarter::Second => Some(block[6]), // - 1 2 -
            Quarter::Third => Some(block[10]), // - 4 3 -
            Quarter::Fourth => Some(block[9]), // - - - -
            _ => None,
        };

        part.into_iter().filter_map(|v| v).collect()
    }
}

pub fn encode(input: &str, secret_key: &str) -> String {
    let grille = Grille::new(secret_key);
    let mut buffer = String::new();
    let mut chars = input.chars().peekable();
    
    while chars.peek().is_some() {
        let block = grille.cypher_block(&mut chars);
        buffer.push_str(&block);
    }

    buffer
}

pub fn decode(input: &str, secret_key: &str) -> String {    
    let grille = Grille::new(secret_key);
    let mut buffer = String::new();
    let chars: Box<[char]> = input.chars().collect();
    
    let chunks = chars.chunks_exact(16);
    let remainder = chunks.remainder();
    for block in chunks {
        let block: &[char; 16] = block.try_into().unwrap();
        let decyphered = grille.decypher_block(block);
        buffer.push_str(&decyphered);
    }
    let mut block = Vec::with_capacity(16);
    block.extend_from_slice(remainder);
    block.resize_with(16, random_symbol);
    let decyphered = grille.decypher_block(&block.try_into().unwrap());
    buffer.push_str(&decyphered);

    buffer
}

#[cfg(test)]
mod test {
    use crate::classic::cardan::{random_symbol, ALPHABET};

    use super::{decode, encode};

    #[test]
    fn alphabet() {
        assert_eq!("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZабвгдежзийклмнопрстуфхцчшщъыьэюяАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ.,-+_—%;:?!@#&()*~\"' \n", ALPHABET.iter().collect::<String>());
    }

    #[test]
    fn random_str_t() {
        const LEN: usize = 20;
        let random_str: Box<[char]> = (0..LEN).into_iter().map(|_| random_symbol()).collect();
        assert_eq!(LEN, random_str.len());
        assert!(random_str.iter().all(|c| ALPHABET.contains(c)));
    }

    #[test]
    fn encode_decode() {
        let message = "LOrEM! - ИПСуМ ?% doLor_+ сиТ";
        let secret_key = "KEY";
        let encoded = encode(message, secret_key);
        let decoded = decode(&encoded, secret_key);
        assert!(decoded.starts_with(&message));
    }
}
