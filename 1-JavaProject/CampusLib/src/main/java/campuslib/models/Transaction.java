package campuslib.models;

import java.time.LocalDate;

/**
 * @brief Classe astratta che rappresenta una transazione nella biblioteca.
 * Superclasse per Loan e Giveback.
 * @see Loan
 * @see Giveback
 */
public abstract class Transaction {
    private static int nextId = 1;
    private final int id;
    private final Book borrowedBook;
    private final User borrowerUser;
    private final LocalDate startDate;

    /**
     * @brief Istanzia un nuovo oggetto Transaction.
     * @param[in] borrowedBook Il libro preso in prestito.
     * @param[in] borrowerUser L'utente che ha preso in prestito il libro.
     * @param[in] startDate La data di inizio del prestito.
     * @return Un nuovo oggetto Transaction.
     * @pre
     * - il libro deve trovarsi nello stato "ADOPTED".
     * - l'utente deve essere attivo.
     */
    public Transaction(Book borrowedBook, User borrowerUser, LocalDate startDate) {
   
    
    }

    /**
     * @brief restituisce l'ID univoco del prestito.
     * @return L'ID univoco del prestito.
     */
    public int getId() {
        
        return 0;
    }
    /**
     * @brief Restituisce il libro preso in prestito.
     * @return Il libro preso in prestito.
     */
    public Book getBorrowedBook() {

        return null;
    }
    /**
     * @brief Restituisce l'utente che ha preso in prestito il libro.
     * @return L'utente che ha preso in prestito il libro.
     */
    public User getBorrowerUser() {
        
        return null;
    }

    /**
     * @brief Restituisce la data di inizio del prestito.
     * @return La data di inizio del prestito.
     */
    public LocalDate getStartDate() {
        
        return null;
    }

}
