# About
A example Spring Boot application to add/edit/delete Person records and their related Address records.

Data is persisted via JPA to an in-memory or file based H2 RDBMS (see production profile below). Thymeleaf is used as the templating framework. Webjars are used for local Bootstrap js/css files.

### Installation 
```
git clone https://github.com/TC201700/SB20211207.git
cd ./SB20211207
```

### Run Application
If you use Maven, you can run the application by using ```./mvnw spring-boot:run```. Alternatively, you can build the JAR file with ```./mvnw clean package``` and then run the JAR file, as follows:
```
java -jar target/demo-0.0.1-SNAPSHOT.jar
```
and point your browser to http://localhost:8080/

### Persisting Data
The application uses an in-memory database which is destroyed when the application is stopped. To use a filesystem based version, start the application using the "production" profile, e.g.
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=production
```
or
```
java -Dspring.profiles.active=production -jar target/demo-0.0.1-SNAPSHOT.jar
```

### REST backend

When the application is running the REST backend is available at http://localhost:8080/api
