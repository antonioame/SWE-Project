package it.campuslib.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import it.campuslib.domain.users.InvalidUserInfoException;
import it.campuslib.domain.users.User;
import it.campuslib.domain.users.UserStatus;
import java.io.File;
import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;


public class UserRegistryTest {
    private UserRegistry registry;
    private User user1;
    private User user2;
    private User user3;
    private final String TEST_FILE = "TestUserRegistry.ser";
    
    private final String ID1 = "1111111111";
    private final String ID2 = "2222222222";
    private final String ID3 = "3333333333";
    
    @BeforeEach
    public void setUp() throws InvalidUserInfoException {
        registry = new UserRegistry();
        
        // Creazione e aggiunta al registro utenti di test
        user1 = new User("Mario", "Rossi", ID1, "mario.rossi@studenti.unisa.it");
        user1.setStatus(UserStatus.ACTIVE);
        
        user2 = new User("Luisa", "Bianchi", ID2, "luisa.bianchi@unisa.it");
        user2.setStatus(UserStatus.ACTIVE);
        
        user3 = new User("Mario", "Rossi", ID3, "m.rossi@unisa.it");
        user3.setStatus(UserStatus.INACTIVE);
        
        registry.addUser(user1);
        registry.addUser(user2);
        registry.addUser(user3);
    }
    
    @Test
    public void testConstructor() {
        UserRegistry emptyRegistry = new UserRegistry();
        //Il registro dovrebbe essere vuoto
        assertEquals("Registro utenti è vuoto", emptyRegistry.toString());
    }

    @Test
    public void testAddUser() throws InvalidUserInfoException {
        User user4 = new User("Anna", "Verdi", "4444444444", "anna.verdi@studenti.unisa.it");
        
        assertTrue(registry.addUser(user4));
        
        // Verifica che l'utente sia stato aggiunto
        assertNotNull(registry.searchByEnrollmentID("4444444444"));

        // Tentativo di aggiungere lo stesso utente nuovamente
        assertFalse(registry.addUser(user4));

        // Aggiunta di utente nullo
        assertFalse(registry.addUser(null));
    }

    @Test
    public void testRemoveUser() {
      
        registry.removeUser(user1.getEnrollmentID());
        registry.removeUser(user2.getEnrollmentID());
        registry.removeUser(user3.getEnrollmentID());
        
        assertEquals(UserStatus.INACTIVE, user1.getStatus());
        assertEquals(UserStatus.INACTIVE, user2.getStatus());
        assertEquals(UserStatus.INACTIVE, user3.getStatus());
    }

    @Test
    public void testSearchByName() {
        // Dovrebbe restituire una lista di 2 utenti
        LinkedList<User> result1 = registry.searchByName("Mario", "Rossi");
        assertEquals(2, result1.size()); 
        
        LinkedList<User> result2 = registry.searchByName("Luisa", "Bianchi");
        assertEquals(1, result2.size()); //Dovrebbe trovare un solo utente
        assertEquals(user2, result2.getFirst()); //ossia Luisa Bianchi
        
        // 3. Cerca utenti non esistenti
        LinkedList<User> result3 = registry.searchByName("Publio", "Ovidio");
        assertTrue(result3.isEmpty()); //non dovrebbe trovare utenti
        
        // 4. Cerca con parametri nulli
        LinkedList<User> result4 = registry.searchByName(null, "Rossi");
        assertTrue(result4.isEmpty()); // Non dovrebbe cercare con nome nullo
        LinkedList<User> result5 = registry.searchByName("Mario", null);
        assertTrue(result5.isEmpty()); // Non dovrebbe cercare con cognome nullo
    }

    @Test
    public void testSearchByEnrollmentID() {
        // 1. Cerca ID esistente; dovrebbe trovare l'utente user3, con ID 3333333333.
        User foundUser1 = registry.searchByEnrollmentID(ID3);
        assertNotNull(foundUser1); 
        assertEquals(user3, foundUser1);

        // 2. Cerca ID non esistente; non dovrebbe trovare utenti
        User foundUser2 = registry.searchByEnrollmentID("0000000000");
        assertNull(foundUser2); 

        // 3. Non dovrebbe cercare con ID nullo
        User foundUser3 = registry.searchByEnrollmentID(null);
        assertNull(foundUser3);
    }

    @Test
    public void testSearch() {
        // 1. Ricerca per ID di iscrizione esatta; dovrebbe trovare un utente: user1.
        LinkedList<User> resultID = registry.search(ID1);
        assertEquals(1, resultID.size());
        assertEquals(user1, resultID.getFirst());

        // 2. Ricerca per Nome (case-insensitive)
        LinkedList<User> resultName = registry.search("mArIo");
        assertEquals(2, resultName.size()); // La ricerca per nome 'mArIo' dovrebbe trovare 2 utenti
        
        // 3. Ricerca per Cognome (case-insensitive)
        LinkedList<User> resultSurname = registry.search("BIANCHI");
        assertEquals(1, resultSurname.size()); // La ricerca per cognome 'BIANCHI' dovrebbe trovare 1 utente
        
        // 4. Ricerca per Email (case-insensitive)
        LinkedList<User> resultEmail = registry.search("luisa.bianchi@unisa.it");
        assertEquals(1, resultEmail.size()); // La ricerca per email dovrebbe trovare 1 utente
        
        // 5. Ricerca per query non esistente
        LinkedList<User> resultNonExistent = registry.search("inesistente");
        assertTrue(resultNonExistent.isEmpty()); // Non dovrebbe trovare nessun utente per query inesistente
        
        // 6. Ricerca per query nulla o vuota
        LinkedList<User> resultNull = registry.search(null);
        assertTrue(resultNull.isEmpty()); // Non dovrebbe cercare con query nulla
        LinkedList<User> resultEmpty = registry.search("");
        assertTrue(resultEmpty.isEmpty()); // Non dovrebbe cercare con query vuota
    }

    @Test
    public void testToString() {
        String resultReg = registry.toString();
        assertTrue(resultReg.contains("=== Stampa Registro Utenti ===")); // Dovrebbe contenere l'intestazione
        assertTrue(resultReg.contains(ID1)); // Dovrebbe contenere l'ID1
        assertTrue(resultReg.contains(ID2)); // Dovrebbe contenere l'ID2
        
        //Registro vuoto
        UserRegistry emptyRegistry = new UserRegistry();
        String resultEmpty = emptyRegistry.toString();
        assertEquals("Registro utenti è vuoto", resultEmpty); // Dovrebbe indicare che il registro è vuoto
    }


    @Test
    public void testExportOnFile() throws Exception {
        registry.addUser(user1);
        registry.addUser(user2);

        String fileName = "userRegistryExportTest.ser";

        // Esporta il registro
        registry.exportOnFile(fileName);

        File file = new File(fileName);
        assertTrue(file.exists(), "Il file dovrebbe essere stato creato");

        // Pulizia
        file.delete();
       }
    
        @Test
        public void testImportFromFile() throws Exception {
        registry.addUser(user1);
        registry.addUser(user2);

        String fileName = "userRegistryImportTest.ser";

        // Esporta prima il registro per avere un file valido
        registry.exportOnFile(fileName);

        // Importa il registro
        UserRegistry importedRegistry = UserRegistry.importFromFile(fileName);

        assertNotNull(importedRegistry, "L'importazione non dovrebbe restituire null");
        assertEquals(user1, importedRegistry.searchByEnrollmentID(user1.getEnrollmentID()));
        assertEquals(user2, importedRegistry.searchByEnrollmentID(user2.getEnrollmentID()));

        // Pulizia
        new File(fileName).delete();
        }

}

