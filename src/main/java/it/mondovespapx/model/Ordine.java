package it.mondovespapx.model;

import java.sql.Timestamp;
//Intestazione di un ordine effettuato sul sito
public class Ordine {
    private int id;                 
    private int idUtente;           
    private Timestamp dataOrdine;   
    private String stato;          
    private double totale;   
    private String nomeUtente;
    private String indirizzoSpedizione;
    private String metodoPagamento;

    //Metodi Getters e setters

    public int getId() {
    	return id; 
    	}
    public void setId(int id) {
    	this.id = id; 
    	}

    public int getIdUtente() {
    	return idUtente; 
    	}
    public void setIdUtente(int idUtente) {
    	this.idUtente = idUtente; 
    	}

    public Timestamp getDataOrdine() {
    	return dataOrdine; 
    	}
    
    public void setDataOrdine(Timestamp dataOrdine) {
    	this.dataOrdine = dataOrdine; 
    	}

    public String getStato() {
    	return stato; 
    	}
    public void setStato(String stato) {
    	this.stato = stato; 
    	}

    public double getTotale() {
    	return totale; 
    	}
    
    public void setTotale(double totale) {
    	this.totale = totale; 
    	}
    
    public String getNomeUtente() {
    	return nomeUtente; 
    	}
    
    public void setNomeUtente(String nomeUtente) {
    	this.nomeUtente = nomeUtente; 
    	}

    public String getIndirizzoSpedizione() {
    	return indirizzoSpedizione; 
    	}
    
    public void setIndirizzoSpedizione(String indirizzoSpedizione) {
    	this.indirizzoSpedizione = indirizzoSpedizione; 
    	}

    public String getMetodoPagamento() {
    	return metodoPagamento; 
    	}
    
    public void setMetodoPagamento(String metodoPagamento) {
    	this.metodoPagamento = metodoPagamento; 
    	}
}