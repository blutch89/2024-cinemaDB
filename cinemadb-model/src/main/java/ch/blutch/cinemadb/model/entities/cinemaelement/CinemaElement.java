/**
 * 
 */
package ch.blutch.cinemadb.model.entities.cinemaelement;

import java.util.Date;
import java.util.List;

public abstract class CinemaElement {

	protected int idApi;
	
	protected String title;
	
	protected String image;
	
	protected String synopsis;
	
	protected String releaseDate;
	
	protected float spectatorNote;

	protected Credits credits;

	public int getIdApi() {
		return idApi;
	}

	public void setIdApi(int idApi) {
		this.idApi = idApi;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public float getSpectatorNote() {
		return spectatorNote;
	}

	public void setSpectatorNote(float spectatorNote) {
		this.spectatorNote = spectatorNote;
	}

	public Credits getCredits() {
		return credits;
	}

	public void setCredits(Credits credits) {
		this.credits = credits;
	}
	
}
