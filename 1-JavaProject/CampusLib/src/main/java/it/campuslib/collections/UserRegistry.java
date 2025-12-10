package it.campuslib.collections;

import it.campuslib.domain.users.User;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @brief Il registro degli utenti della biblioteca.
 */
public class UserRegistry {
    private HashMap<Integer, User> registry;

    /**
     * @brief Costruttore.
     * @post
     * Il registro utenti è vuoto.
     */
    public UserRegistry() {
    }

    /**
     * @brief Aggiunge un utente al registro.
     * L'utente è aggiunto se l'ID di iscrizione è valido.
     * @param[in] user Utente da aggiungere.
     * @return Esito aggiunta.
     */
    public boolean addUser(User user) {
        return false;
    }

    /**
     * @brief Rimuove un utente dal registro.
     * @param[in] enrollmentID ID di iscrizione dell'utente da rimuovere.
     * @return Esito rimozione.
     * @post
     * Se presente, l'utente viene rimosso dal registro, altrimenti restituisce false.
     */
    public boolean removeUser(int enrollmentID) {
        return false;
    }

    /**
     * @brief Cerca utenti nel registro per nome e cognome.
     * @param[in] name Nome da cercare.
     * @param[in] surname Cognome da cercare.
     * @return Lista di utenti trovati (vuota se nessun utente corrisponde al criterio di ricerca).
     */
    public LinkedList<User> searchByName(String name, String surname) {
        return null;
    }

    /**
     * @brief Cerca un utente nel registro per ID di iscrizione.
     * @param[in] enrollmentID ID di iscrizione da cercare.
     * @return Utente trovato o null se non presente.
     */
    public User searchByEnrollmentID(int enrollmentID) {
        return null;
    }

    /**
     * @brief Cerca utenti secondo una query generica.
     * @param[in] query Stringa di ricerca (cerca in nome, cognome, email, ID).
     * @return Lista di utenti che corrispondono alla query (vuota se nessun utente corrisponde al criterio di ricerca).
     */
    public LinkedList<User> search(String query) {
        return null;
    }

    /**
     * @brief Restituisce una rappresentazione testuale del registro.
     * @return Rappresentazione testuale del registro.
     */
    public String toString() {
        return null;
    }

    /**
     * @brief Esporta il registro su file.
     * @param[in] fileName Nome del file da scrivere.
     * @post
     * Il registro viene salvato sul file specificato.
     */
    public void exportOnFile(String fileName) {
    }

    /**
     * @brief Importa un registro da file.
     * @param[in] fileName Nome del file da cui importare.
     * @return Registro importato o null se il file non è valido.
     */
    public static UserRegistry importFromFile(String fileName) {
        return null;
    }
}
