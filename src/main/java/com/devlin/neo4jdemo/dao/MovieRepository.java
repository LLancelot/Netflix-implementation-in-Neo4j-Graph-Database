package com.devlin.neo4jdemo.dao;/*
 * @created 24/04/2021 - 5:28 PM
 * @project neo4jdemo
 * @author devlin
 */

import com.devlin.neo4jdemo.model.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends Neo4jRepository<Movie, Long> {
    Movie findByTitle(String title);
}
