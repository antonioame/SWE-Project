package campuslib.models;

import campuslib.enums.UserStatus;
import java.util.LinkedList;

public class User extends Person implements Comparable<User>{
    private final int enrollmentID;
    private String email;
    private UserStatus status;
    private int maxLoans;
    
    /**
     * @brief Costruttore dell'utente (studente).
     * Istanzia un nuovo oggetto User, utilizzando il costruttore della superclasse Person.
     * @param[in] name 
     * @param[in] surname 
     * @param[in] enrollmentID Matricola dell'utente.
     * @param[in] email E-mail dell'utente. 
     * @see Person.
     */
    public User(String name, String surname, int enrollmentID, String email) {}
    
    /**
     * @brief Restituisce la matricola dell'utente.
     * @return enrollmentID Matricola utente.
     */
    public int getEnrollmentID() {
    
        return null;
    }
    
    /**
     * @brief Restituisce l'e-mail dell'utente.
     * @return email E-mail utente.
     */
    public String getEmail() {
    
        return null;
    }
    
    /**
     * @brief Restituisce lo stato dell'utente.
     * @return status Stato utente.
     */
    public UserStatus getStatus() {
    
        return null;
    }
    
    /**
     * @brief Restituisce il numero massimo di prestiti
     * che può effettuare l'utente.
     * 
     * @return maxLoans Numero massimo di prestiti utente. 
     */
    public int getMaxLoans() {
    
        
    }
    
    /**
     * @brief Imposta l'e-mail dell'utente.
     * @param[in] email E-mail utente. 
     */
    public void setEmail(String email) {}
    
    /**
     * @brief Imposta lo stato dell'utente.
     * @param[in] status Stato utente.
     */
    public void setStatus(UserStatus status) {}
    
    /**
     * @brief Indica se l'utente è attivo o meno.
     * @return Valore booleano che indica lo stato dell'utente.
     */
    public boolean isActive() {
    
        return false;
    }
    
    /**
     * @brief Ottiene una collezione contenente i prestiti attivi dell'utente.
     * @return Lista contenente tutti i presti associati all'utente.
     */
    private LinkedList<Loan> getActiveLoans() {
    
        return null;
    }
    
    /**
     * @brief Utilizza la lista dei suoi prestiti associati, e l'attributo maxLoans,
     * per determinare il numero di prestiti attualmente associati.
     * 
     * @return Numero di prestiti attualmente associati.
     * @post Il numero di prestiti associati non deve essere minore di zero né
     * maggiore del massimo numero di prestiti possibili.
     * @see getActiveLoans()
     */
    public int getAvailableLoanSlots() {}
    
    /**
     * @brief Indica se l'utente può effettuare nuovi prestiti o meno.
     * @return Valore booleano che indica se l'utente può effettuare nuovi prestiti.
     */
    public boolean canBorrow() {
    
        return false;
    }
    
    /**
     * @brief Controllo sul formato dell'e-mail dell'utente da effettuare nel costruttore
     * di questo. 
     * 
     * @param email E-mail dell'utente su cui controllare il formato.
     * @return Valore booleano che indica se l'e-mail è scritta in un formato corretto
     * o meno.
     * @see User(String name, String surname, int enrollmentID, String email)
     */
    private boolean checkEmail(String email) {
    
        return false;
    }
    
    /**
     * @brief Controllo sul formato della matricola dell'utente da effettuare nel costruttore
     * di questo.
     * 
     * @param enrollmentID Matricola dell'utente su cui controllare il formato.
     * @return Valore booleano che indica se la matricola è scritta in un formato corretto
     * o meno.
     * @see User(String name, String surname, int enrollmentID, String email)
     */
    private boolean checkEnrollmentID(int enrollmentID) {
    
        return false;
    }
    
    /**
     * @brief Restituisce una rappresentazione testuale dell'oggetto User.
     * @return Stringa che contiene le informazioni sull'oggetto User.
     */
    @Override
    public String toString() {
    
        return null;
    }
    
    /**
     * @brief Confronta l'oggetto User corrente con un altro oggetto User per verificarne l'uguaglianza.
     * L'uguaglianza è verificata se entrambi gli oggetti sono di tipo User e hanno la stessa matricola.
     * @param[i] obj L'oggetto da confrontare con l'utente corrente.
     * @return Valore booleano che indica la relazione di uguaglianza tra i due oggetti.
     */
    @Override
    public boolean equals(Object obj) {
    
        return false;
    }
    
    /**
     * @brief Restituisce il valore di hash dell'oggetto corrente.
     * 
     * Il valore restituito è coerente con il metodo equals, di conseguenza, se due oggetti User
     * sono ritenuti uguali, allora il loro codice di hash sarà lo stesso.
     * 
     * @return Valore intero che rappresenta il codice di hash.
     * @see equals(Object obj)
     */
    @Override
    public int hashCode() {}
    
    /**
     * @brief Confronta l'oggetto User corrente con un altro oggetto User ai fini dell'ordinamento.
     * L'ordinamento è basato sulla matricola dell'utente.
     * 
     * @param other Altro oggetto di tipo User su cui viene effettuato il confronto.
     * @return Valore intero che può essere zero, negativo o positivo se l'utente corrente è rispettivamente
     * uguale, minore o maggiore dell'altro utente, secondo l'ordinamento.
     */   
    @Override
    public int compareTo(User other) {}
    
    
    
    
    
    
}