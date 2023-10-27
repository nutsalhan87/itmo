mod perf;
mod proccpu;
mod procio;
mod procmaps;
mod procmem;
mod procnet;

pub use perf::PerfStat;
pub use proccpu::ProcCpu;
pub use procio::ProcIo;
pub use procmaps::ProcMaps;
pub use procmem::ProcMem;
pub use procnet::ProcNet;

use std::sync::mpsc::Sender;

pub trait Event: Send {
    fn timestamp_millis(&self) -> u32;
    fn description(&self) -> String;
    fn value(&self) -> Option<u64>;
    fn unit(&self) -> &str;
}

pub trait Prof: Sync + Send {
    fn profiler(&self, freq_millis: u32, pid: u32, sender: Sender<Box<dyn Event>>);
}

pub fn profs(events: &str) -> Vec<Box<dyn Prof>> {
    let mut p: Vec<Box<dyn Prof>> = Vec::new();
    for event in events.split(',') {
        match event {
            "maps" => p.push(Box::new(ProcMaps {})),
            "mem" => p.push(Box::new(ProcMem {})),
            "io" => p.push(Box::new(ProcIo {})),
            "cpu" => p.push(Box::new(ProcCpu {})),
            "net" => p.push(Box::new(ProcNet {})),
            _ => (),
        }
    }
    p
}
