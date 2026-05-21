package it.mondovespapx.model;

public class Prodotto {

    private int id;
    private String nome;
    private String descrizione;
    private double prezzo;
    private int quantitaDisponibile;
    private String immagine;
    private int idCategoria;
    
    //Metodi getters e setters
    public int getId() {
    	return id; 
    	}
    
    public void setId(int id) {
    	this.id = id; 
    	}

    public String getNome() {
    	return nome; 
    	}
    
    public void setNome(String nome) {
    	this.nome = nome; 
    	}

    public String getDescrizione() {
    	return descrizione; 
    	}
    
    public void setDescrizione(String descrizione) {
    	this.descrizione = descrizione; 
    	}

    public double getPrezzo() {
    	return prezzo; 
    	}
    
    public void setPrezzo(double prezzo) {
    	this.prezzo = prezzo; 
    	}

    public int getQuantitaDisponibile() {
    	return quantitaDisponibile; 
    	}
    
    public void setQuantitaDisponibile(int quantitaDisponibile) {
    	this.quantitaDisponibile = quantitaDisponibile; 
    	}

    public String getImmagine() {
    	return immagine; 
    	}
    
    public void setImmagine(String immagine) {
    	this.immagine = immagine; 
    	}

    public int getIdCategoria() {
    	return idCategoria; 
    	}
    
    public void setIdCategoria(int idCategoria) {
    	this.idCategoria = idCategoria; 
    	}
}