package com.devlin.neo4jdemo.util;/*
 * @created 01/05/2021 - 2:03 AM
 * @project neo4jdemo
 * @author devlin
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UpdateUtil {
    public static final String CHANGE_ACTOR_NAME = "MATCH (a:Actor {Name: '%s'}) SET a.Name = '%s'";
    public static final String CHANGE_DIRECTOR_NAME = "MATCH (d:Director {Name: '%s'}) SET d.Name = '%s'";
    public static final String CHANGE_MOVIE_TITLE = "MATCH (m:Movie {Title: '%s'}) SET a.Title = '%s'";

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void processChange(String template, String matchedName, String changedName) {
        if (matchedName.equals("") || changedName.equals("")) return;
        System.out.println(JsonUtil.processFormat(template, matchedName, changedName));
    }

    public static void setChangeActorName() throws IOException {
        System.out.println("Please input the current actor's name:");
        String matchedName = reader.readLine();
        System.out.println("Please input the new actor's name");
        String changedName = reader.readLine();
        processChange(CHANGE_ACTOR_NAME, matchedName, changedName);
    }

    public static void setChangeDirectorName() throws IOException {
        System.out.println("Please input the current director's name:");
        String matchedName = reader.readLine();
        System.out.println("Please input the new director's name");
        String changedName = reader.readLine();
        processChange(CHANGE_DIRECTOR_NAME, matchedName, changedName);
    }

    public static void setChangeMovieTitle() throws IOException {
        System.out.println("Please input the current movie's title:");
        String matchedMovie = reader.readLine();
        System.out.println("Please input the new movie's title");
        String changedMovie = reader.readLine();
        processChange(CHANGE_MOVIE_TITLE, matchedMovie, changedMovie);
    }

}
