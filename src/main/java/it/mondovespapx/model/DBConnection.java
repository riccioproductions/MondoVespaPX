package it.mondovespapx.model;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

//Gestisce la connessione al database tramite un connection pool
public class DBConnection {

    //riferimento al pool di connessioni
    private static DataSource dataSource;
    static {
        try {
            Context ctx = new InitialContext();
            //Cerca la risorsa del database configurata nel context.xml di tomcat e la assegna alla variabile dataSource
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/mondovespapx");
            
        } catch (NamingException e) {
            throw new RuntimeException("Errore lookup DataSource", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}