package campuslib.collections;

import campuslib.models.Giveback;
import campuslib.models.Loan;
import campuslib.models.User;
import java.util.TreeSet;
import java.util.LinkedList;

/**
 * @brief Il registro dei prestiti attivi della biblioteca.
 */
public class LoanRegistry {
    private TreeSet<Loan> registry;

    /**
     * @brief Costruttore.
     * @post
     * Il registro prestiti è vuoto.
     */
    public LoanRegistry() {
    }

    /**
     * @brief Aggiunge un prestito al registro.
     * @param[in] loan Prestito da aggiungere.
     * @return Esito aggiunta.
     * @post
     * Il prestito è aggiunto al registro.
     */
    public boolean addLoan(Loan loan) {
        return false;
    }

    /**
     * @brief Rimuove un prestito dal registro.
     * @param[in] loan Prestito da rimuovere.
     * @return Esito rimozione.
     * @post
     * Se presente, il prestito viene rimosso dal registro, altrimenti restituisce false.
     */
    public boolean removeLoan(Loan loan) {
        return false;
    }

    /**
     * @brief Converte un prestito in una restituzione.
     * Se presente, rimuove il prestito dal registro e crea l'oggetto Giveback corrispondente.
     * @param[in] loan Prestito da convertire in restituzione.
     * @return Nuova istanza per Giveback o null se il prestito non è presente nel registro.
     * @see removeLoan
     */
    public Giveback convertToGiveback(Loan loan) {
        return null;
    }

    /**
     * @brief Cerca prestiti per utente.
     * @param[in] user Utente per cui cercare i prestiti.
     * @return Lista di prestiti dell'utente (vuota se nessun prestito corrisponde al criterio di ricerca).
     */
    public LinkedList<Loan> searchByUser(User user) {
        return null;
    }

    /**
     * @brief Cerca prestiti per libro.
     * @param[in] isbn ISBN del libro.
     * @return Lista di prestiti del libro (vuota se nessun prestito corrisponde al criterio di ricerca).
     */
    public LinkedList<Loan> searchByBook(String isbn) {
        return null;
    }

    /**
     * @brief Cerca prestiti per data.
     * @param[in] date Data da cercare.
     * @return Lista di prestiti della data specificata (vuota se nessun prestito corrisponde al criterio di ricerca).
     */
    public LinkedList<Loan> searchByDate(String date) {
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
    public static LoanRegistry importFromFile(String fileName) {
        return null;
    }
}
