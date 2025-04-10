use std::num::NonZeroU128;

pub struct LFSR<const N: usize, const A: usize, const B: usize, const C: usize> {
    register: u128,
}

impl<const N: usize, const A: usize, const B: usize, const C: usize> LFSR<N, A, B, C> {
    pub fn new(start_state: NonZeroU128) -> Self {
        const {
            assert!(128 > N, "Tap n must be less than 128");
            assert!(N > A, "Tap n must be greater than tap A");
            assert!(A > B, "Tap A must be greater than tap B");
            assert!(B > C, "Tap B must be greater than tap C");
            assert!(C > 0, "Tap C must be greater than 0");
        }
        Self {
            register: start_state.into(),
        }
    }

    pub fn tact(&mut self) -> bool {
        let bit = ((self.register >> N)
            ^ (self.register >> A)
            ^ (self.register >> B)
            ^ (self.register >> C)
            ^ self.register)
            & 0b1;
        self.register = (self.register >> 1) | (bit << N);

        bit == 0b1
    }

    pub fn get_state(&self) -> u128 {
        self.register
    }
}
