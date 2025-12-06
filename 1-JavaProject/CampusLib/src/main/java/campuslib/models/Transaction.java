package campuslib.models;

import java.time.LocalDate;

public class Transaction {
    private static int nextId = 1;
    private final int id;
    private final Book borrowedBook;
    private final User borrowerUser;
    private final LocalDate startDate;
}
