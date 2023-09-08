package com.seb_main_034.SERVER.movie.dto;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

@Getter
@Setter
public class MovieResponseDto {
    private long movieId;

    private String title;

    private String content;

    private Text description;

}
