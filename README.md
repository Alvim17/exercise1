# Introduction #

The solution for the exercise1 consist of a spark application running in Scala.

# Pre-Requisites #

To compile and run locally, your machine must have installed:

- Java 1.8 or latter
- SBT 1.4.7 or latter (https://www.scala-sbt.org/1.x/docs/Setup.html)

# Running the Application #

After installing the pre-requisites and cloning this repository locally, open a terminal on the root of the project and run SBT, typing:

```shell
sbt
```

To run the program, simply type:
```shell
run
```

# Program Flow #

First, the program will read the .csv file input, located at:

```shell
./data/exercise1/input
```

After processing, the output will be saved in .csv and .parquet files.

.csv file output:
```shell
./data/exercise1/output/csv
```

.parquet file output:
```shell
./data/exercise1/output/parquet
```
