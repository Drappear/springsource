package com.example.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final MovieImageRepository movieImageRepository;

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieImageRepository.getTotalList(null, null, pageable);

        Function<Object[], MovieDTO> function = (en -> entityToDto((Movie) en[0],
                (List<MovieImage>) Arrays.asList((MovieImage) en[1]),
                (Long) en[2],
                (Double) en[3]));

        return new PageResultDTO<>(result, function);
    }

    @Override
    public Long register(MovieDTO movieDTO) {
        Map<String, Object> entityMap = dtoToEntity(movieDTO);

        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImages = (List<MovieImage>) entityMap.get("movieImages");

        movieRepository.save(movie);
        movieImages.forEach(movieImage -> movieImageRepository.save(movieImage));

        return movie.getMno();
    }

    @Override
    public Long modify(MovieDTO movieDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modify'");
    }

    @Transactional
    @Override
    public void delete(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        movieImageRepository.deleteByMovie(movie);
        reviewRepository.deleteByMovie(movie);
        movieRepository.delete(movie);
    }

    @Override
    public MovieDTO getMovieDetail(Long mno) {
        List<Object[]> result = movieImageRepository.getMovieRow(mno);

        Movie movie = (Movie) result.get(0)[0];
        Long reviewCnt = (Long) result.get(0)[2];
        Double reviewAvg = (Double) result.get(0)[3];

        // 1: 영화 이미지
        List<MovieImage> movieImages = new ArrayList<>();
        result.forEach(row -> {
            MovieImage movieImage = (MovieImage) row[1];
            movieImages.add(movieImage);
        });

        return entityToDto(movie, movieImages, reviewCnt, reviewAvg);
    }
}
