/**
 * Gestisce le operazioni di inserimento e recupero dei libri consigliati
 * per uno specifico libro, da parte degli utenti nel sistema.
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

import utility.CSVReaderWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * La classe GestioneLibriConsigliati gestisce l'inserimento e il recupero dei libri consigliati dagli utenti.
 * Consente sia di salvare suggerimenti di libri che di caricare i suggerimenti esistenti.
 */
public class GestioneLibriConsigliati {

    private static final String FILE_CONSIGLIATI = "LibriConsigliati.csv";
    private CSVReaderWriter csvManager = CSVReaderWriter.getInstance();

    /**
     * Inserisce i libri consigliati per un determinato libro da parte di un utente.
     * Se l'utente ha già consigliato libri per lo stesso libro, i nuovi libri verranno aggiunti,
     * limitando il totale a un massimo di 3 libri consigliati.
     * 
     * @param userId L'ID dell'utente che consiglia i libri.
     * @param libro Il titolo del libro per cui l'utente sta fornendo i consigli.
     * @param nuoviLibriConsigliati La lista di nuovi titoli di libri consigliati dall'utente.
     */
    public void inserisciSuggerimentoLibro(String userId, String libro, List<String> nuoviLibriConsigliati) {
        List<String[]> contenutoFile = csvManager.leggiDaCSV(FILE_CONSIGLIATI);
        List<String> libriConsigliatiEsistenti = new ArrayList<>();
        List<String[]> nuovoContenuto = new ArrayList<>();
        
        for (String[] riga : contenutoFile) {
            if (riga[0].equals(userId) && riga[1].equals(libro)) {
                for (int i = 2; i < riga.length; i++) {
                    if (riga[i] != null && !riga[i].isEmpty()) {
                        libriConsigliatiEsistenti.add(riga[i]);
                    }
                }
            } else {
                nuovoContenuto.add(riga);
            }
        }
        
        libriConsigliatiEsistenti.addAll(nuoviLibriConsigliati);

        if (libriConsigliatiEsistenti.size() > 3) {
            libriConsigliatiEsistenti = libriConsigliatiEsistenti.subList(0, 3);
        }

        String[] nuovaRiga = new String[5];
        nuovaRiga[0] = userId;
        nuovaRiga[1] = libro;

        for (int i = 0; i < libriConsigliatiEsistenti.size(); i++) {
            nuovaRiga[i + 2] = libriConsigliatiEsistenti.get(i);
        }

        nuovoContenuto.add(nuovaRiga);

        csvManager.aggiornaCSV(FILE_CONSIGLIATI, nuovoContenuto);
    }


    /**
     * Recupera i libri consigliati da un utente per un determinato libro.
     * 
     * @param userId L'ID dell'utente che ha fornito i consigli.
     * @param titoloLibro Il titolo del libro per cui sono stati consigliati i libri.
     * @return Una lista di titoli di libri consigliati dall'utente.
     */
    public List<String> caricaLibriConsigliatiDaUtente(String userId, String titoloLibro) {
        List<String[]> suggerimenti = csvManager.leggiDaCSV(FILE_CONSIGLIATI);
        List<String> libriConsigliati = new ArrayList<>();

        for (String[] suggerimento : suggerimenti) {
            if (suggerimento[0].equals(userId) && suggerimento[1].equalsIgnoreCase(titoloLibro)) {
                for (int i = 2; i < suggerimento.length; i++) {
                    if (suggerimento[i] != null && !suggerimento[i].isEmpty()) {
                        libriConsigliati.add(suggerimento[i]);
                    }
                }
            }
        }
        return libriConsigliati;
    }

    /**
     * Carica una lista casuale fino a 3 libri consigliati da vari utenti per un determinato libro.
     * Utile per fornire suggerimenti generici a utenti non autenticati o ospiti.
     * 
     * @param titoloLibro Il titolo del libro per cui visualizzare i consigli.
     * @return Una lista casuale fino a 3 libri consigliati.
     */
    public List<String> caricaLibriConsigliatiCasuali(String titoloLibro) {
        List<String[]> suggerimenti = csvManager.leggiDaCSV(FILE_CONSIGLIATI);
        List<String> tuttiConsigli = new ArrayList<>();

        // Aggiungi tutti i libri consigliati per il titolo specifico
        for (String[] suggerimento : suggerimenti) {
            if (suggerimento[1].equalsIgnoreCase(titoloLibro)) {
                for (int i = 2; i < suggerimento.length; i++) {
                    if (suggerimento[i] != null && !suggerimento[i].isEmpty()) {
                        tuttiConsigli.add(suggerimento[i]);
                    }
                }
            }
        }

        // Mescola i consigli e restituisci fino a 3 libri
        Collections.shuffle(tuttiConsigli);
        return tuttiConsigli.size() > 3 ? tuttiConsigli.subList(0, 3) : tuttiConsigli;
    }
}
