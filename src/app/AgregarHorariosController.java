package app;

import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;
import app.Horarios.MisHorarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AgregarHorariosController {

    private Controlador controlador;

    @FXML
    TextField txtHora;
    @FXML
    TextField txtMinutos;
    @FXML
    RadioButton radioLargo;
    @FXML
    RadioButton radioCorto;

    public AgregarHorariosController() throws IOException{
        Stage agregarHorariosStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarHorariosView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        agregarHorariosStage.setTitle("Agregar Horarios");
        agregarHorariosStage.setScene(new Scene(root));
        agregarHorariosStage.show();
        radioLargo.setSelected(true);
    }

    public void agregarHorarios(ActionEvent actionEvent) throws IOException{
        String hora = txtHora.getText();
        String minutos = txtMinutos.getText();
        boolean largo = radioLargo.isSelected();
        controlador.agregarUnHorarioUsuario(hora,minutos,largo);
    }

    public void unsetLargo(){
        radioLargo.setSelected(false);
    }

    public void unsetCorto(){
        radioCorto.setSelected(false);
    }

    public void setControlador(Controlador controlador){
        this.controlador = controlador;
    }


}
