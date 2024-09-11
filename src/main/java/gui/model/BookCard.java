/**
 * La classe BookCard visualizza le informazioni di un libro in una finestra separata,
 * inclusa l'immagine, il titolo, l'autore, la descrizione, le valutazioni e i libri consigliati.
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

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import controller.GestioneSessione;
import controller.GestioneValutazione;
import controller.GestioneLibriConsigliati;
import gui.rating.RatingPage;
import gui.library.BookSelectionPage;
import model.Libro;
import utility.CSVReaderWriter;

/**
 * La classe BookCard crea una finestra grafica che mostra tutti i dettagli di un libro,
 * inclusi titolo, autore, immagine, descrizione, valutazioni e libri consigliati.
 */
public class BookCard extends JFrame {

    private static final long serialVersionUID = 1L;
    private Libro book;
    private JLabel valutazioniLabel;
    private JPanel buttonPanel;
    private JPanel libriConsigliatiPanel;
    private GestioneLibriConsigliati gestioneLibriConsigliati;

    /**
     * Costruttore che crea una nuova finestra per visualizzare le informazioni di un libro.
     * 
     * @param book L'oggetto Libro da visualizzare nella scheda.
     */
    public BookCard(Libro book) {
        this.book = book;
        gestioneLibriConsigliati = new GestioneLibriConsigliati();
        setTitle("Scheda Libro");
        setSize(900, 1000);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(createBookHeader(book));
        mainPanel.add(createSection("DESCRIZIONE", book.getDescrizione().isEmpty() ? "Nessuna descrizione disponibile." : book.getDescrizione()));
        mainPanel.add(createValutazioneSection());
        libriConsigliatiPanel = createLibriConsigliatiSection();
        mainPanel.add(libriConsigliatiPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);
        setVisible(true);
    }

    /**
     * Crea una sezione per i libri consigliati. Se l'utente è loggato, mostra i suoi libri consigliati.
     * Se l'utente è ospite, mostra una selezione casuale di libri consigliati.
     * 
     * @return Un JPanel con la sezione dei libri consigliati.
     */
    private JPanel createLibriConsigliatiSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Pannello per il titolo "LIBRI CONSIGLIATI" e il pulsante "Aggiungi Libri Consigliati"
        JPanel titleAndButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("LIBRI CONSIGLIATI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleAndButtonPanel.add(titleLabel);

        GestioneSessione sessionManager = GestioneSessione.getInstance();
        List<String> libriConsigliati;

        if (sessionManager.getUtenteLoggato() != null) {
            String userId = sessionManager.getUtenteLoggato().getUserId();
            libriConsigliati = gestioneLibriConsigliati.caricaLibriConsigliatiDaUtente(userId, book.getTitolo());

            if (libriConsigliati.size() < 3) {
                JButton aggiungiConsigliatiButton = new JButton("Aggiungi Libri Consigliati");
                aggiungiConsigliatiButton.addActionListener(e -> apriSchermataSelezioneLibriConsigliati());
                titleAndButtonPanel.add(aggiungiConsigliatiButton);
            }

            panel.add(titleAndButtonPanel);

            if (libriConsigliati.isEmpty()) {
                JPanel noBooksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel nessunConsiglio = new JLabel("Non hai ancora consigliato libri per questo titolo.");
                noBooksPanel.add(nessunConsiglio);
                panel.add(noBooksPanel);
            } else {
                for (String titoloLibro : libriConsigliati) {
                    JPanel bookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel libroConsigliatoLabel = new JLabel("- " + titoloLibro);
                    bookPanel.add(libroConsigliatoLabel);
                    panel.add(bookPanel);
                }
            }

        } else {
            libriConsigliati = gestioneLibriConsigliati.caricaLibriConsigliatiCasuali(book.getTitolo());
            panel.add(titleAndButtonPanel);

            if (libriConsigliati.isEmpty()) {
                JPanel noBooksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel nessunConsiglio = new JLabel("Nessun libro consigliato.");
                noBooksPanel.add(nessunConsiglio);
                panel.add(noBooksPanel);
            } else {
                for (String titoloLibro : libriConsigliati) {
                    JPanel bookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel libroConsigliatoLabel = new JLabel("- " + titoloLibro);
                    bookPanel.add(libroConsigliatoLabel);
                    panel.add(bookPanel);
                }
            }
        }

        return panel;
    }

