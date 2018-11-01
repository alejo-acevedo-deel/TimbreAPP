package app;

import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;
import app.Horarios.MisHorarios;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.xml.soap.Text;
import java.io.IOException;

public class AgregarHorariosController {

    @FXML
    private TextField txtHora;
    @FXML
    private TextField txtMinutos;
    @FXML
    private RadioButton radioCorto;
    @FXML
    private RadioButton radioLargo;

    private MisHorarios horarios;

    public AgregarHorariosController(MisHorarios horarios) throws IOException{
        Stage agregarHorariosStage = new Stage();
        agregarHorariosStage.setAlwaysOnTop(true);
        this.loadView(agregarHorariosStage);
        this.horarios = horarios;
        this.radioLargo.setSelected(true);
    }

    public void agregarHorario(ActionEvent event) {
        this.horarios.agregarHorario(this.txtHora.getText(), this.txtMinutos.getText(), this.radioLargo.isSelected());
    }

    public void unsetCorto(ActionEvent event){
        this.radioCorto.setSelected(false);
    }

    public void unsetLargo(ActionEvent actionEvent){
        this.radioLargo.setSelected(false);
    }

    private void loadView(Stage stage)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("agregarHorariosView.fxml"));
        fxmlLoader.setController(this);
        stage.setTitle("Agregar horario");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setResizable(false);
        stage.show();

    }

}
