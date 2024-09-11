/**
 * La classe RegisteredUserMenu rappresenta il menu principale per gli utenti registrati
 * nel sistema Book Recommender. Fornisce opzioni per uscire, cercare libri o gestire le librerie personali.
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

package gui.menu;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.GestioneSessione;
import gui.library.UserLibraryPage;
import gui.auth.Login;
import gui.search.SearchBookPage;

/**
 * La classe RegisteredUserMenu crea un'interfaccia utente per gli utenti registrati,
 * permettendo loro di accedere alle funzionalità principali come uscire, cercare libri
 * e visualizzare le proprie librerie.
 */
public class RegisteredUserMenu {

    private JMenu menu, submenu, submenu1;
    private JMenuItem i1, i2, i3;

    /**
     * Costruttore della classe RegisteredUserMenu. Inizializza il menu e le sue opzioni,
     * e gestisce le interazioni dell'utente tramite ActionListener per ogni voce di menu.
     */
    public RegisteredUserMenu() {
        JFrame f = new JFrame("Book Recommender");
        JMenuBar mb = new JMenuBar();
        menu = new JMenu("Menu");
        submenu = new JMenu("Libri");
        submenu1 = new JMenu("Libreria");

        i1 = new JMenuItem("Esci");
        i2 = new JMenuItem("Ricerca Libro");
        i3 = new JMenuItem("Le Mie Librerie");

        menu.add(submenu);
        menu.add(submenu1);
        menu.add(i1);
        submenu.add(i2);
        submenu1.add(i3);

        mb.add(menu);
        f.setJMenuBar(mb);

        // Ottieni il nome dell'utente loggato
        String nomeUtente = GestioneSessione.getInstance().getUtenteLoggato().getNome();

        // Crea un'etichetta di benvenuto con il nome dell'utente
        JLabel welcomeLabel = new JLabel("Bentornato " + nomeUtente + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 23));
        welcomeLabel.setBounds(50, 100, 250, 50); 

        f.add(welcomeLabel);

        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                JOptionPane.showMessageDialog(f, "Sei uscito dal sistema.");
                GestioneSessione.getInstance().setUtenteLoggato(null);
                new Login();
            }
        });

        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new SearchBookPage();
            }
        });

        i3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new UserLibraryPage(); 
            }
        });

        f.setSize(350, 350);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
    }
}
