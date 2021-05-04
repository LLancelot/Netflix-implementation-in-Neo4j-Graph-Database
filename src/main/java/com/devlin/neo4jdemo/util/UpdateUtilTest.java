package com.devlin.neo4jdemo.util;/*
 * @created 01/05/2021 - 2:17 AM
 * @project neo4jdemo
 * @author devlin
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UpdateUtilTest {
    public static void main(String[] args) throws IOException {
        // query if there exists shortest path between actor and director
        System.out.println("Which property do you want to update?:");
        System.out.println("1. Update the actor's name.");
        System.out.println("2. Update the director's name.");
        System.out.println("3. Update the movie's title.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String type = reader.readLine();
            if (type.equals("")) break;
            if (type.equals("1"))
                UpdateUtil.setChangeActorName();
            else if (type.equals("2"))
                UpdateUtil.setChangeDirectorName();
            else if (type.equals("3"))
                UpdateUtil.setChangeMovieTitle();
            System.out.println("\nContinue update? Please select a property to update, or exit with Enter");
        }
    }
}
