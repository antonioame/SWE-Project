package it.campuslib.collections;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.campuslib.domain.transactions.Giveback;
import it.campuslib.domain.users.User;

/**
 * @brief Il registro delle restituzioni della biblioteca.
 */
public class GivebackRegistry implements Serializable {
    private List<Giveback> registry;

    /**
     * @brief Costruttore.
     * @post
     * Il registro restituzioni è vuoto.
     */
    public GivebackRegistry() {
        this.registry = new ArrayList<>();
    }

    /**
     * @brief Aggiunge una restituzione al registro.
     * @param[in] giveback Restituzione da aggiungere.
     * @return Esito aggiunta.
     */
    public boolean addGiveback(Giveback giveback) {
        if (giveback == null) {
            return false;
        }
        return registry.add(giveback);
    }

    /**
     * @brief Restituisce il numero di restituzioni nel registro.
     * @return Numero di restituzioni presenti nel registro.
     */
    public int getSize() {
        return registry.size();
    }

    /**
     * @brief Cerca restituzioni per utente.
     * @param[in] user Utente per cui cercare le restituzioni.
     * @return Lista di restituzioni dell'utente (vuota se nessun restituzione corrisponde al criterio di ricerca).
     */
    public LinkedList<Giveback> searchByUser(User user) {
        LinkedList<Giveback> result = new LinkedList<>();
        if (user == null) {
            return result;
        }
        
        for (Giveback giveback : registry) {
            if (giveback.getBorrowerUser() != null &&
                giveback.getBorrowerUser().equals(user)) {
                result.add(giveback);
            }
        }
        return result;
    }

    /**
     * @brief Cerca restituzioni per libro.
     * @param[in] isbn ISBN del libro.
     * @return Lista di restituzioni del libro (vuota se nessun restituzione corrisponde al criterio di ricerca).
     */
    public LinkedList<Giveback> searchByBook(String isbn) {
        LinkedList<Giveback> result = new LinkedList<>();
        if (isbn == null) {
            return result;
        }
        
        for (Giveback giveback : registry) {
            if (giveback.getBorrowedBook() != null && 
                giveback.getBorrowedBook().getIsbn() != null &&
                giveback.getBorrowedBook().getIsbn().equals(isbn)) {
                result.add(giveback);
            }
        }
        return result;
    }

    /**
     * @brief Cerca restituzioni per data.
     * @param[in] date Data da cercare.
     * @return Lista di restituzioni della data specificata (vuota se nessun restituzione corrisponde al criterio di ricerca o se la data inserita non è valida).
     */
    public LinkedList<Giveback> searchByDate(String date) {
        LinkedList<Giveback> result = new LinkedList<>();
        if (date == null) {
            return result;
        }
        
        try {
            // Converte la stringa della data in un oggetto LocalDate utilizzando il formato ISO
            LocalDate searchDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            for (Giveback giveback : registry) {
                if (giveback.getEndDate() != null && 
                    giveback.getEndDate().equals(searchDate)) {
                    result.add(giveback);
                }
            }
        } catch (DateTimeParseException e) {
            // Se la data non è valida, restituisce lista vuota
            return result;
        }
        return result;
    }

    /**
     * @brief Restituisce una rappresentazione testuale del registro.
     * @return Rappresentazione testuale del registro.
     */
    public String toString() {
        if (registry.isEmpty()) {
            return "Registro restituzioni vuoto";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Registro Restituzioni:\n");
        sb.append("=======================\n");
        
        for (Giveback giveback : registry) {
            sb.append(giveback.toString()).append("\n");
        }
        
        return sb.toString();
    }

    /**
     * @brief Esporta il registro su file.
     * @param[in] fileName Nome del file da scrivere.
     * @post
     * Il registro viene salvato sul file specificato.
     * Termina senza effettuare operazioni se la stringa non è passata correttamente.
     * Termina stampando messaggio di errore se l'esportazione non è riuscita.
     */
    public void exportOnFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return;
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(registry);
        } catch (IOException e) {
            // In caso di errore, il metodo termina senza sollevare eccezioni
            System.err.println("ERR. Esportazione Non Riuscita: " + e.getMessage());
        }
    }

    /**
     * @brief Importa un registro da file.
     * @param[in] fileName Nome del file da cui importare.
     * @return Registro importato o null se il file non è valido.
     */
    public static GivebackRegistry importFromFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            // Notazione per sopprimere l'avviso del compilatore per il cast non controllato dell'oggetto da Object Input Stream a List<Giveback>
            @SuppressWarnings("unchecked")
            
            List<Giveback> loadedRegistry = (List<Giveback>) ois.readObject();
            GivebackRegistry registry = new GivebackRegistry();
            registry.registry = loadedRegistry;
            return registry;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
