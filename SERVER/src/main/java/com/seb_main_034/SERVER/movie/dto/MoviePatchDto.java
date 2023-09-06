package com.seb_main_034.SERVER.movie.dto;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MoviePatchDto {
    private long movieId;

    @NotNull
    private String title;

    @NotNull
    private Text content;

    private Text description;

    private Long vote;
}
