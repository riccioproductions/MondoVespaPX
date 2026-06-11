package it.mondovespapx.dao;

import it.mondovespapx.model.DBConnection;
import it.mondovespapx.model.Ordine;
import it.mondovespapx.model.DettaglioOrdine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {
    //Recupera lo storico degli ordini effettuati da uno specifico utente
    public List<Ordine> getOrdiniByUtente(int idUtente) throws SQLException {
        List<Ordine> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordini WHERE id_utente = ? ORDER BY data_ordine DESC";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idUtente);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Ordine o = new Ordine();
            o.setId(rs.getInt("id"));
            o.setIdUtente(rs.getInt("id_utente"));
            o.setDataOrdine(rs.getTimestamp("data_ordine"));
            o.setStato(rs.getString("stato"));
            o.setTotale(rs.getDouble("totale"));
            //Righe aggiunte per mappare i campi mancanti
            o.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
            o.setMetodoPagamento(rs.getString("metodo_pagamento"));
            lista.add(o);
        }
        rs.close();
        ps.close();
        con.close();
        return lista;
    }

    //Recupera i prodotti che compongono un ordine specifico
    public List<DettaglioOrdine> getDettagliByOrdine(int idOrdine) throws SQLException {
        List<DettaglioOrdine> lista = new ArrayList<>();
        String sql = "SELECT d.*, p.nome FROM dettagli_ordine d " +
                     "JOIN prodotti p ON d.id_prodotto = p.id " +
                     "WHERE d.id_ordine = ?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idOrdine);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            DettaglioOrdine d = new DettaglioOrdine();
            d.setId(rs.getInt("id"));
            d.setIdOrdine(rs.getInt("id_ordine"));
            d.setIdProdotto(rs.getInt("id_prodotto"));
            d.setNomeProdotto(rs.getString("nome"));
            d.setQuantita(rs.getInt("quantita"));
            d.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
            lista.add(d);
        }
        rs.close();
        ps.close();
        con.close();
        return lista;
    }

    //Salva un nuovo ordine e i relativi dettagli nel database
    public void inserisciOrdine(Ordine o, List<DettaglioOrdine> dettagli) throws SQLException {
        Connection con = DBConnection.getConnection();
        con.setAutoCommit(false);
        try {
            String sqlOrdine = "INSERT INTO ordini (id_utente, totale, stato, indirizzo_spedizione, metodo_pagamento) " +
                               "VALUES (?, ?, 'in attesa', ?, ?)";
            PreparedStatement ps = con.prepareStatement(sqlOrdine, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, o.getIdUtente());
            ps.setDouble(2, o.getTotale());
            ps.setString(3, o.getIndirizzoSpedizione());
            ps.setString(4, o.getMetodoPagamento());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int idOrdine = 0;
            if (rs.next()) {
                idOrdine = rs.getInt(1);
            }
            String sqlDettaglio = "INSERT INTO dettagli_ordine (id_ordine, id_prodotto, quantita, prezzo_unitario) VALUES (?, ?, ?, ?)";
            PreparedStatement psD = con.prepareStatement(sqlDettaglio);
            String sqlQuantita = "UPDATE prodotti SET quantita_disponibile = quantita_disponibile - ? " +
                                 "WHERE id = ? AND quantita_disponibile >= ?";
            PreparedStatement psQ = con.prepareStatement(sqlQuantita);

            for (DettaglioOrdine d : dettagli) {
                psD.setInt(1, idOrdine);
                psD.setInt(2, d.getIdProdotto());
                psD.setInt(3, d.getQuantita());
                psD.setDouble(4, d.getPrezzoUnitario());
                psD.executeUpdate();

                psQ.setInt(1, d.getQuantita());
                psQ.setInt(2, d.getIdProdotto());
                psQ.setInt(3, d.getQuantita());
                psQ.executeUpdate();
            }
            con.commit();
            rs.close();
            ps.close();
            psD.close();
            psQ.close();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }
    
    //Restituisce tutti gli ordini effettuati
    public List<Ordine> getAllOrdini() throws SQLException {
        List<Ordine> lista = new ArrayList<>();
        String sql = "SELECT o.*, u.nome, u.cognome FROM ordini o " +
                     "JOIN utenti u ON o.id_utente = u.id " +
                     "ORDER BY o.data_ordine DESC";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Ordine o = new Ordine();
            o.setId(rs.getInt("id"));
            o.setIdUtente(rs.getInt("id_utente"));
            o.setNomeUtente(rs.getString("nome") + " " + rs.getString("cognome"));
            o.setDataOrdine(rs.getTimestamp("data_ordine"));
            o.setStato(rs.getString("stato"));
            o.setTotale(rs.getDouble("totale"));
            o.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
            o.setMetodoPagamento(rs.getString("metodo_pagamento"));
            lista.add(o);
        }
        rs.close();
        ps.close();
        con.close();

        return lista;
    }
    
    //Aggiorna lo stato dell'ordine
    public void aggiornaStato(int idOrdine, String stato) throws SQLException {
        String sql = "UPDATE ordini SET stato=? WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, stato);
        ps.setInt(2, idOrdine);
        ps.executeUpdate();
        ps.close();
        con.close();
    }
    
    // Restituisce gli ordini filtrati per data e cliente
    public List<Ordine> getOrdiniFiltrati(String dataInizio, String dataFine, String cliente) throws SQLException {
        List<Ordine> lista = new ArrayList<>();
        String sql = "SELECT o.*, u.nome, u.cognome FROM ordini o " +
                     "JOIN utenti u ON o.id_utente = u.id " +
                     "WHERE 1=1 ";
        if (dataInizio != null && !dataInizio.isEmpty()) {
            sql += "AND DATE(o.data_ordine) >= ? ";
        }
        if (dataFine != null && !dataFine.isEmpty()) {
            sql += "AND DATE(o.data_ordine) <= ? ";
        }
        if (cliente != null && !cliente.isEmpty()) {
            sql += "AND (u.nome LIKE ? OR u.cognome LIKE ? OR u.email LIKE ?) ";
        }
        sql += "ORDER BY o.data_ordine DESC";
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        int i = 1;
        if (dataInizio != null && !dataInizio.isEmpty()) {
            ps.setString(i++, dataInizio);
        }
        if (dataFine != null && !dataFine.isEmpty()) {
            ps.setString(i++, dataFine);
        }
        if (cliente != null && !cliente.isEmpty()) {
            ps.setString(i++, "%" + cliente + "%");
            ps.setString(i++, "%" + cliente + "%");
            ps.setString(i++, "%" + cliente + "%");
        }
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Ordine o = new Ordine();
            o.setId(rs.getInt("id"));
            o.setIdUtente(rs.getInt("id_utente"));
            o.setNomeUtente(rs.getString("nome") + " " + rs.getString("cognome"));
            o.setDataOrdine(rs.getTimestamp("data_ordine"));
            o.setStato(rs.getString("stato"));
            o.setTotale(rs.getDouble("totale"));
            o.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
            o.setMetodoPagamento(rs.getString("metodo_pagamento"));
            lista.add(o);
        }
        rs.close();
        ps.close();
        con.close();
        return lista;
    }
}