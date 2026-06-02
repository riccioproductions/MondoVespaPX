package it.mondovespapx.dao;

import it.mondovespapx.model.HashUtil;
import it.mondovespapx.model.DBConnection;
import it.mondovespapx.model.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {
    //Verifica le credenziali e restituisce l'utente se i dati coincidono
    public Utente login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM utenti WHERE email = ? AND password = ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, HashUtil.sha512(password));
        ResultSet rs = ps.executeQuery();
        Utente u = null;
        
        //Se la query trova una tabella, estrae i dati e popola l'oggetto Utente
        if (rs.next()) {
            u = new Utente();
            u.setId(rs.getInt("id"));
            u.setNome(rs.getString("nome"));
            u.setCognome(rs.getString("cognome"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setRuolo(rs.getString("ruolo"));
            u.setIndirizzo(rs.getString("indirizzo"));
            u.setMetodoPagamento(rs.getString("metodo_pagamento"));
        }
        rs.close();
        ps.close();
        con.close();
        return u;
    }

    //Inserisce un nuovo utente nel database al momento della registrazione
    public void registra(Utente u) throws SQLException {
        String sql = "INSERT INTO utenti (nome, cognome, email, password, ruolo) VALUES (?, ?, ?, ?, 'user')";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, u.getNome());
        ps.setString(2, u.getCognome());
        ps.setString(3, u.getEmail());
        ps.setString(4, HashUtil.sha512(u.getPassword()));
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    //Controlla se un'email è già presente nel database per evitare registrazioni doppie
    public boolean emailEsiste(String email) throws SQLException {
        String sql = "SELECT id FROM utenti WHERE email = ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        
        //Se true allora c'è un risultato
        boolean esiste = rs.next();
        rs.close();
        ps.close();
        con.close();
        return esiste;
    }
    
    //Aggiorna il profilo dell'utente
    public void aggiorna(Utente u) throws SQLException {
        String sql = "UPDATE utenti SET nome=?, cognome=?, email=?, indirizzo=?, metodo_pagamento=? WHERE id=?";
        //Aggiorna i campi nel database
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, u.getNome());
        ps.setString(2, u.getCognome());
        ps.setString(3, u.getEmail());
        ps.setString(4, u.getIndirizzo());
        ps.setString(5, u.getMetodoPagamento());
        ps.setInt(6, u.getId());
        ps.executeUpdate();

        ps.close();
        con.close();
    }
}