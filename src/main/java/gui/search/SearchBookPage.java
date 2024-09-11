/**
 * La classe SearchBookPage rappresenta un'interfaccia utente generica per un sistema di gestione
 * libri. Consente agli utenti di cercare libri per titolo, autore o autore e anno attraverso un'interfaccia
 * grafica con un menu laterale e un'area di visualizzazione.
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

package gui.search;

import javax.swing.*;
import controller.GestioneLibro;
import controller.GestioneSessione;
import gui.auth.Registrazione;
import gui.menu.RegisteredUserMenu;
import model.Libro;
import java.awt.*;
import java.util.List;

/**
 * La classe SearchBookPage crea un'interfaccia utente con un menu laterale che consente di
 * cercare libri per titolo, autore o autore e anno. Le diverse sezioni di ricerca vengono
 * visualizzate dinamicamente utilizzando un layout CardLayout.
 */
public class SearchBookPage extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private GestioneLibro bookManager = new GestioneLibro();

    /**
     * Costruttore della classe SearchBookPage.
     * Inizializza la finestra principale con un menu laterale e varie opzioni di ricerca.
     */
    public SearchBookPage() {
        setTitle("Inizia una nuova ricerca");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel sideMenu = createSideMenu();
        add(sideMenu, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(createSearchByTitlePanel(), "Cerca libro per titolo");
        contentPanel.add(createSearchByAuthorPanel(), "Cerca libro per autore");
        contentPanel.add(createSearchByAuthorAndYearPanel(), "Cerca libro per autore e anno");
        cardLayout.show(contentPanel, "Cerca libro per titolo");

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Crea il menu laterale che consente di selezionare le opzioni di ricerca.
     * 
     * @return Un JPanel contenente i pulsanti del menu laterale.
     */
    private JPanel createSideMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton searchByTitleButton = new JButton("Cerca libro per titolo");
        JButton searchByAuthorButton = new JButton("Cerca libro per autore");
        JButton searchByAuthorAndYearButton = new JButton("Cerca libro per autore e anno");
        JButton backButton = new JButton("Torna indietro");

        searchByTitleButton.addActionListener(e -> cardLayout.show(contentPanel, "Cerca libro per titolo"));
        searchByAuthorButton.addActionListener(e -> cardLayout.show(contentPanel, "Cerca libro per autore"));
        searchByAuthorAndYearButton.addActionListener(e -> cardLayout.show(contentPanel, "Cerca libro per autore e anno"));
        backButton.addActionListener(e -> {
            if (GestioneSessione.getInstance().getUtenteLoggato() != null) {
                dispose();
                new RegisteredUserMenu();
            } else {
                dispose();
                new Registrazione();
            }
        });

        panel.add(searchByTitleButton);
        panel.add(searchByAuthorButton);
        panel.add(searchByAuthorAndYearButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backButton);

        return panel;
    }

    /**
     * Crea il pannello per la ricerca di libri per titolo.
     * 
     * @return Un JPanel per la ricerca di libri per titolo.
     */
    private JPanel createSearchByTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Cerca libro per titolo:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Cerca");

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(searchField);
        inputPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            String titolo = searchField.getText().trim();
            if (titolo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci un titolo per la ricerca.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                List<Libro> result = bookManager.cercaLibroPerTitolo(titolo);
                new BookDisplayPage(result);
            }
        });

        panel.add(label, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea il pannello per la ricerca di libri per autore.
     * 
     * @return Un JPanel per la ricerca di libri per autore.
     */
    private JPanel createSearchByAuthorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Cerca libro per autore:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Cerca");

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(searchField);
        inputPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            String autore = searchField.getText().trim();
            if (autore.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci un autore per la ricerca.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                List<Libro> result = bookManager.cercaLibroPerAutore(autore);
                new BookDisplayPage(result);
            }
        });

        panel.add(label, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea il pannello per la ricerca di libri per autore e anno minimo.
     * 
     * @return Un JPanel per la ricerca di libri per autore e anno.
     */
    private JPanel createSearchByAuthorAndYearPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel authorLabel = new JLabel("Autore:");
        JTextField authorField = new JTextField(15);
        addComponent(inputPanel, authorLabel, gbc, 0, 0, 0.2);
        addComponent(inputPanel, authorField, gbc, 1, 0, 1.0);

        JLabel yearLabel = new JLabel("Anno Minimo:");
        JTextField yearField = new JTextField(5);
        addComponent(inputPanel, yearLabel, gbc, 0, 1, 0.2);
        addComponent(inputPanel, yearField, gbc, 1, 1, 1.0);

        JButton searchButton = new JButton("Cerca");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(searchButton, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);

        searchButton.addActionListener(e -> {
            String autore = authorField.getText().trim();
            String annoText = yearField.getText().trim();

            if (autore.isEmpty() || annoText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci autore e anno minimo.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int annoMinimo = Integer.parseInt(annoText);
                List<Libro> result = bookManager.cercaLibroPerAutoreAnno(autore, annoMinimo);
                new BookDisplayPage(result);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Anno minimo non valido. Inserisci un numero intero.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    /**
     * Metodo di supporto per aggiungere componenti al layout GridBagLayout.
     * 
     * @param panel Il JPanel su cui aggiungere il componente.
     * @param component Il componente da aggiungere.
     * @param gbc Il GridBagConstraints per il layout.
     * @param x La posizione della cella in orizzontale.
     * @param y La posizione della cella in verticale.
     * @param weightx Il peso orizzontale della cella.
     */
    private void addComponent(JPanel panel, JComponent component, GridBagConstraints gbc, int x, int y, double weightx) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }
}
