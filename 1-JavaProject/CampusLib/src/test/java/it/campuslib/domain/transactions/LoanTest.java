package it.campuslib.domain.transactions;

import org.junit.jupiter.api.Test;

import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.users.User;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;


public class LoanTest {
    
    public LoanTest() {
    }

    @BeforeAll
    public void setUp() {
        Book borrowedBook = new Book("9788836018123", "Programmazione Java", "Claudio De Sio Cesari", 2025, 1 );
        BorrowerUser borrowerUser = new User("Giovanni", "Rossi", 0612709852, "g.rossi36@studenti.unisa.it");
        LocalDate startDate = new LocalDate(2025, 12, 1); 
        LocalDate expectedReturnDate = new LocalDate(2025, 12, 15); 
    }

    @Test
    public void testGetExpectedReturnDate() {
    }

    @Test
    public void testSetExpectedReturnDate() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testCompareTo() {
    }

    @Test
    public void testIsOverdue() {
    }
    
}
