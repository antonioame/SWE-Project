package it.campuslib.domain.users;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;



import it.campuslib.domain.transactions.Loan;
import it.campuslib.domain.catalog.*;
import it.campuslib.collections.LoanRegistry;
import it.campuslib.domain.transactions.InvalidLoanInfoException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
 
/**
 *
 * @author luca
 */
public class UserTest {
    
    private User utente1, utente2, utente3;
    
    
    

    @BeforeEach
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void setUp() throws InvalidUserInfoException{
    
        utente1 = new User("Mario", "Rossi", "1112701345", "mario.rossi@studenti.unisa.it");
        utente2 = new User("Andrea", "Bianchi", "1234567890", "andrea.bianchi@unisa.it");
        utente3 = new User("Luca", "Verdi", "1000111222", "luca.verdi@studenti.unisa.it");
    }
    
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testConstructorValid() throws InvalidUserInfoException{
    
        assertDoesNotThrow(()-> {
        
            User u1 = new User("Mario", "Rossi", "0612701345", "mario.rossi@unisa.it"); //Formati corretti
            assertNotNull(u1); //Controllo sulla creazione effettiva dell'oggetto
        });
        
        
        assertDoesNotThrow(()-> {
        
            User u2 = new User("Pino", "Daniele", "0517412345", "pino.daniele@unisa.it"); //Formati corretti
            assertNotNull(u2); //Controllo sulla creazione effettiva dell'oggetto
        });
        
        
        assertDoesNotThrow(()-> {
        
            User u3 = new User("oiraM", "issoR", "5431072160", "rossi.mario@studenti.unisa.it"); //Formati corretti
            assertNotNull(u3); //Controllo sulla creazione effettiva dell'oggetto
        });
        
        
        
        
    }
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testConstructorInvalidEnrollmentID() throws InvalidUserInfoException {
    
        assertThrows(InvalidUserInfoException.class, ()->{
        
            User u1 = new User("Mario", "Bianchi", "0612455", "mario.bianchi@studenti.unisa.it"); //Formato matricola errato
        });
        
        
        assertThrows(InvalidUserInfoException.class, ()->{
        
            User u2 = new User("Maria", "Verdi", "0612455", "maria.verdi@studenti.unisa.it"); //Formato matricola errato
        });
        
        
        assertThrows(InvalidUserInfoException.class, ()->{
        
            User u3 = new User("Luca", "Carboni", "0612455", "sempre.stesso@unisa.it"); //Formato matricola errato
        });
        
        
        
    }
       
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testConstructorInvalidEmail() {
    
        assertThrows(InvalidUserInfoException.class, ()-> {
        
            User u1 = new User("Mario", "Rossi", "0612701345", "mario.rossi@unina.it");
        });
        
        assertThrows(InvalidUserInfoException.class, ()-> {
        
            User u2 = new User("Claudio", "Baglioni", "0123456789", "email@email.it");
        });
        
        assertThrows(InvalidUserInfoException.class, ()-> {
        
            User u3 = new User("Francesco", "Salerno", "0612700001", "f.salerno@unisa.studenti.it");
        });
    }
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testConstructorInvalidAll() {
    
        assertThrows(InvalidUserInfoException.class, ()-> {
        
            User u1 = new User("Mario", "Rossi", "0132", "mario.rossi@unina.it");
        });
        
        assertThrows(InvalidUserInfoException.class, ()-> {
        
             User u2 = new User("Maria", "Verdi", "0612455", "maria.verdi@studenti.informatica.unisa.it");
        });
        
        assertThrows(InvalidUserInfoException.class, ()-> {
        
             User u3 = new User("oiraM", "issoR", "54312160", "rossi.mario@stud.unisa.it");
        });

    }
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testGetEnrollmentID() {
        
        assertEquals("1112701345", utente1.getEnrollmentID());
        assertEquals("1234567890", utente2.getEnrollmentID());
        assertEquals("1000111222", utente3.getEnrollmentID());
       
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testGetSetEmail() throws InvalidUserInfoException{
        
        User utente4 = new User("Maria", "Roma", "0612710122", "maria.roma@unisa.it");
        
        utente4.setEmail("maria.roma@studenti.unisa.it");
        
        assertEquals("mario.rossi@studenti.unisa.it", utente1.getEmail());
        assertEquals("andrea.bianchi@unisa.it", utente2.getEmail());
        assertEquals("luca.verdi@studenti.unisa.it", utente3.getEmail());
        assertEquals("maria.roma@studenti.unisa.it", utente4.getEmail());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testGetSetStatusActive() {
        
        
        utente1.setStatus(UserStatus.ACTIVE);
        utente2.setStatus(UserStatus.ACTIVE);
        utente3.setStatus(UserStatus.ACTIVE);
        
        assertEquals(UserStatus.ACTIVE, utente1.getStatus());
        assertEquals(UserStatus.ACTIVE, utente2.getStatus());
        assertEquals(UserStatus.ACTIVE, utente3.getStatus());
        
    }
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testGetSetStatusInactive() {
    
        utente1.setStatus(UserStatus.INACTIVE);
        utente2.setStatus(UserStatus.INACTIVE);
        utente3.setStatus(UserStatus.INACTIVE);
        
        assertEquals(UserStatus.INACTIVE, utente1.getStatus());
        assertEquals(UserStatus.INACTIVE, utente2.getStatus());
        assertEquals(UserStatus.INACTIVE, utente3.getStatus());
    }

    

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testIsActive() {
        
        utente1.setStatus(UserStatus.ACTIVE);
        utente2.setStatus(UserStatus.ACTIVE);
        utente3.setStatus(UserStatus.ACTIVE);
        
        assertTrue(utente1.isActive());
        assertTrue(utente2.isActive());
        assertTrue(utente3.isActive());
    }
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testIsInactive() {
    
        utente1.setStatus(UserStatus.INACTIVE);
        utente2.setStatus(UserStatus.INACTIVE);
        utente3.setStatus(UserStatus.INACTIVE);
        
        assertFalse(utente1.isActive());
        assertFalse(utente2.isActive());
        assertFalse(utente3.isActive());
        
    }


    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testSetGetMaxLoans() {
    
        assertEquals(3, utente1.getMaxLoans());
        
        utente2.setMaxLoans(2);
        assertEquals(2, utente2.getMaxLoans());
        
        utente3.setMaxLoans(1);
        assertEquals(1, utente3.getMaxLoans());
        
        
    }
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testEquals() throws InvalidUserInfoException{
        
        User utente4 = new User("Andrea", "Bianchi", "1234567890", "andrea.bianchi@unisa.it");
        
        assertTrue(utente2.equals(utente4));
        
    }
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testDoesNotEqual() {
    
        assertFalse(utente1.equals(utente2));
        assertFalse(utente2.equals(utente3));
        
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testEqualHashCode() throws InvalidUserInfoException{
        
        User utente4 = new User("Andrea", "Bianchi", "1234567890", "andrea.bianchi@unisa.it");
        
        assertEquals(utente2.hashCode(), utente4.hashCode());
    }
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testNotEqualHashCode() {
    
        assertNotEquals(utente1.hashCode(), utente3.hashCode());
        assertNotEquals(utente2.hashCode(), utente3.hashCode());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testCompareTo() throws InvalidUserInfoException{
        
        //utente3<utente2
        User utente4 = new User("Andrea", "Bianchi", "1234567890", "andrea.bianchi@unisa.it");
        
        assertTrue(utente3.compareTo(utente2) < 0);
        assertTrue(utente2.compareTo(utente1) > 0);
        assertTrue(utente2.compareTo(utente4) == 0);
    }
    
    
   
    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    public void testActiveLoans() throws InvalidLoanInfoException{
    
        Loan loan1, loan2, loan3, loan4, loan5, loan6;
        Book book1, book2, book3;
        LoanRegistry registry = new LoanRegistry();
        Author author1, author2, author3;
        
        author1 = new Author("Focault", "Michel");
        author2 = new Author("Steinbeck", "John");
        author3 = new Author("Camilleri", "Andrea");
        
        ArrayList<Author> a1 = new ArrayList<>();
        ArrayList<Author> a2 = new ArrayList<>();
        ArrayList<Author> a3 = new ArrayList<>();
        
        a1.add(author1);
        a2.add(author2);
        a3.add(author3);
        
        book1 = new Book("1234567890123", "Sorvegliare e punire", a1, 1976, 2);
        book2 = new Book("1313131313131", "Furore", a2, 1939, 5);
        book3 = new Book("2020202020202", "Il cane di terracotta", a3, 1996, 1);
        
        loan1 = new Loan(book1, utente1, LocalDate.now(), LocalDate.of(2026, 3, 12));
        loan2 = new Loan(book2, utente2, LocalDate.of(2025, 5, 18), LocalDate.now());
        loan3 = new Loan(book3, utente3, LocalDate.of(2025, 10, 17), LocalDate.now());
        loan4 = new Loan(book2, utente1, LocalDate.of(2025, 5, 18), LocalDate.now());
        loan5 = new Loan(book3, utente2, LocalDate.of(2025, 10, 17), LocalDate.now());
        loan6 = new Loan(book1, utente3, LocalDate.now(), LocalDate.of(2026, 3, 12));
        
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        registry.addLoan(loan3);
        registry.addLoan(loan4);
        registry.addLoan(loan5);
        registry.addLoan(loan6);
        
        LinkedList<Loan> associatedLoans = new LinkedList<>();
        
        associatedLoans = utente1.getActiveLoans(registry);
        
        for(Loan l : associatedLoans) {
        
            assertTrue((l.equals(loan1) && !l.equals(loan4)) || (!l.equals(loan1) || l.equals(loan4)));
        }
        
        associatedLoans = utente2.getActiveLoans(registry);
        
        for(Loan l : associatedLoans) {
        
            assertTrue((l.equals(loan2) && !l.equals(loan5)) || (!l.equals(loan2) || l.equals(loan5)));
        }
        
        
        associatedLoans = utente3.getActiveLoans(registry);
        
        for(Loan l : associatedLoans) {
        
            assertTrue((l.equals(loan3) && !l.equals(loan6)) || (!l.equals(loan3) || l.equals(loan6)));
        }
           
    }
    
     @Test
     @Timeout(value = 15, unit = TimeUnit.SECONDS)
     public void testCanBorrow() throws InvalidLoanInfoException {
     
         Loan loan1, loan2, loan3, loan4, loan5, loan6;
        Book book1, book2, book3;
        LoanRegistry registry = new LoanRegistry();
        Author author1, author2, author3;
        
        author1 = new Author("Focault", "Michel");
        author2 = new Author("Steinbeck", "John");
        author3 = new Author("Camilleri", "Andrea");
        
        ArrayList<Author> a1 = new ArrayList<>();
        ArrayList<Author> a2 = new ArrayList<>();
        ArrayList<Author> a3 = new ArrayList<>();
        
        a1.add(author1);
        a2.add(author2);
        a3.add(author3);
        
        book1 = new Book("1234567890123", "Sorvegliare e punire", a1, 1976, 2);
        book2 = new Book("1313131313131", "Furore", a2, 1939, 5);
        book3 = new Book("2020202020202", "Il cane di terracotta", a3, 1996, 1);
        
        loan1 = new Loan(book1, utente1, LocalDate.now(), LocalDate.of(2026, 3, 12));
        loan2 = new Loan(book2, utente2, LocalDate.of(2025, 5, 18), LocalDate.now());
        loan3 = new Loan(book3, utente3, LocalDate.of(2025, 10, 17), LocalDate.now());
        loan4 = new Loan(book2, utente1, LocalDate.of(2025, 5, 18), LocalDate.now());
        loan5 = new Loan(book3, utente2, LocalDate.of(2025, 10, 17), LocalDate.now());
        loan6 = new Loan(book1, utente3, LocalDate.now(), LocalDate.of(2026, 3, 12));
        
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        registry.addLoan(loan3);
        registry.addLoan(loan4);
        registry.addLoan(loan5);
        registry.addLoan(loan6);
        
        //Tutti e tre gli utenti hanno come numero massimo di prestiti 3
        
        assertTrue(utente1.canBorrow(registry));
        assertTrue(utente2.canBorrow(registry));
        assertTrue(utente3.canBorrow(registry));
     }
     
     @Test
     @Timeout(value = 15, unit = TimeUnit.SECONDS)
     public void testCannotBorrow() throws InvalidLoanInfoException{
     
        Loan loan1, loan2, loan3, loan4, loan5, loan6;
        Book book1, book2, book3;
        LoanRegistry registry = new LoanRegistry();
        Author author1, author2, author3;
        
        author1 = new Author("Focault", "Michel");
        author2 = new Author("Steinbeck", "John");
        author3 = new Author("Camilleri", "Andrea");
        
        ArrayList<Author> a1 = new ArrayList<>();
        ArrayList<Author> a2 = new ArrayList<>();
        ArrayList<Author> a3 = new ArrayList<>();
        
        a1.add(author1);
        a2.add(author2);
        a3.add(author3);
        
        book1 = new Book("1234567890123", "Sorvegliare e punire", a1, 1976, 2);
        book2 = new Book("1313131313131", "Furore", a2, 1939, 5);
        book3 = new Book("2020202020202", "Il cane di terracotta", a3, 1996, 1);
        
        loan1 = new Loan(book1, utente1, LocalDate.now(), LocalDate.of(2026, 3, 12));
        loan2 = new Loan(book2, utente2, LocalDate.of(2025, 5, 18), LocalDate.now());
        loan3 = new Loan(book3, utente3, LocalDate.of(2025, 10, 17), LocalDate.now());
        loan4 = new Loan(book2, utente1, LocalDate.of(2025, 5, 18), LocalDate.now());
        loan5 = new Loan(book3, utente2, LocalDate.of(2025, 10, 17), LocalDate.now());
        loan6 = new Loan(book1, utente3, LocalDate.now(), LocalDate.of(2026, 3, 12));
        
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        registry.addLoan(loan3);
        registry.addLoan(loan4);
        registry.addLoan(loan5);
        registry.addLoan(loan6);
        
        //Tutti e tre gli utenti hanno come numero massimo di prestiti 2
        utente1.setMaxLoans(2);
        utente2.setMaxLoans(2);
        utente3.setMaxLoans(2);
        
        assertFalse(utente1.canBorrow(registry));
        assertFalse(utente2.canBorrow(registry));
        assertFalse(utente3.canBorrow(registry));
     }
     
     @Test
     @Timeout(value = 15, unit = TimeUnit.SECONDS)
     public void testGetAvailableLoanSlots() throws InvalidLoanInfoException{
     
        Loan loan1, loan2, loan3, loan4, loan5, loan6;
        Book book1, book2, book3;
        LoanRegistry registry = new LoanRegistry();
        Author author1, author2, author3;
        
        author1 = new Author("Focault", "Michel");
        author2 = new Author("Steinbeck", "John");
        author3 = new Author("Camilleri", "Andrea");
        
        ArrayList<Author> a1 = new ArrayList<>();
        ArrayList<Author> a2 = new ArrayList<>();
        ArrayList<Author> a3 = new ArrayList<>();
        
        a1.add(author1);
        a2.add(author2);
        a3.add(author3);
        
        book1 = new Book("1234567890123", "Sorvegliare e punire", a1, 1976, 2);
        book2 = new Book("1313131313131", "Furore", a2, 1939, 5);
        book3 = new Book("2020202020202", "Il cane di terracotta", a3, 1996, 1);
        
        loan1 = new Loan(book1, utente1, LocalDate.now(), LocalDate.of(2026, 3, 12));
        loan2 = new Loan(book2, utente2, LocalDate.of(2025, 5, 18), LocalDate.now());
        loan3 = new Loan(book3, utente3, LocalDate.of(2025, 10, 17), LocalDate.now());
        loan4 = new Loan(book2, utente1, LocalDate.of(2025, 5, 20), LocalDate.of(2027, 3, 15));
        loan5 = new Loan(book3, utente2, LocalDate.of(2025, 6, 17), LocalDate.of(2025, 8, 17));
        loan6 = new Loan(book1, utente3, LocalDate.now(), LocalDate.of(2026, 3, 12));
        
        registry.addLoan(loan1);
        registry.addLoan(loan2);
        registry.addLoan(loan3);
        registry.addLoan(loan4);
        registry.addLoan(loan5);
        registry.addLoan(loan6);
        
        
        //utente1 ha un numero massimo di prestiti pari a 3
        utente1.setMaxLoans(3);
        //utente2 e utente2 hanno un numero massimo di prestiti pari a 3
        utente2.setMaxLoans(2);
        utente3.setMaxLoans(2);
        
        /*
        Dopo i due prestiti per ogni utente, utente1 potrà effettuare un altro
        prestito, mentre utente2 e utente3 non ne potranno effettuare altri.
        */
        
        assertEquals(1, utente1.getAvailableLoanSlots(registry));
        assertEquals(0, utente2.getAvailableLoanSlots(registry));
        assertEquals(0, utente3.getAvailableLoanSlots(registry));
        
     }
}
