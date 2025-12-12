package it.campuslib.domain.users;

import java.io.Serializable;

/**
 * @brief Rappresenta una Persona.
 * Superclasse per Author e User
 * @see Author
 * @see User
 */
public abstract class Person implements Serializable {
    private String surname;
    private String name;
    
    /**
    * @brief Costruttore.
    * Istanzia un nuovo oggetto Person.
    * @param[in] surname Cognome della persona.
    * @param[in] name Nome della persona.
    */
    public Person(String surname, String name) {
        
        this.surname = surname;
        this.name = name;
    }
    
    /**
     * @brief Restituisce il nome della persona.
     * @return name Nome persona.
     */
    public String getName() {
    
        return name;
    }
    
    /**
     * @brief Restituisce il cognome della persona.
     * @return surname Cognome persona.
     */
    public String getSurname() {
    
        return surname;
    }
    
    /**
     * @brief Imposta il nome della persona.
     * @param[in] name Nome persona.
     */
    public void setName(String name) {
        
        this.name = name;
    }
    
    /**
     * @brief Imposta il cognome della persona.
     * @param[in] surname Cognome persona.
     */
    public void setSurname(String surname) {
        
        this.surname = surname;
    }
    
    /**
     * @brief Restituisce una rappresentazione testuale dell'oggetto Person.
     * @return Stringa che contiene le informazioni sull'oggetto Person.
     */
    @Override
    public String toString() {
    
        return "Nome: " + name + " Cognome: " + surname;
    }
    
    
}

