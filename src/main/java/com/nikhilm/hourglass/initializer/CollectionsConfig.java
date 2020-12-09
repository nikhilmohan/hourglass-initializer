package com.nikhilm.hourglass.initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class CollectionsConfig {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public void initIndexes() {
        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("name")
                .onField("description")
                .build();
        reactiveMongoTemplate.indexOps("goals") // collection name string or .class
            .ensureIndex(textIndex);

        log.info("index created!");

    }
}