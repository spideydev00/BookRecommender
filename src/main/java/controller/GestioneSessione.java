/**
 * Questa classe gestisce la sessione dell'utente nel sistema BookRecommender.
 * Implementa il pattern Singleton per assicurare che vi sia una sola istanza di sessione attiva per l'utente loggato.
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

import model.Utente;

/**
 * La classe GestioneSessione gestisce la sessione di un utente nel sistema.
 * Utilizza il pattern Singleton per garantire che vi sia una sola istanza attiva della sessione.
 */
public class GestioneSessione {
    
    private Utente utenteLoggato;
    private static GestioneSessione instance;

    /**
     * Costruttore privato per impedire la creazione di più istanze.
     * La classe utilizza il pattern Singleton.
     */
    private GestioneSessione() {}

    /**
     * Restituisce l'istanza Singleton di GestioneSessione. 
     * Se l'istanza non esiste, viene creata una nuova.
     * 
     * @return L'istanza Singleton di GestioneSessione.
     */
    public static synchronized GestioneSessione getInstance() {
        if (instance == null) {
            instance = new GestioneSessione();
        }
        return instance;
    }

    /**
     * Restituisce l'utente attualmente loggato.
     * 
     * @return L'utente attualmente loggato, o null se nessun utente è loggato.
     */
    public Utente getUtenteLoggato() {
        return this.utenteLoggato;
    }

    /**
     * Imposta l'utente attualmente loggato nel sistema.
     * 
     * @param utente L'utente che esegue l'accesso.
     */
    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }
}
