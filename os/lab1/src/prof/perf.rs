use std::{
    fs::File,
    io::{BufRead, BufReader},
};

use serde::{Deserialize, Deserializer};

use super::Event;

fn perf_value<'de, D>(de: D) -> Result<Option<u64>, D::Error>
where
    D: Deserializer<'de>,
{
    let s: &str = serde::de::Deserialize::deserialize(de).unwrap();
    match s.parse::<f32>() {
        Ok(value) => Ok(Some(value as u64)),
        Err(_) => Ok(None),
    }
}

#[derive(Debug, Deserialize)]
pub struct PerfStat {
    interval: f32,
    #[serde(rename(deserialize = "counter-value"), deserialize_with = "perf_value")]
    counter_value: Option<u64>,
    unit: String,
    event: String,
}

impl PerfStat {
    pub fn new(pipe: File) -> Vec<Box<dyn Event>> {
        let pipe_reader = BufReader::new(pipe);
        let pipe_lines = pipe_reader.lines();
        let mut stats = Vec::new();
        for line in pipe_lines.map(|v| v.unwrap()).skip(2) {
            stats.push(
                Box::new(serde_json::de::from_str::<PerfStat>(&line).unwrap()) as Box<dyn Event>,
            );
        }
        stats
    }
}

impl Event for PerfStat {
    fn timestamp_millis(&self) -> u32 {
        (self.interval * 1000f32) as u32
    }

    fn description(&self) -> String {
        self.event.clone()
    }

    fn value(&self) -> Option<u64> {
        self.counter_value
    }

    fn unit(&self) -> &str {
        &self.unit
    }
}
