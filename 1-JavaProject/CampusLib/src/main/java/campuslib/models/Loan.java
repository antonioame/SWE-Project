package campuslib.models;

import java.time.LocalDate;

public class Loan extends Transaction {
    private LocalDate expectedReturnDate;
    
    /**
     * @brief Istanzia un nuovo oggetto Loan
     * Il metodo istanzia un nuovo oggetto Loan, utilizzando il costruttore della superclasse Transaction
     * @param[in] expectedReturnDate La data di restituzione prevista del prestito
     * @param[in] startDate La data di inizio del prestito
     * @param[in] Book Il libro preso in prestito
     * @param[in] User L'utente che ha preso in prestito il libro
     * @return Un nuovo oggetto Loan
     * @see Transaction
     */
    public Loan(Book borrowedBook, User borrowerUser, LocalDate startDate, LocalDate expectedReturnDate) {
        
    }


    /**
     * @brief Restituisce la data di restituzione prevista del prestito
     * @return La data di restituzione prevista del prestito
     */
    public LocalDate getExpectedReturnDate() {
        
        return null;
    }

    /**
     * @brief Imposta la data di restituzione prevista del prestito
     * @param[in] newDate La nuova data di restituzione prevista del prestito
     */
    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        
    }

    /**
     * @brief Restituisce una rappresentazione testuale dell'oggetto Loan
     * @return Una stringa contenente le informazioni del prestito
     */
    public String toString() {
        
        return null;
    }

    /**
     * @brief Confronta l'oggetto Loan corrente con un altro oggetto per verificarne l'uguaglianza
     * L'uguaglianza è verificata se entrambi gli oggetti sono di tipo Loan e hanno stessa data di restituzione prevista
     * @param[in] obj L'oggetto da confrontare con il prestito corrente
     */
    public boolean equals(Object obj) {
        
        return false;
    }

    /**
     * @brief Confronta l'oggetto Loan corrente con un altro oggetto Loan per l'ordinamento
     * L'ordinamento è basato sulla data di restituzione prevista
     * @param[in] other L'oggetto Loan da confrontare con il prestito corrente
     * @return Un valore negativo, zero o positivo se il prestito corrente è minore, uguale o maggiore dell'altro prestito
     */
    public int compareTo(Loan other) {
        
        return 0;
    }

    /**
     * @brief Verifica se il prestito è in ritardo
     * La funzione confronta la data di restituezione prevista con la data corrente
     * per determinare se il prestito è in ritardo.
     * @return true se il prestito è in ritardo, false altrimenti
     * @pre 
     * La data di inizio del prestito deve essere antecedente o uguale alla data corrente.
     * @see getExpectedReturnDate()
     */
    public boolean isOverdue() {
    
        return false;
    }
}