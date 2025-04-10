use clap::ValueEnum;

mod cardan;

#[derive(Clone, Copy, ValueEnum)]
pub enum Method {
    /// Cardan grille 4x4
    Cardan,
}

impl Method {
    pub fn encode(&self, input: &str, secret_key: &str) -> String {
        match self {
            Method::Cardan => cardan::encode(input, secret_key),
        }
    }

    pub fn decode(&self, input: &str, secret_key: &str) -> String {
        match self {
            Method::Cardan => cardan::decode(input, secret_key),
        }
    }
}