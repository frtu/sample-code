# Project - activities-rust

## About

Create activities for rust.

## Instalation

### Cargo command

* Install [Rust & Cargo](https://www.rust-lang.org/tools/install) OR using `inst_rust` from [bask-fwk](https://github.com/frtu/bash-fwk/blob/master/libs/DevMisc.md#library-dev-rust)
* Install proto using `inst_protobuf`

### Install project

#### Run the Worker

```bash
cargo run --bin worker
```

### Trigger workflow

#### Create a starter project using Actix Web server

```bash
cargo run --bin main
```
It will start web-server on localhost:800.

#### Execute workflow

Send post request using curl. 

```bash
curl --location --request POST 'localhost:8000/transfer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "source_account": "11-11",
    "target_account": "22-22",
    "amount": 100
}'
```

## About

## Release notes

### 0.0.1-SNAPSHOT - Current version

* Initial version
