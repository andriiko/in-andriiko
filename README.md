
## Summary

REST API server, providing information about products.

## Requirements

As the server is implemented using akka.http and Scala, the prerequisites are as follows:
* Java 8 JDK
* Scala Build Tool (SBT).

## Usage

The simplest way is to launch the server from the command line using SBT:
1. Open your terminal and go to 'ElectronicsAPI' folder'.
2. Compile solution: `sbt compile`.
3. Launch the REST API server: `sbt run`.

You should see: 
```bash
Server online at http://localhost:8080/
Press RETURN to stop...
```


## Sample requests


1 - Parse the products which are more expensive than $39.

```bash
time curl http://localhost:8080/electronics/products?priceGreaterThan=39
{"products":[...]}
real	0m0.055s
user	0m0.011s
sys	0m0.008s
```

2 - Parse the products which have the word “player” in their title.

```bash
in-excercise-andriiko$ time curl http://localhost:8080/electronics/products?titleKeyword=surveillance
{"products":[...]}
real	0m0.099s
user	0m0.011s
sys	0m0.013s
```

3 - Parse the first 10 products with Highest EAN number (Primary) and Lowest Price (Secondary).

```bash
in-excercise-andriiko$ time curl http://localhost:8080/electronics/products?desc=EAN&asc=ListPrice&primaryOrder=desc&limit=10
{"products":[...]}
real	0m0.164s
user	0m0.011s
sys	0m0.012s
```