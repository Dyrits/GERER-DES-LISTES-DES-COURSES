package fr.eni.javaee.gestionlistescourses.dal;

import fr.eni.javaee.gestionlistescourses.bo.Liste;
import fr.eni.javaee.gestionlistescourses.dal.jdbc.DAOListeJDBC;

public abstract class DAOFactory {
	
	public static DAO<Liste> getListeDAO() { return new DAOListeJDBC(); }
}
	