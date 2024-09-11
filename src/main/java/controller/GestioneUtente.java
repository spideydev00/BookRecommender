/**
 * Gestisce tutte le operazioni relative agli utenti del sistema BookRecommender.
 * Implementa operazioni come la registrazione, l'autenticazione e la gestione della sessione dell'utente.
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
import model.Utente;
import utility.CSVReaderWriter;

/**
 * La classe GestioneUtente fornisce metodi per la gestione degli utenti, inclusi 
 * la registrazione, l'autenticazione e il login.
 */
public class GestioneUtente {
    
    private CSVReaderWriter csvManager;
    private String fileCSV; 
    private static GestioneUtente instance;

    /**
     * Costruttore privato della classe. Utilizza il pattern Singleton.
     */
    private GestioneUtente() {
        this.csvManager = CSVReaderWriter.getInstance();
        this.fileCSV = "UtentiRegistrati.csv";
    }

    /**
     * Restituisce l'istanza Singleton di GestioneUtente.
     * Se non esiste, viene creata una nuova istanza.
     * 
     * @return L'istanza Singleton di GestioneUtente.
     */
    public static synchronized GestioneUtente getInstance() {
        if(instance == null) {
            instance = new GestioneUtente();
        }
        return instance;
    }

    /**
     * Registra un nuovo utente nel sistema.
     * Se l'email o lo username sono già presenti, viene lanciata un'eccezione.
     * 
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param codiceFiscale Codice fiscale dell'utente.
     * @param email Email dell'utente.
     * @param userId UserID per l'accesso al sistema.
     * @param password Password per l'accesso al sistema.
     * @throws UtenteEsistenteException Se l'utente esiste già.
     */
    public void registraUtente(String nome, String cognome, String codiceFiscale, String email, String userId, String password) throws UtenteEsistenteException {
        String[] datiUtente = {nome, cognome, codiceFiscale, email, userId, password};
        
        if (csvManager.indexIsEqualToCSV(fileCSV, datiUtente, new int[]{3})) {
            throw new UtenteEsistenteException("Utente già esistente. Cambia Indirizzo-Email!");
        }
        if (csvManager.indexIsEqualToCSV(fileCSV, datiUtente, new int[]{4})) {
            throw new UtenteEsistenteException("Utente già esistente. Cambia username!");
        }
        
        csvManager.scriviSuCSV(fileCSV, datiUtente);
    }

    /**
     * Verifica le credenziali dell'utente per l'accesso al sistema.
     * 
     * @param userId UserID dell'utente.
     * @param password Password dell'utente.
     * @return true se l'autenticazione ha successo, false altrimenti.
     */
    public boolean checkCredenziali(String userId, String password) {
        List<String[]> contenutoCSV = csvManager.leggiDaCSV(fileCSV);
        return contenutoCSV.stream()
                .anyMatch(riga -> riga.length == 6 && riga[4].equals(userId) && riga[5].equals(password));
    }

    /**
     * Effettua il login di un utente e imposta l'utente loggato nella sessione corrente.
     * 
     * @param userId UserID dell'utente.
     * @param password Password dell'utente.
     * @return L'oggetto Utente se il login ha successo, null altrimenti.
     */
    public Utente loginUtente(String userId, String password) {
        String[] datiUtente = csvManager.getAllData(fileCSV, userId, 4);

        if (datiUtente != null && datiUtente.length >= 6) {
            Utente utenteLoggato = new Utente(datiUtente[0], datiUtente[1], datiUtente[2], datiUtente[3], userId, password);
            GestioneSessione.getInstance().setUtenteLoggato(utenteLoggato);
            return utenteLoggato;
        }

        return null;
    }

    /**
     * Classe interna che rappresenta un'eccezione lanciata quando si cerca di registrare un utente già esistente.
     */
    public class UtenteEsistenteException extends Exception {
        private static final long serialVersionUID = 1L;

        /**
         * Costruttore che crea una nuova eccezione con un messaggio descrittivo.
         * 
         * @param message Il messaggio descrittivo dell'eccezione.
         */
        public UtenteEsistenteException(String message) {
            super(message);
        }
    }
}
