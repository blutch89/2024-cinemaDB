package ch.blutch.cinemadb.consumer.contract.dao.ws;

import java.util.List;


import ch.blutch.cinemadb.model.entities.persons.Person;
import ch.blutch.cinemadb.model.enums.LoadType;

public interface PersonWsDao {

    public List<Person> searchPersons(String query);
    public Person getPerson(int id);
    public Person getPerson(int id, LoadType loadType);

}
