package com.devlin.neo4jdemo.util;/*
 * @created 24/04/2021 - 2:29 AM
 * @project neo4jdemo
 * @author devlin
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.*;

public class JsonUtil {
    public static final String CREATE_DIRECTOR_NODE = "CREATE (director_%s: Director {Name: '%s'})";
    public static final String CREATE_MOVIE_NODE = "CREATE (movie_%s: Movie {Title: '%s'})";
    public static final String MERGE_MOVIE_NODE = "(movie_%s: Movie {Title: '%s'})";
    public static final String CREATE_REL_DIR_MOV = "(director_%s)-[:DIRECTED]->(movie_%s)";

    public static final String CREATE_ACTOR_NODE = "CREATE (actor_%s: Actor {Name: '%s'})";
    public static final String CREATE_ACTRESS_NODE = "CREATE (actress_%s: Actress {Name: '%s'})";

    public static final String CREATE_REL_ACT_MOV = "(actor_%s)-[:ACTED_IN]->(movie_%s)";
    public static final String CREATE_REL_ACTRESS_MOV = "(actress_%s)-[:ACTED_IN]->(movie_%s)";

    public static final String CREATE_REL_ACT_ACT_KNOWS = "MATCH (a:Actor {Name: '%s'}), (b:Actor {Name: '%s'}) MERGE (a)-[:KNOWS]->(b)";
    public static final String CREATE_REL_ACT_DIR_KNOWS = "MATCH (a:Actor {Name: '%s'}), (b:Director {Name: '%s'}) MERGE (a)-[:KNOWS]->(b)";
    public static final String CREATE_REL_DIR_ACT_KNOWS = "MATCH (a:Director {Name: '%s'}), (b:Actor {Name: '%s'}) MERGE (a)-[:KNOWS]->(b)";
    public static final String CREATE_REL_ACT_DIR_BOTH_KNOWS = "MATCH (a:Actor {Name: '%s'}), (b:Director {Name: '%s'}) MERGE (a)-[:KNOWS]->(b)-[:KNOWS]->(a)";
    public static final String CREATE_REL_ACT_ACT_BOTH_KNOWS = "MATCH (a:Actor {Name: '%s'}), (b:Actor {Name: '%s'}) MERGE (a)-[:KNOWS]->(b)-[:KNOWS]->(a)";



    public static BufferedWriter writer = null;


    public static int totalMovieNum = 0;

    /**
     * <K, V> is <MovieTitle, MovieId>
     */
    public static Map<String, String> movieHashMap = new HashMap<>();

    public static Map<String, String> movieIdMap = new HashMap<>();

    /**
     * <K, V> is <directorName, listOfMovies>
     */
    public static Map<String, Set<String>> directorMovieMap = new HashMap<>();

    /**
     * <K, V> is <actorName, listOfMovies>
     */
    public static Map<String, Set<String>> actorMovieMap = new HashMap<>();


    public static String readJsonFile(String filePath) throws IOException{
        String jsonStr = "";
        try {
            File jsonFile = new File(filePath);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String processFormat(String template, String... params) {
        if (template == null || params == null) return null;
        return String.format(template, params);
    }

    public static void processJson(String fileName) throws IOException{

    }

    /**
     * Generate commands for creating director nodes.
     * @param fileName
     * @throws IOException
     */
    public static void createDirectorCommand(String fileName) throws IOException{
        String filePath = JsonUtil.class.getClassLoader().getResource(fileName).getPath();
        String s = readJsonFile(filePath);
        JSONArray jsonArray = JSONArray.parseArray(s);
        String[] params = new String[2];
        writer = new BufferedWriter(new FileWriter("src/main/resources/create_director_command.txt"));
//        PrintStream ps = new PrintStream("src/main/resources/create_director_command.txt");
//        System.setOut(ps);
        HashSet<String> directorSet = new HashSet<>();
        int cnt = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            params[1] = object.getString("PersonName");
            if (!directorSet.contains(params[1])) {
                params[0] = String.valueOf(cnt++);
                writer.write(processFormat(CREATE_DIRECTOR_NODE, params)+"\n");
//                System.out.println(processFormat(CREATE_DIRECTOR_NODE, params));
                directorSet.add(params[1]);
            }
        }
//        ps.close();
        writer.close();
    }

    /**
     * Generate commands for creating movie nodes;
     * @param fileName
     * @throws IOException
     */
    public static void createMovieCommand(String fileName) throws IOException{
        String filePath = JsonUtil.class.getClassLoader().getResource(fileName).getPath();
        String s = readJsonFile(filePath);
        JSONArray jsonArray = JSONArray.parseArray(s);
        String[] params = new String[2];
//        PrintStream ps = new PrintStream("src/main/resources/create_movie_command.txt");
//        System.setOut(ps);
        writer = new BufferedWriter(new FileWriter("src/main/resources/create_movie_command.txt"));
        int cnt = 0;
        String directorName = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            directorName = object.getString("PersonName");
            cnt = getNumActor(params, cnt, object);
            if (!directorMovieMap.containsKey(directorName)) {
                directorMovieMap.put(directorName, new HashSet<>());
            }
            directorMovieMap.get(directorName).add(params[1]);
//            System.out.println(processFormat(CREATE_MOVIE_NODE, params));
            writer.write(processFormat(CREATE_MOVIE_NODE, params) + "\n");
        }
        writer.close();
//        ps.close();
    }

    public static void createDirectorRelationshipCommand(String fileName) throws IOException {
        String filePath = JsonUtil.class.getClassLoader().getResource(fileName).getPath();
        String s = readJsonFile(filePath);
        JSONArray jsonArray = JSONArray.parseArray(s);
        String[] dirParams = new String[2];
        String[] movieParams = new String[2];
//        String[] relationParams = new String[2];
//        PrintStream ps = new PrintStream("src/main/resources/create_director_relationship.txt");
//        System.setOut(ps);
        writer = new BufferedWriter(new FileWriter("src/main/resources/create_director_relationship.txt"));
        int numDirector = 0, numMovie = 0;
        String directorName = "";
        for (Map.Entry<String, Set<String>> mapElement : directorMovieMap.entrySet()) {
            int totalCnt = mapElement.getValue().size();
            dirParams[0] = String.valueOf(numDirector++);
            dirParams[1] = mapElement.getKey();
//            System.out.println(processFormat(CREATE_DIRECTOR_NODE, dirParams)); // create director's node
            writer.write(processFormat(CREATE_DIRECTOR_NODE, dirParams) + "\n");
            Iterator iterator = mapElement.getValue().iterator();
            while (iterator.hasNext()) {
//                totalCnt--;
                movieParams[0] = String.valueOf(numMovie++);
                movieParams[1] = (String) iterator.next();
//                System.out.println(processFormat(CREATE_MOVIE_NODE, movieParams));
                writer.write(processFormat(CREATE_MOVIE_NODE, movieParams) + "\n");
                movieHashMap.put(movieParams[1], movieParams[0]);
                movieIdMap.put(movieParams[0], movieParams[1]);
            }
            numMovie -= totalCnt;
            iterator = mapElement.getValue().iterator();
//            System.out.print("CREATE\n\t");
            writer.write("CREATE\n\t");
            while (iterator.hasNext()) {
                totalCnt--;
//                System.out.print(processFormat(CREATE_REL_DIR_MOV, dirParams[0], String.valueOf(numMovie++)));
                writer.write(processFormat(CREATE_REL_DIR_MOV, dirParams[0], String.valueOf(numMovie++)));
                if (totalCnt > 0) {
//                    System.out.println(",");
                    writer.write(",\n");
                }
                iterator.next();
//                System.out.print("\t");
                writer.write("\t");
            }
            totalMovieNum = numMovie;   // get total amounts of movies
//            System.out.println("\n");
            writer.write("\n\n");
        }
        writer.close();
//        ps.close();
    }

    /**
     * Create Actor nodes and "ACTED IN" relationships to Movie nodes.
     * @param fileName movie_actor.json
     */
    public static void createActorRelationshipCommand(String fileName) throws IOException {
        String filePath = JsonUtil.class.getClassLoader().getResource(fileName).getPath();
        String s = readJsonFile(filePath);
        JSONArray jsonArray = JSONArray.parseArray(s);
        String[] actorParams = new String[2];

//        PrintStream ps = new PrintStream("src/main/resources/create_actor_relationship.txt");
//        System.setOut(ps);
        writer = new BufferedWriter(new FileWriter("src/main/resources/create_actor_relationship.txt"));
        int numActor = 0;
        String actorName = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            actorName = object.getString("ActorName");
            numActor = getNumActor(actorParams, numActor, object);
            if (!actorMovieMap.containsKey(actorName)) {
                actorMovieMap.put(actorName, new HashSet<>());
            }
            if (movieHashMap.containsKey(actorParams[1])) {
                actorMovieMap.get(actorName).add(movieHashMap.get(actorParams[1])); // add movie num into list
            } else {
                int x = totalMovieNum++;
                actorMovieMap.get(actorName).add(String.valueOf(x));  // new movie, not in MovieHashMap.
                movieIdMap.put(String.valueOf(x), actorParams[1]);
                movieHashMap.put(actorParams[1], String.valueOf(x));
            }
        }

        numActor = 0;
        for (Map.Entry<String, Set<String>> entry : actorMovieMap.entrySet()) { // forEach Actor
            int totalCnt = entry.getValue().size(); // total amount of movies that actor acted in
            actorName = entry.getKey();
//            System.out.println(processFormat(CREATE_ACTOR_NODE, String.valueOf(numActor), actorName)); // create actor node
            writer.write(processFormat(CREATE_ACTOR_NODE, String.valueOf(numActor), actorName) + "\n");
            Iterator iterator = entry.getValue().iterator();

            while (iterator.hasNext()) {
//                System.out.print("MERGE\n\t");
                writer.write("MERGE\n\t");
                String strMovieId = (String)iterator.next();
//                System.out.println(processFormat(MERGE_MOVIE_NODE, strMovieId, movieIdMap.get(strMovieId)));
                writer.write(processFormat(MERGE_MOVIE_NODE, strMovieId, movieIdMap.get(strMovieId)) + "\n");
            }
            totalCnt = entry.getValue().size();
            iterator = entry.getValue().iterator();
//            System.out.print("\nCREATE\n\t");
            writer.write("\nCREATE\n\t");
            while (iterator.hasNext()) {
                String strMovieId = (String)iterator.next();
                totalCnt--;
//                System.out.print(processFormat(CREATE_REL_ACT_MOV, String.valueOf(numActor), strMovieId));
                writer.write(processFormat(CREATE_REL_ACT_MOV, String.valueOf(numActor), strMovieId));
                if (totalCnt > 0) {
//                    System.out.println(",");
                    writer.write(",\n");
                }
//                System.out.print("\t");
                writer.write("\t");
            }
//            System.out.println("\n");
            writer.write("\n\n");
            numActor++;
        }
