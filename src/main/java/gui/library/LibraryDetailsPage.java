/**
 * La classe LibraryDetailsPage visualizza i dettagli di una libreria esistente, permettendo
 * agli utenti di vedere, aggiungere o rimuovere libri dalla libreria selezionata.
 * L'interfaccia mostra ogni libro con un checkbox e offre pulsanti per interagire con la libreria.
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
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import controller.GestioneLibreria;
import controller.GestioneSessione;
import gui.model.BookCard;
import model.Libro;

/**
 * La classe LibraryDetailsPage permette di visualizzare i dettagli di una libreria selezionata,
 * mostrando i libri presenti e consentendo di aggiungerli o rimuoverli.
 */
public class LibraryDetailsPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private GestioneLibreria gestioneLibreria;
    private JPanel listaLibriPanel;
    private String nomeLibreria;
    private List<JCheckBox> checkboxes;

    /**
     * Costruttore della classe LibraryDetailsPage. Inizializza l'interfaccia per visualizzare
     * e modificare i libri in una libreria esistente.
     * 
     * @param nomeLibreria Il nome della libreria da visualizzare.
     */
    public LibraryDetailsPage(String nomeLibreria) {
        this.nomeLibreria = nomeLibreria;
        gestioneLibreria = new GestioneLibreria();
        checkboxes = new ArrayList<>();

        setTitle("Dettagli Libreria: " + nomeLibreria);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        listaLibriPanel = new JPanel();
        listaLibriPanel.setLayout(new BoxLayout(listaLibriPanel, BoxLayout.Y_AXIS));

        caricaLibriLibreria(); 

        // Bottone per aggiungere libri
        JButton aggiungiLibriButton = new JButton("Aggiungi Libri");
        aggiungiLibriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apriSchermataAggiungiLibri();
            }
        });

        // Bottone per rimuovere i libri selezionati
        JButton rimuoviLibriButton = new JButton("Rimuovi Libri");
        rimuoviLibriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rimuoviLibriSelezionati();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(aggiungiLibriButton);
        buttonPanel.add(rimuoviLibriButton);

        add(new JScrollPane(listaLibriPanel), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Carica i libri presenti nella libreria e visualizza ogni libro con un checkbox.
     */
    private void caricaLibriLibreria() {
        listaLibriPanel.removeAll();
        checkboxes.clear(); 

        List<Libro> libriLibreria = gestioneLibreria.getLibriFromLibreria(nomeLibreria, GestioneSessione.getInstance().getUtenteLoggato().getUserId());

        if (libriLibreria.isEmpty()) {
            JLabel noBooksLabel = new JLabel("Nessun libro presente nella libreria.");
            listaLibriPanel.add(noBooksLabel);
        } else {
            for (Libro libro : libriLibreria) {
                JPanel libroPanel = new JPanel();
                libroPanel.setLayout(new BoxLayout(libroPanel, BoxLayout.Y_AXIS));

                // Crea il checkbox per il libro
                JCheckBox checkbox = new JCheckBox(libro.getTitolo());
                checkboxes.add(checkbox);  // Aggiunge il checkbox alla lista

                // Crea il pulsante per aprire la scheda del libro
                JButton apriSchedaButton = new JButton("Apri scheda libro");
                apriSchedaButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new BookCard(libro);  // Apri una nuova istanza di BookCard
                    }
                });

                // Aggiungi il checkbox e il pulsante al pannello del libro
                libroPanel.add(checkbox);
                libroPanel.add(apriSchedaButton);

                // Aggiungi il pannello del libro al pannello principale della lista
                listaLibriPanel.add(libroPanel);
            }
        }

        listaLibriPanel.revalidate();
        listaLibriPanel.repaint();
    }

    /**
     * Apre la schermata di selezione dei libri per aggiungerli alla libreria.
     */
    private void apriSchermataAggiungiLibri() {
        BookSelectionPage selezioneLibri = new BookSelectionPage();
        selezioneLibri.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        selezioneLibri.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                List<Libro> libriSelezionati = selezioneLibri.getLibriSelezionati();
                if (!libriSelezionati.isEmpty()) {
                    for (Libro libro : libriSelezionati) {
                        gestioneLibreria.aggiungiLibro(GestioneSessione.getInstance().getUtenteLoggato().getUserId(), nomeLibreria, libro.getTitolo());
                    }
                    caricaLibriLibreria(); 
                }
            }
        });
    }

    /**
     * Rimuove i libri selezionati (checkbox selezionati) dalla libreria.
     */
    private void rimuoviLibriSelezionati() {
        List<String> libriDaRimuovere = new ArrayList<>();
        
        // Raccoglie i titoli dei libri selezionati dai checkbox
        for (JCheckBox checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                libriDaRimuovere.add(checkbox.getText());  // Aggiunge il titolo del libro da rimuovere
            }
        }

        // Se ci sono libri da rimuovere, rimuovili
        if (!libriDaRimuovere.isEmpty()) {
            for (String titoloLibro : libriDaRimuovere) {
                gestioneLibreria.rimuoviLibro(GestioneSessione.getInstance().getUtenteLoggato().getUserId(), nomeLibreria, titoloLibro);
            }
            caricaLibriLibreria(); 
        } else {
            JOptionPane.showMessageDialog(this, "Seleziona almeno un libro da rimuovere.");
        }
    }
}
