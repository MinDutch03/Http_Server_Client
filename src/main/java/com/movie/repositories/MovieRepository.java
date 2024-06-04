package com.movie.repositories;

import com.movie.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
