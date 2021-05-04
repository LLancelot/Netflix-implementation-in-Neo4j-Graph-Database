package com.devlin.neo4jdemo.util;/*
 * @created 01/05/2021 - 2:34 AM
 * @project neo4jdemo
 * @author devlin
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteUtilTest {
    public static void main(String[] args) throws IOException {
        // query if there exists shortest path between actor and director
        System.out.println("Which relationship do you want to delete?:");
        System.out.println("1. Delete the actor-[r:KNOWS]->actor.");
        System.out.println("2. Delete the director-[r:DIRECTED]->movie.");
        System.out.println("3. Delete the actor-[r:ACTED_IN]->movie.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String type = reader.readLine();
            if (type.equals("")) break;
            if (type.equals("1"))
                DeleteUtil.setDeleteRelActorKnows();
            else if (type.equals("2"))
                DeleteUtil.setDeleteRelDirectorMovie();
            else if (type.equals("3"))
                DeleteUtil.setDeleteRelActorMovie();
            System.out.println("\nContinue delete? Please select a relationship to delete, or exit with Enter");
        }
    }
}
