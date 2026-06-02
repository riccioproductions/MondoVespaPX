package it.mondovespapx.model;

//Singola riga di un ordine
public class DettaglioOrdine {

    //Variabili che mappano le colonne della tabella 'dettagli_ordine' nel database
    private int id;                 
    private int idOrdine;          
    private int idProdotto;       
    private String nomeProdotto;   
    private int quantita;           
    private double prezzoUnitario;  

    //Metodi getters e setters
    public int getId() {
    	return id; 
    	}
    public void setId(int id) {
    	this.id = id; 
    	}

    public int getIdOrdine() {
    	return idOrdine; 
    	}
    public void setIdOrdine(int idOrdine) {
    	this.idOrdine = idOrdine; 
    	}

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

    public int getQuantita() {
    	return quantita; 
    	}
    public void setQuantita(int quantita) {
    	this.quantita = quantita; 
    	}

    public double getPrezzoUnitario() {
    	return prezzoUnitario; 
    	}
    public void setPrezzoUnitario(double prezzoUnitario) {
    	this.prezzoUnitario = prezzoUnitario; 
    	}
}