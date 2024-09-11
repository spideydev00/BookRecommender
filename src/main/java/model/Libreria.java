/**
 * Questa classe rappresenta il modello di una libreria di libri nel sistema BookRecommender.
 * Ogni libreria ha un nome, un creatore e una lista di libri associati.
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

package model;

import java.util.List;

/**
 * La classe Libreria rappresenta una collezione di libri creata da un utente. 
 * Ogni libreria ha un nome, un creatore e una lista di libri associati.
 */
public class Libreria {
    private String nomeLibreria;
    private Utente creatore;
    private List<Libro> libri;

    /**
     * Costruisce una nuova libreria con un nome, un creatore e una lista di libri.
     *
     * @param nomeLibreria Il nome della libreria
     * @param creatore L'utente che ha creato la libreria
     * @param libri La lista iniziale dei libri nella libreria
     */
    public Libreria(String nomeLibreria, Utente creatore, List<Libro> libri) {
        this.nomeLibreria = nomeLibreria;
        this.creatore = creatore;
        this.libri = libri;
    }

    /**
     * Restituisce il nome della libreria.
     *
     * @return Il nome della libreria
     */
    public String getNomeLibreria() {
        return nomeLibreria;
    }

    /**
     * Imposta il nome della libreria.
     *
     * @param nomeLibreria Il nuovo nome della libreria
     */
    public void setNomeLibreria(String nomeLibreria) {
        this.nomeLibreria = nomeLibreria;
    }

    /**
     * Restituisce l'utente che ha creato la libreria.
     *
     * @return L'utente creatore della libreria
     */
    public Utente getCreatore() {
        return creatore;
    }

    /**
     * Imposta l'utente che ha creato la libreria.
     *
     * @param creatore Il nuovo creatore della libreria
     */
    public void setCreatore(Utente creatore) {
        this.creatore = creatore;
    }

    /**
     * Restituisce la lista dei libri nella libreria.
     *
     * @return La lista dei libri nella libreria
     */
    public List<Libro> getLibri() {
        return libri;
    }
}
