package app;

import Excepciones.IpYaExiste;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;


public class ModificarTimbresController {

    @FXML
    TextField txtNombreAgregar;
    @FXML
    TextField txtIPAgregar;
    @FXML
    TextField txtNombreModificar;
    @FXML
    TextField txtIPModificar;
    @FXML
    ComboBox<Timbre> comboTimbreModificar;

    private static final String CSV_FILE = "Timbres.csv";
    private MisTimbres misTimbres;
    private Stage modificarTimbresStage;
    private Timbre timbreModificar;

    public ModificarTimbresController(MisTimbres misTimbres) throws IOException{
        this.modificarTimbresStage = new Stage();
        this.misTimbres = misTimbres;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarTimbresView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        modificarTimbresStage.setTitle("Modificar Timbres");
        modificarTimbresStage.setScene(new Scene(root));
        modificarTimbresStage.show();
        this.actualizarComboBox();
    }


    public void agregarTimbre(ActionEvent actionEvent) throws IOException{
        String nombre = txtNombreAgregar.getText();
        String IP = txtIPAgregar.getText();
        if(txtIPAgregar.getText().isEmpty() || txtNombreAgregar.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Timbre vacio");
            alert.setContentText("No se pueden agregar sin nombre o IP");
            alert.showAndWait();
            return;
        }
        try {
            this.misTimbres.agregarTimbre(nombre, IP);
            txtNombreAgregar.setText("");
            txtIPAgregar.setText("");
        }catch (IpYaExiste e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Timbre ya existe");
            alert.setContentText("No se pueden agregar dos timbres con la misma IP, borre el otro o modifiquelo ");
            alert.showAndWait();
        }
        this.actualizarComboBox();
    }

    public void modificarTimbre(ActionEvent actionEvent) throws IOException{
        String nombre = txtNombreModificar.getText();
        String IP = txtIPModificar.getText();
        if(txtIPModificar.getText().isEmpty() || txtNombreModificar.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Timbre vacio");
            alert.setContentText("No se pueden agregar sin nombre o IP");
            alert.showAndWait();
            return;
        }
        try {
            this.misTimbres.chequearIps(IP);
            this.timbreModificar.setearIp(IP);
            this.timbreModificar.setearNombre(nombre);
        }catch (IpYaExiste e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Timbre ya existe");
            alert.setContentText("No se pueden agregar dos timbres con la misma IP, borre el otro o modifiquelo ");
            alert.showAndWait();
        }
        this.actualizarComboBox();
    }

    public void timbreSeleccionadoModificar(){
        this.timbreModificar = comboTimbreModificar.getSelectionModel().getSelectedItem();
        if(this.timbreModificar != null){
            txtNombreModificar.setText(this.timbreModificar.obtenerNombre());
            txtIPModificar.setText(this.timbreModificar.obtenerIp());
        }else{
            txtNombreModificar.setText("");
        }
    }

    private void actualizarComboBox(){
        comboTimbreModificar.getItems().clear();
        comboTimbreModificar.getItems().addAll(this.misTimbres.obtenerTimbres());
    }

}
