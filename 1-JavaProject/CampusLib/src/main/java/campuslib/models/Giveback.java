package campuslib.models;

import java.time.LocalDate;

public class Giveback {
    private final int id;
    private final Book borrowedBook;
    private final User borrowerUser;
    private final LocalDate startDate;
    private final LocalDate endDate;
}