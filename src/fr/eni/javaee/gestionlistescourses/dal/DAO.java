package fr.eni.javaee.gestionlistescourses.dal;

import java.sql.SQLException;
import java.util.List;

import fr.eni.javaee.gestionlistescourses.ListeException;

public interface DAO<T> {

	public void insert(T object) throws ListeException;

	public void delete(int identifier) throws ListeException;

	public T selectById(int identifier) throws ListeException;

	public List<T> selectAll() throws ListeException;

	public void deleteContent(int identifierContent) throws ListeException;

	public void deleteContentFrom(int identifier) throws ListeException;

	public void checkContent(int identifierContent) throws ListeException;

	public void uncheckContent(int identifierContent) throws ListeException;

	public void uncheckAllContent(int identifier) throws ListeException;
}
