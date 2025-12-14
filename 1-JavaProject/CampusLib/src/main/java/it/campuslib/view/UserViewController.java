package it.campuslib.view;

import it.campuslib.domain.transactions.Loan;
import it.campuslib.domain.users.InvalidUserInfoException;
import it.campuslib.domain.users.InvalidUserInfoException;
import it.campuslib.domain.users.User;
import it.campuslib.domain.users.UserStatus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.SetChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.converter.DefaultStringConverter;
import it.campuslib.collections.UserRegistry;
import it.campuslib.collections.LoanRegistry;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author ecoll
 */
public class UserViewController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField idField;
    @FXML
    private TextField emailField;
    @FXML
    private Button btnAddUser;
    @FXML
    private TextField searchUserField;
    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, String> clmName;
    @FXML
    private TableColumn<User, String> clmSurname;
    @FXML
    private TableColumn<User, String> clmId;
    @FXML
    private TableColumn<User, String> clmEmail;
    @FXML
    private TableColumn<User, Integer> clmUserLoan;
    @FXML
    private TableColumn<User, Integer> clmMaxLoans;
    @FXML
    private TableColumn<User, UserStatus> clmStatus;
    
    private ObservableList<User> userList;
    private UserRegistry userRegistry;
    private ObservableList<User> allUsers;
    private ObservableList<User> filteredUsers;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userRegistry = UserRegistry.getInstance();
        allUsers = FXCollections.observableArrayList(userRegistry.getAllUsers());
        filteredUsers = allUsers;

        clmName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        clmSurname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        clmId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnrollmentID()));
        clmEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        clmUserLoan.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(
            LoanRegistry.getInstance().searchByUser(cellData.getValue()).size()
        ));
        clmMaxLoans.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getMaxLoans()));
        clmStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getStatus()));

        tableUsers.setEditable(true);
        clmName.setEditable(true);
        clmSurname.setEditable(true);

        clmName.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        clmSurname.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        tableUsers.setItems(allUsers);

        LoanRegistry lr = LoanRegistry.getInstance();
        if (lr != null && lr.getRegistry() != null) {
            lr.getRegistry().addListener((SetChangeListener<Loan>) change -> tableUsers.refresh());
        }

        searchUserField.textProperty().addListener((obs, oldV, newV) -> filterUsers());
    }    

    @FXML
    private void addUser(ActionEvent event) {
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String id = idField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || surname.isEmpty() || id.isEmpty() || email.isEmpty()) return;

        try {
            User u = new User(name, surname, id, email);
            if (userRegistry.addUser(u)) {
                allUsers.add(u);
                userRegistry.exportOnFile("personal-files/io-binary-files/users.dat");
                nameField.clear(); surnameField.clear(); idField.clear(); emailField.clear();
                filterUsers();
            }
        } catch (Exception e) {
            // ID Duplicato
            return;
        }
    }

    @FXML
    private void searchUser(ActionEvent event) {
        filterUsers();
    }

    private void filterUsers() {
        String q = searchUserField.getText().trim().toLowerCase();
        if (q.isEmpty()) {
            tableUsers.setItems(allUsers);
        } else {
            ObservableList<User> filtered = FXCollections.observableArrayList();
            for (User u : allUsers) {
                if (u.getName().toLowerCase().contains(q) || u.getSurname().toLowerCase().contains(q)
                        || u.getEnrollmentID().contains(q) || u.getEmail().toLowerCase().contains(q)) {
                    filtered.add(u);
                }
            }
            tableUsers.setItems(filtered);
        }
    }

    @FXML
    private void updateName(TableColumn.CellEditEvent<User, String> event) {
        User u = event.getRowValue();
        String newName = event.getNewValue();
        if (newName != null && !newName.trim().isEmpty()) {
            u.setName(newName);
            userRegistry.exportOnFile("personal-files/io-binary-files/users.dat");
            tableUsers.refresh();
            filterUsers();
        }
    }

    @FXML
    private void updateSurname(TableColumn.CellEditEvent<User, String> event) {
        User u = event.getRowValue();
        String newSurname = event.getNewValue();
        if (newSurname != null && !newSurname.trim().isEmpty()) {
            u.setSurname(newSurname);
            userRegistry.exportOnFile("personal-files/io-binary-files/users.dat");
            tableUsers.refresh();
            filterUsers();
        }
    }

    @FXML
    private void updateEmail(TableColumn.CellEditEvent<User, String> event) {
        User u = event.getRowValue();
        String newEmail = event.getNewValue();
        if (newEmail != null && !newEmail.trim().isEmpty()) {
            try {
                u.setEmail(newEmail);
                userRegistry.exportOnFile("personal-files/io-binary-files/users.dat");
                tableUsers.refresh();
                filterUsers();
            } catch (InvalidUserInfoException e) {
                return;
            }
        }
    }
    
}
