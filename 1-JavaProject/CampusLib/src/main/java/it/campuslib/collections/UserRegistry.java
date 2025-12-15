package it.campuslib.collections;

import it.campuslib.domain.users.User;
import it.campuslib.domain.users.UserStatus;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;

/**
 * @brief Il registro degli utenti della biblioteca.
 */
public class UserRegistry implements Serializable {
    private Map<String, User> registry;
    private transient ObservableList<User> observableUsers;

    /**
     * @brief Costruttore.
     * @post
     * Il registro utenti è vuoto.
     */
    public UserRegistry() {
        this.registry = new HashMap<>();
        this.observableUsers = FXCollections.observableArrayList();
    }

    /**
     * @brief Aggiunge un utente al registro.
     * L'utente è aggiunto se l'ID di iscrizione è valido.
     * @param[in] user Utente da aggiungere.
     * @return Esito aggiunta.
     */
    public boolean addUser(User user) {
        if (user == null)
            return false;
        String id = user.getEnrollmentID();

        if (id == null)
            return false;
        if (registry.containsKey(id))
            return false;
        
        registry.put(id, user);
        if (observableUsers == null)
            observableUsers = FXCollections.observableArrayList(registry.values());
        observableUsers.add(user);
        return true;
    }

    /**
     * @brief Rimuove un utente dal registro.
     * @param[in] enrollmentID ID di iscrizione dell'utente da rimuovere.
     * @return Esito rimozione.
     * @post
     * Se presente, l'utente viene rimosso dal registro, altrimenti restituisce false.
     */
    public boolean removeUser(String enrollmentID) {
        User u = registry.get(enrollmentID);

        if (u == null) {
            return false;
        } else {
            u.setStatus(UserStatus.INACTIVE);
            return true;
        }
    }

    /**
     * @brief Cerca utenti nel registro per nome e cognome.
     * @param[in] name Nome da cercare.
     * @param[in] surname Cognome da cercare.
     * @return Lista di utenti trovati (vuota se nessun utente corrisponde al criterio di ricerca).
     */
    public LinkedList<User> searchByName(String name, String surname) {
        LinkedList<User> list = new LinkedList<>();
        if (name == null || surname == null)
            return list;

        for (User u : registry.values()) {
            if (u.getName() != null && u.getSurname() != null &&
                u.getName().equals(name) &&
                u.getSurname().equals(surname)) {
                list.add(u);
            }
        }
        return list;
    }

    /**
     * @brief Cerca un utente nel registro per ID di iscrizione.
     * @param[in] enrollmentID ID di iscrizione da cercare.
     * @return Utente trovato o null se non presente.
     */
    public User searchByEnrollmentID(String enrollmentID) {
        User user = null;
        if (enrollmentID == null)
            return null;
        if (registry.containsKey(enrollmentID)) user = registry.get(enrollmentID);
        return user;
    }

    /**
     * @brief Cerca utenti secondo una query generica.
     * @param[in] query Stringa di ricerca (cerca in nome, cognome, email, ID).
     * @return Lista di utenti che corrispondono alla query (vuota se nessun utente corrisponde al criterio di ricerca).
     */
    public LinkedList<User> search(String query) {
        LinkedList<User> lista = new LinkedList<>();

        if (query == null || query.isEmpty())
            return lista;

        // Controlla se la query contiene la matricola:
        User user = registry.get(query);
        if (user != null) {
            lista.add(user);
            return lista;
        }

        // Controlla nei campi dei valori:
        String q = query.toLowerCase();
        for (User u : registry.values()) {
            if (u.getName().equalsIgnoreCase(q) ||
                u.getSurname().equalsIgnoreCase(q) ||
                u.getEmail().equalsIgnoreCase(q)) {
                lista.add(u);
            }
        }
        return lista;
    }

    /**
     * @brief Restituisce una rappresentazione testuale del registro.
     * @return Rappresentazione testuale del registro.
     */
    public String toString() {
        if (registry.isEmpty())
            return "Registro utenti è vuoto";

        StringBuilder sb = new StringBuilder();

        sb.append(" * === Stampa Registro Utenti === * \n");

        for (User user : registry.values()) {
            sb.append(user.toString()).append("\n");
        }

        return sb.toString();
    }

    /**
     * @brief Esporta il registro su file.
     * @param[in] fileName Nome del file da scrivere.
     * @post
     * Il registro viene salvato sul file specificato.
     */
    public void exportOnFile(String fileName) {
        if (fileName == null != fileName.isEmpty()) {
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            oos.writeObject(registry);
        } catch (IOException ex) {
            System.err.println("ERR. Esportazione Non Riuscita: " + ex.getMessage());
        }
    }

    /**
     * @brief Importa un registro da file.
     * @param[in] fileName Nome del file da cui importare.
     * @return Registro importato o null se il file non è valido.
     */
    public static UserRegistry importFromFile(String fileName) {
        if (fileName == null || fileName.isEmpty())
            return null;

        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            // Notazione per sopprimere l'avviso del compilatore per il cast non controllato dell'oggetto
            @SuppressWarnings("unchecked")
            HashMap<String, User> registry = (HashMap<String, User>) ois.readObject();
            UserRegistry userRegistry = new UserRegistry();

            userRegistry.registry = registry;
            userRegistry.observableUsers = FXCollections.observableArrayList(registry.values());

            instance = userRegistry;
            return userRegistry;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    private static UserRegistry instance = null;

    /**
     * Restituisce tutti gli utenti presenti nel registro.
     * @return Lista di utenti (non modificabile direttamente il registro interno)
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(this.registry.values());
    }

    /**
     * Restituisce una ObservableList osservabile degli utenti (aggiornabile automaticamente).
     * @return ObservableList degli utenti
     */
    public ObservableList<User> getAllUsersObservable() {
        if (observableUsers == null) {
            observableUsers = FXCollections.observableArrayList(registry.values());
        }
        return observableUsers;
    }

    public static UserRegistry getInstance() {
        if (instance == null) {
            instance = importFromFile("personal-files/io-binary-files/users.dat");
            if (instance == null) {
                instance = new UserRegistry();
            }
        }
        return instance;
    }
}
