package com.rahul.conference;

public class Conference {

	private String acronym;

	private String name;

	private String location;

	public Conference() {

	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return acronym + "\t" + name + "\t" + location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
