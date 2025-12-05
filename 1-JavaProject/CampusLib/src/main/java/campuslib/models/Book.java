package campuslib.models;

import java.util.ArrayList;

import campuslib.enums.AdoptionStatus;

public class Book {
    private final String isbn;
    private String title;
    private ArrayList<Author> authors;
    private int publishingYear;
    private AdoptionStatus status;
    private int copies;
}