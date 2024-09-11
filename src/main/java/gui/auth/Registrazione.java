/**
 * La classe Registrazione gestisce l'interfaccia utente per la registrazione di un nuovo utente nel sistema.
 * Consente agli utenti di inserire i propri dati personali per creare un nuovo account.
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

package gui.auth;

import javax.swing.*;

import controller.GestioneUtente;
import controller.GestioneUtente.UtenteEsistenteException;
import gui.search.SearchBookPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La classe Registrazione gestisce l'interfaccia per permettere agli utenti di registrarsi nel sistema.
 * Include la validazione del codice fiscale e dell'email, oltre al controllo che tutti i campi siano compilati correttamente.
 */
public class Registrazione extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField codicefiscaleField;
    private JTextField emailField;
    private JTextField useridField;
    private JPasswordField passwordField;

    /**
     * Costruttore della classe Registrazione. Inizializza l'interfaccia per la registrazione e i relativi campi di input.
     */
    public Registrazione() {
        setTitle("Registra un nuovo account");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Nome");
        JLabel lastNameLabel = new JLabel("Cognome");
        JLabel codicefiscaleLabel = new JLabel("Codice Fiscale");
        JLabel emailLabel = new JLabel("Email");
        JLabel useridLabel = new JLabel("User ID");
        JLabel passwordLabel = new JLabel("Password");

        nameField = new JTextField(20);
        lastNameField = new JTextField(20);
        codicefiscaleField = new JTextField(20);
        emailField = new JTextField(30);
        useridField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JButton registrationButton = new JButton("Registrazione");
        JButton toLoginButton = new JButton("Ho già un account");
        JButton genericAccessButton = new JButton("Accedi come ospite");

        GestioneUtente gestioneUtente = GestioneUtente.getInstance();
        String[] dati = new String[6];

        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dati[0] = nameField.getText();
                dati[1] = lastNameField.getText();
                dati[2] = codicefiscaleField.getText();
                dati[3] = emailField.getText();
                dati[4] = useridField.getText();
                dati[5] = new String(passwordField.getPassword());

                // Controlli di validazione
                if (!fieldHasText()) {
                    JOptionPane.showMessageDialog(Registrazione.this, "Inserisci i campi mancanti.", "Errore di compilazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!emailIsFormatted(dati[3])) {
                    JOptionPane.showMessageDialog(Registrazione.this, "L'email inserita non è valida!", "Errore di compilazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!codiceFiscaleIsFormatted(dati[2].toUpperCase())) {
                    JOptionPane.showMessageDialog(Registrazione.this, "Il codice fiscale non è corretto!", "Errore di compilazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    gestioneUtente.registraUtente(dati[0], dati[1], dati[2], dati[3], dati[4], dati[5]);

                    Object[] options = { "OK" };
                    int n = JOptionPane.showOptionDialog(Registrazione.this, "Registrazione completata", "Successo",
                            JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                    if (n == JOptionPane.OK_OPTION) {
                        dispose();
                        new Login();
                    }

                } catch (UtenteEsistenteException ex) {
                    JOptionPane.showMessageDialog(Registrazione.this, ex.getMessage(), "Errore di Registrazione", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        toLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });

        genericAccessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SearchBookPage();
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(nameLabel);
        add(nameField);
        add(lastNameLabel);
        add(lastNameField);
        add(codicefiscaleLabel);
        add(codicefiscaleField);
        add(emailLabel);
        add(emailField);
        add(useridLabel);
        add(useridField);
        add(passwordLabel);
        add(passwordField);
        add(registrationButton);
        add(toLoginButton);
        add(genericAccessButton);

        setVisible(true);
    }

    /**
     * Controlla se tutti i campi di testo sono stati compilati.
     * 
     * @return true se tutti i campi sono compilati, false altrimenti.
     */
    private boolean fieldHasText() {
        return !(nameField.getText().isEmpty() || lastNameField.getText().isEmpty()
                || passwordField.getPassword().length == 0 || codicefiscaleField.getText().isEmpty()
                || emailField.getText().isEmpty() || useridField.getText().isEmpty());
    }

    /**
     * Verifica se l'email inserita dall'utente è correttamente formattata.
     * 
     * @param email L'email da verificare.
     * @return true se l'email è valida, false altrimenti.
     */
    private boolean emailIsFormatted(String email) {
        String regexPattern = "^[0-9a-zA-Z]+([._-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+([.-]?[0-9a-zA-Z]+)*\\.[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Verifica se il codice fiscale inserito dall'utente è correttamente formattato e include
     * il calcolo del carattere di controllo.
     * 
     * @param codiceFiscale Il codice fiscale da verificare.
     * @return true se il codice fiscale è valido, false altrimenti.
     */
    private boolean codiceFiscaleIsFormatted(String codiceFiscale) {
        String regexPattern = "^[A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(codiceFiscale);

        if (!matcher.matches()) {
            return false;
        }

        char[] lettere = codiceFiscale.toCharArray();
        int valoreFinale = 0;

        int[] valoriDispari = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25,
                24, 23 };
        int[] valoriPari = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
                25 };

        for (int i = 0; i < 15; i++) {
            char lettera = lettere[i];
            int indiceLettera = Character.isDigit(lettera) ? lettera - '0' : lettera - 'A';

            if (i % 2 == 0) {
                valoreFinale += valoriDispari[indiceLettera];
            } else {
                valoreFinale += valoriPari[indiceLettera];
            }
        }

        valoreFinale %= 26;
        char carattereControllo = (char) ('A' + valoreFinale);

        return carattereControllo == lettere[15];
    }
}
