package com.example.movie.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.MovieImageDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;

public interface MovieService {

    // 영화 목록
    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // 영화 등록
    Long register(MovieDTO movieDTO);

    // 영화 수정
    Long modify(MovieDTO movieDTO);

    // 영화 삭제
    void delete(Long mno);

    // 상세조회
    MovieDTO getMovieDetail(Long mno);

    default MovieDTO entityToDto(Movie movie, List<MovieImage> movieImages, Long reviewCnt, Double reviewAvg) {

        MovieDTO dto = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .reviewAvg(reviewAvg != null ? reviewAvg : 0.0d)
                .reviewCnt(reviewCnt)
                .regDate(movie.getRegDate())
                .build();

        List<MovieImageDTO> movieImageDTOs = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .build();
        }).collect(Collectors.toList());

        dto.setMovieImageDTOs(movieImageDTOs);

        return dto;
    }

    default Map<String, Object> dtoToEntity(MovieDTO movieDTO) {
        return null;
    }

}
