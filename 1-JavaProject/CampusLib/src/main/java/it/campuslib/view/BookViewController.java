/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.campuslib.view;

import it.campuslib.domain.catalog.AdoptionStatus;
import it.campuslib.domain.catalog.Book;
import it.campuslib.collections.BookCatalog;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author ecoll
 */
public class BookViewController implements Initializable {

    private BookCatalog bookCatalog;
    private ObservableList<Book> allBooks;
    private ObservableList<Book> filteredBooks;

    @FXML
    private TextField isbnField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorsField;
    @FXML
    private TextField yearField;
    @FXML
    private Button btnAddBook;
    @FXML
    private TableView<Book> tableBooks;
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<Book, String> clmIsbn;
    @FXML
    private TableColumn<Book, String> clmTitle;
    @FXML
    private TableColumn<Book, String> clmAuthors;
    @FXML
    private TableColumn<Book, Integer> clmYear;
    @FXML
    private TableColumn<Book, Integer> clmCopies;
    @FXML
    private TableColumn<Book, AdoptionStatus> clmStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookCatalog = BookCatalog.getInstance();
        
        clmIsbn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIsbn()));
        clmTitle.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        clmAuthors.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAuthors()));
        clmYear.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPublishingYear()));
        clmCopies.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCopies()));
        clmStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getStatus()));
        
        tableBooks.setEditable(true);
        clmTitle.setEditable(true);
        clmAuthors.setEditable(true);
        clmYear.setEditable(true);
        clmStatus.setEditable(true);
        
        clmTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        clmAuthors.setCellFactory(TextFieldTableCell.forTableColumn());
        clmYear.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clmStatus.setCellFactory(ComboBoxTableCell.forTableColumn(AdoptionStatus.values()));
        
        allBooks = bookCatalog.getAllBooks();
        tableBooks.setItems(allBooks);
        filteredBooks = allBooks;
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterBooks();
        });
        
        UnaryOperator<TextFormatter.Change> isbnFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,13}")) {
                return change;
            }
            return null;
        };
        isbnField.setTextFormatter(new TextFormatter<>(isbnFilter));
    }
    
    private void filterBooks() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            tableBooks.setItems(allBooks);
        } else {
            ObservableList<Book> filtered = FXCollections.observableArrayList();
            for (Book book : allBooks) {
                if (book.getTitle().toLowerCase().contains(query) || 
                    book.getAuthors().toLowerCase().contains(query) || 
                    book.getIsbn().contains(query)) {
                    filtered.add(book);
                }
            }
            tableBooks.setItems(filtered);
        }
    }

    @FXML
    private void addBook(ActionEvent event) {
        try {
            String isbn = isbnField.getText().trim();
            String title = titleField.getText().trim();
            String authors = authorsField.getText().trim();
            String yearText = yearField.getText().trim();
            
            if (isbn.isEmpty() || title.isEmpty() || authors.isEmpty() || yearText.isEmpty()) {
                return;
            }
            
            int year = Integer.parseInt(yearText);
            int copies = 1;
            
            Book newBook = new Book(isbn, title, authors, year, copies);
            if (bookCatalog.addBook(newBook)) {
                filterBooks();
                isbnField.clear();
                titleField.clear();
                authorsField.clear();
                yearField.clear();

                bookCatalog.exportOnFile("personal-files/io-binary-files/books.dat");
            } else {
                // TODO: Gestire errore
            }
        } catch (NumberFormatException e) {
            // TODO: Gestire eccezione
        } catch (Exception e) {
            // TODO: Gestire eccezione
        }
    }

    @FXML
    private void updateTitle(TableColumn.CellEditEvent<Book, String> event) {
        Book book = event.getRowValue();
        String newTitle = event.getNewValue();
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            book.setTitle(newTitle);
            filterBooks();
            bookCatalog.exportOnFile("personal-files/io-binary-files/books.dat");
            tableBooks.refresh();
        }
    }

    @FXML
    private void updateAuthors(TableColumn.CellEditEvent<Book, String> event) {
        Book book = event.getRowValue();
        String newAuthors = event.getNewValue();
        if (newAuthors != null) {
            book.setAuthors(newAuthors);
            filterBooks();
            bookCatalog.exportOnFile("personal-files/io-binary-files/books.dat");
            tableBooks.refresh();
        }
    }

    @FXML
    private void updatYear(TableColumn.CellEditEvent<Book, Integer> event) {
        Book book = event.getRowValue();
        Integer newYear = event.getNewValue();
        if (newYear != null && newYear > 0) {
            book.setPublishingYear(newYear);
            filterBooks();
            bookCatalog.exportOnFile("personal-files/io-binary-files/books.dat");
            tableBooks.refresh();
        }
    }

    @FXML
    private void updateStatus(TableColumn.CellEditEvent<Book, AdoptionStatus> event) {
        Book book = event.getRowValue();
        AdoptionStatus newStatus = event.getNewValue();
        if (newStatus != null) {
            book.setStatus(newStatus);
            filterBooks();
            bookCatalog.exportOnFile("personal-files/io-binary-files/books.dat");
            tableBooks.refresh();
        }
    }
}
