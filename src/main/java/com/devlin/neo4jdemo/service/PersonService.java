package com.devlin.neo4jdemo.service;/*
 * @created 30/04/2021 - 2:24 AM
 * @project neo4jdemo
 * @author devlin
 */

import com.devlin.neo4jdemo.dao.PersonRepository;
import com.devlin.neo4jdemo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    public Collection<Person> getDirectors() {
        return personRepository.getAllDirectors();
    }
}
