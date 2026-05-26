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
}