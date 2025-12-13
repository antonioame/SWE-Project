package it.campuslib.collections;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.campuslib.domain.catalog.Author;
import it.campuslib.domain.catalog.Book;

public class BookCatalogTest {
    private BookCatalog catalog;
    private Book book1, book2;
    private Author author1;
    
    //File temporaneo per i test di serializzazione
    private static final String TEST_FILENAME = "testCatalog.ser";
    
    public BookCatalogTest() {
    }

    @BeforeEach
    void setUp() {
        catalog = new BookCatalog();
        author1 = new Author("Italo", "Calvino");
        ArrayList<Author> authors1 = new ArrayList<>();
        authors1.add(author1);
        
        // Supponiamo che il costruttore Book sia: (isbn, title, authors, publishingYear, copies)
        book1 = new Book("9788804368581", "Il Sentiero Dei Nidi Di Ragno", authors1, 1947, 5);
        book2 = new Book("9788804685715", "Marcovaldo", authors1, 1963, 10);
        
        // Aggiungo i libri al catalogo iniziale
        catalog.addBook(book1);
        catalog.addBook(book2);
    }
    
    //eliminazione file di test
    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Test
    void testAddBook_Success() {
        Book newBook = new Book("9788804603610", "Se Una Notte d'Inverno Un Viaggiatore", new ArrayList<>(), 1979, 3);
        assertEquals(2, catalog.getCatalogSize()); 
        
        assertTrue(catalog.addBook(newBook));
        assertNotNull(catalog.searchByIsbn("9788804603610"));
    }
    
    @Test
    void testAddBook_Fail_InputNull() {
        assertFalse(catalog.addBook(null));
    }

    @Test
    void testRemoveBook_Success() {
        // Verifica lo stato iniziale (presumibilmente ADOPTED, dato il costruttore Book)
        // assertTrue(book1.isAdopted());  //Precondizione: il libro è registrato
        
        // Rimuove il libro (cambia lo stato)
        assertTrue(catalog.removeBook(book1.getIsbn()));
        
        // Verifica che lo stato sia cambiato
        assertFalse(book1.isAdopted());
    }

    /* Precondizione: il libro è registrato
    @Test
    void testRemoveBook_Fallimento_ISBNInesistente() {
        assertFalse(catalog.removeBook("9999999999999"));
    }
    */

    @Test
    void testSearchByTitle_Partial_Success() {
        LinkedList<Book> results = catalog.searchByTitle("sentiero");
        assertEquals(1, results.size());
        assertEquals(book1.getTitle(), results.getFirst().getTitle());
    }
    
    @Test
    void testTearchByTitle_Success_CaseInsensitive() {
        LinkedList<Book> results = catalog.searchByTitle("MARCOVAlDo");
        assertEquals(1, results.size());
    }

    @Test
    void testSearchByTitle_NoResult() {
        LinkedList<Book> results = catalog.searchByTitle("Titolo Inesistente");
        assertTrue(results.isEmpty());
    }

    @Test
    void testSearchByAuthor_Success() {
        // author1 è autore di book1 e book2
        LinkedList<Book> results = catalog.searchByAuthor(author1);
        assertEquals(2, results.size());
        assertTrue(results.contains(book1) && results.contains(book2));
    }
    
    @Test
    void testSearchByAuthor_NoResult() {
        Author authorUnknown = new Author("Ciccio", "Graziani");
        LinkedList<Book> results = catalog.searchByAuthor(authorUnknown);
        assertTrue(results.isEmpty());
    }

    @Test
    void testSearchByIsbn_Success() {
        Book foundBook = catalog.searchByIsbn(book2.getIsbn());
        assertNotNull(foundBook);
        assertEquals("Marcovaldo", foundBook.getTitle());
    }

    @Test
    void searchByIsbn_Fail_INVALID_ISBN() {
        assertNull(catalog.searchByIsbn("INVALID_ISBN"));
    }

    @Test
    void testSearch_Success_Query() {
        // Ricerca per ISBN parziale
        assertEquals(1, catalog.search("8581").size()); // Dovrebbe trovare book1 per ISBN parziale
        
        // Ricerca per Anno di pubblicazione
        assertEquals(1, catalog.search("1963").size()); // Dovrebbe trovare book2 per anno di pubblicazione
        
        // Ricerca per Titolo parziale e case-insensitive
        assertEquals(1, catalog.search("rAgno").size()); // Dovrebbe trovare book1 per titolo parziale
        
        // Ricerca per Autore parziale (assumendo che toString() includa il cognome)
        assertEquals(2, catalog.search("calvino").size()); // Dovrebbe trovare entrambi i libri per autore
    }

    @Test
    void testToString_Full_Catalog() {
        String result = catalog.toString();
        
        // Verifica che contenga il riassunto e i titoli dei libri
        assertTrue(result.contains("Catalogo Libri (Totale: 2 libri distinti)"));
        assertTrue(result.contains("Il Sentiero Dei Nidi Di Ragno"));
        assertTrue(result.contains("Marcovaldo"));
        
        // Verifica un elemento chiave del contenuto del libro (es. ISBN o Stato)
        assertTrue(result.contains(book1.getIsbn()));
    }
    
    @Test
    void testToString_Empty_Catalog() {
        BookCatalog emptyCatalog = new BookCatalog();
        String result = emptyCatalog.toString();
        
        // Verifica il messaggio specifico per il catalogo vuoto
        assertEquals("Il catalogo è attualmente vuoto.\n", result);
    }

    @Test
    public void testImportExport() {
        // 1. Esporta lo stato corrente
        catalog.exportOnFile(TEST_FILENAME);
        
        // 2. Importa da file
        BookCatalog importedCatalog = BookCatalog.importFromFile(TEST_FILENAME);
        
        assertNotNull(importedCatalog); // L'importazione non deve restituire null
        
        // 3. Verifica il contenuto
        // Inizialmente 2 libri
        assertEquals(2, importedCatalog.getCatalogSize()); 
        
        // 4. Verifica che i libri siano corretti (e che abbiano mantenuto lo stato)
        Book importedBook1 = importedCatalog.searchByIsbn(book1.getIsbn());
        assertNotNull(importedBook1);
        assertEquals(book1.getTitle(), importedBook1.getTitle());
    }
    
    @Test
    public void testExportOnFile_Fail_Invalid_Name() {
        assertDoesNotThrow(() -> catalog.exportOnFile(null)); // Non deve lanciare eccezioni per nome file nullo
    }

    @Test
    public void testImportFromNonExistentFile() {
        //File Inesistente
        assertNull(BookCatalog.importFromFile("file_inesistente_xyz.ser")); // L'importazione deve restituire null se il file non esiste
    }
    
    @Test
    public void testGetCatalogSize() {
        // Verifica la dimensione dopo il setUp (2 libri)
        assertEquals(2, catalog.getCatalogSize());
        
        // Aggiunge un libro e verifica l'incremento
        Book newBook = new Book("9780000000000", "Nuovo", new ArrayList<>(), 2024, 1);
        catalog.addBook(newBook);
        assertEquals(3, catalog.getCatalogSize());
    }
}
