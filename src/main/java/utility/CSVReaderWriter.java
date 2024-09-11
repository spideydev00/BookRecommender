/**
 * Questa classe fornisce metodi di utilità per leggere e scrivere file CSV
 * all'interno del sistema BookRecommender. Implementa il pattern Singleton per
 * garantire che una sola istanza della classe sia utilizzata durante l'esecuzione.
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

package utility;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvValidationException;

/**
 * La classe CSVReaderWriter fornisce metodi per leggere e scrivere dati nei file CSV.
 * Utilizza il pattern Singleton per garantire che una sola istanza sia creata durante l'esecuzione.
 */
public class CSVReaderWriter {
    private static CSVReaderWriter instance;

    /**
     * Costruttore privato per impedire l'instanziazione esterna (Singleton).
     */
    private CSVReaderWriter() {}

    /**
     * Restituisce l'unica istanza di CSVReaderWriter (Singleton).
     *
     * @return L'istanza singleton di CSVReaderWriter.
     */
    public static synchronized CSVReaderWriter getInstance() {
        if (instance == null) {
            instance = new CSVReaderWriter();
        }
        return instance;
    }

    /**
     * Legge i dati da un file CSV e li restituisce come lista di array di stringhe.
     *
     * @param nomeFile Il nome del file CSV da leggere.
     * @return Una lista di righe del CSV, rappresentate come array di stringhe.
     */
    public List<String[]> leggiDaCSV(String nomeFile) {
        String currentDirectory = System.getProperty("user.dir");
        Path percorsoFile;

        if (currentDirectory.equals("/Users/alex/Desktop/ALEX/UNI/INSUBRIA/PRIMO-ANNO/laboratorioA/BookRecommender/bin")) {
            percorsoFile = Paths.get(currentDirectory, "..", "src", "main", "data", nomeFile).normalize();
        } else {
            percorsoFile = Paths.get("src", "main", "data", nomeFile);
        }

        List<String[]> contenutoFile = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(percorsoFile.toString()))
                .withCSVParser(new RFC4180ParserBuilder().build())
                .build()) {
            String[] rigaSuccessiva;
            while ((rigaSuccessiva = reader.readNext()) != null) {
                contenutoFile.add(rigaSuccessiva);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            System.err.println("Errore nella lettura del file CSV: " + nomeFile);
        }

        return contenutoFile;
    }

    /**
     * Scrive una riga di dati in un file CSV.
     *
     * @param nomeFile Il nome del file CSV di destinazione.
     * @param dati La riga di dati da scrivere nel file CSV.
     */
    public void scriviSuCSV(String nomeFile, String[] dati) {
        String currentDirectory = System.getProperty("user.dir");
        Path percorsoFile;

        if (currentDirectory.equals("/Users/alex/Desktop/ALEX/UNI/INSUBRIA/PRIMO-ANNO/laboratorioA/BookRecommender/bin")) {
            percorsoFile = Paths.get(currentDirectory, "..", "src", "main", "data", nomeFile).normalize();
        } else {
            percorsoFile = Paths.get("src", "main", "data", nomeFile);
        }

        try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(percorsoFile.toString(), true))
                .withLineEnd(CSVWriter.DEFAULT_LINE_END).build()) {
            writer.writeNext(dati);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Errore nella scrittura del file CSV: " + percorsoFile);
        }
    }

    /**
     * Verifica se una riga di dati esterni esiste già nel file CSV.
     *
     * @param nomeFile Il nome del file CSV.
     * @param datiEsterni La riga di dati da confrontare.
     * @return true se i dati esterni sono già presenti nel CSV, false altrimenti.
     */
    public boolean isEqualToCSV(String nomeFile, String[] datiEsterni) {
        List<String[]> contenutoCSV = leggiDaCSV(nomeFile);
        return contenutoCSV.stream().anyMatch(riga -> Arrays.equals(riga, datiEsterni));
    }

    /**
     * Verifica se i valori di specifici indici corrispondono tra i dati esterni e il file CSV.
     *
     * @param nomeFile Il nome del file CSV.
     * @param datiEsterni I dati da confrontare.
     * @param indiciDaConfrontare Gli indici delle colonne da confrontare.
     * @return true se i dati corrispondono agli indici specificati, false altrimenti.
     */
    public boolean indexIsEqualToCSV(String nomeFile, String[] datiEsterni, int[] indiciDaConfrontare) {
        List<String[]> contenutoCSV = leggiDaCSV(nomeFile);
        for (String[] riga : contenutoCSV) {
            boolean isMatch = true;
            for (int indice : indiciDaConfrontare) {
                if (riga.length <= indice || !riga[indice].equals(datiEsterni[indice])) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ottiene una riga di dati dal file CSV che corrisponde a un valore di input in una specifica colonna.
     *
     * @param file Il nome del file CSV.
     * @param inputData Il valore di input da cercare.
     * @param i L'indice della colonna da confrontare.
     * @return La riga corrispondente se trovata, altrimenti null.
     */
    public String[] getAllData(String file, String inputData, int i) {
        List<String[]> contenutoCSV = leggiDaCSV(file);
        return contenutoCSV.stream()
                .filter(riga -> riga.length > i && riga[i].equals(inputData))
                .findFirst()
                .orElse(null);
    }

    /**
     * Sovrascrive il contenuto di un file CSV con nuovi dati.
     *
     * @param nomeFile Il nome del file CSV da sovrascrivere.
     * @param nuovoContenuto La lista di righe che sostituirà il contenuto esistente.
     */
    public void aggiornaCSV(String nomeFile, List<String[]> nuovoContenuto) {
        String currentDirectory = System.getProperty("user.dir");
        Path percorsoFile;

        if (currentDirectory.equals("/Users/alex/Desktop/ALEX/UNI/INSUBRIA/PRIMO-ANNO/laboratorioA/BookRecommender/bin")) {
            percorsoFile = Paths.get(currentDirectory, "..", "src", "main", "data", nomeFile).normalize();
        } else {
            percorsoFile = Paths.get("src", "main", "data", nomeFile);
        }

        try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(percorsoFile.toString(), false))
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build()) {
            for (String[] riga : nuovoContenuto) {
                writer.writeNext(riga);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Errore nella sovrascrittura del file CSV: " + percorsoFile);
        }
    }

}
