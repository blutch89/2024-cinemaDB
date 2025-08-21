package ch.blutch.cinemadb.model.entities.cinemaelement;

import java.util.ArrayList;
import java.util.List;

import ch.blutch.cinemadb.model.entities.persons.Person;

public class HasPlayed {
    private Person actor;

    private CinemaElement cinemaElement;

    private List<Role> roles = new ArrayList<>();

    public HasPlayed(Person actor, List<Role> roles) {
        this.actor = actor;
        this.roles = roles;
    }

    public HasPlayed(CinemaElement cinemaElement, List<Role> roles) {
        this.cinemaElement = cinemaElement;
        this.roles = roles;
    }

    public HasPlayed() {
    }

    public Person getActor() {
        return actor;
    }

    public void setActor(Person actor) {
        this.actor = actor;
    }

    public CinemaElement getCinemaElement() {
        return cinemaElement;
    }

    public void setCinemaElement(CinemaElement cinemaElement) {
        this.cinemaElement = cinemaElement;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
