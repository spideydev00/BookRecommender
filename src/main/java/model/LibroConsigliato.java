/**
 * Questa classe rappresenta il modello di un utente nel sistema BookRecommender.
 * Gli utenti hanno attributi personali come nome, cognome, codice fiscale, email, userId e password.
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

/**
 * La classe LibroConsigliato associa un libro principale a una lista di libri consigliati
 * da un utente nel sistema.
 */
public class LibroConsigliato {

    private String utenteId;
    private String libroPrincipale;
    private String[] libriConsigliati;

    /**
     * Costruttore della classe LibroConsigliato.
     * 
     * @param utenteId L'ID dell'utente che consiglia i libri
     * @param libroPrincipale Il titolo del libro principale per cui sono consigliati altri libri
     * @param libriConsigliati Un array contenente i titoli dei libri consigliati
     */
    public LibroConsigliato(String utenteId, String libroPrincipale, String[] libriConsigliati) {
        this.utenteId = utenteId;
        this.libroPrincipale = libroPrincipale;
        this.libriConsigliati = libriConsigliati;
    }

    /**
     * Restituisce l'ID dell'utente che ha consigliato i libri.
     * 
     * @return L'ID dell'utente
     */
    public String getUtenteId() {
        return utenteId;
    }

    /**
     * Restituisce il titolo del libro principale per cui sono consigliati altri libri.
     * 
     * @return Il titolo del libro principale
     */
    public String getLibroPrincipale() {
        return libroPrincipale;
    }

    /**
     * Restituisce l'array contenente i titoli dei libri consigliati dall'utente.
     * 
     * @return Un array di titoli di libri consigliati
     */
    public String[] getLibriConsigliati() {
        return libriConsigliati;
    }
}
