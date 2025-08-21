package ch.blutch.cinemadb.model.entities.persons;

import ch.blutch.cinemadb.model.enums.PersonGender;

public class Person {
    protected int idApi;

    protected PersonGender personGender;

    protected String name;

    protected String originalName;

    protected String image;

    protected String job;

    protected float popularity;

    public int getIdApi() {
        return idApi;
    }

    public void setIdApi(int idApi) {
        this.idApi = idApi;
    }

    public String getPersonGender() {
        return personGender.toString();
    }

    public void setPersonGender(int genderNo) {
        this.personGender = PersonGender.fromValue(genderNo);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        switch (job) {
            case "Acting":
                this.job = "Acteur";
                break;
            case "Directing":
                this.job = "Réalisateur";
                break;
            case "Production":
                this.job = "Producteur";
                break;
            default:
                this.job = job;
                break;
        }
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }
}
