use std::{
    collections::HashMap,
    fs::{read_dir, read_to_string},
    sync::mpsc::Sender,
    thread,
    time::{Duration, Instant},
};

use super::{Event, Prof};

pub struct ProcNet {}

impl Prof for ProcNet {
    fn profiler(&self, freq_millis: u32, pid: u32, sender: Sender<Box<dyn Event>>) {
        let start_time = Instant::now();
        let mut net_devs_stat_prev = HashMap::new();
        net(pid, &mut net_devs_stat_prev);
        loop {
            thread::sleep(Duration::from_millis(freq_millis as u64));
            let mut net_devs_stat = HashMap::new();
            net(pid, &mut net_devs_stat);
            let timestamp = Instant::now().duration_since(start_time);
            for (dev, (recieve, transmit)) in net_devs_stat {
                let (r_prev, t_prev) = net_devs_stat_prev.get_mut(&dev).unwrap();
                sender
                    .send(Box::new(NetStat {
                        timestamp,
                        value: recieve - *r_prev,
                        dev: dev.clone(),
                        operation: NetOperation::Recieve,
                    }))
                    .unwrap();
                sender
                    .send(Box::new(NetStat {
                        timestamp,
                        value: transmit - *t_prev,
                        dev,
                        operation: NetOperation::Transmit,
                    }))
                    .unwrap();
                *r_prev = recieve;
                *t_prev = transmit;
            }
        }
    }
}

fn net(pid: u32, map: &mut HashMap<String, (u64, u64)>) {
    let dev = read_to_string(format!("/proc/{pid}/net/dev"));
    if let Err(_) = dev {
        return;
    }
    let dev = dev.unwrap();
    let process_net = net_values(&dev);
    for (dev, (recieve, transmit)) in process_net {
        map.entry(dev)
            .and_modify(|(r, t)| {
                *r += recieve;
                *t += transmit;
            })
            .or_insert_with(|| (recieve, transmit));
    }
    let task = read_dir(format!("/proc/{pid}/task"));
    if let Err(_) = task {
        return;
    }
    let task = task.unwrap();
    task.for_each(|v| {
        let path = v.unwrap().path();
        let tid = path.to_str().unwrap();
        let children = read_to_string(format!("{tid}/children"));
        if let Ok(children) = children {
            children
                .split_whitespace()
                .for_each(|v| net(v.parse::<u32>().unwrap(), map))
        }
    })
}

fn net_values(dev: &str) -> Vec<(String, (u64, u64))> {
    let lines = dev.lines().skip(2);
    lines
        .map(|v| {
            let mut words = v.split_whitespace();
            let dev = words.next().unwrap();
            let dev_len = dev.len();
            let dev = dev.chars().take(dev_len - 1).collect();
            let recieve: u64 = words.next().unwrap().parse().unwrap();
            let mut words = words.skip(7);
            let transmit: u64 = words.next().unwrap().parse().unwrap();
            (dev, (recieve, transmit))
        })
        .collect::<Vec<_>>()
}

enum NetOperation {
    Recieve,
    Transmit,
}

struct NetStat {
    timestamp: Duration,
    value: u64,
    dev: String,
    operation: NetOperation,
}

impl Event for NetStat {
    fn timestamp_millis(&self) -> u32 {
        self.timestamp.as_millis() as u32
    }

    fn description(&self) -> String {
        match self.operation {
            NetOperation::Recieve => format!("Net Recieve {}", self.dev),
            NetOperation::Transmit => format!("Net Transmit {}", self.dev),
        }
    }

    fn value(&self) -> Option<u64> {
        Some(self.value >> 10)
    }

    fn unit(&self) -> &str {
        "kB"
    }
}
