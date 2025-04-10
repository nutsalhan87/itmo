use crate::{
    block::{Block, BlockEndec},
    util::{blockify, to_u32_extended, ExtendPolicy},
};

pub fn encode(input: &[u8], block_endec: &dyn BlockEndec, iv: Block) -> Box<[u8]> {
    let blocks_input = blockify(
        &to_u32_extended(input, ExtendPolicy::Fill(0)),
        ExtendPolicy::Fill(0),
    );
    let mut endec_input = iv;
    blocks_input
        .iter()
        .map(|block_input| {
            let endec_output = block_endec.encode_block(endec_input);
            endec_input = endec_output;
            [
                block_input[0] ^ endec_output[0],
                block_input[1] ^ endec_output[1],
                block_input[2] ^ endec_output[2],
                block_input[3] ^ endec_output[3],
            ]
        })
        .flatten()
        .map(|v| v.to_le_bytes())
        .flatten()
        .take(input.len())
        .collect::<Box<_>>()
}

#[cfg(test)]
mod test {
    use crate::{
        block::{algorithm::rc6::RC6, Block, BlockEndec}, 
        util::{create_initialization_vector, stable_hash},
    };

    use super::encode;

    #[test]
    fn cycle() {
        let input = b"Lorem Ipsum Dolor Sit Amet.";
        let main_key = "secret";
        let iv = create_initialization_vector(stable_hash(main_key));
        let rc6 = RC6::new(main_key.as_bytes());
        let cyphered = encode(input, &rc6, iv);
        let decyphered = encode(&cyphered, &rc6, iv);
        assert_eq!(input, decyphered.as_ref());
    }

    #[test]
    fn ass() {
        let text: Block = [1, 10, 100, 1000];
        let main_key = "secret";
        let iv = create_initialization_vector(stable_hash(main_key));
        let rc6 = RC6::new(main_key.as_bytes());
        
        let iv_encoded = rc6.encode_block(iv);
        let cyphered = [
            iv_encoded[0] ^ text[0],
            iv_encoded[1] ^ text[1],
            iv_encoded[2] ^ text[2],
            iv_encoded[3] ^ text[3],
        ];
        let decyphered = [    
            iv_encoded[0] ^ cyphered[0],
            iv_encoded[1] ^ cyphered[1],
            iv_encoded[2] ^ cyphered[2],
            iv_encoded[3] ^ cyphered[3],
        ];
        assert_eq!(text, decyphered);
    }
}
