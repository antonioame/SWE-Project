package it.campuslib.domain.transactions;

import java.time.LocalDate;

import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.users.User;

/**
 * @brief Rappresenta un prestito di un libro.
 * Estende Transaction aggiungendo la data prevista di restituzione.
 * @see Transaction
 */
public class Loan extends Transaction {
    private LocalDate expectedReturnDate;
    
    /**
     * @brief Istanzia un nuovo oggetto Loan.
     * Il metodo istanzia un nuovo oggetto Loan, utilizzando il costruttore della superclasse Transaction.
     * @param[in] expectedReturnDate La data di restituzione prevista del prestito.
     * @param[in] startDate La data di inizio del prestito.
     * @param[in] borrowedBook Il libro preso in prestito.
     * @param[in] borrowerUser L'utente che ha preso in prestito il libro.
     * @see Transaction
     */
    public Loan(Book borrowedBook, User borrowerUser, LocalDate startDate, LocalDate expectedReturnDate) throws InvalidLoanInfoException{
        super(borrowedBook, borrowerUser, startDate);
        
        if(!checkDate(startDate, expectedReturnDate)) {
        
            throw new InvalidLoanInfoException("La data di inizio del prestito deve essere precedente alla data prevista di restituzione");
        }
        this.expectedReturnDate = expectedReturnDate;
    }

    /**
     * @brief Istanzia un nuovo oggetto Loan preservando l'ID del prestito originale.
     * Utilizzato per test o conversioni mantenendo lo stesso ID.
     * @param[in] id L'ID del prestito originale da preservare.
     * @param[in] borrowedBook Il libro preso in prestito.
     * @param[in] borrowerUser L'utente che ha preso in prestito il libro.
     * @param[in] startDate La data di inizio del prestito.
     * @param[in] expectedReturnDate La data di restituzione prevista del prestito.
     * @see Transaction
     */
    protected Loan(int id, Book borrowedBook, User borrowerUser, LocalDate startDate, LocalDate expectedReturnDate) throws InvalidLoanInfoException{
        super(id, borrowedBook, borrowerUser, startDate);
        
        if(!checkDate(startDate, expectedReturnDate)) {
        
            throw new InvalidLoanInfoException("La data di inizio del prestito deve essere precedente alla data prevista di restituzione");
        }
        this.expectedReturnDate = expectedReturnDate;
    }

    /**
     * @brief Restituisce la data di restituzione prevista del prestito.
     * @return La data di restituzione prevista del prestito.
     */
    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    /**
     * @brief Restituisce la data utilizzata per l'ordinamento delle transazioni.
     * Per Loan, è la data di restituzione prevista.
     * @return La data di ordinamento.
     */
    @Override
    public LocalDate getSortingDate() {
        return expectedReturnDate;
    }

    /**
     * @brief Imposta la data di restituzione prevista del prestito.
     * @param[in] newDate La nuova data di restituzione prevista del prestito.
     */
    public void setExpectedReturnDate(LocalDate expectedReturnDate) throws InvalidLoanInfoException{
       
        if(!checkDate(super.getStartDate(), expectedReturnDate)) {
        
            throw new InvalidLoanInfoException("La data di inizio del prestito deve essere precedente alla data prevista di restituzione");
        }
        this.expectedReturnDate = expectedReturnDate;
    }

    /**
     * @brief Restituisce una rappresentazione testuale dell'oggetto Loan.
     * @return Una stringa contenente le informazioni del prestito.
     */
    // FIXME: Implementare toString
    public String toString() {
        return null;
    }

    /**
     * @brief Verifica se il prestito è in ritardo.
     * La funzione confronta la data di restituzione prevista con la data corrente
     * per determinare se il prestito è in ritardo.
     * @return true se il prestito è in ritardo, false altrimenti.
     * @pre 
     * La data di inizio del prestito deve essere antecedente o uguale alla data corrente.
     * @see getExpectedReturnDate()
     */
    public boolean isOverdue() {
        return this.expectedReturnDate.isAfter(LocalDate.now());
    }
    
    public static boolean checkDate(LocalDate startDate, LocalDate expectedReturnDate) {
    
        if(expectedReturnDate.isAfter(startDate)) return true;
        else return false;
    }
           
}