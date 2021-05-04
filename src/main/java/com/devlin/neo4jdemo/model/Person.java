package com.devlin.neo4jdemo.model;/*
 * @created 23/04/2021 - 10:27 PM
 * @project neo4jdemo
 * @author devlin
 */

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NodeEntity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Set<Movie> getMovieList() {
        return movieSet;
    }

    @Relationship(type = "DIRECTED", direction = Relationship.OUTGOING)
    private Set<Movie> movieSet;

    public void addDirector(Movie movie) {
        if (movieSet == null) movieSet = new HashSet<>();
        movieSet.add(movie);
    }
}
