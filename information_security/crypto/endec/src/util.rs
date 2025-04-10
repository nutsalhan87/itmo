use std::hash::Hash;

use rustc_stable_hash::{FromStableHash, SipHasher128Hash, StableSipHasher128};

use crate::block::Block;

pub enum ExtendPolicy {
    #[allow(dead_code)]
    Random,
    Fill(usize),
}

pub fn to_u32_extended(bytes: &[u8], extend_policy: ExtendPolicy) -> Box<[u32]> {
    bytes
        .chunks(4)
        .map(|v| {
            let word: [u8; 4] = match TryInto::<[u8; 4]>::try_into(v) {
                Ok(v) => v,
                Err(_) => {
                    let mut word = [0u8; 4];
                    for (i, byte) in v.iter().enumerate() {
                        word[i] = *byte;
                    }
                    for i in v.len()..4 {
                        match extend_policy {
                            ExtendPolicy::Random => rand::random(),
                            ExtendPolicy::Fill(v) => word[i] = v as u8,
                        }
                    }
                    word
                }
            };
            u32::from_le_bytes(word)
        })
        .collect()
}

pub fn blockify(words: &[u32], extend_policy: ExtendPolicy) -> Box<[Block]> {
    words
        .chunks(4)
        .map(|v| {
            match TryInto::<Block>::try_into(v) {
                Ok(v) => v,
                Err(_) => {
                    let mut block = [0u32; 4];
                    for (i, word) in v.iter().enumerate() {
                        block[i] = *word;
                    }
                    for i in v.len()..4 {
                        match extend_policy {
                            ExtendPolicy::Random => rand::random(),
                            ExtendPolicy::Fill(v) => block[i] = v as u32,
                        }
                    }
                    block
                }
            }
        })
        .collect()
}

struct Hash128(u128);
impl FromStableHash for Hash128 {
    type Hash = SipHasher128Hash;

    fn from(SipHasher128Hash(hash): SipHasher128Hash) -> Hash128 {
        let hash = ((hash[1] as u128) << 64) + hash[0] as u128;
        Hash128(hash)
    }
}

pub fn stable_hash(hash: impl Hash) -> u128 {
    let mut hasher = StableSipHasher128::new();
    hash.hash(&mut hasher);
    let Hash128(hash) = StableSipHasher128::finish::<Hash128>(hasher);
    hash
}

pub fn create_initialization_vector(hash: u128) -> Block {
    let iv = stable_hash(hash);
    [
        iv as u32,
        (iv << 32) as u32,
        (iv << 64) as u32,
        (iv << 96) as u32,
    ]
}