//        ps.close();
        writer.close();
    }

    public static void createActressRelationshipCommand(String fileName) throws IOException {
        String filePath = JsonUtil.class.getClassLoader().getResource(fileName).getPath();
        String s = readJsonFile(filePath);
        JSONArray jsonArray = JSONArray.parseArray(s);
        String[] actressParams = new String[2];

        writer = new BufferedWriter(new FileWriter("src/main/resources/create_actress_relationship.txt"));
        int numActress = 0;
        String actressName = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            actressName = object.getString("ActressName");
            numActress = getNumActor(actressParams, numActress, object);
            if (!actorMovieMap.containsKey(actressName)) {
                actorMovieMap.put(actressName, new HashSet<>());
            }
            if (movieHashMap.containsKey(actressParams[1])) {
                actorMovieMap.get(actressName).add(movieHashMap.get(actressParams[1]));
            } else {
                int x = totalMovieNum++;
                actorMovieMap.get(actressName).add(String.valueOf(x));
                movieIdMap.put(String.valueOf(x), actressParams[1]);
                movieHashMap.put(actressParams[1], String.valueOf(x));
            }
//            System.out.println(actorMovieMap.get(actressName));
        }
    }

    public static void createActorKnowsActorCommand(String actor1, String actor2) throws IOException {
        System.out.println("Please input two actor's name");
        System.out.println("Actor1:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        actor1 = reader.readLine();
        System.out.println("Actor2:");
        actor2 = reader.readLine();

//        PrintStream ps = new PrintStream("src/main/resources/create_actor_knows_actor_relationship.txt");
//        System.setOut(ps);
        writer = new BufferedWriter(new FileWriter("src/main/resources/create_actor_knows_actor_relationship.txt"));
        if (actorMovieMap.containsKey(actor1) && actorMovieMap.containsKey(actor2)) {
            Set<String> setActor1 = actorMovieMap.get(actor1);
            Set<String> setActor2 = actorMovieMap.get(actor2);
            setActor1.retainAll(setActor2);
            Set<String> related = new HashSet<>();
            for (String movieId : setActor1) related.add(movieIdMap.get(movieId));

            if (!related.isEmpty()) {
                System.out.println(processFormat("Yes! %s & %s know each other! The related movies are:", actor1, actor2));
                System.out.println(related);
                System.out.println(processFormat(CREATE_REL_ACT_ACT_KNOWS, actor1, actor2));
                System.out.println(processFormat(CREATE_REL_ACT_ACT_KNOWS, actor2, actor1));
                System.out.println(processFormat(CREATE_REL_ACT_ACT_BOTH_KNOWS, actor1, actor2));
                writer.write(processFormat(CREATE_REL_ACT_ACT_KNOWS, actor1, actor2) + "\n");
                writer.write(processFormat(CREATE_REL_ACT_ACT_KNOWS, actor2, actor1) + "\n");
                writer.write(processFormat(CREATE_REL_ACT_ACT_BOTH_KNOWS, actor1, actor2) + "\n");
                writer.write("\n");
            }
        }
//        ps.close();
        writer.close();
    }


    public static void createActorKnowsDirectorCommand(String actorName, String directorName) throws IOException {
//        PrintStream ps = new PrintStream("src/main/resources/create_actor_knows_director_relationship.txt");
//        System.setOut(ps);
        System.out.println("Please input two person's name");
        System.out.println("Actor:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        actorName = reader.readLine();
        System.out.println("Director:");
        directorName = reader.readLine();

        writer = new BufferedWriter(new FileWriter("src/main/resources/create_actor_knows_director_relationship.txt"));
        if (actorMovieMap.containsKey(actorName) && directorMovieMap.containsKey(directorName)) {
            Set<String> setActor = actorMovieMap.get(actorName);
            Set<String> setDirectorMovie = directorMovieMap.get(directorName);
            Set<String> setActorMovie = new HashSet<>();
            for (String movId : setActor) setActorMovie.add(movieIdMap.get(movId));

            setActorMovie.retainAll(setDirectorMovie); // check if they have common related movies
            if (!setActorMovie.isEmpty()) { // check if they have common related movies
                System.out.println("Yes! They know each other! The related movies are:");
                System.out.println(setActorMovie);
                System.out.println(processFormat(CREATE_REL_ACT_DIR_KNOWS, actorName, directorName));
                System.out.println(processFormat(CREATE_REL_DIR_ACT_KNOWS, directorName, actorName));
                System.out.println(processFormat(CREATE_REL_ACT_DIR_BOTH_KNOWS, actorName, directorName));
//                writer.write(processFormat(CREATE_REL_ACT_DIR_KNOWS, actorName, directorName));
//                writer.write(processFormat(CREATE_REL_DIR_ACT_KNOWS, directorName, actorName));
                writer.write(processFormat(CREATE_REL_ACT_DIR_BOTH_KNOWS, actorName, directorName)+"\n");
                writer.write("\n");

            }
        }
//        ps.close();
        writer.close();
    }

    private static int getNumActor(String[] actorParams, int numActor, JSONObject object) {
        actorParams[1] = object.getString("DVDTitle");
        while(Character.isSpaceChar(actorParams[1].charAt(actorParams[1].length() - 1))) {
            actorParams[1] = actorParams[1].substring(0, actorParams[1].length() - 1).trim();
        }
        actorParams[0] = String.valueOf(numActor++);
        return numActor;
    }

    public static void outputNeo4jCreateDirectorMovieCommand(String fileName) throws IOException {
        String filePath = JsonUtil.class.getClassLoader().getResource(fileName).getPath();
        String s = readJsonFile(filePath);
        JSONArray array = JSONArray.parseArray(s);
        String[] params = new String[1];
        String regStartSpace = "^[　 ]*";
        String regEndSpace = "[　 ]*$";
//        PrintStream ps = new PrintStream("src/main/resources/create_director_movie_command.txt");
//        System.setOut(ps);
//        for (int i = 0; i < array.size(); i++) {
//            JSONObject object = array.getJSONObject(i);
//            params[0] = object.getString("PersonName").replaceAll(" ", "_");
//            params[2] = params[0];
//            params[1] = object.getString("DVDTitle");
//            while(Character.isSpaceChar(params[1].charAt(params[1].length() - 1))) {
//                params[1] = params[1].substring(0, params[1].length() - 1).trim();
//            }
//            System.out.println(processFormat(CREATE_DIRECTOR_MOVIE_TEMPLATE, params));
////            System.out.println(CREATE_DIRECTOR_MOVIE_TEMPLATE);
//        }
//        ps.close();
//        System.out.println("PersonName: " + obj.get("PersonName"));
    }

}
