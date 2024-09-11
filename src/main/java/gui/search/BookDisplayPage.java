/**
 * Questa classe rappresenta una finestra GUI che visualizza un elenco di libri.
 * Ogni libro viene mostrato come una riga contenente le sue informazioni principali 
 * e permette di cliccare per visualizzare i dettagli completi.
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

import gui.model.BookCard;
import gui.model.BookOverviewRow;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import model.Libro;

/**
 * La classe BookDisplayPage visualizza un elenco di libri all'interno di una finestra. 
 * Ogni libro viene mostrato come una riga cliccabile e permette di accedere alla 
 * visualizzazione dei dettagli completi del libro.
 */
public class BookDisplayPage extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Costruttore che crea una finestra per visualizzare l'elenco dei libri.
     * 
     * @param books Una lista di oggetti Libro da visualizzare.
     */
    public BookDisplayPage(List<Libro> books) {
        setTitle("Elenco Libri");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (Libro book : books) {
            BookOverviewRow bookOverview = new BookOverviewRow(book, new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new BookCard(book);
                }
            });
            mainPanel.add(bookOverview.getBookRow());
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
