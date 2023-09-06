package com.seb_main_034.SERVER.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
public class MoviePostDto {
    @Id
    private long movieId;

    private String title;

    private Text content;

    private Text description;
}
