package com.seb_main_034.SERVER.movie.service;

import com.seb_main_034.SERVER.movie.entity.Movie;
import com.seb_main_034.SERVER.movie.repository.MovieRepository;
import com.seb_main_034.SERVER.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final UserService userService;

    public MovieService(UserService userService) {
        this.userService = userService;
    }

    public Movie createMovie(Movie movie, Long userId) {
        if (userService.userHasRole(userId, "ADMIN")) {
            movie.setUser(userService.findUser(userId));
            return movieRepository.save(movie);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "영화를 등록할 권한이 없습니다.");
        }
    }

    public Movie updateMovie(Movie movie, Long userId) {
        if (userHasMovieUpdatePermission(movie.getMovieId(), userId)) {
            Movie findMovie = findMovie(movie.getMovieId());
            findMovie.setContent(movie.getContent());
            return movieRepository.save(findMovie);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "영화를 수정할 권한이 없습니다.");
        }
    }

    public void deleteMovie(long movieId, Long userId) {
        if (userHasMovieUpdatePermission(movieId, userId)) {
            Movie findMovie = findMovie(movieId);
            movieRepository.delete(findMovie);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "영화를 삭제할 권한이 없습니다.");
        }
    }
    private boolean userHasMovieUpdatePermission(long movieId, Long userId) {
        Movie findMovie = findMovie(movieId);
        Long findMovieUserId = findMovie.getUser().getUserId();
        return findMovieUserId.equals(userId) || userService.userHasRole(userID, "ADMIN");
    }
}

//    public Movie createMovie(Movie movie, Long userId) {
//        movie.setUser(userService.findUser(userId));
//        return movieRepository.save(movie);
//    }
//
//    public Movie updateMovie(Movie movie, long userId) {
//        Movie findMovie = findMovie(movie.getMovieId());
//        Long findMovieUserId = findMovie.getUser().getUserId();
//        if (findMovieUserId.equals(userId)) {
//            findMovie.setContent(movie.getContent());
//            return movieRepository.save(findMovie);
//        } else {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
//        }
//    }
//
//    public Movie findMovie(long movieId) {
//        return movieRepository.findById(movieId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "영화를 찾을 수 없습니다."));
//    }
//
//    public void deleteMovie(long movieId, long userId) {
//        Movie findMovie = findMovie(movieId);
//        Long findMovieUserId = findMovie.getUser().getUserId();
//        if (findMovieUserId.equals(userId)) {
//            movieRepository.delete(findMovie);
//        } else {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
//        }
//    }
