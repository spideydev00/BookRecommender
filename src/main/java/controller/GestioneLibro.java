/**
 * Questa classe gestisce tutte le operazioni relative ai libri nel sistema BookRecommender.
 * Permette di cercare libri per titolo, autore o una combinazione di autore e anno.
 *
 * Developer del progetto:
 * 
 * <ul>
 *   <li>Nome: Alexandru</li>
 *   <li>Cognome: Raita</li>
 *   <li>Numero di Matricola: 757601</li>
 *   <li>Sede: VA</li>
 * </ul>
 * 
 * @author Alexandru Raita
 */

package controller;

import java.util.List;
import model.Libro;
import utility.CSVReaderWriter;

/**
 * La classe GestioneLibro gestisce tutte le operazioni relative ai libri nel sistema.
 * Permette di cercare libri per titolo, autore o una combinazione di autore e anno.
 */
public class GestioneLibro {
    private CSVReaderWriter csvManager = CSVReaderWriter.getInstance();
    private static final String FILE_CSV = "Libri.csv";
    
    /**
     * Cerca libri nel file CSV utilizzando una parola chiave che corrisponde al titolo.
     * 
     * @param parolaTitolo La parola da cercare nei titoli dei libri.
     * @return Una lista di oggetti Libro che corrispondono al titolo cercato.
     */
    public List<Libro> cercaLibroPerTitolo(String parolaTitolo) {
        List<String[]> contenutoFile = csvManager.leggiDaCSV(FILE_CSV);
        
        return contenutoFile.stream()
                .filter(riga -> riga[0].toLowerCase().trim().contains(parolaTitolo.toLowerCase().trim()))
                .map(riga -> creaLibroDaRiga(riga))
                .toList();
    }

    /**
     * Cerca libri nel file CSV basandosi sul nome dell'autore.
     * 
     * @param autoreLibro Il nome dell'autore del libro.
     * @return Una lista di oggetti Libro scritti dall'autore specificato.
     */
    public List<Libro> cercaLibroPerAutore(String autoreLibro) {
        List<String[]> contenutoFile = csvManager.leggiDaCSV(FILE_CSV);
        
        return contenutoFile.stream()
                .filter(riga -> riga[1].toLowerCase().trim().contains(autoreLibro.toLowerCase().trim()))
                .map(riga -> creaLibroDaRiga(riga))
                .toList();
    }

    /**
     * Cerca libri nel file CSV in base al nome dell'autore e all'anno di pubblicazione.
     * 
     * @param autoreLibro Il nome dell'autore del libro.
     * @param annoMinimo L'anno minimo di pubblicazione del libro.
     * @return Una lista di oggetti Libro che corrispondono all'autore e all'anno specificati.
     */
    public List<Libro> cercaLibroPerAutoreAnno(String autoreLibro, int annoMinimo) {
        List<String[]> contenutoFile = csvManager.leggiDaCSV(FILE_CSV);
        
        return contenutoFile.stream()
                .filter(riga -> riga[1].toLowerCase().trim().contains(autoreLibro.toLowerCase().trim()) 
                        && Integer.parseInt(riga[7]) >= annoMinimo)
                .map(riga -> creaLibroDaRiga(riga))
                .toList();
    }
    
    /**
     * Crea un oggetto Libro a partire dai dati di una riga del CSV.
     * 
     * @param riga Un array di stringhe che rappresenta una riga del file CSV.
     * @return Un oggetto Libro con i dati della riga.
     */
    public Libro creaLibroDaRiga(String[] riga) {
        String titolo = riga[0];
        String autore = riga[1];
        String descrizione = riga[2];
        String categoria = riga[3];
        String editore = riga[4];
        String prezzo = riga[5];
        String mesePubblicazione = riga[6];
        String annoPubblicazione = riga[7];
        
        return new Libro(titolo, autore, descrizione, categoria, editore, prezzo, mesePubblicazione, annoPubblicazione);
    }
}
