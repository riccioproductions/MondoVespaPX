package it.mondovespapx.dao;

import it.mondovespapx.model.DBConnection;
import it.mondovespapx.model.Prodotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {
	//Restituisce la lista di tutti i prodotti
    public List<Prodotto> getAllProdotti() throws SQLException {
        List<Prodotto> lista = new ArrayList<>();
        String sql = "SELECT * FROM prodotti";

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        //Inserisce in una lista come oggetti di tipo prodotto il resultset della query
        while (rs.next()) {
            Prodotto p = new Prodotto();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setDescrizione(rs.getString("descrizione"));
            p.setPrezzo(rs.getDouble("prezzo"));
            p.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
            p.setImmagine(rs.getString("immagine"));
            p.setIdCategoria(rs.getInt("id_categoria"));
            lista.add(p);
        }

        rs.close();
        ps.close();
        con.close();

        return lista;
    }
    
    //Restituisce i prodotti filtrandoli per una determinata categoria (per id)
    public List<Prodotto> getProdottiByCategoria(int idCategoria) throws SQLException {
        List<Prodotto> lista = new ArrayList<>();
        String sql = "SELECT * FROM prodotti WHERE id_categoria = ?";

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idCategoria);
        ResultSet rs = ps.executeQuery();
        
      //Inserisce in una lista come oggetti di tipo prodotto il resultset della query
        while (rs.next()) {
            Prodotto p = new Prodotto();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setDescrizione(rs.getString("descrizione"));
            p.setPrezzo(rs.getDouble("prezzo"));
            p.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
            p.setImmagine(rs.getString("immagine"));
            p.setIdCategoria(rs.getInt("id_categoria"));
            lista.add(p);
        }

        rs.close();
        ps.close();
        con.close();

        return lista;
    }
    
    //Restituisce un prodotto in base ad un id
    public Prodotto getProdottoById(int id) throws SQLException {
        String sql = "SELECT * FROM prodotti WHERE id = ?";

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Prodotto p = null;
        if (rs.next()) {
            p = new Prodotto();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setDescrizione(rs.getString("descrizione"));
            p.setPrezzo(rs.getDouble("prezzo"));
            p.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
            p.setImmagine(rs.getString("immagine"));
            p.setIdCategoria(rs.getInt("id_categoria"));
        }

        rs.close();
        ps.close();
        con.close();

        return p;
    }
}