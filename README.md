# HTTP(Server-Client)
 
- Firstly, create database named 'movies' in MySQL. <br />
Access _/src/main/resources/application.properties_ and input your password in **_password_**.

- Run this file: **_/src/main/java/com/movie/MovieApiApplication.java_** and check your database.

## Postman (http://localhost:8080) :bulb:
* Firstly, navigate to Body and select form-data
### 1. **Post** method
- Upload poster: /file/upload; key: file, File; Value: choose img from your system. ;-> Send
- Upload movies: /api/v1/movie/add-movie; key: movieDto, Text; Value: your Json file; -> Send
### 2. **Get** method
- Get the poster: /file/{fileName}
- Get all movies: /api/v1/movie/all
- Get a specific movie: /api/v1/movie/{movieId}
### 3. **Put** method
- Update movie: /api/v1/movie/update/{movieId}
### 4. **Delete** method
- Delete movie: /api/v1/movie/delete/{movieId}

