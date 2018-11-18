package app;

import javafx.scene.control.Alert;

public class Mensaje {

    public Mensaje(String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Se ha realizado la accion con exito");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
