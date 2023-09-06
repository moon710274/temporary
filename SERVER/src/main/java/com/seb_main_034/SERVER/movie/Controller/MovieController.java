package com.seb_main_034.SERVER.movie.Controller;

import com.seb_main_034.SERVER.movie.dto.MoviePatchDto;
import com.seb_main_034.SERVER.movie.dto.MoviePostDto;
import com.seb_main_034.SERVER.movie.dto.MovieResponseDto;
import com.seb_main_034.SERVER.movie.entity.Movie;
import com.seb_main_034.SERVER.movie.mapper.MovieMapper;
import com.seb_main_034.SERVER.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Validated
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    //영화 등록
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity postMovie(@Valid @RequestBody MoviePostDto moviePostDto) {
        Movie movie = movieService.createMovie(movieMapper.moviePostDtoToMovie(moviePostDto));
        MovieResponseDto response = movieMapper.movieToMovieResponseDto(movie);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //영화 수정
    @PatchMapping("/{movie-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity patchMovie(@PathVariable("movie-id") @Positive long movieId,
                                     @Valid @RequestBody MoviePatchDto moviePatchDto) {
        moviePatchDto.setMovieId(movieId);
        Movie movie = movieService.updateMovie(movieMapper.moviePatchDtoToMovie(moviePatchDto));
        MovieResponseDto response = movieMapper.movieToMovieResponseDto(movie);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //전체 영화 조회
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getMovie(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Movie> moviePage = movieService.findMovie(page - 1, size);
        List<Movie> movies = moviePage.getContent();
        List<MovieResponseDto> response = movieMapper.movieToMovieResponseDto(movies);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //영화 삭제
    @DeleteMapping("/delete/{movie-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteMovie(@PathVariable("movie-id") @Positive long movieId) {
        movieService.deleteMovie(movieId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //영화 키워드를 통한 쿼리문 검색기능
    @GetMapping("/movie-search")
    public ResponseEntity getMovieSearch(@RequestParam(value = "movie-search" ) String keyWord,
                                            @Positive int page) {
        Page<Movie> moviePage = movieService.findKeyWordMoives(keyWord, page);
        List<Movie> movies = moviePage.getContent();
        List<MovieResponseDto> response = movieMapper.movieToMovieResponseDto(movies);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

//    @GetMapping("/{movie-id}/vote-up")
//    public ResponseEntity patchMovieVoteUp(httpServletRequest request)
//    @GetMapping("/{movie-id}/vote-down")
//    public ResponseEntity patchMovieVoteDown
}