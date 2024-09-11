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

import javax.swing.SwingUtilities;
import gui.auth.Registrazione;

public class BookRecommender {

    /**
     * Metodo principale che avvia l'applicazione.
     * Questo metodo utilizza `SwingUtilities.invokeLater` per eseguire la creazione
     * della GUI della registrazione sul thread dell'evento di Swing.
     * 
     * @param args Argomenti della riga di comando (non utilizzati in questo caso).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Avvia una nuova istanza della finestra di registrazione
                new Registrazione();
            }
        });
    }
}
