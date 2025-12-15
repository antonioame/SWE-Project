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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import it.campuslib.domain.catalog.InvalidPublicationYearException;
import it.campuslib.domain.catalog.InvalidBookInfoException;
import it.campuslib.collections.LoanRegistry;
import javafx.scene.control.Alert;

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
    private TextField copiesField;
    @FXML
    private Button btnAddBook;
    @FXML
    private TableView<Book> tableBooks;
    @FXML
    private TextField searchField;
    @FXML
    private CheckBox showNotAdoptedCheckbox;
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
        clmCopies.setEditable(true);
        clmStatus.setEditable(true);
        
        clmTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        clmAuthors.setCellFactory(TextFieldTableCell.forTableColumn());
        clmYear.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clmCopies.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clmCopies.setOnEditCommit(event -> updateCopies(event));
        clmStatus.setCellFactory(ComboBoxTableCell.forTableColumn(AdoptionStatus.values()));
        
        allBooks = bookCatalog.getAllBooks();
        filteredBooks = allBooks;
        tableBooks.setItems(allBooks);
        if (showNotAdoptedCheckbox != null) {
            showNotAdoptedCheckbox.selectedProperty().addListener((obs, oldV, newV) -> filterBooks());
        }
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterBooks());
        
        UnaryOperator<TextFormatter.Change> isbnFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,13}")) {
                return change;
            }
            return null;
        };
        isbnField.setTextFormatter(new TextFormatter<>(isbnFilter));
        if (copiesField != null) {
            UnaryOperator<TextFormatter.Change> copiesFilter = change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d{0,6}")) {
                    return change;
                }
                return null;
            };
            copiesField.setTextFormatter(new TextFormatter<>(copiesFilter));
        }
    }
    
    private void filterBooks() {
        String query = searchField.getText().trim().toLowerCase();
        ObservableList<Book> source = allBooks;
        ObservableList<Book> filtered = FXCollections.observableArrayList();
        boolean showNotAdopted = showNotAdoptedCheckbox == null ? true : showNotAdoptedCheckbox.isSelected();
        for (Book book : source) {
            if (!showNotAdopted && book.getStatus() == AdoptionStatus.NOT_ADOPTED) continue;
            if (query.isEmpty() || book.getTitle().toLowerCase().contains(query) || 
                    book.getAuthors().toLowerCase().contains(query) || 
                    book.getIsbn().contains(query)) {
                filtered.add(book);
            }
        }
        tableBooks.setItems(filtered);
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
            if (copiesField != null) {
                String copiesText = copiesField.getText().trim();
                if (copiesText.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore: Copie");
                    alert.setHeaderText("Numero copie non valido");
                    alert.setContentText("Inserire un numero di copie maggiore o uguale a 1.");
                    alert.showAndWait();
                    return;
                }
                try {
                    copies = Integer.parseInt(copiesText);
                    if (copies < 1) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore: Copie");
                        alert.setHeaderText("Numero copie non valido");
                        alert.setContentText("Il numero di copie deve essere almeno 1.");
                        alert.showAndWait();
                        return;
                    }
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore: Copie");
                    alert.setHeaderText("Numero copie non valido");
                    alert.setContentText("Inserire un numero intero valido per le copie.");
                    alert.showAndWait();
                    return;
                }
            }
            
            Book newBook = new Book(isbn, title, authors, year, copies);
            if (bookCatalog.addBook(newBook)) {
                filterBooks();
                isbnField.clear();
                titleField.clear();
                authorsField.clear();
                yearField.clear();
                if (copiesField != null) copiesField.clear();

                bookCatalog.exportOnFile("personal-files/io-binary-files/books.dat");
            } else {
                throw new InvalidBookInfoException("ISBN già presente nel catalogo: " + isbn);
            }
        } catch (NumberFormatException e) {
            return;
        } catch (InvalidBookInfoException e) {
            // L'eccezione mostrerà il pop-up all'utente tramite il suo costruttore
            return;
        } catch (Exception e) {
            return;
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
    private void updateCopies(TableColumn.CellEditEvent<Book, Integer> event) {
        Book book = event.getRowValue();
        Integer newCopies = event.getNewValue();
        // Salva il valore precedente per ripristinarlo in caso di input non valido
        int oldCopies = book.getCopies();
        if (newCopies == null) {
            tableBooks.refresh();
            return;
        }

        // Calcola il numero di prestiti attivi associati a questo libro
        int activeLoans = LoanRegistry.getInstance().searchByBook(book.getIsbn()).size();

        // Se il nuovo valore non è valido, mostra popup e ripristina il valore originale
        if (newCopies <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore: Numero di Copie");
            alert.setHeaderText("Numero Copie Non Valido");
            alert.setContentText("Non è possibile inserire un numero di copie totali minore o uguale a zero. Se il libro non è più disponibile in biblioteca, modifica il suo stato di adozione.");
            alert.showAndWait();
            book.setCopies(oldCopies);
            tableBooks.refresh();
            return;
        }

        if (newCopies < activeLoans) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erore: Copie Insufficienti");
            alert.setHeaderText("Numero Copie Totali Minore del Numero di Copie Attualmente in Prestito");
            alert.setContentText("Non è possibile inserire un numero totale di copie minore del numero di copie attualmente prese in prestito. La modifica verrà annullata.");
            alert.showAndWait();
            book.setCopies(oldCopies);
            tableBooks.refresh();
            return;
        }

        // Valido: applica la modifica
        book.setCopies(newCopies);
        filterBooks();
        bookCatalog.exportOnFile("personal-files/io-binary-files/books.dat");
        tableBooks.refresh();
    }

    @FXML
    private void updateYear(TableColumn.CellEditEvent<Book, Integer> event) {
        Book book = event.getRowValue();
        Integer newYear = event.getNewValue();
        if (newYear != null && newYear > 0) {
            try {
                book.setPublishingYear(newYear);
                filterBooks();
                bookCatalog.exportOnFile("personal-files/io-binary-files/books.dat");
            } catch (InvalidPublicationYearException ex) {
                // L'eccezione genererà pop-up
            } finally {
                tableBooks.refresh();
            }
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
