package app;

import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;
import Excepciones.IpYaExiste;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    @FXML
    ComboBox<Timbre> comboTimbreBorrar;
    @FXML
    Label lblNombreBorrar;
    @FXML
    Label lblIPBorrar;

    private static final String CSV_FILE = "Timbres.csv";
    private MisTimbres misTimbres;
    private Stage modificarTimbresStage;
    private Timbre timbreModificar;
    private Timbre timbreBorrar;

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
        try {
            this.misTimbres.agregarTimbre(nombre, IP);
            txtNombreAgregar.setText("");
            txtIPAgregar.setText("");
        }catch (IpYaExiste e){
            this.mostrarAlerta(e);
        }catch (FaltaIP m){
            this.mostrarAlerta(m);
        }catch (FaltaNombre n) {
            this.mostrarAlerta(n);
        }
        this.actualizarComboBox();
    }

    public void modificarTimbre(ActionEvent actionEvent) throws IOException{
        String nombre = txtNombreModificar.getText();
        String IP = txtIPModificar.getText();
        try {
            this.misTimbres.chequearIps(IP);
            this.timbreModificar.setearIp(IP);
            this.timbreModificar.setearNombre(nombre);
        }catch (IpYaExiste e){
            this.mostrarAlerta(e);
        }catch (FaltaIP m){
            mostrarAlerta(m);
        }catch (FaltaNombre n){
            mostrarAlerta(n);
        }
        this.actualizarComboBox();
    }

    public void borrarTimbre(ActionEvent actionEvent) throws Exception{
        this.misTimbres.borrarTimbre(this.timbreBorrar);
        this.actualizarComboBox();
    }

    public void timbreSeleccionadoModificar(){
        this.timbreModificar = comboTimbreModificar.getSelectionModel().getSelectedItem();
        if(this.timbreModificar != null){
            txtNombreModificar.setText(this.timbreModificar.obtenerNombre());
            txtIPModificar.setText(this.timbreModificar.obtenerIp());
        }else{
            txtNombreModificar.setText("");
            txtIPModificar.setText("");
        }
    }

    public void timbreSeleccionadoBorrar(){
        this.timbreBorrar = comboTimbreBorrar.getSelectionModel().getSelectedItem();
        if(this.timbreBorrar != null){
            lblNombreBorrar.setText(this.timbreBorrar.obtenerNombre());
            lblIPBorrar.setText(this.timbreBorrar.obtenerIp());

        }else{
            lblNombreBorrar.setText("");
            lblIPBorrar.setText("");
        }
    }

    private void actualizarComboBox(){
        comboTimbreModificar.getItems().clear();
        comboTimbreModificar.getItems().addAll(this.misTimbres.obtenerTimbres());
        comboTimbreBorrar.getItems().clear();
        comboTimbreBorrar.getItems().addAll(this.misTimbres.obtenerTimbres());
    }


    private void mostrarAlerta(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
