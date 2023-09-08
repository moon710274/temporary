package com.seb_main_034.SERVER.movie.service;

import com.seb_main_034.SERVER.movie.entity.Movie;
import com.seb_main_034.SERVER.movie.exception.BusinessLogicException;
import com.seb_main_034.SERVER.movie.repository.MovieRepository;
import com.seb_main_034.SERVER.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final UserService userService;

    //영화 등록
    public Movie createMovie(Movie movie, Long userId) {
        movie.setUser(userService.findUser(userId));
        return movieRepository.save(movie);
    }

    //영화 게시글 수정
    public Movie updateMovie(Movie movie, Long userId) {
        Movie findMovie = findMovie(movie.getMovieId());
        Long findMovieUserId = findMovie.getUser().getUserId();
        if (findMovieUserId.equals(userId)) {
            findMovie.setContent(movie.getContent());
            findMovie.setDescription(movie.getDescription());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"이 영화를 수정할 권한이 없습니다.");
        }
    }

    //쿼리문 사용을 위한 조회
    public Movie findMovie(long movieId) {
        return findMovieByQuery(movieId);
    }

    // 전체 글 조회
    public Page<Movie> findMovies(int page, int size) {
        movieRepository.count();
        return movieRepository.findAll(PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "MovieId").descending()));
    }
    // 평점 상위 10개 조회
    public Page<Movie> findMovies() {
        return movieRepository.findAll(PageRequest.of(0,10,
                Sort.by("rating").descending()));
    }

    //게시글 작성자에 따른 전체 글 조회(관리자에 의한 필요?)
    public Page<Movie> findUserMovies(int page, long userId) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("movieId").descending());
        Page<Movie> findMovies = movieRepository.findByUserUserId(userId, pageable);

        return findMovies;
    }

    //쿼리문 검색을 위한 본문 조회
    private Movie findMovieByQuery(long movieId) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        return optionalMovie.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MOVIE_NOT_FOUND));
    }

    public void deleteMovie(long movieId, Long userId) {
        Movie findMovie = findMovie(movieId);
        Long findMovieUserId = findMovie.getUser().getUserId();
        if (findMovieUserId.equals(userId)){
            movieRepository.delete(findMovie);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 영화 정보를 삭제 할 권한이 없습니다.");
        }
    }

    // 10개의 영화 출력, 검색기능 구현을 위한 로직
    public Page<Movie> findKeyWordMovies(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("movieId").descending());

        return movieRepository.findByKeyWordMovie(keyword, pageable);
    }
}
