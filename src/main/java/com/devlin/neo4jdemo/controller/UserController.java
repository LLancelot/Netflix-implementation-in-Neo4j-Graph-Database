package com.devlin.neo4jdemo.controller;/*
 * @created 23/04/2021 - 10:25 PM
 * @project neo4jdemo
 * @author devlin
 */

import com.devlin.neo4jdemo.model.Person;
import com.devlin.neo4jdemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/neo4j/director")
public class UserController {

//    @GetMapping
//    public Collection<> getAll() {
//
//    }
    @Autowired
    PersonService personService;

    @GetMapping
    public Collection<Person> queryDirector() {
        return personService.getDirectors();
    }
}
