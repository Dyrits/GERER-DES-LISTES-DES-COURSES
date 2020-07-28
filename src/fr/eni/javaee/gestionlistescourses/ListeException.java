package fr.eni.javaee.gestionlistescourses;

import java.util.ArrayList;
import java.util.List;

public class ListeException extends Exception{
	private List<Integer> codes;

	public ListeException() {
		super();
		this.codes = new ArrayList<>();
	}

	public ListeException(int code) {
		super();
		this.codes = new ArrayList<>();
		this.codes.add(code);
	}

	public ListeException(String message, ListeException exception) {
		super(message);
		this.codes = new ArrayList<>();
		this.codes.addAll(exception.getCodes());
	}

	public void addCode(int code) { if (!this.codes.contains(code)) { this.codes.add(code); } }

	public boolean hasCodes() { return this.codes.size() > 0; }

	public List<Integer> getCodes() { return this.codes; }

	@Override
	public String getMessage() {
		return "Liste Exception | " + super.getMessage();
	}
}
