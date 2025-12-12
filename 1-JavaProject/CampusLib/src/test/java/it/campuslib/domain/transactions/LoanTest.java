package it.campuslib.domain.transactions;

import org.junit.jupiter.api.Test;

import it.campuslib.domain.catalog.Author;
import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.users.User;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


public class LoanTest {
    
    private Book borrowedBook;
    private User borrowerUser;
    private ArrayList<Author> authors;
    public LoanTest() {
    }

    @BeforeEach
    public void setUp() {
        authors = new ArrayList<>();
        authors.add(new Author("Claudio", "De Sio Cesari"));
        borrowedBook = new Book("9788836018123", "Programmazione Java", authors, 2025, 1 );
        borrowerUser = new User("Giovanni", "Rossi", "1612709852", "g.rossi36@studenti.unisa.it");
        
    }

    @Test 
    /*Test per il costruttore e per i metodi di get */
    public void testConstructor() {       
        LocalDate startDate = LocalDate.of(2025, 12, 1);
        LocalDate expectedReturnDate = LocalDate.of(2025, 12, 15);
        Loan loan = new Loan(borrowedBook, borrowerUser, startDate, expectedReturnDate);
        assertNotNull(loan);
        assertEquals(borrowedBook, loan.getBorrowedBook());
        assertEquals(borrowerUser, loan.getBorrowerUser());
        assertEquals(startDate, loan.getStartDate());
        assertEquals(expectedReturnDate, loan.getExpectedReturnDate());
    }

    
    @Test
    public void testSetExpectedReturnDate() {
        
        LocalDate startDate = LocalDate.of(2025, 12, 1);
        LocalDate expectedReturnDate = LocalDate.of(2025, 12, 15);
        Loan loan = new Loan(borrowedBook, borrowerUser, startDate, expectedReturnDate);
        LocalDate newReturnDate = LocalDate.of(2025, 12, 10); 
        loan.setExpectedReturnDate(newReturnDate);
        assertEquals(newReturnDate, loan.getExpectedReturnDate());
    }


    @Test
    public void testEquals() {

        /*l1 e mockLoan hanno stessa data di restituzione, per cui mi aspetto che siano uguali  */
        LocalDate startDate = LocalDate.of(2025, 12, 1);
        LocalDate expectedReturnDate = LocalDate.of(2025, 12, 15);
        Loan l1 = new Loan(borrowedBook, borrowerUser, startDate, expectedReturnDate);
        Loan mockLoan = new Loan(borrowedBook, borrowerUser, LocalDate.of(2025, 12, 2), expectedReturnDate);
        assertTrue(l1.equals(mockLoan));

    }

    @Test
    /*Uguaglianza non verificata */
    public void testEquals2(){
        LocalDate startDate = LocalDate.of(2025, 11, 1);
        Loan l1 = new Loan(borrowedBook, borrowerUser, startDate, LocalDate.now());
        Loan l3 = new Loan(borrowedBook, borrowerUser, startDate, LocalDate.now().plusDays(1));
        assertFalse(l1.equals(l3));
    }
    @Test
    public void testCompareTo() {
        Loan l1= new Loan(borrowedBook, borrowerUser, LocalDate.of(2025, 12, 1), LocalDate.now());
        Loan l2= new Loan(borrowedBook, borrowerUser, LocalDate.of(2025, 12, 3), LocalDate.now().plusDays(5));
        Loan l3= new Loan(borrowedBook, borrowerUser, LocalDate.of(2025, 12, 5), LocalDate.now().minusDays(2));
        User u1= new User("Mario", "Verdi", "1234567890", "m.verdi@unisa.it");
        Loan l4= new Loan(borrowedBook, u1, LocalDate.of(2025, 12, 7), LocalDate.now());
        //copre i tre casi di minore, maggiore e uguale
        assertTrue(l1.compareTo(l2) < 0);
        assertTrue(l1.compareTo(l3) > 0);
        assertTrue(l1.compareTo(l4) == 0);
    }

    @Test
    //Caso di not overdue
    public void testIsOverdue1(){
        LocalDate startDate=LocalDate.of(2025, 12, 1);
        LocalDate expectedReturnDate=LocalDate.now().minusDays(5);
        Loan l= new Loan(borrowedBook, borrowerUser, startDate, expectedReturnDate);
        assertFalse(l.isOverdue());
    }
    @Test
    //Caso limite
       public void testIsOverdue2(){
        LocalDate startDate=LocalDate.of(2025, 12, 1);
        LocalDate expectedReturnDateDate=LocalDate.now();
        Loan l = new Loan(borrowedBook, borrowerUser, startDate, expectedReturnDateDate);
        assertFalse(l.isOverdue());
    }

    @Test
    //Caso di overdue
       public void testIsOverdue3(){
        LocalDate startDate=LocalDate.of(2025 , 12, 1);
        LocalDate expectedReturnDate=LocalDate.now().plusDays(5);
        Loan l= new Loan(borrowedBook, borrowerUser, startDate, expectedReturnDate);
        assertTrue(l.isOverdue());


       }
}
