package ch.blutch.cinemadb.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.blutch.cinemadb.business.contract.manager.PersonManager;
import ch.blutch.cinemadb.model.entities.persons.Person;

@RestController
@ResponseBody
@RequestMapping("/person")
public class PersonController {
    
    @Autowired
    @Qualifier("personManager")
    private PersonManager personManager;

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Person>> searchPersons(@PathVariable String query) {
        List<Person> persons = personManager.searchPersons(query);
        return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        Person person = personManager.getPerson(id);
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }
}
