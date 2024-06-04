package com.movie.service;

import com.movie.dto.MovieDto;
import com.movie.dto.MoviePageResponse;
import com.movie.entities.Movie;
import com.movie.exceptions.MovieNotFoundException;
import com.movie.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }


    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // upload the file
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))){
            throw new FileNotFoundException("File already exists! Please input another file name.");
        };
        String uploadedFileName = fileService.uploadFile(path, file);

        // set the value of field 'poster' as fileName
        movieDto.setPoster(uploadedFileName);

        // Map dto to Movie object
        Movie movie = new Movie(
                null, // prevent update query from insert query if MovieId matches with any of the record in DB
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );

        // save the movie object -> saved Movie object
        Movie savedMovie = movieRepository.save(movie);

        // generate the posterUrl
        String posterUrl = baseUrl + "/file/" + uploadedFileName;

        // map Movie object to DTO object and return it
        MovieDto response = new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );


        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        // check data in DB and if exists, fetch the data
        Movie movie = movieRepository.findById(movieId).orElseThrow(() ->
                new MovieNotFoundException("Movie not found with id = " + movieId));


        // generate posterUrl;
        String posterUrl = baseUrl + "/file/" + movie.getPoster();

        // map to MovieDto object and return it
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );


        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        // fetch all data from DB
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = new ArrayList<>();

        // iterate through the list, generate posterUrl for each movie obj
        // map to MovieDto obj
        for(Movie movie: movies){
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }

        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        // check if the movie obj exists with given Id
        Movie mv = movieRepository.findById(movieId).orElseThrow(() ->
                new RuntimeException("Movie not found with id = " + movieId));

        // if file is null, skip; otherwise, delete existing file and upload the new one
        String fileName = mv.getPoster();
        if (file != null){
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path, file);
        }

        // set movieDto's poster value
        movieDto.setPoster(fileName);

        // save movie obj -> return saved movie obj
        Movie movie = new Movie(
                null, // prevent update query from insert query if MovieId matches with any of the record in DB
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );

        // generate posterUrl for it
        Movie updatedMovie = movieRepository.save(movie);
        String posterUrl = baseUrl + "/file/" + movie.getPoster();

        // map to MovieDto and return it
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );

        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        // check if movie obj exists in DB
        Movie mv = movieRepository.findById(movieId).orElseThrow(() ->
                new MovieNotFoundException("Movie not found with id = " + movieId));
        Integer id = mv.getMovieId();

        // delete the file linked with this project
        Files.deleteIfExists(Paths.get(path + File.separator + mv.getPoster()));

        // delete the movie object
        movieRepository.delete(mv);


        return "Movie deleted with id: " + id;
    }

}
