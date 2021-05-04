package com.devlin.neo4jdemo.util;/*
 * @created 01/05/2021 - 2:23 AM
 * @project neo4jdemo
 * @author devlin
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.devlin.neo4jdemo.util.UpdateUtil.processChange;

public class DeleteUtil {
    public static final String DELETE_REL_ACTOR_KNOWS
            = "MATCH (a:Actor {Name: '%s'})-[r:KNOWS]->(b:Actor {Name: '%s'}) DELETE r";
    public static final String DELETE_REL_ACTOR_MOVIE
            = "MATCH (a:Actor {Name: '%s'})-[r:ACTED_IN]->(m:Movie {Title: '%s'}) DELETE r";
    public static final String DELETE_REL_DIRECTOR_MOVIE
            = "MATCH (d:Director {Name: '%s'})-[r:DIRECTED]->(m:Movie {Title: '%s'}) DELETE r";

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void setDeleteRelActorKnows() throws IOException {
        System.out.println("Please input the actor1's name:");
        String actor1 = reader.readLine();
        System.out.println("Please input the actor2's name");
        String actor2 = reader.readLine();
        processChange(DELETE_REL_ACTOR_KNOWS, actor1, actor2);
        // this could be bidirectional...
    }

    public static void setDeleteRelActorMovie() throws IOException {
        System.out.println("Please input the actor's name:");
        String actor1 = reader.readLine();
        System.out.println("Please input the movie's title");
        String movie1 = reader.readLine();
        processChange(DELETE_REL_ACTOR_MOVIE, actor1, movie1);
    }

    public static void setDeleteRelDirectorMovie() throws IOException {
        System.out.println("Please input the director's name:");
        String director1 = reader.readLine();
        System.out.println("Please input the movie's title");
        String movie1 = reader.readLine();
        processChange(DELETE_REL_DIRECTOR_MOVIE, director1, movie1);
    }
}
