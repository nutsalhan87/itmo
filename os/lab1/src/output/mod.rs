mod plain;

pub use plain::PlainOutput;

use crate::prof::Event;

pub trait Outputter {
    fn output(&mut self, events: &[Box<dyn Event>]);
}
