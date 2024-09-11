/**
 * Questa classe rappresenta un componente UI che mostra una riga con l'anteprima delle informazioni di un libro.
 * Visualizza il titolo, l'autore, la categoria e il prezzo del libro, oltre a un'immagine associata.
 * La riga è cliccabile e può essere utilizzata per aprire una finestra con i dettagli completi del libro.
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

package gui.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Paths;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import model.Libro;

/**
 * La classe BookOverviewRow crea una riga cliccabile che mostra le informazioni principali di un libro,
 * come il titolo, l'autore, la categoria, il prezzo e l'immagine associata. Viene utilizzata per
 * fornire un'anteprima dei libri in una lista.
 */
public class BookOverviewRow {

    private JPanel bookRow;

    /**
     * Costruttore che crea una riga contenente le informazioni principali del libro.
     * Questa riga include l'immagine, il titolo, l'autore, la categoria e il prezzo del libro.
     * La riga è cliccabile e può essere utilizzata per aprire una finestra con i dettagli completi del libro.
     * 
     * @param book L'oggetto Libro da visualizzare nella riga.
     * @param clickListener Il listener per gestire i click sulla riga del libro.
     */
    public BookOverviewRow(Libro book, MouseListener clickListener) {
        bookRow = new JPanel();
        bookRow.setLayout(new BorderLayout());
        bookRow.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bookIcon;
        String currentDirectory = System.getProperty("user.dir");
        String imagePath;

        if(currentDirectory.equals("/Users/alex/Desktop/ALEX/UNI/INSUBRIA/PRIMO-ANNO/laboratorioA/BookRecommender/bin")) {
            imagePath = Paths.get(currentDirectory, "..", "src", "main", "data", "book_icon.png").normalize().toString();
        } else {
            imagePath = Paths.get("src", "main", "data", "book_icon.png").toString();
        }

        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            bookIcon = new JLabel(new ImageIcon(scaledImage));
        } else {
            bookIcon = new JLabel("No Image");
            System.out.println("Immagine non trovata: " + imagePath);
        }

        bookIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        bookRow.add(bookIcon, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        String titolo = book.getTitolo();
        if (titolo.length() > 100) {
            titolo = titolo.substring(0, 100) + "...";
        }

        JLabel titleLabel = new JLabel(titolo);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel authorLabel = new JLabel("By " + book.getAutore());
        JLabel categoryLabel = new JLabel(book.getCategoria().trim());
        JLabel publisherLabel = new JLabel(book.getEditore() + ", " + book.getMesePubblicazione() + " " + book.getAnnoPubblicazione());

        infoPanel.add(titleLabel);
        infoPanel.add(authorLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(publisherLabel);

        bookRow.add(infoPanel, BorderLayout.CENTER);

        JLabel priceLabel = new JLabel("$ " + book.getPrezzo());
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bookRow.add(priceLabel, BorderLayout.EAST);

        bookRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bookRow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bookRow.setBackground(new Color(220, 220, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bookRow.setBackground(UIManager.getColor("Panel.background"));
            }
        });

        bookRow.addMouseListener(clickListener);
    }

    /**
     * Restituisce il pannello JPanel che rappresenta la riga del libro.
     * 
     * @return Un JPanel contenente l'anteprima del libro.
     */
    public JPanel getBookRow() {
        return bookRow;
    }
}
