/**
 * La classe Login rappresenta l'interfaccia utente per il login al sistema. Gli utenti
 * possono inserire il proprio nome utente e password per autenticarsi, oppure
 * registrarsi nel sistema tramite un pulsante di registrazione.
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
import gui.menu.RegisteredUserMenu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe Login gestisce l'interfaccia per il login degli utenti.
 * Gli utenti possono inserire nome utente e password per accedere, oppure possono registrarsi.
 */
public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private GestioneUtente userManager;

    /**
     * Costruttore della classe Login. Inizializza l'interfaccia grafica per il login
     * e la registrazione.
     */
    public Login() {
        setTitle("Accedi al tuo account");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Nome Utente");
        JLabel passwordLabel = new JLabel("Password");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        userManager = GestioneUtente.getInstance();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Oppure registrati");

        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (userManager.checkCredenziali(userId, password)) {
                    Object[] options = {"Prosegui"};
                    int n = JOptionPane.showOptionDialog(Login.this,
                            "Login Confermato. Clicca su \"Prosegui\"",
                            "Login Completato!",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null, options, options[0]);

                    if (n == JOptionPane.YES_OPTION) {
                        userManager.loginUtente(userId, password);
                        dispose();
                        new RegisteredUserMenu();
                    }
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Nome Utente o Password non validi", "Errore di Validazione", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Registrazione();
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(registerButton);
        setVisible(true);
    }
}
