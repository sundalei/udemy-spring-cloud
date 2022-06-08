## netflix naming server
```
./mvnw -pl netflix-eureka-naming-server clean

./mvnw -pl netflix-eureka-naming-server install

java -jar netflix-eureka-naming-server/target/netflix-eureka-naming-server-0.0.1-SNAPSHOT.jar
```
## config server
```
./mvnw -pl spring-cloud-config-server clean

./mvnw -pl spring-cloud-config-server install

java -jar spring-cloud-config-server/target/spring-cloud-config-server-0.0.1-SNAPSHOT.jar
```
## spring bus endpoints
```
POST http://localhost:8080/actuator/bus-refresh

application.yml

management:
  endpoint:
    health:
      show-details: always
      bus-refresh:
        enabled: true
  endpoints:
    web:
      exposure:
        include: '*'
```