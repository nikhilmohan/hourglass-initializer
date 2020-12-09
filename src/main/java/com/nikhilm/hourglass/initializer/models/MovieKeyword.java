package com.nikhilm.hourglass.initializer.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "moviekeywords")
public class MovieKeyword {
    @Id
    private String id;
    private String keyword;
    private int lastPageAccessed = 0;

    public static MovieKeyword of(String keyword)  {
        MovieKeyword movieKeyword = new MovieKeyword();
        movieKeyword.setKeyword(keyword);
        movieKeyword.setLastPageAccessed(0);
        return movieKeyword;
    }
}
