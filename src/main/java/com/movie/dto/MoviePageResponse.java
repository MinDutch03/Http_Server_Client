package com.movie.dto;

import java.util.List;

public record MoviePageResponse(List<MovieDto> movieDtos, Integer pageNumber, int totalElements, int totalPages,
                                boolean isLast) {

}
