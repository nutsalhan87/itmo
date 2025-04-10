use std::{
    error::Error, fs::File, io::{stdin, Read, Write}, num::NonZeroU128, path::PathBuf
};

use lfsr::LFSR;

mod lfsr;

struct Var {
    pub l1: LFSR<77, 6, 5, 2>,
    pub l2: LFSR<53, 6, 2, 1>,
}

impl Var {
    const F: usize = 3;

    pub fn new() -> Self {
        let l1 =
            LFSR::<77, 6, 5, 2>::new(NonZeroU128::new(0xABBAABBAABBAABBAABBAABBAABBAABBA).unwrap());
        let l2 =
            LFSR::<53, 6, 2, 1>::new(NonZeroU128::new(0xDEADDEADDEADDEADDEADDEADDEADDEAD).unwrap());
        Self { l1, l2 }
    }

    pub fn next_gamma(&mut self) -> u8 {
        let mut gamma = 0;
        for _ in 0..(size_of::<u8>() * 8) {
            self.l1.tact();
            for _ in 0..Self::F {
                self.l2.tact();
            }
            gamma = (gamma << 1)
                | ((((self.l1.get_state() >> 77) & (self.l2.get_state() >> 52))
                    ^ ((self.l1.get_state() >> 3) & (self.l2.get_state() >> 41)))
                    as u8
                    & 0b1);
        }
        gamma.reverse_bits()
    }
}

pub fn stream(output_file: PathBuf) -> Result<(), Box<dyn Error>> {
    let mut var = Var::new();
    let mut stdin = stdin();
    let mut output_file = File::create(output_file)?;
    let mut buf = [0u8; 1];
    while let Ok(()) = stdin.read_exact(&mut buf) {
        for sym in &buf[..] {
            let gamma = var.next_gamma();
            output_file.write_all(&[*sym ^ gamma]).unwrap();
        }
        output_file.flush().unwrap();
    }

    Ok(())
}
