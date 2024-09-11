/**
 * Questa classe gestisce tutte le operazioni relative alle librerie nel sistema BookRecommender.
 * Consente la gestione di librerie utente con un massimo di 10 libri.
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

import java.util.ArrayList;
import java.util.List;
import model.Libro;
import model.Libreria;
import utility.CSVReaderWriter;

/**
 * La classe GestioneLibreria permette la gestione delle librerie di un utente.
 * Consente di registrare, aggiornare, eliminare e modificare librerie con un massimo di 10 libri.
 */
public class GestioneLibreria {
    private static final int MAX_LIBRI = 10;
    private static final String FILE_CSV = "Librerie.csv";
    private CSVReaderWriter csvManager = CSVReaderWriter.getInstance();

    /**
     * Registra una nuova libreria nel CSV associata a un utente, contenente un massimo di 10 libri.
     * I campi rimanenti, se la libreria ha meno di 10 libri, saranno lasciati vuoti.
     * 
     * @param userId L'ID dell'utente.
     * @param nomeLibreria Il nome della libreria da registrare.
     * @param libri La lista di titoli dei libri da includere nella libreria.
     */
    public void registraLibreria(String userId, String nomeLibreria, List<String> libri) {
        String[] datiLibreria = new String[MAX_LIBRI + 2]; 
        datiLibreria[0] = userId;
        datiLibreria[1] = nomeLibreria;
        
        for (int i = 0; i < MAX_LIBRI; i++) {
            if (i < libri.size()) {
                datiLibreria[i + 2] = libri.get(i);
            } else {
                datiLibreria[i + 2] = "";
            }
        }
        
        csvManager.scriviSuCSV(FILE_CSV, datiLibreria);
    }

    /**
     * Elimina una libreria dal file CSV identificata dall'ID utente e dal nome della libreria.
     * 
     * @param userId L'ID dell'utente.
     * @param nomeLibreria Il nome della libreria da eliminare.
     */
    public void eliminaLibreria(String userId, String nomeLibreria) {
        List<String[]> contenutoFile = csvManager.leggiDaCSV(FILE_CSV);
        List<String[]> nuovoContenuto = new ArrayList<>();

        for (String[] riga : contenutoFile) {
            if (!(riga[0].equals(userId) && riga[1].equals(nomeLibreria))) {
                nuovoContenuto.add(riga);
            }
        }

        // Sovrascrive il CSV con il contenuto aggiornato
        csvManager.aggiornaCSV(FILE_CSV, nuovoContenuto);
    }

    /**
     * Aggiorna una libreria esistente nel CSV, contenente un massimo di 10 libri.
     * Se ci sono meno di 10 libri, i campi rimanenti saranno vuoti.
     * 
     * @param userId L'ID dell'utente.
     * @param nomeLibreria Il nome della libreria da aggiornare.
     * @param nuoviLibri La nuova lista di titoli di libri.
     */
    public void aggiornaLibreria(String userId, String nomeLibreria, List<String> nuoviLibri) {
        List<String[]> contenutoFile = csvManager.leggiDaCSV(FILE_CSV);
        List<String[]> nuovoContenuto = new ArrayList<>();

        for (String[] riga : contenutoFile) {
            if (riga[0].equals(userId) && riga[1].equals(nomeLibreria)) {
                for (int i = 0; i < MAX_LIBRI; i++) {
                    if (i < nuoviLibri.size()) {
                        riga[i + 2] = nuoviLibri.get(i);
                    } else {
                        riga[i + 2] = "";
                    }
                }
            }
            nuovoContenuto.add(riga);
        }

        // Sovrascrive il CSV con il contenuto aggiornato
        csvManager.aggiornaCSV(FILE_CSV, nuovoContenuto);
    }

    /**
     * Restituisce tutte le librerie associate a un utente specifico.
     * 
     * @param userId L'ID dell'utente.
     * @return Una lista di librerie create dall'utente.
     */
    public List<Libreria> getLibrerieByUser(String userId) {
        List<Libreria> librerieUtente = new ArrayList<>();
        List<String[]> contenutoFile = csvManager.leggiDaCSV(FILE_CSV);

        for (String[] riga : contenutoFile) {
            if (riga[0].equals(userId)) {
                String nomeLibreria = riga[1];
                List<Libro> libri = parseLibri(riga);
                librerieUtente.add(new Libreria(nomeLibreria, GestioneSessione.getInstance().getUtenteLoggato(), libri));
            }
        }
        return librerieUtente;
    }

