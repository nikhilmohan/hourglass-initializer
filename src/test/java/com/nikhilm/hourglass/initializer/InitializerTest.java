package com.nikhilm.hourglass.initializer;

import com.nikhilm.hourglass.initializer.models.Category;
import com.nikhilm.hourglass.initializer.models.MovieKeyword;
import com.nikhilm.hourglass.initializer.models.Topic;
import com.nikhilm.hourglass.initializer.repositories.MovieKeywordRepository;
import com.nikhilm.hourglass.initializer.repositories.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.ReactiveIndexOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class InitializerTest {

    @InjectMocks
    InitializerApplication application;

    @Mock
    MovieKeywordRepository movieKeywordRepository;

    @Mock
    TopicRepository topicRepository;

    @Mock
    ReactiveMongoTemplate reactiveMongoTemplate;



    @Test
    public void testInitialize() {
        List<MovieKeyword> keywordList = Arrays.asList(new MovieKeyword("1", "forest", 1),
                new MovieKeyword("2", "land", 2), new MovieKeyword("3", "car", 3));

        Flux<MovieKeyword> movieKeywordFlux = Flux.fromIterable(keywordList);

        Flux<Topic> topicFlux = Flux.fromIterable(Arrays.asList(new Topic("thermodynamics", Category.science), new Topic("astrophysics", Category.science),
                new Topic("buoyancy", Category.science), new Topic("capillary", Category.science),
                new Topic("football", Category.sport), new Topic("Plitvice national park", Category.travel),
                new Topic("blockchain", Category.technology), new Topic("deep learning", Category.technology),
                new Topic("bouncer", Category.sport), new Topic("placebo", Category.science),
                new Topic("handball", Category.sport), new Topic("jungle", Category.travel),
                new Topic("handball", Category.sport), new Topic("monastery", Category.travel),
                new Topic("handball", Category.sport), new Topic("ocean walk", Category.travel)));


        Mockito.when(movieKeywordRepository.deleteAll()).thenReturn(Mono.empty());
        Mockito.when(movieKeywordRepository.saveAll(any(List.class))).thenReturn(movieKeywordFlux);
        Mockito.when(topicRepository.deleteAll()).thenReturn(Mono.empty());
        Mockito.when(topicRepository.saveAll(any(List.class))).thenReturn(topicFlux);

        ReactiveIndexOperations reactiveIndexOperations = mock(ReactiveIndexOperations.class);
        Mockito.when(reactiveMongoTemplate.indexOps("goals")).thenReturn(reactiveIndexOperations);
        Mockito.when(reactiveIndexOperations.ensureIndex(any(IndexDefinition.class))).thenReturn(Mono.just("index_1"));
        application.initialize();
        verify(movieKeywordRepository).deleteAll();
    }
    @Test
    public void testTopic()  {
        Topic topic = new Topic("computer", Category.technology);
        assertEquals("computer", topic.getWord());
        assertEquals(Category.technology, topic.getCategory());
        Topic topic1 = new Topic();
        topic1.setCategory(Category.sport);
        topic1.setWord("cricket");
        assertEquals("cricket", topic1.getWord());
        assertEquals(Category.sport, topic1.getCategory());
    }
    @Test
    public void testMovieKeyword()  {
        MovieKeyword keyword  = new MovieKeyword();
        keyword.setId("100");
        keyword.setLastPageAccessed(2);
        keyword.setKeyword("city");
        assertEquals("city", keyword.getKeyword());
        assertEquals(2, keyword.getLastPageAccessed());
        assertEquals("100", keyword.getId());
    }

}
