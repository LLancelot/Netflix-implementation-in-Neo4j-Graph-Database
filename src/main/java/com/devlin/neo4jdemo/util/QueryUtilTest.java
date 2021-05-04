package com.devlin.neo4jdemo.util;/*
 * @created 01/05/2021 - 12:31 AM
 * @project neo4jdemo
 * @author devlin
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QueryUtilTest {
    public static void main(String[] args) throws IOException {
        // query if there exists shortest path between actor and director
        System.out.println("Please choose the query type:");
        System.out.println("1. Query the shortest path between actor & director");
        System.out.println("2. Query the movie related actors");
        System.out.println("3. Query the shortest path between actor & actor");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String type = reader.readLine();
            if (type.equals("")) break;
            if (type.equals("1"))
                QueryUtil.setQueryShortestPathActorDirector("", "");
            else if (type.equals("2"))
                QueryUtil.setQueryMovieRelatedActors("");
            else if (type.equals("3"))
                QueryUtil.setQueryShortestPathActorActor("", "");
            System.out.println("\nContinue query? Please choose the query type, or exit query with Enter");
        }
    }
}
