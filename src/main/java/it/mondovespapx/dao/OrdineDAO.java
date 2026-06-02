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
        // Query per estrarre gli ordini dal più recente al più vecchio
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
        //Esegue una JOIN con la tabella prodotti per poter mostrare il nome del ricambio
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
        //Disabilita il salvataggio automatico per far eseguire le query solo quando viene chiamato commit
        con.setAutoCommit(false);
        try {
            //Inserisce i dati generali dell'ordine (totale, utente, stato)
            String sqlOrdine = "INSERT INTO ordini (id_utente, totale, stato) VALUES (?, ?, 'in attesa')";
            //RETURN_GENERATED_KEYS chiede al database di restituirci l'ID appena creato
            PreparedStatement ps = con.prepareStatement(sqlOrdine, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, o.getIdUtente());
            ps.setDouble(2, o.getTotale());
            ps.executeUpdate();

            //Legge l'ID generato per il nuovo ordine
            ResultSet rs = ps.getGeneratedKeys();
            int idOrdine = 0;
            if (rs.next()) {
                idOrdine = rs.getInt(1);
            }

            //Prepara la query per inserire i singoli prodotti legandoli all'ID dell'ordine appena creato
            String sqlDettaglio = "INSERT INTO dettagli_ordine (id_ordine, id_prodotto, quantita, prezzo_unitario) VALUES (?, ?, ?, ?)";
            PreparedStatement psD = con.prepareStatement(sqlDettaglio);

            //Scorre il carrello e inserisce i prodotti uno alla volta
            for (DettaglioOrdine d : dettagli) {
                psD.setInt(1, idOrdine);
                psD.setInt(2, d.getIdProdotto());
                psD.setInt(3, d.getQuantita());
                psD.setDouble(4, d.getPrezzoUnitario());
                psD.executeUpdate();
            }

            //Conferma le operazioni: scrive definitivamente ordine e dettagli nel database
            con.commit();

            //Chiusura risorse
            rs.close();
            ps.close();
            psD.close();

        } catch (SQLException e) {
            //Se fallisce l'inserimento di una qualsiasi riga annulla l'intera operazione evitando ordini a metà
            con.rollback();
            throw e;
        } finally {
            //Ripristina il comportamento standard della connessione prima di chiuderla
            con.setAutoCommit(true);
            con.close();
        }
    }
}