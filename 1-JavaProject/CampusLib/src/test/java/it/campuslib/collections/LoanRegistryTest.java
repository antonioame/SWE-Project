package it.campuslib.collections;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.campuslib.domain.catalog.Author;
import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.transactions.Giveback;
import it.campuslib.domain.transactions.Loan;
import it.campuslib.domain.users.User;
import it.campuslib.domain.users.InvalidUserInfoException;

/**
 * Classe di test per LoanRegistry
 */
public class LoanRegistryTest {
    private static final String TEST_FILES_DIR = "target/test-files/";
    private LoanRegistry registry;
    private Book book1, book2;
    private User user, student;
    private Loan loan1, loan2, loan3;

    @BeforeEach
    void setUp() throws InvalidUserInfoException{
        // Creazione directory per i file di test I/O, se inesistente:
        new File(TEST_FILES_DIR).mkdirs();
        
        registry = new LoanRegistry();
        
        Author author1 = new Author("Antonio", "Rossi");
        ArrayList<Author> authors1 = new ArrayList<>();
        authors1.add(author1);
        
        Author author2 = new Author("Gianmarco", "Verdi");
        ArrayList<Author> authors2 = new ArrayList<>();
        authors2.add(author1);
        authors2.add(author2);
        
        book1 = new Book("9780123456789", "Object Oriented Programming", authors1, 2020, 3);
        book2 = new Book("9780987654321", "Data Structures and Algorithms", authors2, 2022, 2);
        
        student = new User("Luca", "Martini", "0612710777", "luca.martini@studenti.unisa.it");
        user = new User("Maria", "Ferrari", "0000054321", "mferrari@unisa.it");
        
        loan1 = new Loan(book1, student, LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 15));
        loan2 = new Loan(book2, student, LocalDate.of(2025, 12, 5), LocalDate.of(2025, 12, 20));
        loan3 = new Loan(book1, user, LocalDate.of(2025, 12, 10), LocalDate.of(2025, 12, 25));
    }

    @Test
    void testConstructor() {
        assertNotNull(registry, "Il registro non deve essere null");
        assertNotNull(registry.toString(), "toString non deve restituire null");
    }

    @Test
    void testAddLoan() {
        assertTrue(registry.addLoan(loan1), "L'aggiunta del primo prestito deve avere successo");
        assertTrue(registry.addLoan(loan2), "L'aggiunta del secondo prestito deve avere successo");
        assertFalse(registry.addLoan(loan1), "Non è possibile aggiungere lo stesso prestito due volte");
        assertFalse(registry.addLoan(null), "Non è possibile aggiungere un prestito come null");
    }

    @Test
    void testRemoveLoan() {
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        
        assertTrue(registry.removeLoan(loan1), "La rimozione di un prestito esistente deve avere successo");
        assertFalse(registry.removeLoan(loan1), "Non è possibile rimuovere un prestito già rimosso");
        assertFalse(registry.removeLoan(loan3), "Non è possibile rimuovere un prestito non presente");
        assertFalse(registry.removeLoan(null), "Non è possibile rimuovere un prestito null");
    }

    @Test
    void testPullAsGiveback() {
        registry.addLoan(loan1);
        
        Giveback giveback = registry.pullAsGiveback(loan1);
        assertNotNull(giveback, "La conversione deve restituire un oggetto Giveback");
        
        // Verifica che l'ID sia stato preservato
        assertEquals(loan1.getId(), giveback.getId(), 
                    "L'ID del Giveback deve corrispondere all'ID del Loan originale");
        
        LocalDate today = LocalDate.now();
        LocalDate endDate = giveback.getEndDate();
        assertNotNull(endDate, "La data di restituzione non deve essere null");
        assertTrue(endDate.equals(today) || endDate.equals(today.minusDays(1)) || endDate.equals(today.plusDays(1)), 
                  "La data di restituzione deve essere oggi o con tolleranza di un giorno");
        
        assertEquals(loan1.getBorrowedBook(), giveback.getBorrowedBook(), 
                    "Il libro deve corrispondere");
        assertEquals(loan1.getBorrowerUser(), giveback.getBorrowerUser(), 
                    "L'utente deve corrispondere");
        assertEquals(loan1.getStartDate(), giveback.getStartDate(),
                    "La data di inizio deve corrispondere");
        
        assertFalse(registry.removeLoan(loan1), "Il prestito convertito deve essere rimosso dal registro");
        assertNull(registry.pullAsGiveback(loan2), 
                  "La rimozione e conversione di un prestito non presente deve restituire null");
        assertNull(registry.pullAsGiveback(null), 
                  "La rimozione e conversione di un prestito null deve restituire null");
    }

    @Test
    void testSearchByUser() throws InvalidUserInfoException{
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        registry.addLoan(loan3);
        
        // Test ricerca per student (ha 2 prestiti)
        LinkedList<Loan> loansstudent = registry.searchByUser(student);
        assertNotNull(loansstudent, "La lista dei prestiti non deve essere null");
        assertEquals(2, loansstudent.size(), "L'utente 1 deve avere 2 prestiti");
        assertTrue(loansstudent.contains(loan1), "Deve contenere loan1");
        assertTrue(loansstudent.contains(loan2), "Deve contenere loan2");
        
        // Test ricerca per user (ha 1 prestito)
        LinkedList<Loan> loansuser = registry.searchByUser(user);
        assertNotNull(loansuser, "La lista dei prestiti non deve essere null");
        assertEquals(1, loansuser.size(), "L'utente 2 deve avere 1 prestito");
        assertTrue(loansuser.contains(loan3), "Deve contenere loan3");
        
        // Test ricerca utente senza prestiti
        User user3 = new User("Paolo", "Tognani", "0000099999", "paolo.tognani@studenti.unisa.it");
        LinkedList<Loan> loansUser3 = registry.searchByUser(user3);
        assertNotNull(loansUser3, "La lista deve essere non null");
        assertEquals(0, loansUser3.size(), "L'utente senza prestiti deve avere lista vuota");
        
        // Test ricerca con utente null
        LinkedList<Loan> loansNull = registry.searchByUser(null);
        assertNotNull(loansNull, "La ricerca con utente null deve restituire lista vuota");
        assertEquals(0, loansNull.size(), "La lista deve essere vuota");
    }

    @Test
    void testSearchByBook() {
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        registry.addLoan(loan3);
        
        // Test ricerca per book1 (ha 2 prestiti: loan1 e loan3)
        LinkedList<Loan> loansBook1 = registry.searchByBook(book1.getIsbn());
        assertNotNull(loansBook1, "La lista dei prestiti non deve essere null");
        assertEquals(2, loansBook1.size(), "Il libro 1 deve avere 2 prestiti");
        assertTrue(loansBook1.contains(loan1), "Deve contenere loan1");
        assertTrue(loansBook1.contains(loan3), "Deve contenere loan3");
        
        // Test ricerca per book2 (ha 1 prestito)
        LinkedList<Loan> loansBook2 = registry.searchByBook(book2.getIsbn());
        assertNotNull(loansBook2, "La lista dei prestiti non deve essere null");
        assertEquals(1, loansBook2.size(), "Il libro 2 deve avere 1 prestito");
        assertTrue(loansBook2.contains(loan2), "Deve contenere loan2");
        
        // Test ricerca libro senza prestiti
        LinkedList<Loan> loansNonExistent = registry.searchByBook("9780111111111");
        assertNotNull(loansNonExistent, "La lista deve essere non null");
        assertEquals(0, loansNonExistent.size(), "Libro senza prestiti deve avere lista vuota");
        
        // Test ricerca con ISBN null
        LinkedList<Loan> loansNull = registry.searchByBook(null);
        assertNotNull(loansNull, "La ricerca con ISBN null deve restituire lista vuota");
        assertEquals(0, loansNull.size(), "La lista deve essere vuota");
    }

    @Test
    void testSearchByDate() {
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        registry.addLoan(loan3);
        
        // Test ricerca per data 2025-12-01 (loan1)
        LinkedList<Loan> loansDate1 = registry.searchByDate("2025-12-01");
        assertNotNull(loansDate1, "La lista dei prestiti non deve essere null");
        assertEquals(1, loansDate1.size(), "Deve esserci 1 prestito per questa data");
        assertTrue(loansDate1.contains(loan1), "Deve contenere loan1");
        
        // Test ricerca per data 2025-12-05 (loan2)
        LinkedList<Loan> loansDate2 = registry.searchByDate("2025-12-05");
        assertNotNull(loansDate2, "La lista dei prestiti non deve essere null");
        assertEquals(1, loansDate2.size(), "Deve esserci 1 prestito per questa data");
        assertTrue(loansDate2.contains(loan2), "Deve contenere loan2");
        
        // Test ricerca per data senza prestiti
        LinkedList<Loan> loansDateEmpty = registry.searchByDate("2025-11-01");
        assertNotNull(loansDateEmpty, "La lista deve essere non null");
        assertEquals(0, loansDateEmpty.size(), "Data senza prestiti deve avere lista vuota");
        
        // Test ricerca con data null
        LinkedList<Loan> loansNull = registry.searchByDate(null);
        assertNotNull(loansNull, "La ricerca con data null deve restituire lista vuota");
        assertEquals(0, loansNull.size(), "La lista deve essere vuota");
        
        // Test ricerca con data in formato errato
        LinkedList<Loan> loansInvalidDate = registry.searchByDate("invalid-date");
        assertNotNull(loansInvalidDate, "La ricerca con data invalida deve restituire lista vuota");
        assertEquals(0, loansInvalidDate.size(), "La lista deve essere vuota per data invalida");
    }

    @Test
    void testToString() {
        // Test registro vuoto
        String emptyString = registry.toString();
        assertNotNull(emptyString, "toString non deve restituire null");
        assertTrue(emptyString.contains("vuoto"), "toString per registro vuoto deve indicare che è vuoto");
        
        // Test registro con prestiti
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        String registryString = registry.toString();
        assertNotNull(registryString, "toString non deve restituire null");
        assertFalse(registryString.isEmpty(), "toString non deve restituire stringa vuota");
    }

    @Test
    void testExportOnFile() {
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        
        // Test esportazione su file valido
        String fileName = TEST_FILES_DIR + "test_loan_registry.txt";
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
        // Aggiungi prestiti e esporta
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        String fileName = TEST_FILES_DIR + "test_import_loan_registry.txt";
        registry.exportOnFile(fileName);
        
        // Test importazione da file valido
        LoanRegistry importedRegistry = LoanRegistry.importFromFile(fileName);
        assertNotNull(importedRegistry, "L'importazione da file valido deve restituire un registro");
        
        // Test importazione da file non esistente
        LoanRegistry nullRegistry = LoanRegistry.importFromFile("non_existent_file.txt");
        assertNull(nullRegistry, "L'importazione da file non esistente deve restituire null");
        
        // Test importazione con nome file null
        assertNull(LoanRegistry.importFromFile(null), 
                  "L'importazione con nome null deve restituire null");
        
        // Test importazione con nome file vuoto
        assertNull(LoanRegistry.importFromFile(""), 
                  "L'importazione con nome vuoto deve restituire null");
    }
    
    @Test
    void testGetSize() {
        // Test registro vuoto
        assertEquals(0, registry.getSize(), "Registro vuoto deve avere dimensione 0");
        
        // Aggiungi elementi e verifica dimensione
        assertTrue(registry.addLoan(loan1));
        assertEquals(1, registry.getSize(), "Dopo aggiunta di un elemento, dimensione deve essere 1");
        
        assertTrue(registry.addLoan(loan2));
        assertEquals(2, registry.getSize(), "Dopo aggiunta di un secondo elemento, dimensione deve essere 2");
        
        // Aggiunta di elemento duplicato non deve cambiare dimensione (TreeSet)
        assertFalse(registry.addLoan(loan1), "Aggiunta di duplicato deve fallire");
        assertEquals(2, registry.getSize(), "Dimensione deve rimanere 2 dopo tentativo di aggiunta duplicato");
        
        // Rimozione elemento
        assertTrue(registry.removeLoan(loan1));
        assertEquals(1, registry.getSize(), "Dopo rimozione di un elemento, dimensione deve essere 1");
        
        // Rimozione elemento non presente non cambia dimensione
        assertFalse(registry.removeLoan(loan3));
        assertEquals(1, registry.getSize(), "Dimensione deve rimanere 1 dopo tentativo di rimozione elemento non presente");
        
        // Rimozione ultimo elemento
        assertTrue(registry.removeLoan(loan2));
        assertEquals(0, registry.getSize(), "Dopo rimozione dell'ultimo elemento, dimensione deve essere 0");
    }
    
    @Test
    void testMultipleOperations() {
        // Test scenario complesso: aggiunta, ricerca, rimozione
        assertTrue(registry.addLoan(loan1), "Aggiunta loan1");
        assertTrue(registry.addLoan(loan2), "Aggiunta loan2");
        assertTrue(registry.addLoan(loan3), "Aggiunta loan3");
        
        // Verifica ricerca
        assertEquals(2, registry.searchByUser(student).size(), "student deve avere 2 prestiti");
        assertEquals(2, registry.searchByBook(book1.getIsbn()).size(), "Book1 deve avere 2 prestiti");
        
        // Converti loan1 in giveback
        Giveback giveback = registry.pullAsGiveback(loan1);
        assertNotNull(giveback, "Conversione deve avere successo");
        
        // Verifica stato dopo conversione
        assertEquals(1, registry.searchByUser(student).size(), "student ora deve avere 1 prestito");
        assertEquals(1, registry.searchByBook(book1.getIsbn()).size(), "Book1 ora deve avere 1 prestito");
        
        // Rimuovi loan2
        assertTrue(registry.removeLoan(loan2), "Rimozione loan2 deve avere successo");
        assertEquals(0, registry.searchByUser(student).size(), "student ora non deve avere prestiti");
    }

    @Test
    void testExportImportConsistency() {
        // Aggiungi prestiti
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        registry.addLoan(loan3);
        
        // Esporta
        String fileName = TEST_FILES_DIR + "test_consistency_loan.txt";
        registry.exportOnFile(fileName);
        
        // Importa
        LoanRegistry importedRegistry = LoanRegistry.importFromFile(fileName);
        assertNotNull(importedRegistry, "Il registro importato non deve essere null");
        
        // Verifica consistenza (dimensione)
        assertEquals(3, importedRegistry.searchByUser(student).size() + importedRegistry.searchByUser(user).size(), 
                    "Il numero totale di prestiti deve essere consistente");
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