package fr.eni.javaee.gestionlistescourses.dal.jdbc;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBC {
    private static DataSource dataSource;
    static {
        Context context;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
        } catch (NamingException exception) {
            throw new RuntimeException("Impossible d'accéder à la base de données.");
        }
    }

    public static Connection getConnection() throws SQLException { return dataSource.getConnection(); }
}
