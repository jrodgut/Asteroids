package org.example.asteroids.model;

import java.util.Calendar;
import java.util.Date;

public class RankingPosition {
	private String name;

	private int points;

	private Calendar creation;

	public RankingPosition(String name, int points) {
		this.name = name;
		this.points = points;
		this.creation = Calendar.getInstance();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Calendar getCreation() {
		return creation;
	}

	public void setCreation(Calendar creation) {
		this.creation = creation;
	}
	
	public Date getCreationDate(){
		return this.creation.getTime();
	}

}
