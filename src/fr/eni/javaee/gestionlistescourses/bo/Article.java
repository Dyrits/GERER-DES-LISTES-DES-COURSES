package fr.eni.javaee.gestionlistescourses.bo;

import java.io.Serializable;

public class Article {
	private int identifier;
	private String name;
	private boolean isChecked;


	// CONSTRUCTORS

	public Article(String name) {
		setName(name);
	}

	public Article(int identifier, String name) {
		this(name);
		setIdentifier(identifier);
	}

	public Article(int identifier, String name, boolean isChecked) {
		this(identifier, name);
		setChecked(isChecked);
	}




	// GETTERS & SETTERS

	public int getIdentifier() { return this.identifier; }

	public void setIdentifier(int identifier) { this.identifier = identifier; }

	public String getName() { return this.name; }

	public void setName(String name) { this.name = name; }

	public boolean isChecked() { return this.isChecked; }

	public String getChecked() { return this.isChecked ? "checked" : null; }

	public void setChecked(boolean checked) { isChecked = checked; }
}
