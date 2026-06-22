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
    stato ENUM('in attesa', 'confermato', 'spedito', 'consegnato', 'annullato') DEFAULT 'in attesa',
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

INSERT INTO categorie (nome, descrizione) VALUES
('Motore', 'Ricambi per il motore Vespa PX'),
('Impianto elettrico', 'Impianto elettrico e illuminazione'),
('Scarichi', 'Scarichi originali ed espansioni'),
('Freni', 'Sistema frenante'),
('Carrozzeria', 'Parti estetiche e strutturali'),
('Trasmissione', 'Frizione e cambio');

INSERT INTO prodotti (nome, descrizione, prezzo, quantita_disponibile, immagine, id_categoria) VALUES
('Kit Gruppo Termico Cilindro Pistone Completo Originale Vespa PX125', 'KIT CILINDRO E PISTONE VESPA PX 125 ORIGINALE PIAGGIO Articolo Originale OEM', 139.99, 20, null, 1),
('Kit Gruppo Termico Cilindro Pistone Completo Originale Vespa PX150', 'KIT CILINDRO E PISTONE VESPA PX 150 ORIGINALE PIAGGIO Articolo Originale OEM', 149.99, 20, null, 1),
('Kit Gruppo Termico Cilindro Pistone Completo Originale Vespa PX200', 'KIT CILINDRO E PISTONE VESPA PX 200 ORIGINALE PIAGGIO Articolo Originale OEM', 199.99, 20, null, 1),
('Candela NGK B7HS', 'Candela di accensione NGK originale di grado 7', 8.90, 50, null, 2),
('Marmitta originale', 'Marmitta tipo originale vespa px 125/150', 95.00, 8, null, 3),
('Espansione Sito', 'Espansione Sito Racing per Vespa PX 125/150', 145.00, 5, null, 3),
('Kit freni completo', 'Ganasce freno anteriore e posteriore', 32.00, 25, null, 4),
('Parafango anteriore', 'Parafango anteriore grezzo da verniciare', 89.00, 10, null, 5),
('Frizione completa', 'Frizione completa di dischi (già montata), 6 molle, pignone da 21 denti', 38.00, 18, null, 6),