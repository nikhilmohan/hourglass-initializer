package com.nikhilm.hourglass.initializer.repositories;

import com.nikhilm.hourglass.initializer.models.MovieKeyword;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieKeywordRepository extends ReactiveMongoRepository<MovieKeyword, String> {
}
