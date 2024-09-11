/**
 * Questa classe rappresenta la finestra di valutazione per un libro.
 * Gli utenti possono inserire una valutazione per vari aspetti del libro (stile, contenuto, gradevolezza, originalità, edizione)
 * e fornire una recensione opzionale.
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

package gui.rating;

import javax.swing.*;
import java.awt.*;
import controller.GestioneValutazione;
import gui.model.BookCard;
import controller.GestioneSessione;
import model.Libro;
import model.Utente;
import model.Valutazione;

/**
 * La classe RatingPage crea un'interfaccia grafica per permettere agli utenti di valutare un libro
 * in base a diversi parametri e inserire una recensione opzionale.
 */
public class RatingPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JSpinner stileSpinner, contenutoSpinner, gradevolezzaSpinner, originalitaSpinner, edizioneSpinner;
    private JTextArea recensioneTextArea;
    private GestioneValutazione gestioneValutazione;
    private Libro libro;
    private BookCard bookCardInstance;

    /**
     * Costruttore che crea la finestra di valutazione per il libro fornito.
     * 
     * @param libro L'oggetto Libro da valutare.
     * @param bookCardInstance L'istanza di BookCard che dovrà essere aggiornata dopo l'invio della valutazione.
     */
    public RatingPage(Libro libro, BookCard bookCardInstance) {
        this.libro = libro;
        this.bookCardInstance = bookCardInstance;
        gestioneValutazione = new GestioneValutazione();

        setTitle("Valuta il libro: " + libro.getTitolo());
        setSize(600, 500); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        mainPanel.add(createLabeledSpinner("Stile (1-5):", stileSpinner = createSpinner()));
        mainPanel.add(createLabeledSpinner("Contenuto (1-5):", contenutoSpinner = createSpinner()));
        mainPanel.add(createLabeledSpinner("Gradevolezza (1-5):", gradevolezzaSpinner = createSpinner()));
        mainPanel.add(createLabeledSpinner("Originalità (1-5):", originalitaSpinner = createSpinner()));
        mainPanel.add(createLabeledSpinner("Edizione (1-5):", edizioneSpinner = createSpinner()));

        JLabel recensioneLabel = new JLabel("Recensione (opzionale):");
        recensioneTextArea = new JTextArea(5, 20);
        recensioneTextArea.setLineWrap(true);
        recensioneTextArea.setWrapStyleWord(true);
        JScrollPane recensioneScrollPane = new JScrollPane(recensioneTextArea);
        mainPanel.add(recensioneLabel);
        mainPanel.add(recensioneScrollPane);

        JButton submitButton = new JButton("Invia Valutazione");
        submitButton.addActionListener(e -> inviaValutazione());

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(submitButton);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Metodo ausiliario che crea uno spinner con etichetta per l'inserimento delle valutazioni.
     * 
     * @param labelText Il testo dell'etichetta.
     * @param spinner Lo spinner per la selezione della valutazione.
     * @return Un pannello contenente l'etichetta e lo spinner.
     */
    private JPanel createLabeledSpinner(String labelText, JSpinner spinner) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(labelText));
        panel.add(spinner);
        return panel;
    }

    /**
     * Crea uno spinner che permette di selezionare un valore da 1 a 5.
     * 
     * @return Lo spinner configurato per la valutazione.
     */
    private JSpinner createSpinner() {
        SpinnerNumberModel model = new SpinnerNumberModel(5, 1, 5, 1);
        return new JSpinner(model);
    }

    /**
     * Invia la valutazione inserita dall'utente e la salva tramite il sistema di gestione delle valutazioni.
     */
    private void inviaValutazione() {
        int stile = (int) stileSpinner.getValue();
        int contenuto = (int) contenutoSpinner.getValue();
        int gradevolezza = (int) gradevolezzaSpinner.getValue();
        int originalita = (int) originalitaSpinner.getValue();
        int edizione = (int) edizioneSpinner.getValue();
        String recensione = recensioneTextArea.getText().trim();

        Utente utente = GestioneSessione.getInstance().getUtenteLoggato();
        Valutazione valutazione = new Valutazione(libro, utente, stile, contenuto, gradevolezza, originalita, edizione, recensione.isEmpty() ? "" : recensione);

        gestioneValutazione.salvaValutazione(valutazione);
        bookCardInstance.aggiornaValutazioni();

        JOptionPane.showMessageDialog(this, "Valutazione inviata con successo!");
        dispose();
    }
}
