package com.devlin.neo4jdemo;/*
 * @created 24/04/2021 - 5:57 PM
 * @project neo4jdemo
 * @author devlin
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.devlin.neo4jdemo.dao.MovieRepository;
import com.devlin.neo4jdemo.dao.PersonRepository;
import com.devlin.neo4jdemo.model.Movie;
import com.devlin.neo4jdemo.model.Person;
import com.devlin.neo4jdemo.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.devlin.neo4jdemo.util.JsonUtil.readJsonFile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Neo4jDemoApplicationTests {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    PersonRepository personRepository;

    public void initMovieAndPerson() {
        movieRepository.deleteAll();
        personRepository.deleteAll();
    }

    public void initMovieRepo() {
        movieRepository.deleteAll();
    }

    public void initPersonRepo() {
        personRepository.deleteAll();
    }

    @Test
    public void contestLoads() {}

    @Test
    public void testSaveSampleMovies() {
        initMovieAndPerson();
        Movie m1 = new Movie("Hook");
        Movie m2 = new Movie("Catch Me If You Can");
        movieRepository.save(m1);
        movieRepository.save(m2);
    }

    public void addDirectorNode(String personName) {
        Person p = null;
        if (personRepository.findByName(personName) == null) {
            p = new Person(personName);
        } else p = personRepository.findByName(personName);
        personRepository.save(p);
    }

    @Test
    public void testSaveSamplePerson() {
        initPersonRepo();
        Person p1 = new Person("Steven Spielberg");
        Movie m1 = movieRepository.findByTitle("1941");
        Movie m2 =  movieRepository.findByTitle("Catch Me If You Can");
        if (m1 != null) p1.addDirector(m1);
        if (m2 != null) p1.addDirector(m2);
        personRepository.save(p1);
    }

    /**
     * Based on the movie_director.json, extract DVDTitle
     * and save them into Neo4j
     */

    @Test
    public void testSaveAllDirectors() throws IOException {
//        initMovieAndPerson();
        String filePath = JsonUtil.class.getClassLoader().getResource("movie_director.json").getPath();
        String s = readJsonFile(filePath);
        JSONArray array = JSONArray.parseArray(s);
        String personName = "";
        String dvdTitle = "";
//        Set<String> personSet = new HashSet<>();
        for (int i = 0; i < 50; i++) {
            JSONObject object = array.getJSONObject(i);
            personName = object.getString("PersonName");
            dvdTitle = object.getString("DVDTitle");
            while(Character.isSpaceChar(dvdTitle.charAt(dvdTitle.length() - 1))) {
                dvdTitle = dvdTitle.substring(0, dvdTitle.length() - 1).trim();
            }
            addDirectorNode(personName);
        }
    }
}
