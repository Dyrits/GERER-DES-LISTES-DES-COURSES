package fr.eni.javaee.gestionlistescourses.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.gestionlistescourses.ListeException;
import fr.eni.javaee.gestionlistescourses.bo.Article;
import fr.eni.javaee.gestionlistescourses.bo.Liste;
import fr.eni.javaee.gestionlistescourses.dal.CodesExceptionDAL;
import fr.eni.javaee.gestionlistescourses.dal.DAO;

public class DAOListeJDBC implements DAO<Liste> {
	private static final String SELECT_ALL =
			"SELECT id, nom " +
					"FROM LISTES";
	private static final String SELECT_BY_ID =
			"SELECT " +
					"LISTES.id, " +
					"LISTES.nom, " +
					"ARTICLES.id AS id_article, " +
					"ARTICLES.nom AS nom_article, " +
					"coche " +
					"FROM LISTES " +
					"LEFT JOIN ARTICLES ON LISTES.id = ARTICLES.id_liste " +
					"WHERE LISTES.id = ?";
	private static final String INSERT_LISTE =
			"INSERT INTO LISTES(nom) VALUES (?)";
	private static final String INSERT_ARTICLE =
			"INSERT INTO ARTICLES(nom, id_liste) VALUES (?, ?)";
	private static final String DELETE_ARTICLE =
			"DELETE FROM ARTICLES WHERE id = ?";
	private static final String DELETE_LISTE =
			"DELETE FROM LISTES where id = ?";
	private static final String CHECK_ARTICLE =
			"UPDATE ARTICLES SET coche = 1 WHERE id = ?";
	private static final String UNCHECK_ARTICLE =
			"UPDATE ARTICLES SET coche = 0 WHERE id = ?";
	private static final String UNCHECK_ALL_ARTICLES =
			"UPDATE ARTICLES SET coche = 0 WHERE id_liste = ?";

	/**
	 * Insert a list and all its articles.
	 */
	@Override
	public void insert(Liste liste) throws ListeException {
		if (liste == null) {
			throw new ListeException(CodesExceptionDAL.INSERT_OBJET_NULL);
		}
		try (Connection connection = JDBC.getConnection()) {
			try {
				connection.setAutoCommit(false);
				PreparedStatement statement;
				if (liste.getIdentifier() == 0) {
					statement = connection.prepareStatement(INSERT_LISTE, PreparedStatement.RETURN_GENERATED_KEYS);
					statement.setString(1, liste.getName());
					statement.executeUpdate();
					ResultSet resultSet = statement.getGeneratedKeys();
					if (resultSet.next()) {
						liste.setIdentifier(resultSet.getInt(1)); // An auto-generated identifier is set instead of 0.
					}
					resultSet.close();
					statement.close();
				}
				// At first, I was thinking about adding multiple articles.
				// The function will be reworked to just add the article one by one.
				if (!liste.getArticles().isEmpty()) {
					deleteContentFrom(liste);
					for (Article article : liste.getArticles()) {
						statement = connection.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
						statement.setString(1, article.getName());
						statement.setInt(2, liste.getIdentifier());
						statement.executeUpdate();
						ResultSet resultSet = statement.getGeneratedKeys();
						if (resultSet.next()) {
							article.setIdentifier(resultSet.getInt(1));
						}
						resultSet.close();
						statement.close();
					}
				}
				connection.commit();
			} catch (Exception exception) {
				exception.printStackTrace();
				ListeException listeException = new ListeException(CodesExceptionDAL.INSERT_OBJET_FAIL);
				try { connection.rollback(); }
				catch (SQLException sqlException) {
					exception.printStackTrace();
					listeException.addCode(CodesExceptionDAL.CONNECTION_ROLLBACK_ERROR);
				}
				throw listeException;
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new ListeException(CodesExceptionDAL.CONNECTION_ERROR);
		}
	}

	/**
	 * Delete a list identified by its identifier and all its article.
	 */
	@Override
	public void delete(int identifier) throws ListeException {
		try (Connection connection = JDBC.getConnection()) {
			Liste liste = Liste.getListe(identifier);
			deleteContentFrom(liste);
			PreparedStatement statement = connection.prepareStatement(DELETE_LISTE);
			statement.setInt(1, identifier);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throwListeSQLExceptionDAL(exception, CodesExceptionDAL.SUPPRESSION_LISTE_ERROR);
		}
	}

	/**
	 * Delete an article by identifier.
	 */
	@Override
	public void deleteContent(int identifier) throws ListeException {
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(DELETE_ARTICLE);
			statement.setInt(1, identifier);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throwListeSQLExceptionDAL(exception, CodesExceptionDAL.SUPPRESSION_ARTICLE_ERROR);
		}
	}

