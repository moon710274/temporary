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
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Text content;

    @Column(nullable = false)
    private Text description;

    @Column
    private Long vote = 0L;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;


    public Movie(String title, Text content, Text description, User users, long vote) {
        this.title = title;
        this.content = content;
        this.description = description;
        this.user = users;
    }
}
