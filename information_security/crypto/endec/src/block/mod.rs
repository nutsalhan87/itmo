use algorithm::rc6::RC6;
use clap::ValueEnum;

use crate::util::{create_initialization_vector, stable_hash};

mod algorithm;
mod mode;

pub type Block = [u32; 4];

trait BlockEndec {
    fn encode_block(&self, block: Block) -> Block;

    fn decode_block(&self, block: Block) -> Block;
}

#[derive(Clone, Copy, ValueEnum)]
pub enum Algorithm {
    RC6,
}

impl Algorithm {
    fn build_block_endec(&self, secret_key: &str) -> Box<dyn BlockEndec> {
        match self {
            Algorithm::RC6 => Box::new(RC6::new(secret_key.as_bytes())),
        }
    }
}

#[derive(Clone, Copy, ValueEnum)]
pub enum Mode {
    OFB,
}

impl Mode {
    pub fn encode(
        &self,
        input: &[u8],
        secret_key: &str,
        algorithm: Algorithm,
    ) -> Box<[u8]> {
        let iv = create_initialization_vector(stable_hash(secret_key));
        let block_endec = algorithm.build_block_endec(secret_key);
        match self {
            Mode::OFB => mode::ofb::encode(input, block_endec.as_ref(), iv),
        }
    }

    pub fn decode(
        &self,
        input: &[u8],
        secret_key: &str,
        algorithm: Algorithm,
    ) -> Box<[u8]> {
        let iv = create_initialization_vector(stable_hash(secret_key));
        let block_endec = algorithm.build_block_endec(secret_key);
        match self {
            Mode::OFB => mode::ofb::encode(input, block_endec.as_ref(), iv),
        }
    }
}
