package it.mondovespapx.dao;

import it.mondovespapx.model.Categoria;
import it.mondovespapx.model.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    //Metodo che restituisce una lista di tutte le categorie presenti nel DB
    public List<Categoria> getAllCategorie() throws SQLException {
        //Lista di categorie
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorie";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        //Estrazione risultati
        while (rs.next()) {
            Categoria c = new Categoria();
       
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setDescrizione(rs.getString("descrizione"));
            
            lista.add(c);
        }
        rs.close();
        ps.close();
        con.close();
        return lista;
    }
    
 //Metodo che restituisce una singola categoria dato il suo ID
    public Categoria getCategoriaById(int id) throws SQLException {
        Categoria c = null;
        String sql = "SELECT * FROM categorie WHERE id = ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();        
        if (rs.next()) {
            c = new Categoria();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setDescrizione(rs.getString("descrizione"));
        }        
        rs.close();
        ps.close();
        con.close();      
        return c; //Ritorna l'oggetto o null se non trovato
    }
    
    //Metodo che inserisce una nuova categoria
    public void inserisci(Categoria c) throws SQLException {
        String sql = "INSERT INTO categorie (nome, descrizione) VALUES (?, ?)";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getDescrizione());
        ps.executeUpdate();
        ps.close();
        con.close();
    }
    
    //Metodo che aggiorna una categoria esistente
    public void aggiorna(Categoria c) throws SQLException {
        String sql = "UPDATE categorie SET nome=?, descrizione=? WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getDescrizione());
        ps.setInt(3, c.getId());
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    //Metodo che elimina una categoria
    public void elimina(int id) throws SQLException {
        String sql = "DELETE FROM categorie WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        con.close();
    }
}