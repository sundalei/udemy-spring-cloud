## deploy as docker containers.
### build images
```
./mvnw -pl currency-conversion-service spring-boot:build-image -DskipTests
```
### urls to check
```
http://119.45.123.192:8761/

http://119.45.123.192:8000/currency-exchange/from/USD/to/INR

http://119.45.123.192:8100/currency-converter-feign/from/USD/to/INR/quantity/200
http://119.45.123.192:8100/currency-converter/from/USD/to/INR/quantity/200

http://119.45.123.192:8765/currency-converter/from/USD/to/INR/quantity/200
http://119.45.123.192:8765/currency-converter-feign/from/USD/to/INR/quantity/200
http://119.45.123.192:8765/currency-exchange/from/USD/to/INR

http://http//119.45.123.192:9411
```