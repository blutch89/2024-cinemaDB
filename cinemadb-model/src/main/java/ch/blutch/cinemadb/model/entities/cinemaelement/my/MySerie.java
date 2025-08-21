package ch.blutch.cinemadb.model.entities.cinemaelement.my;

import org.hibernate.annotations.DynamicUpdate;

import ch.blutch.cinemadb.model.entities.cinemaelement.Serie;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "my_serie")
@DynamicUpdate
public class MySerie extends MyCinemaElement implements Comparable<MySerie> {
	
	@Transient
	private Serie serie;

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	@Override
	public int compareTo(MySerie o) {
		if (serie == null) {
			return -1;
		}
		
		if (o.getSerie() == null) {
			return 1;
		}
		
		return serie.getTitle().compareToIgnoreCase(o.getSerie().getTitle());
	}
	
}
