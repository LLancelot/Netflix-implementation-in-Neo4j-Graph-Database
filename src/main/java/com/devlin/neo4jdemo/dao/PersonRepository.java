package com.devlin.neo4jdemo.dao;/*
 * @created 24/04/2021 - 5:35 PM
 * @project neo4jdemo
 * @author devlin
 */

import com.devlin.neo4jdemo.model.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Person findByName(String name);

    @Query("MATCH (d:Director)-[r:DIRECTED]->(m:Movie) RETURN d,r,m")
    Collection<Person> getAllDirectors();

    @Query("MATCH (d:Actor)-[r:ACTED_IN]->(m:Movie) RETURN d,r,m")
    Collection<Person> getAllActors();


}
