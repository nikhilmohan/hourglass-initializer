package com.nikhilm.hourglass.initializer;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DBConfigTest {
    DBConfig dbConfig = new DBConfig();


    @Test
    public void testDBName() {
        dbConfig.setDb("hourglass");
        assertTrue(dbConfig.getDatabaseName().equalsIgnoreCase("hourglass"));
    }


}