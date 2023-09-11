package com.seb_main_034.SERVER.movie.entity;

import com.seb_main_034.SERVER.users.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.w3c.dom.Text;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Text description;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

//    public Movie(String title, String content, Text description, Users user) {
//        this.title = title;
//        this.content = content;
//        this.description = description;
//        this.user = user;
//    }
}
