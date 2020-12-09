package com.nikhilm.hourglass.initializer;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.nikhilm.hourglass.initializer.models.Category;
import com.nikhilm.hourglass.initializer.models.MovieKeyword;
import com.nikhilm.hourglass.initializer.models.Topic;
import com.nikhilm.hourglass.initializer.repositories.MovieKeywordRepository;
import com.nikhilm.hourglass.initializer.repositories.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class InitializerApplication implements CommandLineRunner {

	@Autowired
	MovieKeywordRepository movieKeywordRepository;

	@Autowired
	TopicRepository topicRepository;

	@Autowired
	ReactiveMongoTemplate reactiveMongoTemplate;

	List<MovieKeyword> keywords = new ArrayList<>();
	List<Topic> topics = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(InitializerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		log.info("Initializing movie keyword data and topic data");

		loadMovieKeywordData();
		loadTidbitTopicData();

		movieKeywordRepository.deleteAll()
				.thenMany(movieKeywordRepository.saveAll(keywords))
				.thenMany(topicRepository.deleteAll())
				.thenMany(topicRepository.saveAll(topics))
				.blockLast();

		log.info("done!");


	}
	private void loadMovieKeywordData()	{
		keywords.addAll(Arrays.asList(MovieKeyword.of("city"), MovieKeyword.of("woods"), MovieKeyword.of("sand")
		, MovieKeyword.of("water"), MovieKeyword.of("ball"), MovieKeyword.of("three"), MovieKeyword.of("summer"),
				MovieKeyword.of("land")));
	}

	private void loadTidbitTopicData()	{
		topics.addAll(Arrays.asList(new Topic("thermodynamics", Category.science), new Topic("astrophysics", Category.science),
				new Topic("buoyancy", Category.science), new Topic("capillary", Category.science),
				new Topic("football", Category.sport), new Topic("Plitvice national park", Category.travel),
				new Topic("blockchain", Category.technology), new Topic("deep learning", Category.technology),
				new Topic("bouncer", Category.sport), new Topic("placebo", Category.science),
				new Topic("handball", Category.sport), new Topic("jungle", Category.travel),
				new Topic("handball", Category.sport), new Topic("monastery", Category.travel),
				new Topic("handball", Category.sport), new Topic("ocean walk", Category.travel)));

	}

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create();
	}

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() {
		return new ReactiveMongoTemplate(mongoClient(), "hourglass");
	}

	@PostConstruct
	public void initIndexes() {
		TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
				.onField("name")
				.onField("description")
				.build();
		reactiveMongoTemplate.indexOps("goals") // collection name string or .class
				.ensureIndex(textIndex).block();

		log.info("index created!");

	}

}
