package fr.eni.javaee.gestionlistescourses.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Liste {
	private static final Map<Integer, Liste> LISTES = new HashMap<>();
	private int identifier;
	private String name;
	private List<Article> articles = new ArrayList<>();


	// CONSTRUCTORS

	public Liste() {}

	public Liste(String name) {
		setName(name);
	}

	public Liste(int identifier, String name) {
		this(name);
		setIdentifier(identifier);
	}

	public Liste(int identifier, String name, List<Article> articles) {
		this(identifier, name);
		setArticles(articles);
	}


	public void addArticle(Article article) {
		this.articles.add(article);
	}


	@Override
	public String toString() {
		return "Liste de courses : " + name + " (" + identifier + ")";
	}


	// GETTERS & SETTERS

	public static Liste getListe(int identifier) {
		return LISTES.get(identifier);
	}

	public static Map getListes() { return LISTES; }

	public int getIdentifier() { return this.identifier; }

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
		LISTES.put(identifier, this);
	}

	public String getName() { return this.name; }

	public void setName(String name) { this.name = name; }

	public List<Article> getArticles() { return this.articles; }

	public void setArticles(List<Article> articles) { this.articles = articles; }

}
