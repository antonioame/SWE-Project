package it.campuslib.domain.catalog;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * @brief Eccezione lanciata quando i dati del libro non sono validi.
 *
 * Crea un Alert JavaFX di tipo ERROR e lo mostra
 * Usa showAndWait() per attendere la chiusura del pop-up generato
 */
public class InvalidBookInfoException extends Exception {

    public InvalidBookInfoException() {
    }
    
    public InvalidBookInfoException(String msg) {
        super(msg);
        try {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore: Dati libro");
                alert.setHeaderText("Dati libro non validi");
                alert.setContentText(msg);
                alert.showAndWait();
            });
        } catch (IllegalStateException ex) {
            // Test: non mostrare popup né stampare messaggi
        }
    }
}

