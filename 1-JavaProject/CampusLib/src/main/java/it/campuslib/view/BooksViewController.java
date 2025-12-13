/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.campuslib.view;

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
public class BooksViewController implements Initializable {

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
    private TableView<?> tableBooks;
    @FXML
    private TableColumn<?, ?> clmIsbn;
    @FXML
    private TableColumn<?, ?> clmTitle;
    @FXML
    private TableColumn<?, ?> clmAuthors;
    @FXML
    private TableColumn<?, ?> clmYear;
    @FXML
    private TableColumn<?, ?> clmCopies;
    @FXML
    private TableColumn<?, ?> clmStatus;

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
    private void updateTitle(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updateAuthors(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updatYear(TableColumn.CellEditEvent<S, T> event) {
    }
    
}