    /**
     * Restituisce la lista dei libri presenti in una libreria specifica di un utente.
     * 
     * @param nomeLibreria Il nome della libreria da cui ottenere i libri.
     * @param userId L'ID dell'utente proprietario della libreria.
     * @return Una lista di oggetti Libro presenti nella libreria o una lista vuota se non trovata.
     */
    public List<Libro> getLibriFromLibreria(String nomeLibreria, String userId) {
        List<Libreria> librerieUtente = getLibrerieByUser(userId);

        for (Libreria libreria : librerieUtente) {
            if (libreria.getNomeLibreria().equals(nomeLibreria)) {
                return libreria.getLibri();
            }
        }

        return new ArrayList<>();
    }


    /**
     * Aggiunge un libro a una libreria esistente se c'è spazio disponibile.
     * Aggiorna il file CSV con il nuovo contenuto.
     * 
     * @param userId L'ID dell'utente proprietario della libreria.
     * @param nomeLibreria Il nome della libreria a cui aggiungere il libro.
     * @param libro Il titolo del libro da aggiungere.
     */
    public void aggiungiLibro(String userId, String nomeLibreria, String libro) {
        List<String[]> contenutoFile = csvManager.leggiDaCSV(FILE_CSV);
        List<String[]> nuovoContenuto = new ArrayList<>();

        for (String[] riga : contenutoFile) {
            if (riga[0].equals(userId) && riga[1].equals(nomeLibreria)) {
                List<String> listaLibri = parseLibriToStringList(riga);
                
                if (listaLibri.size() < MAX_LIBRI && !listaLibri.contains(libro)) {
                    for (int i = 2; i < 2 + MAX_LIBRI; i++) {
                        if (riga[i].isEmpty()) {
                            riga[i] = libro;
                            break;
                        }
                    }
                }
            }
            nuovoContenuto.add(riga);
        }

        csvManager.aggiornaCSV(FILE_CSV, nuovoContenuto);
    }

    /**
     * Rimuove un libro da una libreria esistente e aggiorna il file CSV.
     * 
     * @param userId L'ID dell'utente proprietario della libreria.
     * @param nomeLibreria Il nome della libreria da cui rimuovere il libro.
     * @param libro Il titolo del libro da rimuovere.
     */
    public void rimuoviLibro(String userId, String nomeLibreria, String libro) {
        List<String[]> contenutoFile = csvManager.leggiDaCSV(FILE_CSV);
        List<String[]> nuovoContenuto = new ArrayList<>();

        for (String[] riga : contenutoFile) {
            if (riga[0].equals(userId) && riga[1].equals(nomeLibreria)) {
                List<String> listaLibri = parseLibriToStringList(riga);
                
                if (listaLibri.contains(libro)) {
                    listaLibri.remove(libro);
                    for (int i = 0; i < MAX_LIBRI; i++) {
                        if (i < listaLibri.size()) {
                            riga[i + 2] = listaLibri.get(i);
                        } else {
                            riga[i + 2] = "";
                        }
                    }
                }
            }
            nuovoContenuto.add(riga);
        }

        csvManager.aggiornaCSV(FILE_CSV, nuovoContenuto);
    }

    /**
     * Converte una riga del CSV in una lista di oggetti Libro.
     * I campi vuoti vengono ignorati.
     * 
     * @param riga La riga del CSV contenente i titoli dei libri.
     * @return Una lista di oggetti Libro.
     */
    private List<Libro> parseLibri(String[] riga) {
        List<Libro> libri = new ArrayList<>();
        GestioneLibro bookManager = new GestioneLibro();
        
        for (int i = 2; i < 2 + MAX_LIBRI; i++) {
            if (!riga[i].isEmpty()) {
                List<Libro> libroTemp = bookManager.cercaLibroPerTitolo(riga[i]);
                if (!libroTemp.isEmpty()) {
                    Libro libro = libroTemp.get(0);
                    libri.add(bookManager.creaLibroDaRiga(new String[]{
                        libro.getTitolo(), libro.getAutore(), libro.getDescrizione(),
                        libro.getCategoria(), libro.getEditore(), libro.getPrezzo(),
                        libro.getMesePubblicazione(), libro.getAnnoPubblicazione()
                    }));
                }
            }
        }
        return libri;
    }

    /**
     * Converte una riga del CSV in una lista di stringhe contenenti i titoli dei libri.
     * 
     * @param riga La riga del CSV.
     * @return Una lista di titoli dei libri come stringhe.
     */
    private List<String> parseLibriToStringList(String[] riga) {
        List<String> listaLibri = new ArrayList<>();
        for (int i = 2; i < 2 + MAX_LIBRI; i++) {
            if (!riga[i].isEmpty()) {
                listaLibri.add(riga[i]);
            }
        }
        return listaLibri;
    }
}