    /**
     * Apre una finestra per selezionare e aggiungere fino a 3 libri consigliati.
     * Dopo la selezione, i libri vengono salvati e la sezione viene aggiornata.
     */
    private void apriSchermataSelezioneLibriConsigliati() {
        BookSelectionPage selezioneLibri = new BookSelectionPage();
        selezioneLibri.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        selezioneLibri.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                List<Libro> libriSelezionati = selezioneLibri.getLibriSelezionati();

                if (libriSelezionati.size() > 3) {
                    libriSelezionati = libriSelezionati.subList(0, 3);
                }

                if (!libriSelezionati.isEmpty()) {
                    List<String> titoliLibri = libriSelezionati.stream().map(Libro::getTitolo).toList();
                    gestioneLibriConsigliati.inserisciSuggerimentoLibro(
                            GestioneSessione.getInstance().getUtenteLoggato().getUserId(),
                            book.getTitolo(),
                            titoliLibri
                    );
                    aggiornaLibriConsigliati();
                }
            }
        });
    }

    /**
     * Aggiorna la sezione dei libri consigliati con i libri inseriti dall'utente.
     * Mostra anche il pulsante per aggiungere libri se ce ne sono meno di 3.
     */
    private void aggiornaLibriConsigliati() {
        libriConsigliatiPanel.removeAll();
        JLabel titleLabel = new JLabel("LIBRI CONSIGLIATI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        libriConsigliatiPanel.add(titleLabel);

        GestioneSessione sessionManager = GestioneSessione.getInstance();
        List<String> libriConsigliati = gestioneLibriConsigliati.caricaLibriConsigliatiDaUtente(sessionManager.getUtenteLoggato().getUserId(), book.getTitolo());

        if (libriConsigliati.isEmpty()) {
            JLabel nessunConsiglio = new JLabel("Nessun libro consigliato.");
            nessunConsiglio.setAlignmentX(Component.LEFT_ALIGNMENT);
            libriConsigliatiPanel.add(nessunConsiglio);
        } else {
            for (String titoloLibro : libriConsigliati) {
                JLabel libroConsigliatoLabel = new JLabel("- " + titoloLibro);
                libroConsigliatoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                libriConsigliatiPanel.add(libroConsigliatoLabel);
            }
        }

        if (libriConsigliati.size() < 3) {
            JButton aggiungiConsigliatiButton = new JButton("Aggiungi Libri Consigliati");
            aggiungiConsigliatiButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            aggiungiConsigliatiButton.addActionListener(e -> apriSchermataSelezioneLibriConsigliati());
            libriConsigliatiPanel.add(Box.createVerticalStrut(10));
            libriConsigliatiPanel.add(aggiungiConsigliatiButton);
        }

        libriConsigliatiPanel.revalidate();
        libriConsigliatiPanel.repaint();
    }

    /**
     * Crea una sezione per le valutazioni del libro. Se l'utente è loggato, può aggiungere valutazioni.
     * 
     * @return Un JPanel con la sezione delle valutazioni.
     */
    private JPanel createValutazioneSection() {
        JPanel valutazionePanel = new JPanel();
        valutazionePanel.setLayout(new BoxLayout(valutazionePanel, BoxLayout.Y_AXIS));
        valutazionePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("VALUTAZIONI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        buttonPanel.add(titleLabel);

        JPanel valutazioneMessagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        valutazioniLabel = new JLabel(loadBookRating(book.getTitolo()));
        valutazioniLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valutazioneMessagePanel.add(valutazioniLabel);

        valutazionePanel.add(buttonPanel);
        valutazionePanel.add(valutazioneMessagePanel);

        aggiornaValutazioni();

        return valutazionePanel;
    }

    /**
     * Aggiorna la sezione delle valutazioni con le nuove informazioni.
     * Rimuove il pulsante "Aggiungi Valutazione" se la valutazione è già stata fatta.
     */
    public void aggiornaValutazioni() {
        buttonPanel.removeAll();

        JLabel titleLabel = new JLabel("VALUTAZIONI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        buttonPanel.add(titleLabel);

        GestioneSessione sessionManager = GestioneSessione.getInstance();
        GestioneValutazione ratingManager = new GestioneValutazione();

        if (sessionManager.getUtenteLoggato() != null 
                && !ratingManager.utenteHaValutato(book.getTitolo(), sessionManager.getUtenteLoggato().getUserId())) {
            JButton aggiungiValutazioneButton = new JButton("Aggiungi Valutazione");
            aggiungiValutazioneButton.addActionListener(e -> new RatingPage(book, this));
            buttonPanel.add(aggiungiValutazioneButton);
        }

        valutazioniLabel.setText(loadBookRating(book.getTitolo()));

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    /**
     * Carica le valutazioni medie di un libro dal file CSV ValutazioniLibri.csv.
     * 
     * @param titoloLibro Il titolo del libro di cui caricare le valutazioni.
     * @return Una stringa con la valutazione media o un messaggio se non ci sono valutazioni.
     */
    private String loadBookRating(String titoloLibro) {
        List<String[]> valutazioni = CSVReaderWriter.getInstance().leggiDaCSV("ValutazioniLibri.csv");
        int count = 0;
        double totaleVotoFinale = 0;

        for (String[] riga : valutazioni) {
            if (riga[0].equalsIgnoreCase(titoloLibro)) {
                totaleVotoFinale += Double.parseDouble(riga[7]);
                count++;
            }
        }

        if (count > 0) {
            double media = totaleVotoFinale / count;
            return String.format("Valutazione media: %.2f su %d valutazioni.", media, count);
        } else {
            return "Nessuna valutazione trovata.";
        }
    }

    /**
     * Crea una sezione scrollabile che contiene il titolo e il contenuto di una sezione del libro.
     * 
     * @param title Il titolo della sezione.
     * @param content Il contenuto della sezione.
     * @return Un JPanel contenente il titolo e il contenuto della sezione.
     */
    private JPanel createSection(String title, String content) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sectionPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setOpaque(true);
        contentArea.setBorder(BorderFactory.createEmptyBorder());
        contentArea.setColumns(20);

        JScrollPane scrollableTextArea = new JScrollPane(contentArea);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableTextArea.setPreferredSize(new Dimension(550, 150));

        sectionPanel.add(scrollableTextArea, BorderLayout.CENTER);

        return sectionPanel;
    }

    /**
     * Crea l'header del libro con l'immagine e le informazioni principali come titolo e autore.
     * 
     * @param book Il libro da visualizzare.
     * @return Un JPanel contenente l'header con l'immagine e le informazioni principali.
     */
    private JPanel createBookHeader(Libro book) {
        JPanel bookHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bookIcon;
        String currentDirectory = System.getProperty("user.dir");
        String imagePath;

        if (currentDirectory.equals("/Users/alex/Desktop/ALEX/UNI/INSUBRIA/PRIMO-ANNO/laboratorioA/BookRecommender/bin")) {
            imagePath = Paths.get(currentDirectory, "..", "src", "main", "data", "book_icon.png").normalize().toString();
        } else {
            imagePath = Paths.get("src", "main", "data", "book_icon.png").toString();
        }

        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            bookIcon = new JLabel(new ImageIcon(scaledImage));
        } else {
            bookIcon = new JLabel("No Image");
        }

        bookIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(book.getTitolo());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel authorLabel = new JLabel("By " + book.getAutore());
        JLabel categoryLabel = new JLabel(book.getCategoria().trim());
        JLabel publisherLabel = new JLabel(book.getEditore() + ", " + book.getMesePubblicazione() + " " + book.getAnnoPubblicazione());
        JLabel priceLabel = new JLabel("$ " + book.getPrezzo());
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        infoPanel.add(titleLabel);
        infoPanel.add(authorLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(publisherLabel);
        infoPanel.add(priceLabel);

        bookHeader.add(bookIcon);
        bookHeader.add(infoPanel);

        return bookHeader;
    }
}