	public void deleteContent(Article article) throws ListeException {
		deleteContent(article.getIdentifier());
	}

	/**
	 * Delete all articles from a list.
	 */
	@Override
	public void deleteContentFrom(int identifier) throws ListeException {
			Liste liste = Liste.getListe(identifier);
			if (liste != null) {
				for (Article article : liste.getArticles()) { deleteContent(article); }
			}
	}

	public void deleteContentFrom(Liste liste) throws ListeException {
		deleteContentFrom(liste.getIdentifier());
	}

	/**
	 * Select a list (and its articles) by identifier.
	 */
	@Override
	public Liste selectById(int identifier) throws ListeException {
		Liste liste = null;
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
			statement.setInt(1, identifier);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (liste == null) {
					liste = new Liste(resultSet.getInt("id"), resultSet.getString("nom"));
				}
				if (resultSet.getString("nom_article") != null) {
					liste.addArticle(new Article(
							resultSet.getInt("id_article"),
							resultSet.getString("nom_article"),
							resultSet.getBoolean("coche")
					));
				}
			}
		} catch (SQLException exception) {
			throwListeSQLExceptionDAL(exception, CodesExceptionDAL.LECTURE_LISTE_FAIL);
		}
		if (liste == null || liste.getIdentifier() == 0) {
			throw new ListeException(CodesExceptionDAL.LECTURE_LISTE_INEXISTANTE);
		}
		return liste;
	}

	/**
	 * Select all lists.
	 */
	@Override
	public List<Liste> selectAll() throws ListeException {
		List<Liste> listes = new ArrayList<>();
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				listes.add(new Liste(resultSet.getInt("id"), resultSet.getString("nom")));
			}
		} catch (SQLException exception) {
			throwListeSQLExceptionDAL(exception, CodesExceptionDAL.LECTURE_LISTES_FAIL);
		}
		return listes;
	}

	/**
	 * Check the checkbox of an article according to its identifier..
	 */
	@Override
	public void checkContent(int identifierContent) throws ListeException {
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(CHECK_ARTICLE);
			statement.setInt(1, identifierContent);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throwListeSQLExceptionDAL(exception, CodesExceptionDAL.CHECK_ARTICLE_ERROR);
		}
	}

	/**
	 * Uncheck the checkbox of one or more articles according to an identifier and a SQL query.
	 */
	private void uncheckContentBy(int identifier, String query, int codeExceptionDAL) throws ListeException {
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, identifier);
			statement.executeUpdate();
		} catch (SQLException exception) {
			throwListeSQLExceptionDAL(exception, codeExceptionDAL);
		}
	}

	/**
	 * Uncheck the checkbox of an article according to its identifier.
	 */
	@Override
	public void uncheckContent(int identifierContent) throws ListeException {
		uncheckContentBy(identifierContent, UNCHECK_ARTICLE, CodesExceptionDAL.UNCHECK_ARTICLE_ERROR);
	}

	/**
	 * Uncheck the checkbox of all articles of a list according to its identifier..
	 */
	@Override
	public void uncheckAllContent(int identifier) throws ListeException {
		uncheckContentBy(identifier, UNCHECK_ALL_ARTICLES, CodesExceptionDAL.UNCHECK_ALL_ARTICLE_ERROR);
	}

	private void throwListeSQLExceptionDAL(Exception exception, int codeExceptionDAL) throws ListeException {
		exception.printStackTrace();
		ListeException listeException = new ListeException(CodesExceptionDAL.CONNECTION_ERROR);
		listeException.addCode(codeExceptionDAL);
		throw listeException;
	}
}








