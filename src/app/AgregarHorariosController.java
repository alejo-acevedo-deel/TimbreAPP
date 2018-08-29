package app;

import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AgregarHorariosController {

    private MisHorarios misHorarios;

    @FXML
    TextField txtHora;
    @FXML
    TextField txtMinutos;
    @FXML
    RadioButton radioLargo;
    @FXML
    RadioButton radioCorto;


    private static final String CSV_FILE = "Horarios.csv";

    public AgregarHorariosController(MisHorarios misHorarios) throws IOException{
        Stage agregarHorariosStage = new Stage();
        this.misHorarios = misHorarios;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarHorariosView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        agregarHorariosStage.setTitle("Agregar Horarios");
        agregarHorariosStage.setScene(new Scene(root));
        agregarHorariosStage.show();
    }

    public void agregarHorarios(ActionEvent actionEvent) throws IOException{
        String hora = txtHora.getText();
        String minutos = txtMinutos.getText();
        boolean largo = radioLargo.isSelected();
        try {
            misHorarios.agregarHorario(hora, minutos, largo);
        }catch (FormatoHoraErroneo formatoHoraErroneo){
            new Alerta(formatoHoraErroneo);
        }catch (FormatoMinutoErroneo formatoMinutoErroneo){
            new Alerta(formatoMinutoErroneo);
        }
    }

    public void unsetLargo(){
        radioLargo.setSelected(false);
    }

    public void unsetCorto(){
        radioCorto.setSelected(false);
    }


}
