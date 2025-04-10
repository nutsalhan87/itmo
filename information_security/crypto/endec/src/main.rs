mod block;
mod classic;
mod stream;
mod util;

use std::{
    error::Error,
    fs::{self, File},
    path::PathBuf,
};

use clap::{Parser, Subcommand};

#[derive(Parser)]
#[command(version, about, long_about = None)]
#[command(propagate_version = true)]
struct Cli {
    #[command(subcommand)]
    command: Commands,
}

#[derive(Subcommand)]
enum Commands {
    /// Encode and decode file with classic methods
    Classic {
        #[arg(long, short = 'f', value_name = "INPUT_FILE", help = "Input file", value_hint = clap::ValueHint::FilePath, value_parser = file_exists)]
        file: PathBuf,
        #[arg(
            long,
            short = 'k',
            value_name = "SECRET_KEY",
            help = "Secret key used to encode/decode text"
        )]
        key: String,
        #[arg(
            value_enum,
            long,
            short = 'm',
            value_name = "METHOD",
            help = "Encode/decode method"
        )]
        method: classic::Method,
        #[arg(
            long,
            short = 'd',
            help = "Whether input must be decoded instead of encoded"
        )]
        decode: bool,
    },
    /// Encode and decode file with block symmetric cypher algorithms
    Block {
        #[arg(long, short = 'i', value_name = "INPUT_FILE", help = "Input file", value_hint = clap::ValueHint::FilePath, value_parser = file_exists)]
        input_file: PathBuf,
        #[arg(long, short = 'o', value_name = "OUTPUT_FILE", help = "Output file", value_hint = clap::ValueHint::FilePath, value_parser = can_write)]
        output_file: PathBuf,
        #[arg(
            long,
            short = 'k',
            value_name = "SECRET_KEY",
            help = "Secret key used to encode/decode text"
        )]
        key: String,
        #[arg(
            value_enum,
            long,
            short = 'a',
            value_name = "ALGORITHM",
            help = "Algorithm of block cypher"
        )]
        algorithm: block::Algorithm,
        #[arg(
            value_enum,
            long,
            short = 'm',
            value_name = "MODE",
            help = "Block cypher mode of operation"
        )]
        mode: block::Mode,
        #[arg(
            long,
            short = 'd',
            help = "Whether input must be decoded instead of encoded"
        )]
        decode: bool,
    },
    /// Encode input srteam with stream cypher algorithms
    Stream {
        #[arg(long, short = 'o', value_name = "OUTPUT_FILE", help = "Output file", value_hint = clap::ValueHint::FilePath, value_parser = can_write)]
        output_file: PathBuf,
    },
}

fn file_exists(s: &str) -> Result<PathBuf, String> {
    let path = PathBuf::from(s);
    if !path.exists() {
        Err("Input file does not exist.".to_string())
    } else if path.is_dir() {
        Err("Input path points to directory, not file.".to_string())
    } else {
        Ok(path)
    }
}

fn can_write(s: &str) -> Result<PathBuf, String> {
    File::create(s)
        .map(|_| PathBuf::from(s))
        .map_err(|err| format!("Error with {}: {}", s, err.to_string()))
}

fn main() -> Result<(), Box<dyn Error>> {
    let cli = Cli::parse();
    match cli.command {
        Commands::Classic {
            file,
            key,
            method,
            decode,
        } => {
            let input = fs::read_to_string(file)?;

            let code = if decode {
                method.decode(&input, &key)
            } else {
                method.encode(&input, &key)
            };

            println!("{code}");
        }
        Commands::Block {
            input_file: file,
            output_file,
            algorithm,
            mode,
            key,
            decode,
        } => {
            let input = fs::read(file)?;

            let code = if decode {
                mode.decode(&input, &key, algorithm)
            } else {
                mode.encode(&input, &key, algorithm)
            };

            fs::write(output_file, code)?;
        }
        Commands::Stream { output_file } => stream::stream(output_file)?,
    }

    Ok(())
}
