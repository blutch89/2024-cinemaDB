package ch.blutch.cinemadb.model.entities.cinemaelement;

public class Role {
    private String character;

    private int order;

    public Role(String character, int order) {
        this.character = character;
        this.order = order;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
