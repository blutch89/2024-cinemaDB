package ch.blutch.cinemadb.consumer.impl.dao.ws.factories;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ch.blutch.cinemadb.consumer.contract.dao.ws.factories.WsFactory;
import ch.blutch.cinemadb.model.entities.cinemaelement.Movie;
import ch.blutch.cinemadb.model.entities.persons.Person;

@Component
@Qualifier("wsFactory")
public class TMDBWsFactory implements WsFactory {

    @Override
    public Movie createMovie(JSONObject movieObj) {
        Movie toReturn = new Movie();
		toReturn.setIdApi(movieObj.getInt("id"));
		toReturn.setTitle(movieObj.getString("title"));
		toReturn.setSynopsis(movieObj.getString("overview"));
		toReturn.setReleaseDate(movieObj.getString("release_date"));
		toReturn.setSpectatorNote(movieObj.getLong("vote_average"));
		toReturn.setImage(!movieObj.isNull("poster_path") ? movieObj.getString("poster_path") : "");
		
		return toReturn;
    }

    @Override
    public Person createPerson(JSONObject creditObj) {
        Person person = new Person();
		person.setIdApi(creditObj.getInt("id"));
		person.setPersonGender(creditObj.getInt("gender"));
		person.setName(creditObj.getString("name"));
		person.setOriginalName(!creditObj.isNull("original_name") ? creditObj.getString("original_name") : "");
		person.setImage(!creditObj.isNull("profile_path") ? creditObj.getString("profile_path") : "");
		person.setJob(creditObj.getString("known_for_department"));
		person.setPopularity(creditObj.getFloat("popularity"));

		return person;
    }

}
