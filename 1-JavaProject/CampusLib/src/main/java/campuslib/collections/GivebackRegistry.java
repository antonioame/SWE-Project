package campuslib.collections;

import campuslib.models.Giveback;
import java.util.ArrayList;

/**
 * @brief Il registro delle restituzioni della biblioteca.
 */
public class GivebackRegistry {
    private ArrayList<Giveback> registry;

    /**
     * @brief Costruttore.
     * @post
     * Il registro restituzioni è vuoto.
     */
    public GivebackRegistry() {
    }

    /**
     * @brief Aggiunge una restituzione al registro.
     * @param[in] restitution Restituzione da aggiungere.
     * @return Esito aggiunta.
     */
    public boolean addGiveback(Giveback restitution) {
        return false;
    }

    /**
     * @brief Restituisce una rappresentazione testuale del registro.
     * @return Rappresentazione testuale del registro.
     */
    public String toString() {
        return null;
    }

    /**
     * @brief Esporta il registro su file.
     * @param[in] fileName Nome del file da scrivere.
     * @post
     * Il registro viene salvato sul file specificato.
     */
    public void exportOnFile(String fileName) {
    }

    /**
     * @brief Importa un registro da file.
     * @param[in] fileName Nome del file da cui importare.
     * @return Registro importato o null se il file non è valido.
     */
    public static GivebackRegistry importFromFile(String fileName) {
        return null;
    }
}
