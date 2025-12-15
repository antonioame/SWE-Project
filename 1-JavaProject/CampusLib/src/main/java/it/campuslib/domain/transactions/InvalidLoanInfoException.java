package it.campuslib.domain.transactions;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * @brief Eccezione lanciata quando le informazioni del prestito non sono valide.
 *
 * Crea un Alert JavaFX di tipo ERROR e lo mostra
 * Usa showAndWait() per attendere la chiusura del pop-up generato
 */
public class InvalidLoanInfoException extends Exception{

    public InvalidLoanInfoException() {
    }

    public InvalidLoanInfoException(String msg) {
        super(msg);
        try {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore: Prestito");
                alert.setHeaderText("Informazioni prestito non valide");
                alert.setContentText(msg);
                alert.showAndWait();
            });
        } catch (IllegalStateException ex) {
            // Test: non mostrare popup né stampare messaggi
        }
    }
}
