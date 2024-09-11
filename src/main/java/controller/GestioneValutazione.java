/**
 * Gestisce le operazioni relative alle valutazioni dei libri da parte degli utenti.
 * Le valutazioni vengono salvate su un file CSV.
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

import model.Valutazione;
import utility.CSVReaderWriter;

import java.util.List;

/**
 * La classe GestioneValutazione fornisce metodi per la gestione delle valutazioni 
 * dei libri da parte degli utenti, inclusi il salvataggio e il controllo delle valutazioni esistenti.
 */
public class GestioneValutazione {
    private static final String FILE_VALUTAZIONI = "ValutazioniLibri.csv";
    private CSVReaderWriter csvManager = CSVReaderWriter.getInstance();

    /**
     * Salva la valutazione di un libro nel file CSV.
     * 
     * @param valutazione L'oggetto Valutazione da salvare.
     */
    public void salvaValutazione(Valutazione valutazione) {
        String[] datiValutazione = { 
            valutazione.getLibro().getTitolo(), 
            valutazione.getUtente().getUserId(), 
            String.valueOf(valutazione.getVotoStile()), 
            String.valueOf(valutazione.getVotoContenuto()), 
            String.valueOf(valutazione.getVotoGradevolezza()), 
            String.valueOf(valutazione.getVotoOriginalita()), 
            String.valueOf(valutazione.getVotoEdizione()), 
            String.valueOf(valutazione.getVotoFinale()),
            valutazione.getRecensione()
        };

        csvManager.scriviSuCSV(FILE_VALUTAZIONI, datiValutazione);
    }

    /**
     * Verifica se l'utente ha già valutato un determinato libro.
     * 
     * @param titoloLibro Il titolo del libro da controllare.
     * @param userId L'ID dell'utente da verificare.
     * @return true se l'utente ha già valutato il libro, altrimenti false.
     */
    public boolean utenteHaValutato(String titoloLibro, String userId) {
        List<String[]> valutazioni = csvManager.leggiDaCSV(FILE_VALUTAZIONI);
        for (String[] valutazione : valutazioni) {
            if (valutazione[0].equals(titoloLibro) && valutazione[1].equals(userId)) {
                return true;
            }
        }
        return false;
    }
}
