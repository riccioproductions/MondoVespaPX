package it.mondovespapx.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    //prende un testo in chiaro e lo trasforma in un hash
    public static String sha512(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            //Converte la stringa di testo in byte e ne calcola l'hash
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                //Converte ogni singolo byte nel corrispondente valore esadecimale a 2 cifre con (%02x) e lo aggiunge alla stringa in costruzione
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            //Se la versione di Java installata non supporta SHA-512, genera un errore che ferma l'esecuzione
            throw new RuntimeException("Algoritmo SHA-512 non disponibile", e);
        }
    }
}