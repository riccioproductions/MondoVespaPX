CREATE DATABASE IF NOT EXISTS mondovespapx
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE mondovespapx;

CREATE TABLE utenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    ruolo ENUM('user', 'admin') DEFAULT 'user',
    indirizzo VARCHAR(255),
    metodo_pagamento VARCHAR(50),
    data_registrazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categorie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descrizione TEXT
);

CREATE TABLE prodotti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descrizione TEXT,
    prezzo DECIMAL(10,2) NOT NULL,
    quantita_disponibile INT DEFAULT 0,
    immagine VARCHAR(255),
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES categorie(id)
);

CREATE TABLE ordini (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_utente INT NOT NULL,
    data_ordine TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    stato ENUM('in attesa', 'confermato', 'spedito', 'consegnato') DEFAULT 'in attesa',
    totale DECIMAL(10,2) NOT NULL,
    indirizzo_spedizione VARCHAR(255),
    metodo_pagamento VARCHAR(50),
    FOREIGN KEY (id_utente) REFERENCES utenti(id)
);

CREATE TABLE dettagli_ordine (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_ordine INT NOT NULL,
    id_prodotto INT NULL,
    quantita INT NOT NULL,
    prezzo_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_ordine) REFERENCES ordini(id),
    FOREIGN KEY (id_prodotto) REFERENCES prodotti(id) ON DELETE SET NULL
);


INSERT INTO utenti (nome, cognome, email, password, ruolo) VALUES
('Admin', 'MondoVespa', 'admin@mondovespapx.it', SHA2('admin1234', 512), 'admin');