package ch.blutch.cinemadb.consumer.impl.dao.ws;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ch.blutch.cinemadb.consumer.contract.dao.ws.SerieWsDao;

@Repository
@Qualifier("serieWsDao")
public class SerieWsDaoImpl extends AbstractWsDaoImpl implements SerieWsDao {

	
}
