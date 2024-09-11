/**
 * La classe UserLibraryPage visualizza la lista delle librerie create dall'utente
 * e permette di creare, visualizzare i dettagli, o eliminare una libreria. L'interfaccia
 * include pulsanti per la gestione delle librerie e la navigazione verso altre sezioni del sistema.
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import controller.GestioneLibreria;
import controller.GestioneSessione;
import gui.menu.RegisteredUserMenu;
import model.Libreria;

/**
 * La classe UserLibraryPage gestisce la visualizzazione e la gestione delle librerie utente.
 * Consente agli utenti di creare, eliminare e visualizzare i dettagli delle librerie create.
 */
public class UserLibraryPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JList<String> listaLibrerie;
    private DefaultListModel<String> listaLibrerieModel;
    private GestioneLibreria gestioneLibreria;

    /**
     * Costruttore della classe UserLibraryPage. Inizializza l'interfaccia grafica per la visualizzazione
     * e gestione delle librerie dell'utente.
     */
    public UserLibraryPage() {
        gestioneLibreria = new GestioneLibreria();
        setTitle("Le mie Librerie");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        listaLibrerieModel = new DefaultListModel<>();
        listaLibrerie = new JList<>(listaLibrerieModel);
        listaLibrerie.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaLibrerie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Doppio clic sulla libreria
                    int index = listaLibrerie.getSelectedIndex();
                    if (index >= 0) {
                        String nomeLibreria = listaLibrerieModel.getElementAt(index);
                        apriDettagliLibreria(nomeLibreria);
                    }
                }
            }
        });

        caricaLibrerie(); 

        JButton aggiungiLibreriaButton = new JButton("Crea nuova Libreria");
        JButton eliminaLibreriaButton = new JButton("Elimina Libreria");
        JButton takeToHomeButton = new JButton("Torna alla Home");

        // Aggiungi Libreria
        aggiungiLibreriaButton.addActionListener(e -> {
            CreateLibraryPage createLibraryPage = new CreateLibraryPage(this);
            createLibraryPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });

        // Elimina Libreria
        eliminaLibreriaButton.addActionListener(e -> {
            int index = listaLibrerie.getSelectedIndex();
            if (index >= 0) {
                String nomeLibreria = listaLibrerieModel.getElementAt(index);
                int conferma = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler eliminare la libreria: " + nomeLibreria + "?",
                        "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
                if (conferma == JOptionPane.YES_OPTION) {
                    eliminaLibreria(nomeLibreria);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona una libreria da eliminare.");
            }
        });

        // Torna alla Home
        takeToHomeButton.addActionListener(e -> {
            dispose();
            new RegisteredUserMenu();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(aggiungiLibreriaButton);
        buttonPanel.add(eliminaLibreriaButton);
        buttonPanel.add(takeToHomeButton);

        add(new JScrollPane(listaLibrerie), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Carica le librerie dell'utente dalla sessione corrente e le visualizza nella lista.
     */
    private void caricaLibrerie() {
        listaLibrerieModel.clear();  // Pulisce la lista esistente
        List<Libreria> librerieUtente = gestioneLibreria.getLibrerieByUser(GestioneSessione.getInstance().getUtenteLoggato().getUserId());

        if (librerieUtente.isEmpty()) {
            listaLibrerieModel.addElement("Nessuna libreria creata.");
        } else {
            for (Libreria libreria : librerieUtente) {
                listaLibrerieModel.addElement(libreria.getNomeLibreria());
            }
        }
    }

    /**
     * Aggiorna la lista delle librerie in tempo reale dopo la creazione o eliminazione di una libreria.
     */
    public void aggiornaLibrerie() {
        caricaLibrerie();  // Ricarica le librerie dell'utente
    }

    /**
     * Apre i dettagli di una libreria in una nuova finestra.
     * 
     * @param nomeLibreria Il nome della libreria da aprire.
     */
    private void apriDettagliLibreria(String nomeLibreria) {
        LibraryDetailsPage detailsPage = new LibraryDetailsPage(nomeLibreria);
        detailsPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Elimina la libreria selezionata e aggiorna la lista delle librerie.
     * 
     * @param nomeLibreria Il nome della libreria da eliminare.
     */
    private void eliminaLibreria(String nomeLibreria) {
        gestioneLibreria.eliminaLibreria(GestioneSessione.getInstance().getUtenteLoggato().getUserId(), nomeLibreria);
        aggiornaLibrerie();  // Ricarica la lista delle librerie dopo l'eliminazione
        JOptionPane.showMessageDialog(this, "Libreria eliminata con successo!");
    }
}
