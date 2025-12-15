package it.campuslib.domain.users;

import java.util.LinkedList;

import it.campuslib.domain.transactions.Loan;
import it.campuslib.collections.LoanRegistry;
/**
 * @brief Rappresenta Utente, specializzando Person.
 * @see Person
 */
public class User extends Person implements Comparable<User> {
    private final String enrollmentID;
    private String email;
    private UserStatus status;
    private int maxLoans;
    
    /**
     * @brief Costruttore dell'utente (studente).
     * Istanzia un nuovo oggetto User, utilizzando il costruttore della superclasse Person.
     * @param[in] name Nome dell'utente.
     * @param[in] surname Cognome dell'utente.
     * @param[in] enrollmentID Matricola dell'utente.
     * @param[in] email E-mail dell'utente.
     * @see Person.
     */
    public User(String name, String surname, String enrollmentID, String email) throws InvalidUserInfoException {
        super(surname, name);
        
        if(!checkEnrollmentID(enrollmentID)) {
        
            throw new InvalidUserInfoException("Formato della matricola non valido.");
        }
  
        if(!checkEmail(email)) {
        
            throw new InvalidUserInfoException("Formato dell'e-mail non valido.");
        }
        
        this.enrollmentID = enrollmentID;
        this.email = email;
        this.maxLoans = 3;
        this.status = UserStatus.ACTIVE;
    }
    
    /**
     * @brief Restituisce la matricola dell'utente.
     * @return La matricola dell'utente.
     */
    public String getEnrollmentID() {
        return enrollmentID;
    }
    
    /**
     * @brief Restituisce l'e-mail dell'utente.
     * @return L'e-mail dell'utente.
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @brief Restituisce lo stato dell'utente.
     * @return Lo stato dell'utente.
     */
    public UserStatus getStatus() {
        return status;
    }
    
    /**
     * @brief Restituisce il numero massimo di prestiti
     * che può effettuare l'utente.
     *
     * @return Il numero massimo di prestiti che può effettuare l'utente.
     */
    public int getMaxLoans() {
        return maxLoans;
    }
    
    /**
     * @brief Imposta l'e-mail dell'utente.
     * @param[in] email E-mail utente.
     */
    public void setEmail(String email) throws InvalidUserInfoException{
        
        if(!checkEmail(email)) {
        
            throw new InvalidUserInfoException();
        }
        this.email = email;
    }
    
    /**
     * @brief Imposta lo stato dell'utente.
     * @param[in] status Stato utente.
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    /**
     * @brief Imposta il numero massimo di prestiti dell'utente
     * @param[in] maxLoans Numero massimo prestiti utente.
     */
    public void setMaxLoans(int maxLoans) throws InvalidUserInfoException {
        if (maxLoans < 0 || maxLoans > 3) {
            throw new InvalidUserInfoException("Numero massimo di prestiti non valido.");
        }
        this.maxLoans = maxLoans;
    }
    
    /**
     * @brief Indica se l'utente è attivo o meno.
     * @return Valore booleano che indica lo stato dell'utente.
     */
    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }
    
    /**
     * @brief Ottiene una collezione contenente i prestiti attivi dell'utente.
     * @return Lista contenente tutti i presti associati all'utente.
     */
    public LinkedList<Loan> getActiveLoans(LoanRegistry registry) {
        
        LinkedList<Loan> associatedLoans = new LinkedList<>();
        
        if(registry == null) return associatedLoans;
        associatedLoans = registry.searchByUser(this);
        
        return associatedLoans;
        }
    
    /**
     * @brief Indica se l'utente può effettuare nuovi prestiti o meno.
     * @return Valore booleano che indica se l'utente può effettuare nuovi prestiti.
     */
    public boolean canBorrow(LoanRegistry registry) {
        
        LinkedList<Loan> associatedLoans = this.getActiveLoans(registry);
        
        if(associatedLoans == null) return maxLoans > 0;
        int numLoans = associatedLoans.size();
        
        return ((maxLoans - numLoans) > 0);
       }
    

    /**
     * @brief Controllo sul formato dell'e-mail dell'utente da effettuare nel costruttore di questo.
     * @param[in] email E-mail dell'utente su cui controllare il formato.
     * @return Valore booleano che indica se l'e-mail è scritta in un formato corretto o meno.
     * @see User(String name, String surname, int enrollmentID, String email)
     */
    private static boolean checkEmail(String email) {
        String s1 = "studenti.unisa.it";
        String s2 = "unisa.it";
        if (email.contains("@")) {
            String parts[] = email.split("@", 2);
            String dominio = parts[1];
            if (dominio.equals(s1) || dominio.equals(s2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    /**
     * @brief Controllo sul formato della matricola dell'utente da effettuare nel costruttore
     * di questo.
     * 
     * @param[in] enrollmentID Matricola dell'utente su cui controllare il formato.
     * @return Valore booleano che indica se la matricola è scritta in un formato corretto o meno.
     * @see User(String name, String surname, String enrollmentID, String email)
     */
    private static boolean checkEnrollmentID(String enrollmentID) {
        return enrollmentID != null && enrollmentID.length() == 10;
    }
    
    /**
     * @brief Restituisce una rappresentazione testuale dell'oggetto User.
     * @return Stringa che contiene le informazioni sull'oggetto User.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.toString());
        sb.append("\n");
        sb.append("Matricola: ").append(enrollmentID);
        sb.append("; E-mail: ").append(email);
        sb.append("; È attivo: ").append(isActive());
        sb.append("; Numero massimo di prestiti: ").append(maxLoans).append("\n");
        
        return sb.toString();
    }
    
    /**
     * @brief Confronta l'oggetto User corrente con un altro oggetto User per verificarne l'uguaglianza.
     * L'uguaglianza è verificata se entrambi gli oggetti sono di tipo User e hanno la stessa matricola.
     * @param[i] obj L'oggetto da confrontare con l'utente corrente.
     * @return Valore booleano che indica la relazione di uguaglianza tra i due oggetti.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        User u = (User) obj;
        return this.enrollmentID.equals(u.getEnrollmentID());
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
    public int hashCode() {
        return this.enrollmentID.hashCode();
    }
    
    /**
     * @brief Confronta l'oggetto User corrente con un altro oggetto User ai fini dell'ordinamento.
     * L'ordinamento è basato sulla matricola dell'utente.
     * 
     * @param other Altro oggetto di tipo User su cui viene effettuato il confronto.
     * @return Valore intero che può essere zero, negativo o positivo se l'utente corrente è rispettivamente
     * uguale, minore o maggiore dell'altro utente, secondo l'ordinamento.
     */   
    @Override
    public int compareTo(User other) {
        return this.enrollmentID.compareTo(other.getEnrollmentID());
    }
}