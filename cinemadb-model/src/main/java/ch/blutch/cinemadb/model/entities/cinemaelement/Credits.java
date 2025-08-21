package ch.blutch.cinemadb.model.entities.cinemaelement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.blutch.cinemadb.model.entities.persons.Person;

public class Credits {

    private List<Person> producers = new ArrayList<>();
    
    private List<Person> directors = new ArrayList<>();

    private List<HasPlayed> actors = new ArrayList<>();

    public Person getProducerByIpApi(int idApi) {
        for (Person person : producers) {
            if (person.getIdApi() == idApi) {
                return person;
            }
        }

        return null;
    }

    public Person getDirectorByIpApi(int idApi) {
        for (Person person : directors) {
            if (person.getIdApi() == idApi) {
                return person;
            }
        }

        return null;
    }

    public HasPlayed getActorByIdApi(int idApi) {
        for (HasPlayed hasPlayed : actors) {
            if (hasPlayed.getActor().getIdApi() == idApi) {
                return hasPlayed;
            }
        }

        return null;
    }

    // Ne garde que les 3 acteurs les plus importants
    public void keepOnlyMainActors() {
        this.keepOnlyMainActors(3);
    }

    // Ne garde que les nb acteurs les plus importants
    public void keepOnlyMainActors(int nb) {
        // Tri des acteurs par ordre d'importance décroissante (du plus important au moins important)
        if (this.actors.size() > 1) {
            Collections.sort(this.actors, new Comparator<HasPlayed>() {
                @Override
                public int compare(HasPlayed o1, HasPlayed o2) {
                    // Récupération du meilleur rôle de l'acteur 1
                    List<Role> roles1 = o1.getRoles();
                    Collections.sort(roles1, new Comparator<Role>() {
                        @Override
                        public int compare(Role o1, Role o2) {
                            return o1.getOrder() - o2.getOrder();
                        }
                    });
                    Role mainRole1 = roles1.get(0);

                    // Récupération du meilleur rôle de l'acteur 2
                    List<Role> roles2 = o2.getRoles();
                    Collections.sort(roles2, new Comparator<Role>() {
                        @Override
                        public int compare(Role o1, Role o2) {
                            return o1.getOrder() - o2.getOrder();
                        }
                    });
                    Role mainRole2 = roles2.get(0);

                    return mainRole1.getOrder() - mainRole2.getOrder();
                }
            });
        }

        if (this.actors.size() > nb) {
            this.actors = this.actors.subList(0, nb);
        }
    }

    public List<Person> getProducers() {
        return producers;
    }

    public void setProducers(List<Person> producers) {
        this.producers = producers;
    }

    public void addProducer(Person producer) {
        this.producers.add(producer);
    }

    public List<Person> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Person> directors) {
        this.directors = directors;
    }

    public void addDirector(Person director) {
        this.directors.add(director);
    }

    public List<HasPlayed> getActors() {
        return actors;
    }

    public void setActors(List<HasPlayed> actors) {
        this.actors = actors;
    }

    public void addActor(HasPlayed actor) {
        this.actors.add(actor);
    }

}
