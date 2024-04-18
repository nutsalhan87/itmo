use std::{
    collections::HashMap,
    f64::consts::PI,
    fs::File,
    io::Write,
    ops::{Bound, RangeBounds, RangeInclusive},
};

#[derive(Hash, PartialEq, Eq)]
struct F64 {
    internal: u64,
}

impl F64 {
    pub fn new(x: f64) -> Self {
        Self {
            internal: f64::to_bits(x),
        }
    }

    pub fn reconstruct(&self) -> f64 {
        f64::from_bits(self.internal)
    }
}

struct Function {
    pub name: String,
    function: Box<dyn Fn(f64) -> f64 + 'static>,
    discontinuitier: Box<dyn Fn(&RangeInclusive<f64>) -> Vec<f64> + 'static>,
    left_bound: Bound<f64>,
    right_bound: Bound<f64>,
}

impl Function {
    pub fn new<T, S>(
        name: &str,
        function: T,
        discontinuitier: S,
        range_inclusive: impl RangeBounds<f64>,
    ) -> Self
    where
        T: Fn(f64) -> f64 + 'static,
        S: Fn(&RangeInclusive<f64>) -> Vec<f64> + 'static,
    {
        Self {
            name: name.to_string(),
            function: Box::new(function),
            discontinuitier: Box::new(discontinuitier),
            left_bound: range_inclusive.start_bound().cloned(),
            right_bound: range_inclusive.end_bound().cloned(),
        }
    }

    pub fn compute(&self, range_inclusive: RangeInclusive<f64>, step: f64) {
        let filepath = format!("../src/test/resources/{}.csv", self.name);
        let mut csv = File::create(filepath).unwrap();
        let mut xy: HashMap<F64, f64> = HashMap::new();
        let discontinuities = self.discontinuitier.as_ref()(&range_inclusive);

        for discontinuity in &discontinuities {
            xy.insert(F64::new(*discontinuity), f64::NAN);
        }

        let mut left = match self.left_bound {
            Bound::Included(x) | Bound::Excluded(x) if x > *range_inclusive.start() => x,
            _ => *range_inclusive.start(),
        };
        let right = match self.right_bound {
            Bound::Included(x) | Bound::Excluded(x) if x < *range_inclusive.end() => x,
            _ => *range_inclusive.end(),
        };
        while left <= right {
            if discontinuities.contains(&left) {
                continue;
            }
            let value = self.function.as_ref()(left);
            if !f64::is_finite(value) {
                panic!("{}: x = {:.5}, y = {:.5}", self.name, left, value)
            }
            xy.insert(F64::new(left), self.function.as_ref()(left));
            left += step;
        }

        for (x, y) in xy {
            let x = x.reconstruct();
            let line = if f64::is_nan(y) {
                format!("{x},NaN\n")
            } else if f64::is_finite(y) {
                format!("{x},{y}\n")
            } else {
                panic!(
                    "Result must be nan or finite. {}, x = {:.5}, y = {:.5}",
                    self.name, x, y
                )
            };
            csv.write_all(line.as_bytes()).unwrap();
        }
    }
}

fn main() {
    let tg_sec_disc = |range_incl: &RangeInclusive<f64>| {
        let l = (range_incl.start() / PI - 0.5f64).ceil() as i32;
        let r = (range_incl.end() / PI - 0.5f64).floor() as i32;
        (l..=r)
            .into_iter()
            .map(|n| (n as f64 + 0.5f64) * PI)
            .collect::<Vec<_>>()
    };
    let cosec_disc = |range_incl: &RangeInclusive<f64>| {
        let l = (range_incl.start() / PI).ceil() as i32;
        let r = (range_incl.end() / PI).floor() as i32;
        (l..=r)
            .into_iter()
            .map(|n| n as f64 * PI)
            .collect::<Vec<_>>()
    };

    [
        Function::new("pow3", |x| x.powi(3), |_| vec![], ..),
        Function::new("sin", f64::sin, |_| vec![], ..),
        Function::new("cos", f64::cos, |_| vec![], ..),
        Function::new("tg", f64::tan, tg_sec_disc, ..),
        Function::new("sec", |x| 1f64 / x.cos(), tg_sec_disc, ..),
        Function::new("cosec", |x| 1f64 / x.sin(), cosec_disc, ..),
        Function::new("ln", f64::ln, |_| vec![], 0.0001f64..),
        Function::new("log3", |x| x.log(3f64), |_| vec![], 0.0001f64..),
        Function::new("log5", |x| x.log(5f64), |_| vec![], 0.0001f64..),
    ]
    .into_iter()
    .for_each(|f| f.compute(-50f64..=50f64, 0.1));
}

#[cfg(test)]
mod test {
    use std::f64::consts::PI;

    #[test]
    fn t() {
        let ass: f64 = 4.4161896362027164E-13;
        println!("{}", ass.sin())
    }
}