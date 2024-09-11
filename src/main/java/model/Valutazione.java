/**
 * La classe Valutazione rappresenta una valutazione di un libro fatta da un utente,
 * includendo voti su vari criteri e un voto finale calcolato.
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
 * La classe Valutazione rappresenta una valutazione di un libro, includendo voti 
 * su vari aspetti come stile, contenuto, gradevolezza, originalità, edizione, e una recensione.
 * Il voto finale è calcolato come media dei voti.
 */
public class Valutazione {
    private Libro libro;
    private Utente utente;
    private int votoStile;
    private int votoContenuto;
    private int votoGradevolezza;
    private int votoOriginalita;
    private int votoEdizione;
    private int votoFinale;
    private String recensione;

    /**
     * Costruisce una nuova valutazione per un libro basata su diversi criteri.
     *
     * @param libro Il libro a cui si riferisce la valutazione
     * @param utente L'utente che ha fatto la valutazione
     * @param votoStile Voto per lo stile del libro
     * @param votoContenuto Voto per il contenuto del libro
     * @param votoGradevolezza Voto per la gradevolezza della lettura
     * @param votoOriginalita Voto per l'originalità del libro
     * @param votoEdizione Voto per l'edizione del libro
     * @param recensione Una recensione testuale sul libro
     */
    public Valutazione(Libro libro, Utente utente, int votoStile, int votoContenuto, int votoGradevolezza, 
                       int votoOriginalita, int votoEdizione, String recensione) {
        this.libro = libro;
        this.utente = utente;
        this.votoStile = votoStile;
        this.votoContenuto = votoContenuto;
        this.votoGradevolezza = votoGradevolezza;
        this.votoOriginalita = votoOriginalita;
        this.votoEdizione = votoEdizione;
        this.votoFinale = calcolaVotoFinale();
        this.recensione = recensione;
    }

    /**
     * Calcola il voto finale come media arrotondata dei voti specifici.
     * 
     * @return Il voto finale arrotondato
     */
    private int calcolaVotoFinale() {
        double media = (votoStile + votoContenuto + votoGradevolezza + votoOriginalita + votoEdizione) / 5.0;
        return (int) Math.round(media);
    }

    /**
     * Restituisce il libro associato alla valutazione.
     *
     * @return Il libro valutato
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * Imposta il libro associato alla valutazione.
     *
     * @param libro Il libro da associare alla valutazione
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    /**
     * Restituisce l'utente che ha effettuato la valutazione.
     *
     * @return L'utente che ha effettuato la valutazione
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * Imposta l'utente che ha effettuato la valutazione.
     *
     * @param utente L'utente da associare alla valutazione
     */
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    /**
     * Restituisce il voto assegnato allo stile del libro.
     *
     * @return Il voto assegnato allo stile
     */
    public int getVotoStile() {
        return votoStile;
    }

    /**
     * Imposta il voto per lo stile del libro e aggiorna il voto finale.
     *
     * @param votoStile Il nuovo voto per lo stile
     */
    public void setVotoStile(int votoStile) {
        this.votoStile = votoStile;
        this.votoFinale = calcolaVotoFinale();
    }

    /**
     * Restituisce il voto assegnato al contenuto del libro.
     *
     * @return Il voto assegnato al contenuto
     */
    public int getVotoContenuto() {
        return votoContenuto;
    }

    /**
     * Imposta il voto per il contenuto del libro e aggiorna il voto finale.
     *
     * @param votoContenuto Il nuovo voto per il contenuto
     */
    public void setVotoContenuto(int votoContenuto) {
        this.votoContenuto = votoContenuto;
        this.votoFinale = calcolaVotoFinale();
    }

    /**
     * Restituisce il voto assegnato alla gradevolezza del libro.
     *
     * @return Il voto assegnato alla gradevolezza
     */
    public int getVotoGradevolezza() {
        return votoGradevolezza;
    }

    /**
     * Imposta il voto per la gradevolezza del libro e aggiorna il voto finale.
     *
     * @param votoGradevolezza Il nuovo voto per la gradevolezza
     */
    public void setVotoGradevolezza(int votoGradevolezza) {
        this.votoGradevolezza = votoGradevolezza;
        this.votoFinale = calcolaVotoFinale();
    }

    /**
     * Restituisce il voto assegnato all'originalità del libro.
     *
     * @return Il voto assegnato all'originalità
     */
    public int getVotoOriginalita() {
        return votoOriginalita;
    }

    /**
     * Imposta il voto per l'originalità del libro e aggiorna il voto finale.
     *
     * @param votoOriginalita Il nuovo voto per l'originalità
     */
    public void setVotoOriginalita(int votoOriginalita) {
        this.votoOriginalita = votoOriginalita;
        this.votoFinale = calcolaVotoFinale();
    }

    /**
     * Restituisce il voto assegnato all'edizione del libro.
     *
     * @return Il voto assegnato all'edizione
     */
    public int getVotoEdizione() {
        return votoEdizione;
    }

    /**
     * Imposta il voto per l'edizione del libro e aggiorna il voto finale.
     *
     * @param votoEdizione Il nuovo voto per l'edizione
     */
    public void setVotoEdizione(int votoEdizione) {
        this.votoEdizione = votoEdizione;
        this.votoFinale = calcolaVotoFinale();
    }

    /**
     * Restituisce il voto finale, calcolato come media arrotondata dei voti.
     *
     * @return Il voto finale
     */
    public int getVotoFinale() {
        return votoFinale;
    }

    /**
     * Imposta la recensione associata alla valutazione.
     *
     * @param recensione La recensione da associare alla valutazione
     */
    public void setRecensione(String recensione) {
        this.recensione = recensione;
    }

    /**
     * Restituisce la recensione associata alla valutazione.
     *
     * @return La recensione del libro
     */
    public String getRecensione() {
        return recensione;
    }
}
