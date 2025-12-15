package it.campuslib.collections;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.catalog.InvalidBookInfoException;
import it.campuslib.domain.transactions.Giveback;
import it.campuslib.domain.users.User;
import it.campuslib.domain.users.InvalidUserInfoException;

/**
 * Classe di test per GivebackRegistry
 */
public class GivebackRegistryTest {
    private static final String TEST_FILES_DIR = "target/test-files/";
    private GivebackRegistry registry;
    private Book book1, book2;
    private User student, user;
    private Giveback giveback1, giveback2, giveback3;

    @BeforeEach
    void setUp() throws InvalidUserInfoException, InvalidBookInfoException {
        // Creazione directory per i file di test I/O, se inesistente:
        new File(TEST_FILES_DIR).mkdirs();

        registry = new GivebackRegistry();

        book1 = new Book("9780123456789", "Object Oriented Programming", "Antonio Rossi", 2020, 3);
        book2 = new Book("9780987654321", "Data Structures and Algorithms", "Antonio Rossi, Gianmarco Verdi", 2022, 2);

        student = new User("Luca", "Martini", "0612710777", "luca.martini@studenti.unisa.it");
        user = new User("Maria", "Ferrari", "0000054321", "mferrari@unisa.it");

        giveback1 = new Giveback(book1, student, LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 10));
        giveback2 = new Giveback(book2, student, LocalDate.of(2025, 12, 5), LocalDate.of(2025, 12, 15));
        giveback3 = new Giveback(book1, user, LocalDate.of(2025, 12, 10), LocalDate.of(2025, 12, 20));
    }

    @Test
    void testConstructor() {
        assertNotNull(registry, "Il registro non deve essere null");
        assertNotNull(registry.toString(), "toString non deve restituire null");
    }

    @Test
    void testAddGiveback() {
        assertTrue(registry.addGiveback(giveback1), "L'aggiunta della prima restituzione deve avere successo");
        assertTrue(registry.addGiveback(giveback2), "L'aggiunta della seconda restituzione deve avere successo");
        assertFalse(registry.addGiveback(null), "Non è possibile aggiungere una restituzione null");
    }

    @Test
    void testSearchByUser() throws InvalidUserInfoException {
        registry.addGiveback(giveback1);
        registry.addGiveback(giveback2);
        registry.addGiveback(giveback3);
        
        // Test ricerca per student (ha 2 restituzioni)
        LinkedList<Giveback> givebacksstudent = registry.searchByUser(student);
        assertNotNull(givebacksstudent, "La lista delle restituzioni non deve essere null");
        assertEquals(2, givebacksstudent.size(), "L'utente 1 deve avere 2 restituzioni");
        assertTrue(givebacksstudent.contains(giveback1), "Deve contenere giveback1");
        assertTrue(givebacksstudent.contains(giveback2), "Deve contenere giveback2");
        
        // Test ricerca per user (ha 1 restituzione)
        LinkedList<Giveback> givebacksuser = registry.searchByUser(user);
        assertNotNull(givebacksuser, "La lista delle restituzioni non deve essere null");
        assertEquals(1, givebacksuser.size(), "L'utente 2 deve avere 1 restituzione");
        assertTrue(givebacksuser.contains(giveback3), "Deve contenere giveback3");
        
        // Test ricerca utente senza restituzioni
        User user3 = new User("Paolo", "Gialli", "0000099999", "paolo.gialli@studenti.unisa.it");
        LinkedList<Giveback> givebacksUser3 = registry.searchByUser(user3);
        assertNotNull(givebacksUser3, "La lista deve essere non null anche se vuota");
        assertEquals(0, givebacksUser3.size(), "L'utente senza restituzioni deve avere lista vuota");
        
        // Test ricerca con utente null
        LinkedList<Giveback> givebacksNull = registry.searchByUser(null);
        assertNotNull(givebacksNull, "La ricerca con utente null deve restituire lista vuota");
        assertEquals(0, givebacksNull.size(), "La lista deve essere vuota");
    }

    @Test
    void testSearchByBook() {
        registry.addGiveback(giveback1);
        registry.addGiveback(giveback2);
        registry.addGiveback(giveback3);
        
        // Test ricerca per book1 (ha 2 restituzioni: giveback1 e giveback3)
        LinkedList<Giveback> givebacksBook1 = registry.searchByBook(book1.getIsbn());
        assertNotNull(givebacksBook1, "La lista delle restituzioni non deve essere null");
        assertEquals(2, givebacksBook1.size(), "Il libro 1 deve avere 2 restituzioni");
        assertTrue(givebacksBook1.contains(giveback1), "Deve contenere giveback1");
        assertTrue(givebacksBook1.contains(giveback3), "Deve contenere giveback3");
        
        // Test ricerca per book2 (ha 1 restituzione)
        LinkedList<Giveback> givebacksBook2 = registry.searchByBook(book2.getIsbn());
        assertNotNull(givebacksBook2, "La lista delle restituzioni non deve essere null");
        assertEquals(1, givebacksBook2.size(), "Il libro 2 deve avere 1 restituzione");
        assertTrue(givebacksBook2.contains(giveback2), "Deve contenere giveback2");
        
        // Test ricerca libro senza restituzioni
        LinkedList<Giveback> givebacksNonExistent = registry.searchByBook("9780111111111");
        assertNotNull(givebacksNonExistent, "La lista deve essere non null anche se vuota");
        assertEquals(0, givebacksNonExistent.size(), "Libro senza restituzioni deve avere lista vuota");
        
        // Test ricerca con ISBN null
        LinkedList<Giveback> givebacksNull = registry.searchByBook(null);
        assertNotNull(givebacksNull, "La ricerca con ISBN null deve restituire lista vuota");
        assertEquals(0, givebacksNull.size(), "La lista deve essere vuota");
    }

    @Test
    void testSearchByDate() {
        registry.addGiveback(giveback1);
        registry.addGiveback(giveback2);
        registry.addGiveback(giveback3);
        
        // Test ricerca per data 2025-12-10 (giveback1 - data di restituzione)
        LinkedList<Giveback> givebacksDate1 = registry.searchByDate("2025-12-10");
        assertNotNull(givebacksDate1, "La lista delle restituzioni non deve essere null");
        assertEquals(1, givebacksDate1.size(), "Deve esserci 1 restituzione per questa data");
        assertTrue(givebacksDate1.contains(giveback1), "Deve contenere giveback1");
        
        // Test ricerca per data 2025-12-15 (giveback2)
        LinkedList<Giveback> givebacksDate2 = registry.searchByDate("2025-12-15");
        assertNotNull(givebacksDate2, "La lista delle restituzioni non deve essere null");
        assertEquals(1, givebacksDate2.size(), "Deve esserci 1 restituzione per questa data");
        assertTrue(givebacksDate2.contains(giveback2), "Deve contenere giveback2");
        
        // Test ricerca per data 2025-12-20 (giveback3)
        LinkedList<Giveback> givebacksDate3 = registry.searchByDate("2025-12-20");
        assertNotNull(givebacksDate3, "La lista delle restituzioni non deve essere null");
        assertEquals(1, givebacksDate3.size(), "Deve esserci 1 restituzione per questa data");
        assertTrue(givebacksDate3.contains(giveback3), "Deve contenere giveback3");
        
        // Test ricerca per data senza restituzioni
        LinkedList<Giveback> givebacksDateEmpty = registry.searchByDate("2025-11-01");
        assertNotNull(givebacksDateEmpty, "La lista deve essere non null anche se vuota");
        assertEquals(0, givebacksDateEmpty.size(), "Data senza restituzioni deve avere lista vuota");
        
        // Test ricerca con data null
        LinkedList<Giveback> givebacksNull = registry.searchByDate(null);
        assertNotNull(givebacksNull, "La ricerca con data null deve restituire lista vuota");
        assertEquals(0, givebacksNull.size(), "La lista deve essere vuota");
        
        // Test ricerca con data in formato errato
        LinkedList<Giveback> givebacksInvalidDate = registry.searchByDate("invalid-date");
        assertNotNull(givebacksInvalidDate, "La ricerca con data invalida deve restituire lista vuota");
        assertEquals(0, givebacksInvalidDate.size(), "La lista deve essere vuota per data invalida");
    }

    @Test
    void testToString() {
        // Test registro vuoto
        String emptyString = registry.toString();
        assertNotNull(emptyString, "toString non deve restituire null");
        assertTrue(emptyString.contains("vuoto"), 
                  "toString per registro vuoto deve indicare che è vuoto");
        
        // Aggiungi restituzioni
        registry.addGiveback(giveback1);
        registry.addGiveback(giveback2);
        
        // Test registro con restituzioni
        String registryString = registry.toString();
        assertNotNull(registryString, "toString non deve restituire null");
        assertFalse(registryString.isEmpty(), "toString non deve restituire stringa vuota");
    }

    @Test
    void testExportOnFile() {
        registry.addGiveback(giveback1);
        registry.addGiveback(giveback2);
        
        // Test esportazione su file valido
        String fileName = TEST_FILES_DIR + "test_giveback_registry.txt";
        assertDoesNotThrow(() -> registry.exportOnFile(fileName), 
                          "L'esportazione su file valido non deve lanciare eccezioni");
        
        // Test esportazione con nome file null (dovrebbe gestire l'errore)
        assertDoesNotThrow(() -> registry.exportOnFile(null), 
                          "L'esportazione con nome null deve essere gestita");
        
        // Test esportazione con nome file vuoto
        assertDoesNotThrow(() -> registry.exportOnFile(""), 
                          "L'esportazione con nome vuoto deve essere gestita");
    }

    @Test
    void testImportFromFile() {
        // Aggiungi restituzioni e esporta
        registry.addGiveback(giveback1);
        registry.addGiveback(giveback2);
        String fileName = TEST_FILES_DIR + "test_import_giveback_registry.txt";
        registry.exportOnFile(fileName);
        
        // Test importazione da file valido
        GivebackRegistry importedRegistry = GivebackRegistry.importFromFile(fileName);
        assertNotNull(importedRegistry, "L'importazione da file valido deve restituire un registro");
        
        // Test importazione da file non esistente
        GivebackRegistry nullRegistry = GivebackRegistry.importFromFile("non_existent_file.txt");
        assertNull(nullRegistry, "L'importazione da file non esistente deve restituire null");
        
        // Test importazione con nome file null
        assertNull(GivebackRegistry.importFromFile(null), 
                  "L'importazione con nome null deve restituire null");
        
        // Test importazione con nome file vuoto
        assertNull(GivebackRegistry.importFromFile(""), 
                  "L'importazione con nome vuoto deve restituire null");
    }

    @Test
    void testMultipleOperations() {
        // Test aggiunta e ricerca
        assertTrue(registry.addGiveback(giveback1), "Aggiunta giveback1");
        assertTrue(registry.addGiveback(giveback2), "Aggiunta giveback2");
        assertTrue(registry.addGiveback(giveback3), "Aggiunta giveback3");
        
        // Verifica ricerca
        assertEquals(2, registry.searchByUser(student).size(), "student deve avere 2 restituzioni");
        assertEquals(2, registry.searchByBook(book1.getIsbn()).size(), "Book1 deve avere 2 restituzioni");
        
        // Verifica ricerca per data
        assertEquals(1, registry.searchByDate("2025-12-10").size(), "Data 2025-12-10 deve avere 1 restituzione");
        assertEquals(1, registry.searchByDate("2025-12-15").size(), "Data 2025-12-15 deve avere 1 restituzione");
        assertEquals(1, registry.searchByDate("2025-12-20").size(), "Data 2025-12-20 deve avere 1 restituzione");
    }

    @Test
    void testExportImportConsistency() {
        // Aggiungi restituzioni
        registry.addGiveback(giveback1);
        registry.addGiveback(giveback2);
        registry.addGiveback(giveback3);
        
        // Esporta
        String fileName = TEST_FILES_DIR + "test_consistency_giveback.txt";
        registry.exportOnFile(fileName);
        
        // Importa
        GivebackRegistry importedRegistry = GivebackRegistry.importFromFile(fileName);
        assertNotNull(importedRegistry, "Il registro importato non deve essere null");
        
        // Verifica consistenza (dimensione)
        assertEquals(3, importedRegistry.searchByUser(student).size() + importedRegistry.searchByUser(user).size(), 
                    "Il numero totale di restituzioni deve essere consistente");
    }

    @Test
    void testGetSize() {
        // Test registro vuoto
        assertEquals(0, registry.getSize(), "Registro vuoto deve avere dimensione 0");
        
        // Aggiungi elementi e verifica dimensione
        assertTrue(registry.addGiveback(giveback1));
        assertEquals(1, registry.getSize(), "Dopo aggiunta di un elemento, dimensione deve essere 1");
        
        assertTrue(registry.addGiveback(giveback2));
        assertEquals(2, registry.getSize(), "Dopo aggiunta di un secondo elemento, dimensione deve essere 2");
        
        assertTrue(registry.addGiveback(giveback3));
        assertEquals(3, registry.getSize(), "Dopo aggiunta di un terzo elemento, dimensione deve essere 3");
        
        // Aggiunta di null non deve cambiare dimensione
        assertFalse(registry.addGiveback(null));
        assertEquals(3, registry.getSize(), "Dimensione deve rimanere 3 dopo tentativo di aggiunta null");
    }

    @Test
    void testEmptyRegistryOperations() {
        // Test operazioni su registro vuoto
        assertTrue(registry.toString().contains("vuoto") || registry.toString().contains("empty"), 
                  "Registro vuoto deve indicarlo nel toString");
        
        // Ricerche su registro vuoto devono restituire liste vuote
        assertEquals(0, registry.searchByUser(student).size(), "Ricerca per utente su registro vuoto");
        assertEquals(0, registry.searchByBook("9780123456789").size(), "Ricerca per libro su registro vuoto");
        assertEquals(0, registry.searchByDate("2025-12-10").size(), "Ricerca per data su registro vuoto");
    }
}
