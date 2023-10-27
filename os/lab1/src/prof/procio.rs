use std::{
    fs::{read_dir, read_to_string},
    sync::mpsc::Sender,
    thread,
    time::{Duration, Instant},
};

use super::{Event, Prof};

pub struct ProcIo {}

impl Prof for ProcIo {
    fn profiler(&self, freq_millis: u32, pid: u32, sender: Sender<Box<dyn Event>>) {
        let mut read_bytes_prev = 0;
        let mut write_bytes_prev = 0;
        let start_time = Instant::now();
        loop {
            thread::sleep(Duration::from_millis(freq_millis.into()));
            let timestamp = Instant::now().duration_since(start_time);
            let (mut read_bytes, mut write_bytes) = rwbytes(pid);
            read_bytes -= read_bytes_prev;
            read_bytes_prev += read_bytes;
            write_bytes -= write_bytes_prev;
            write_bytes_prev += write_bytes;
            sender
                .send(Box::new(IoStat {
                    timestamp,
                    value: read_bytes,
                    operation: IoOperation::Read,
                }))
                .unwrap();
            sender
                .send(Box::new(IoStat {
                    timestamp,
                    value: write_bytes,
                    operation: IoOperation::Write,
                }))
                .unwrap();
        }
    }
}

fn rwbytes(pid: u32) -> (u64, u64) {
    let io = read_to_string(format!("/proc/{pid}/io"));
    if let Err(_) = io {
        return (0, 0);
    }
    let io = io.unwrap();
    let mut lines = io.lines().skip(4);
    let read_bytes: u64 = lines
        .next()
        .unwrap()
        .split_whitespace()
        .nth(1)
        .unwrap()
        .parse()
        .unwrap();
    let write_bytes: u64 = lines
        .next()
        .unwrap()
        .split_whitespace()
        .nth(1)
        .unwrap()
        .parse()
        .unwrap();
    let task = read_dir(format!("/proc/{pid}/task"));
    if let Err(_) = task {
        return (read_bytes, write_bytes);
    }
    let task = task.unwrap();
    task.filter_map(|v| {
        let path = v.unwrap().path();
        let tid = path.to_str().unwrap();
        let children = read_to_string(format!("{tid}/children"));
        if let Err(_) = children {
            return None;
        }
        Some(
            children
                .unwrap()
                .split_whitespace()
                .map(|v| rwbytes(v.parse::<u32>().unwrap()))
                .fold((0, 0), |(acc1, acc2), (v1, v2)| (acc1 + v1, acc2 + v2)),
        )
    })
    .fold((read_bytes, write_bytes), |(acc1, acc2), (v1, v2)| {
        (acc1 + v1, acc2 + v2)
    })
}

enum IoOperation {
    Read,
    Write,
}

struct IoStat {
    timestamp: Duration,
    value: u64,
    operation: IoOperation,
}

impl Event for IoStat {
    fn timestamp_millis(&self) -> u32 {
        self.timestamp.as_millis() as u32
    }

    fn description(&self) -> String {
        match self.operation {
            IoOperation::Read => "io read".to_string(),
            IoOperation::Write => "io write".to_string(),
        }
    }

    fn value(&self) -> Option<u64> {
        Some(self.value)
    }

    fn unit(&self) -> &str {
        "bytes"
    }
}
