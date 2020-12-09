package com.nikhilm.hourglass.initializer.repositories;

import com.nikhilm.hourglass.initializer.models.Topic;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TopicRepository extends ReactiveMongoRepository<Topic, String> {



}
