/**
 * La classe BookSelectionPage rappresenta un'interfaccia grafica che permette agli utenti
 * di cercare e selezionare libri dal sistema. Gli utenti possono selezionare uno o più libri
 * da una lista filtrata per titolo.
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

package gui.library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import controller.GestioneLibro;
import model.Libro;

/**
 * La classe BookSelectionPage permette agli utenti di cercare e selezionare libri
 * da una lista generata in base al titolo inserito. I libri selezionati possono essere
 * confermati e salvati.
 */
public class BookSelectionPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private GestioneLibro gestioneLibro;
    private JPanel mainPanel;
    private List<Libro> libriSelezionati;

    /**
     * Costruttore della classe BookSelectionPage. Inizializza l'interfaccia grafica
     * e i componenti necessari per la ricerca e selezione dei libri.
     */
    public BookSelectionPage() {
        gestioneLibro = new GestioneLibro();
        libriSelezionati = new ArrayList<>();

        setTitle("Seleziona Libri");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Cerca");

        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Imposta il pannello di visualizzazione per i libri
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BoxLayout(booksPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(booksPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        searchButton.addActionListener(e -> caricaLibri(searchField.getText(), booksPanel));

        add(scrollPane, BorderLayout.CENTER);

        JButton confermaButton = new JButton("Conferma");
        confermaButton.addActionListener(this::salvaLibriSelezionati);
        add(confermaButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Carica e visualizza i libri corrispondenti al titolo cercato all'interno del pannello.
     * 
     * @param titolo Il titolo del libro da cercare.
     * @param booksPanel Il pannello in cui verranno visualizzati i risultati della ricerca.
     */
    private void caricaLibri(String titolo, JPanel booksPanel) {
        booksPanel.removeAll();  // Rimuove eventuali elementi precedenti
        List<Libro> libri = gestioneLibro.cercaLibroPerTitolo(titolo); // Cerca per titolo
        for (Libro libro : libri) {
            JCheckBox checkBox = new JCheckBox(libro.getTitolo());
            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    libriSelezionati.add(libro);  // Aggiungi se selezionato
                } else {
                    libriSelezionati.remove(libro);  // Rimuovi se deselezionato
                }
            });
            booksPanel.add(checkBox);
        }
        booksPanel.revalidate();
        booksPanel.repaint();
    }

    /**
     * Salva i libri selezionati dall'utente e chiude la finestra.
     * 
     * @param e L'evento di azione associato al clic del pulsante di conferma.
     */
    private void salvaLibriSelezionati(ActionEvent e) {
        dispose(); 
    }

    /**
     * Restituisce la lista dei libri selezionati dall'utente.
     * 
     * @return Una lista di oggetti Libro selezionati.
     */
    public List<Libro> getLibriSelezionati() {
        return libriSelezionati;
    }
}
