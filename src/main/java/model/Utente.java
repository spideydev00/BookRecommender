/**
 * Questa classe rappresenta il modello di un utente nel sistema BookRecommender.
 * Gli utenti hanno attributi personali come nome, cognome, codice fiscale, email, userId e password.
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

package model;

/**
 * La classe Utente rappresenta un utente con attributi personali e credenziali di accesso,
 * come nome, cognome, codice fiscale, email, userId e password.
 */
public class Utente {
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String email;
    private String userId;
    private String password;

    /**
     * Costruisce un nuovo utente con tutti i dettagli necessari.
     *
     * @param nome Nome dell'utente
     * @param cognome Cognome dell'utente
     * @param codiceFiscale Codice fiscale dell'utente, deve essere valido
     * @param email Email dell'utente, deve essere valida
     * @param userId Identificativo unico dell'utente
     * @param password Password di accesso dell'utente
     */
    public Utente(String nome, String cognome, String codiceFiscale, String email, String userId, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.setCodiceFiscale(codiceFiscale);
        this.setEmail(email);
        this.userId = userId;
        this.password = password;
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return Il nome dell'utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome dell'utente.
     *
     * @param nome Il nuovo nome dell'utente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return Il cognome dell'utente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome dell'utente.
     *
     * @param cognome Il nuovo cognome dell'utente
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce il codice fiscale dell'utente.
     *
     * @return Il codice fiscale dell'utente
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il codice fiscale dell'utente, dopo averne validato il formato.
     *
     * @param codiceFiscale Il codice fiscale da validare e impostare
     * @throws IllegalArgumentException Se il codice fiscale non è valido
     */
    public void setCodiceFiscale(String codiceFiscale) {
        if (validaCodiceFiscale(codiceFiscale)) {
            this.codiceFiscale = codiceFiscale;
        } else {
            throw new IllegalArgumentException("Codice fiscale non valido");
        }
    }

    /**
     * Metodo di validazione per il codice fiscale.
     *
     * @param codiceFiscale Il codice fiscale da validare
     * @return true se il codice fiscale è valido, false altrimenti
     */
    private boolean validaCodiceFiscale(String codiceFiscale) {
        // Logica di validazione del codice fiscale
        return true; // Assumiamo true per semplificare
    }

    /**
     * Restituisce l'email dell'utente.
     *
     * @return L'email dell'utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email dell'utente, dopo averne validato il formato.
     *
     * @param email L'email da validare e impostare
     * @throws IllegalArgumentException Se l'email non è valida
     */
    public void setEmail(String email) {
        if (validaEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email non valida");
        }
    }

    /**
     * Metodo di validazione per l'email.
     *
     * @param email L'email da validare
     * @return true se l'email è valida, false altrimenti
     */
    private boolean validaEmail(String email) {
        // Logica di validazione dell'email
        return true; // Assumiamo true per ora
    }

    /**
     * Restituisce l'ID utente.
     *
     * @return L'ID utente
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Imposta l'ID utente.
     *
     * @param userId Il nuovo ID utente
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Restituisce la password dell'utente.
     *
     * @return La password dell'utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'utente.
     *
     * @param password La nuova password dell'utente
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'utente.
     *
     * @return Una stringa che rappresenta l'utente
     */
    @Override
    public String toString() {
        return "Utente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", codice fiscale='" + codiceFiscale + '\'' +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
