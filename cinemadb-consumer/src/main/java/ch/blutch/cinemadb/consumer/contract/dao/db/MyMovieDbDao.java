package ch.blutch.cinemadb.consumer.contract.dao.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.blutch.cinemadb.model.entities.cinemaelement.my.MyMovie;

@Repository
@Qualifier("myMovieDbDao")
public interface MyMovieDbDao extends CrudRepository<MyMovie, Integer> {

	
}
