/**
 * La classe CreateLibraryPage permette agli utenti di creare una nuova libreria,
 * selezionando un nome per la libreria e aggiungendo uno o più libri da una lista.
 * Fornisce un'interfaccia grafica semplice e intuitiva.
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
import java.util.List;
import controller.GestioneSessione;
import controller.GestioneLibreria;
import model.Libro;

/**
 * La classe CreateLibraryPage fornisce un'interfaccia per creare una nuova libreria,
 * consentendo agli utenti di inserire il nome della libreria e selezionare i libri da aggiungere.
 */
public class CreateLibraryPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField nomeLibreriaField;
    private JList<String> listaLibri;
    private DefaultListModel<String> listaLibriModel;
    private GestioneLibreria gestioneLibreria;
    private UserLibraryPage userLibraryPage;  // Riferimento alla pagina principale delle librerie

    /**
     * Costruttore della classe CreateLibraryPage. Inizializza i componenti dell'interfaccia grafica
     * e imposta il layout per la creazione di una nuova libreria.
     * 
     * @param userLibraryPage La finestra principale delle librerie dell'utente che richiede l'aggiornamento.
     */
    public CreateLibraryPage(UserLibraryPage userLibraryPage) {
        this.userLibraryPage = userLibraryPage;  // Salva il riferimento alla finestra chiamante
        gestioneLibreria = new GestioneLibreria();

        setTitle("Crea una nuova Libreria");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Pannello per il nome libreria e bottone Carica Libri nella stessa linea
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nomeLibreriaLabel = new JLabel("Nome Libreria:");
        nomeLibreriaField = new JTextField(15);  // Regola la larghezza
        nomeLibreriaField.setPreferredSize(new Dimension(200, 30));  // Imposta altezza preferita
        nomeLibreriaField.setMaximumSize(new Dimension(200, 30));    // Imposta altezza massima

        // Bottone per caricare i libri
        JButton caricaLibriButton = new JButton("Carica Libri");
        caricaLibriButton.addActionListener(e -> apriSchermataSelezioneLibri());

        // Aggiungi tutti i componenti al namePanel
        namePanel.add(nomeLibreriaLabel);
        namePanel.add(nomeLibreriaField);
        namePanel.add(caricaLibriButton);

        // Lista di libri
        listaLibriModel = new DefaultListModel<>();
        listaLibri = new JList<>(listaLibriModel);
        listaLibri.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Bottone Conferma allineato a sinistra
        JPanel confermaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton confermaButton = new JButton("Conferma");
        confermaButton.addActionListener(e -> salvaLibreria());
        confermaPanel.add(confermaButton);

        // Aggiungi tutti i componenti al pannello principale
        formPanel.add(namePanel);
        formPanel.add(new JScrollPane(listaLibri));

        add(formPanel, BorderLayout.CENTER);
        add(confermaPanel, BorderLayout.SOUTH);  // Allinea il bottone Conferma a sinistra
        setVisible(true);
    }

    /**
     * Apre la schermata di selezione dei libri per permettere agli utenti di scegliere
     * quali libri aggiungere alla nuova libreria.
     */
    private void apriSchermataSelezioneLibri() {
        BookSelectionPage selezioneLibri = new BookSelectionPage();
        selezioneLibri.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        selezioneLibri.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                List<Libro> libriSelezionati = selezioneLibri.getLibriSelezionati();
                listaLibriModel.clear();  // Pulisce la lista esistente
                for (Libro libro : libriSelezionati) {
                    listaLibriModel.addElement(libro.getTitolo());  // Aggiunge i titoli selezionati
                }
            }
        });
    }

    /**
     * Salva la libreria creata dall'utente, registrando il nome e i libri selezionati
     * nel sistema e aggiornando la pagina principale delle librerie.
     */
    private void salvaLibreria() {
        String nomeLibreria = nomeLibreriaField.getText();
        List<String> libriSelezionati = listaLibri.getSelectedValuesList();
        if (!nomeLibreria.isEmpty() && !libriSelezionati.isEmpty()) {
            gestioneLibreria.registraLibreria(GestioneSessione.getInstance().getUtenteLoggato().getUserId(), nomeLibreria, libriSelezionati);
            dispose();
            JOptionPane.showMessageDialog(this, "Libreria creata con successo!");
            userLibraryPage.aggiornaLibrerie();  // Aggiorna la lista in tempo reale
        } else {
            JOptionPane.showMessageDialog(this, "Inserisci un nome per la libreria e seleziona almeno un libro.");
        }
    }
}
