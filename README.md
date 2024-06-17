# HTTP(Server-Client)
 
- Firstly, create database named 'movies' in MySQL. <br />
Access _/src/main/resources/application.yml_ and input your password in **_password_**.

## How to run:
- Navigate to this folder: `cd /src/main/java/com/movie`
- Run this file: `MovieApiApplication.java`

## Postman (http://localhost:8080) :bulb:
* Firstly, navigate to Body and select form-data
### 1. **Post** method
- Upload poster: /file/upload; key: file, File; Value: choose img from your system. ;-> Send
- Upload movies: /api/v1/movie/add-movie; key: movieDto, Text; Value: your Json file; -> Send
### 2. **Get** method
- Get the poster: /file/{fileName}
- Download the poster: /file/{fileName}?download=True
- Get all movies: /api/v1/movie/all
- Get a specific movie: /api/v1/movie/{movieId}
### 3. **Put** method
- Update movie: /api/v1/movie/update/{movieId}
### 4. **Delete** method
- Delete movie: /api/v1/movie/delete/{movieId}

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.0/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.0/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/docs/3.3.0/reference/htmlsingle/index.html#io.validation)

## Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)


