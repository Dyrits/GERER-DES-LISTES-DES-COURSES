package fr.eni.javaee.gestionlistescourses.bll;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import fr.eni.javaee.gestionlistescourses.ListeException;
import fr.eni.javaee.gestionlistescourses.bo.Article;
import fr.eni.javaee.gestionlistescourses.bo.Liste;
import fr.eni.javaee.gestionlistescourses.dal.DAOFactory;
import fr.eni.javaee.gestionlistescourses.dal.DAO;

public class ListeManager {
	private final DAO<Liste> DAO;

	// CONSTRUCTOR
	public ListeManager() {
		this.DAO = DAOFactory.getListeDAO();
	}

	public List<Liste> selectAllListes() throws ListeException { return this.DAO.selectAll(); }

	public void setListes() throws ListeException {
		for (Liste liste : selectAllListes()) {
			liste = selectListe(liste.getIdentifier());
			liste.setIdentifier(liste.getIdentifier());
		}
	}

	public Liste selectListe(int identifier) throws ListeException {
		return this.DAO.selectById(identifier);
	}

	/**
	 * Add a new list.
	 */
	public Liste addListe(int identifier, String name) throws ListeException {
		checkName(name, CodesExceptionBLL.REGLE_LISTE_NOM_ERREUR);
		Liste liste = new Liste(identifier, name);
		this.DAO.insert(liste);
		Liste.getListes().remove(0); // The key 0 is removed. It was used to create new list.
		return liste;
	}

	/**
	 * Add an article to an existing list.
	 */
	public void addArticle(int identifier, String name) throws ListeException {
		checkName(name, CodesExceptionBLL.REGLE_ARTICLE_NOM_ERREUR);
		Liste liste = Liste.getListe(identifier);
		if (liste != null) {
			Article article = new Article(name.trim());
			liste.addArticle(article);
			this.DAO.insert(liste);
		}
	}


	// CALLS TO DAL

	public void deleteArticle(int identifier) throws ListeException {
		this.DAO.deleteContent(identifier);
	}

	public void deleteListe(int identifier) throws ListeException {
		this.DAO.delete(identifier);
	}

	public void checkArticle(int identifier) throws ListeException {
		this.DAO.checkContent(identifier);
	}

	public void uncheckArticle(int identifier) throws ListeException {
		this.DAO.uncheckContent(identifier);
	}

	public void uncheckAllArticles(int identifier) throws ListeException {
		this.DAO.uncheckAllContent(identifier);
	}

	private void checkName(String name, int codeExceptionBLL) throws ListeException {
		if (name == null || name.trim().length() > 50) {
			throw new ListeException(codeExceptionBLL);
		}
	}
}
