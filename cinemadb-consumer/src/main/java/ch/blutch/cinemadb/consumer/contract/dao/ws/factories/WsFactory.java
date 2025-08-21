package ch.blutch.cinemadb.consumer.contract.dao.ws.factories;

import org.json.JSONObject;

import ch.blutch.cinemadb.model.entities.cinemaelement.Movie;
import ch.blutch.cinemadb.model.entities.persons.Person;

public interface WsFactory {
    public Movie createMovie(JSONObject movieObj);
    public Person createPerson(JSONObject creditObj);
}
