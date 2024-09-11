/**
 * Questa classe rappresenta il modello di un libro nel sistema BookRecommender.
 * Gestisce le proprietà di un libro come titolo, autore, descrizione, categoria, editore,
 * prezzo, e le informazioni di pubblicazione.
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
 * La classe Libro rappresenta un libro con attributi come titolo, autore,
 * descrizione, categoria, editore, prezzo, mese e anno di pubblicazione.
 */
public class Libro {
    private final String titolo;
    private final String autore;
    private final String descrizione;
    private final String categoria;
    private final String editore;
    private final String prezzo;
    private final String mesePubblicazione;
    private final String annoPubblicazione;

    /**
     * Costruisce un nuovo libro con dettagli completi.
     *
     * @param titolo Titolo del libro
     * @param autore Autore del libro
     * @param descrizione Breve descrizione del libro
     * @param categoria Categoria del libro
     * @param editore Editore del libro
     * @param prezzo Prezzo del libro
     * @param mesePubblicazione Mese di pubblicazione del libro
     * @param annoPubblicazione Anno di pubblicazione del libro
     */
    public Libro(String titolo, String autore, String descrizione, String categoria, String editore, String prezzo, String mesePubblicazione, String annoPubblicazione) {
        this.titolo = titolo;
        this.autore = autore;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.editore = editore;
        this.prezzo = prezzo;
        this.mesePubblicazione = mesePubblicazione;
        this.annoPubblicazione = annoPubblicazione;
    }

    /**
     * Restituisce il titolo del libro.
     *
     * @return Il titolo del libro
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Restituisce l'autore del libro.
     *
     * @return L'autore del libro
     */
    public String getAutore() {
        return autore;
    }

    /**
     * Restituisce la descrizione del libro.
     *
     * @return La descrizione del libro
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce la categoria del libro.
     *
     * @return La categoria del libro
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Restituisce l'editore del libro.
     *
     * @return L'editore del libro
     */
    public String getEditore() {
        return editore;
    }

    /**
     * Restituisce il prezzo del libro.
     *
     * @return Il prezzo del libro
     */
    public String getPrezzo() {
        return prezzo;
    }

    /**
     * Restituisce il mese di pubblicazione del libro.
     *
     * @return Il mese di pubblicazione del libro
     */
    public String getMesePubblicazione() {
        return mesePubblicazione;
    }

    /**
     * Restituisce l'anno di pubblicazione del libro.
     *
     * @return L'anno di pubblicazione del libro
     */
    public String getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    /**
     * Restituisce una rappresentazione in stringa del libro.
     *
     * @return una stringa che rappresenta il libro
     */
    @Override
    public String toString() {
        return "Libro: {" +
                "titolo: '" + titolo + '\'' +
                ", autore: '" + autore + '\'' +
                ", descrizione: '" + descrizione + '\'' +
                ", categoria: '" + categoria + '\'' +
                ", editore: '" + editore + '\'' +
                ", prezzo: " + prezzo +
                ", mesePubblicazione: " + mesePubblicazione +
                ", annoPubblicazione: " + annoPubblicazione +
                '}';
    }
}
