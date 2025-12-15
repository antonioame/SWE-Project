package it.campuslib.domain.catalog;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * @brief Eccezione lanciata se l'anno di pubblicazione > anno corrente.
 *
 * Crea un Alert JavaFX di tipo ERROR e lo mostra
 * Usa showAndWait() per attendere la chiusura del pop-up generato
 */
public class InvalidPublicationYearException extends RuntimeException {
    
    public InvalidPublicationYearException() {
        super();
    }
    
    public InvalidPublicationYearException(String msg) {
        super(msg);
        try {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore: Anno di pubblicazione");
                alert.setHeaderText("Anno di pubblicazione non valido");
                alert.setContentText(msg);
                alert.showAndWait();
            });
        } catch (IllegalStateException ex) {
            System.err.println("InvalidPublicationYearException: " + msg);
        }
    }
}
