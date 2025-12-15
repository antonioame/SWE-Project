package it.campuslib.domain.users;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * @brief Eccezione lanciata quando le informazioni dell'utente non sono valide.
 *
 * Crea un Alert JavaFX di tipo ERROR e lo mostra
 * Usa showAndWait() per attendere la chiusura del pop-up generato
 */
public class InvalidUserInfoException extends Exception{
    public InvalidUserInfoException() {
    }

    public InvalidUserInfoException(String msg) {
        super(msg);
        try {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore: Dati utente");
                alert.setHeaderText("Informazioni utente non valide");
                alert.setContentText(msg);
                alert.showAndWait();
            });
        } catch (IllegalStateException ex) {
            System.err.println("InvalidUserInfoException: " + msg);
        }
    }
}
