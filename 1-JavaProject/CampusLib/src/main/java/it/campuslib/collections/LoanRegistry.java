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

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import it.campuslib.domain.transactions.Giveback;
import it.campuslib.domain.transactions.Loan;
import it.campuslib.domain.users.User;

/**
 * @brief Il registro dei prestiti attivi della biblioteca.
 */
public class LoanRegistry implements Serializable {
    private Set<Loan> registry;

    /**
     * @brief Costruttore.
     * @post
     * Il registro prestiti è vuoto.
     */
    public LoanRegistry() {
        this.registry = new TreeSet<>();
    }

    /**
     * @brief Aggiunge un prestito al registro.
     * @param[in] loan Prestito da aggiungere.
     * @return Esito aggiunta.
     */
    public boolean addLoan(Loan loan) {
        if (loan == null) {
            return false;
        }
        return registry.add(loan);
    }

    /**
     * @brief Rimuove un prestito dal registro.
     * @param[in] loan Prestito da rimuovere.
     * @return Esito rimozione.
     * @post
     * Se presente, il prestito viene rimosso dal registro, altrimenti restituisce false.
     */
    public boolean removeLoan(Loan loan) {
        if (loan == null) {
            return false;
        }
        return registry.remove(loan);
    }

    /**
     * @brief Restituisce il numero di prestiti nel registro.
     * @return Numero di prestiti presenti.
     */
    public int getSize() {
        return registry.size();
    }

    /**
     * @brief Estrae un prestito dal registro e lo restituisce come restituzione.
     * Se presente, rimuove il prestito dal registro e crea l'oggetto Giveback corrispondente.
     * @param[in] loan Prestito da estrarre e convertire in restituzione.
     * @return Nuova istanza per Giveback o null se il prestito non è presente nel registro.
     * @see removeLoan
     */
    public Giveback pullAsGiveback(Loan loan) {
        if (loan == null || !registry.contains(loan)) {
            return null;
        }
        registry.remove(loan);

        // Istanziare e restituire Giveback preservando l'ID del prestito originale
        return new Giveback(
            loan.getId(),
            loan.getBorrowedBook(),
            loan.getBorrowerUser(),
            loan.getStartDate(),
            LocalDate.now()
        );
    }

    /**
     * @brief Cerca prestiti per utente.
     * @param[in] user Utente per cui cercare i prestiti.
     * @return Lista di prestiti dell'utente (vuota se nessun prestito corrisponde al criterio di ricerca).
     */
    public LinkedList<Loan> searchByUser(User user) {
        LinkedList<Loan> result = new LinkedList<>();
        if (user == null) {
            return result;
        }
        
        for (Loan loan : registry) {
            if (loan.getBorrowerUser() != null &&
                loan.getBorrowerUser().equals(user)) {
                result.add(loan);
            }
        }
        return result;
    }

    /**
     * @brief Cerca prestiti per libro.
     * @param[in] isbn ISBN del libro.
     * @return Lista di prestiti del libro (vuota se nessun prestito corrisponde al criterio di ricerca).
     */
    public LinkedList<Loan> searchByBook(String isbn) {
        LinkedList<Loan> result = new LinkedList<>();
        if (isbn == null) {
            return result;
        }
        
        for (Loan loan : registry) {
            if (loan.getBorrowedBook() != null && 
                loan.getBorrowedBook().getIsbn() != null &&
                loan.getBorrowedBook().getIsbn().equals(isbn)) {
                result.add(loan);
            }
        }
        return result;
    }

    /**
     * @brief Cerca prestiti per data.
     * @param[in] date Data da cercare.
     * @return Lista di prestiti della data specificata.
     * Restituisce lista vuota se nessun prestito corrisponde al criterio di ricerca o se la data inserita non è valida.
     */
    public LinkedList<Loan> searchByDate(String date) {
        LinkedList<Loan> result = new LinkedList<>();
        if (date == null) {
            return result;
        }
        
        try {
            // Converte la stringa della data in un oggetto LocalDate utilizzando il formato ISO
            LocalDate searchDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

            for (Loan loan : registry) {
                if (loan.getStartDate() != null && 
                    loan.getStartDate().equals(searchDate)) {
                    result.add(loan);
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
            return "Registro prestiti è vuoto.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(" * === Stampa Registro Prestiti === * \n");

        for (Loan loan : registry) {
            sb.append(loan.toString()).append("\n");
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
    public static LoanRegistry importFromFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            // Notazione per sopprimere l'avviso del compilatore per il cast non controllato dell'oggetto da Object Input Stream a Set<Loan>
            @SuppressWarnings("unchecked")
            
            Set<Loan> loadedRegistry = (Set<Loan>) ois.readObject();
            LoanRegistry registry = new LoanRegistry();
            registry.registry = loadedRegistry;
            return registry;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
