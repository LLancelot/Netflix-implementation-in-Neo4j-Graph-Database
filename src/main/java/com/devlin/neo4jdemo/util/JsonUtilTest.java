package com.devlin.neo4jdemo.util;/*
 * @created 24/04/2021 - 2:43 AM
 * @project neo4jdemo
 * @author devlin
 */

import com.devlin.neo4jdemo.dao.MovieRepository;
import com.devlin.neo4jdemo.model.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUtilTest {
    private static final String MOVIE_DIRECTOR_JSON_FILE = "movie_director.json";
    private static final String MOVIE_ACTOR_JSON_FILE = "movie_actor.json";
    private static final String MOVIE_ACTRESS_JSON_FILE = "movie_actress.json";

    public static void initAll() throws IOException {
        // init all director node
        JsonUtil.createDirectorCommand(MOVIE_DIRECTOR_JSON_FILE);

        // init all movie node
        JsonUtil.createMovieCommand(MOVIE_DIRECTOR_JSON_FILE);

        // init director -> movie relationship
        JsonUtil.createDirectorRelationshipCommand(MOVIE_DIRECTOR_JSON_FILE);

        // init actor -> movie relationship
        JsonUtil.createActorRelationshipCommand(MOVIE_ACTOR_JSON_FILE);

        // init actress -> movie relationship
        JsonUtil.createActressRelationshipCommand(MOVIE_ACTRESS_JSON_FILE);
    }

    public static void main(String[] args) throws IOException {
//        JsonUtil.outputNeo4jCreateDirectorMovieCommand("movie_director.json");
//        JsonUtil.createDirectorCommand(MOVIE_DIRECTOR_JSON_FILE);
        System.out.println("Please press start to initialize the processes: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name1 = reader.readLine();
        System.out.println("Starting... Commands of Nodes and relationships are generating...");
        initAll();
        System.out.println("Init works finished!\n");
        System.out.println("Do you want to know the relationships between actors & directors? Please type your choice:");
        System.out.println("1. I want to know the relationship between two actors.");
        System.out.println("2. I want to know the relationship between actor and directors.");
        while (true) {
            String knowsType = reader.readLine();
            if (knowsType.equals("")) break;
            if (knowsType.equals("1"))
                // query if two actors know each other, "r:KNOWS" based on they had acted in same movies.
                JsonUtil.createActorKnowsActorCommand("", "");
            else if (knowsType.equals("2"))
                // query if actor knows director
                JsonUtil.createActorKnowsDirectorCommand("", "");
            System.out.println("Continue? Please choose the query type, or exit query with Enter");
        }

    }
}
