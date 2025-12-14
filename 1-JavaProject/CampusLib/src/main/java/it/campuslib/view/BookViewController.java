/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.campuslib.view;

import it.campuslib.domain.catalog.AdoptionStatus;
import it.campuslib.domain.catalog.Book;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ecoll
 */
public class BookViewController implements Initializable {

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
    private Button btnStatus;
    @FXML
    private TableView<Book> tableBooks;
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
        // TODO
    }    

    @FXML
    private void addBook(ActionEvent event) {
    }

    @FXML
    private void updateBookStatus(ActionEvent event) {
    }

    @FXML
    private void updateTitle(TableColumn.CellEditEvent<Book, String> event) {
    }

    @FXML
    private void updateAuthors(TableColumn.CellEditEvent<Book, String> event) {
    }

    @FXML
    private void updatYear(TableColumn.CellEditEvent<Book, Integer> event) {
    }
    
}
