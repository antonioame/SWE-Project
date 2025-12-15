package it.campuslib.view;

import it.campuslib.collections.GivebackRegistry;
import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.transactions.Giveback;
import it.campuslib.domain.users.User;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GivebackViewController implements Initializable {

    @FXML
    private TextField returnDateField;
    @FXML
    private Button btnAddGb;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Giveback> tableGiveback;
    @FXML
    private TableColumn<Giveback, Integer> clmIdGb;
    @FXML
    private TableColumn<Giveback, String> clmBookGb;
    @FXML
    private TableColumn<Giveback, String> clmUserGb;
    @FXML
    private TableColumn<Giveback, LocalDate> clmStartGb;
    @FXML
    private TableColumn<Giveback, LocalDate> clmReturnGb;

    private ObservableList<Giveback> gbList;
    private ObservableList<Giveback> allGb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GivebackRegistry gReg = GivebackRegistry.getInstance();
        gbList = gReg.getRegistry();
        allGb = gbList;
        tableGiveback.setItems(allGb);

        clmIdGb.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        clmBookGb.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getBorrowedBook().getIsbn() + " - " + cellData.getValue().getBorrowedBook().getTitle()));
        clmUserGb.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getBorrowerUser().getEnrollmentID() + " - " +
                cellData.getValue().getBorrowerUser().getSurname() + " " + cellData.getValue().getBorrowerUser().getName()));
        clmStartGb.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getStartDate()));
        clmReturnGb.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEndDate()));
        if (searchField != null) {
            searchField.textProperty().addListener((obs, oldV, newV) -> filterGivebacks());
        }
    }

    private void filterGivebacks() {
        String q = searchField.getText().trim().toLowerCase();
        if (q.isEmpty()) {
            tableGiveback.setItems(allGb);
            return;
        }

        ObservableList<Giveback> filtered = FXCollections.observableArrayList();
        for (Giveback g : allGb) {
            if (g == null) continue;
            boolean match = false;
            if (String.valueOf(g.getId()).contains(q)) match = true;
            Book b = g.getBorrowedBook();
            if (!match && b != null) {
                if (b.getIsbn() != null && b.getIsbn().toLowerCase().contains(q)) match = true;
                if (!match && b.getTitle() != null && b.getTitle().toLowerCase().contains(q)) match = true;
            }
            User u = g.getBorrowerUser();
            if (!match && u != null) {
                if (u.getEnrollmentID() != null && u.getEnrollmentID().toLowerCase().contains(q)) match = true;
                if (!match && u.getName() != null && u.getName().toLowerCase().contains(q)) match = true;
                if (!match && u.getSurname() != null && u.getSurname().toLowerCase().contains(q)) match = true;
            }
            LocalDate sd = g.getStartDate();
            LocalDate ed = g.getEndDate();
            if (!match && sd != null && sd.toString().contains(q)) match = true;
            if (!match && ed != null && ed.toString().contains(q)) match = true;

            if (match) filtered.add(g);
        }
        tableGiveback.setItems(filtered);
    }

    @FXML
    private void addGiveBack(ActionEvent event) {
    }
}
