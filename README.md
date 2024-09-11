# BookRecommender 📚
BookRecommender è un'applicazione desktop basata su Java progettata per offrire agli utenti un sistema completo per gestire, valutare e raccomandare libri. L'applicazione utilizza l'interfaccia grafica Swing per fornire un'esperienza utente interattiva e semplice, sia per gli utenti registrati che per quelli non registrati. Il progetto si basa su un'architettura a più livelli con separazione tra la logica di controllo, gestione dei dati e interfaccia utente.

# Funzionalità principali ✨
- Registrazione e login degli utenti: Gli utenti possono registrarsi e accedere per creare librerie personali e ricevere raccomandazioni di lettura.
- Gestione delle librerie: Gli utenti registrati possono creare librerie personali, aggiungere, modificare o rimuovere libri.
- Valutazione dei libri: Gli utenti possono valutare i libri su vari criteri (stile, contenuto, gradevolezza, originalità, edizione) e lasciare recensioni.
- Raccomandazioni personalizzate: Il sistema offre consigli sui libri in base alle valutazioni e preferenze degli utenti.
- Ricerca avanzata: L'applicazione permette di cercare libri in base a diversi criteri come titolo, autore o anno di pubblicazione.
- Gestione dei dati con CSV: I dati relativi a utenti, libri, valutazioni e librerie sono salvati e gestiti tramite file CSV.

# Tecnologie utilizzate 🛠️
- Linguaggio: Java 17.0.11+10-LTS
- Framework GUI: Swing
- Gestione dipendenze: Maven
- Persistenza dei dati: File CSV, gestiti tramite la libreria OpenCSV
- Controllo di versione: Git

# Requisiti di sistema 🖥️
- Java: Versione 17 o superiore
- Maven: Per la gestione delle dipendenze
- Ambiente di sviluppo consigliato: Eclipse o Visual Studio Code (con estensione Java)
- Sistema operativo: Compatibile con Windows, macOS e Linux

Installazione e Setup ⚙️
1. Clonare il repository:
## git clone https://github.com/spideydev00/BookRecommender.git

2. Importare il progetto in un IDE (Eclipse o Visual Studio Code).

3. Costruire il progetto usando Maven:
## mvn clean install

4. Eseguire l'applicazione: Esegui la classe BookRecommender che contiene il metodo main per avviare l'applicazione.

# Esecuzione 🏃‍♂️
Per avviare l'applicazione, esegui la classe BookRecommender che avvierà l'interfaccia di registrazione o login dell'utente, o in alternativa apri il terminale nella cartella /bin del progetto ed esegui:

## java -jar BookRecommender.jar

Gli utenti possono navigare nel sistema tramite il menu e accedere a funzionalità come la gestione delle librerie, la ricerca dei libri, la valutazione e la raccomandazione dei libri.

# Dataset di test 📊
Il progetto include file CSV di esempio nella cartella src/main/resources, tra cui:

- Libri.csv: Contiene informazioni sui libri.
- UtentiRegistrati.csv: Contiene gli utenti registrati.
- ValutazioniLibri.csv: Contiene le valutazioni lasciate dagli utenti.
- Librerie.csv: Contiene le librerie personali degli utenti.
- LibriConsigliati.csv: Contiene i libri consigliati da ogni utente

# Limitazioni 🚧
- Attualmente, il sistema di raccomandazioni si basa su regole semplici e non utilizza un algoritmo avanzato di machine learning.
- Non c'è una vera e propria interfaccia di amministrazione per gestire i dati al di fuori dell'uso diretto dei file CSV.

# Contributi 🛠️
Ogni tipo di contributo è benvenuto! Si prega di aprire una "Issue" o inviare una "Pull Request" con miglioramenti o correzioni.