package ch.blutch.cinemadb.business.impl.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.blutch.cinemadb.business.contract.manager.PersonManager;
import ch.blutch.cinemadb.consumer.contract.dao.ws.PersonWsDao;
import ch.blutch.cinemadb.model.entities.persons.Person;
import ch.blutch.cinemadb.model.enums.LoadType;


@Transactional
@Service
@Qualifier("personManager")
public class PersonManagerImpl implements PersonManager {

    @Autowired
    @Qualifier("personWsDao")
    private PersonWsDao personWsDao;

    @Transactional(readOnly = true)
    @Override
    public List<Person> searchPersons(String query) {
        List<Person> persons = personWsDao.searchPersons(query);
        return persons;
    }

    @Transactional(readOnly = true)
    @Override
    public Person getPerson(int id) {
        return this.getPerson(id, LoadType.FULL);
    }

    @Transactional(readOnly = true)
    @Override
    public Person getPerson(int id, LoadType loadType) {
        Person person = personWsDao.getPerson(id);

        // Si le LoadType == MEDIUM ou FULL, charge les crédits de la personne
        if (loadType != LoadType.MINIMUM) {
            
        }

        // TODO Si LoadType == min, charger toutes les infos de la personne.
        // TODO Si MEDIUM, charger en plus le minimum de la filmographie/crédits
        // TODO SI Full, charger toutes les infos des crédits
        // TODO Autres choses à charger ?

        return person;
    }

}
