package ch.blutch.cinemadb.model.entities.cinemaelement.my;

import java.util.Date;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@MappedSuperclass
public abstract class MyCinemaElement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT UNSIGNED")
	protected int id;
	
	@Column(name = "id_api", columnDefinition = "INT UNSIGNED", nullable = false)
	@Min(value = 1, message = "L'id de l'élément n'est pas valide.")
	@NotNull(message = "L'id de l'élément doit être rempli.")
	protected int idApi;
	
	@Column(name = "view_date", columnDefinition = "DATE", nullable = false)
	@Past(message = "La date de visionnage doit être dans le passé.")
	@NotNull(message = "La date de visionnage doit être remplie.")
	protected Date viewDate;
	
	@Column(name = "my_note", columnDefinition = "FLOAT", nullable = false)
	@Range(min = 1, max = 10, message = "La note doit être comprise entre 1 et 10.")
	protected float myNote;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdApi() {
		return idApi;
	}

	public void setIdApi(int idApi) {
		this.idApi = idApi;
	}

	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public float getMyNote() {
		return myNote;
	}

	public void setMyNote(float myNote) {
		this.myNote = myNote;
	}
	
}
