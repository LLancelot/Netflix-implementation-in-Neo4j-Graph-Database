package com.devlin.neo4jdemo.util;/*
 * @created 01/05/2021 - 12:20 AM
 * @project neo4jdemo
 * @author devlin
 */

import java.io.*;


public class QueryUtil {
    public static final String QUERY_SHORTEST_PATH_ACTOR_DIRECTOR =
            "MATCH path=shortestPath(\n\t(a:Actor { Name: '%s'})-[:KNOWS*]-(b:Director {Name: '%s'})\n)\nRETURN path";
    public static final String QUERY_SHORTEST_PATH_ACTOR_ACTOR =
            "MATCH path=shortestPath(\n\t(a:Actor { Name: '%s'})-[:KNOWS*]-(b:Actor {Name: '%s'})\n)\nRETURN path";

    public static final String QUERY_MOVIE_RELATED_ACTORS =
            "MATCH (a: Movie {Title: '%s'})<-[:ACTED_IN*]-(relatedActors) RETURN a,relatedActors";

    public static final String QUERY_MOVIE_DIRECTOR =
            "MATCH (a: Movie {Title: '%s'})<-[:DIRECTED*]-(relatedDirectors) RETURN a,relatedDirectors";

    public static final String SRC_MAIN_RESOURCES = "src/main/resources/";

    public static final String OUTPUT_FILE_QUERY_ACTOR_DIRECTOR_SHORTEST_PATH = "query_actor_director_shortest_path.txt";
    public static final String OUTPUT_FILE_QUERY_MOVIE_RELATED_ACTORS = "query_movie_related_actors.txt";

    public static BufferedWriter writer = null;
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void setQueryShortestPathActorDirector(String actorName, String directorName) throws IOException {
        System.out.println("Please type actor's name:");
        reader = new BufferedReader(new InputStreamReader(System.in));
        actorName = reader.readLine();
        System.out.println("Please type director's name:");
        directorName = reader.readLine();

        if (actorName.equals("") || directorName.equals("")) return;

        writer = new BufferedWriter(new FileWriter(SRC_MAIN_RESOURCES.concat(OUTPUT_FILE_QUERY_ACTOR_DIRECTOR_SHORTEST_PATH)));
        writer.write(JsonUtil.processFormat(QUERY_SHORTEST_PATH_ACTOR_DIRECTOR, actorName, directorName)+"\n");
        writer.close();
        System.out.println(JsonUtil.processFormat(QUERY_SHORTEST_PATH_ACTOR_DIRECTOR, actorName, directorName));
        System.out.println("CQL commands generated successfully! Please open " + OUTPUT_FILE_QUERY_ACTOR_DIRECTOR_SHORTEST_PATH);
    }

    public static void setQueryMovieRelatedActors(String movieTitle) throws IOException {
        System.out.println("Please type movie's title:");
        reader = new BufferedReader(new InputStreamReader(System.in));
        movieTitle = reader.readLine();

        if (movieTitle.equals("")) return;

        writer = new BufferedWriter(new FileWriter(SRC_MAIN_RESOURCES.concat(OUTPUT_FILE_QUERY_MOVIE_RELATED_ACTORS)));
        writer.write(JsonUtil.processFormat(QUERY_MOVIE_RELATED_ACTORS, movieTitle) + "\n");
        writer.close();
        System.out.println(JsonUtil.processFormat(QUERY_MOVIE_RELATED_ACTORS, movieTitle));
        System.out.println("CQL commands generated successfully! Please open " + OUTPUT_FILE_QUERY_MOVIE_RELATED_ACTORS);
    }

    public static void setQueryShortestPathActorActor(String actor1, String actor2) throws IOException {
        System.out.println("Please type actor1's name:");
        reader = new BufferedReader(new InputStreamReader(System.in));
        actor1 = reader.readLine();
        System.out.println("Please type actor2's name:");
        actor2 = reader.readLine();

        if (actor1.equals("") || actor2.equals("")) return;

//        writer = new BufferedWriter(new FileWriter(SRC_MAIN_RESOURCES.concat(OUTPUT_FILE_QUERY_ACTOR_DIRECTOR_SHORTEST_PATH)));
//        writer.write(JsonUtil.processFormat(QUERY_SHORTEST_PATH_ACTOR_DIRECTOR, actorName, directorName)+"\n");
//        writer.close();
        System.out.println(JsonUtil.processFormat(QUERY_SHORTEST_PATH_ACTOR_ACTOR, actor1, actor2));
        System.out.println("CQL commands generated successfully!");
    }
}
