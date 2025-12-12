package it.campuslib.domain.transactions;

import org.junit.jupiter.api.Test;

import it.campuslib.domain.catalog.Author;
import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.users.User;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;


public class GivebackTest {
    

    private Book borrowedBook;
    private User borrowerUser;
    private LocalDate startDate;
    private ArrayList<Author> authors;

    public GivebackTest() {
    }

     @BeforeEach
    public void setUp() {
        authors = new ArrayList<>();
        authors.add(new Author("Claudio", "De Sio Cesari"));
        Book borrowedBook = new Book("978-88-360-1812-3", "Programmazione Java", authors, 2025, 1 );
        User borrowerUser = new User("Giovanni", "Rossi", "0612709852", "g.rossi36@studenti.unisa.it");
        LocalDate startDate = LocalDate.of(2025, 12, 1); 
         
    }

    @Test
    public void testGiveBack() {
    }

    @Test
    public void testConstructor(){
        Giveback giveback = new Giveback(borrowedBook, borrowerUser, startDate, LocalDate.now());
        assertNotNull(giveback);
        assertEquals(borrowedBook, giveback.getBorrowedBook());
        assertEquals(borrowerUser, giveback.getBorrowerUser());
        assertEquals(startDate, giveback.getStartDate());
        assertEquals(LocalDate.now(), giveback.getEndDate());
    }

   
    
}
