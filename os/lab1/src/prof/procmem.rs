use std::{
    fs::read_to_string,
    sync::mpsc::Sender,
    thread,
    time::{Duration, Instant},
};

use super::{Event, Prof};

pub struct ProcMem {}

impl Prof for ProcMem {
    fn profiler(&self, freq_millis: u32, _pid: u32, sender: Sender<Box<dyn Event>>) {
        let start_time = Instant::now();
        let total = mem_total();
        loop {
            thread::sleep(Duration::from_millis(freq_millis as u64));
            let value = mem_avaliable();
            sender
                .send(Box::new(MemStat {
                    timestamp: Instant::now().duration_since(start_time),
                    value: total - value,
                }))
                .unwrap();
        }
    }
}

fn mem_total() -> u64 {
    let meminfo = read_to_string("/proc/meminfo").unwrap();
    let mut total_line = meminfo.lines().next().unwrap().split_whitespace().skip(1);
    total_line.next().unwrap().parse::<u64>().unwrap()
}

fn mem_avaliable() -> u64 {
    let meminfo = read_to_string("/proc/meminfo").unwrap();
    let mut avaliable_line = meminfo
        .lines()
        .skip(2)
        .next()
        .unwrap()
        .split_whitespace()
        .skip(1);
    avaliable_line.next().unwrap().parse::<u64>().unwrap()
}

struct MemStat {
    timestamp: Duration,
    value: u64,
}

impl Event for MemStat {
    fn timestamp_millis(&self) -> u32 {
        self.timestamp.as_millis() as u32
    }

    fn description(&self) -> String {
        "Memory occupied".to_string()
    }

    fn value(&self) -> Option<u64> {
        Some(self.value)
    }

    fn unit(&self) -> &str {
        "kB"
    }
}
