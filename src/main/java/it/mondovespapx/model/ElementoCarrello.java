package it.mondovespapx.model;

public class ElementoCarrello {
    private int idProdotto;
    private String nomeProdotto;
    private double prezzo;      
    private int quantita;       
  
    public ElementoCarrello(int idProdotto, String nomeProdotto, double prezzo, int quantita) {
        this.idProdotto = idProdotto;
        this.nomeProdotto = nomeProdotto;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public double getTotale() {
        return prezzo * quantita;
    }

    //Metodi Getters e setters
    public int getIdProdotto() {
    	return idProdotto; 
    	}
    public void setIdProdotto(int idProdotto) {
    	this.idProdotto = idProdotto; 
    	}

    public String getNomeProdotto() {
    	return nomeProdotto; 
    	}
    public void setNomeProdotto(String nomeProdotto) {
    	this.nomeProdotto = nomeProdotto; 
    	}

    public double getPrezzo() {
    	return prezzo; 
    	}
    public void setPrezzo(double prezzo) {
    	this.prezzo = prezzo; 
    	}

    public int getQuantita() {
    	return quantita; 
    	}
    public void setQuantita(int quantita) {
    	this.quantita = quantita; 
    	}
}