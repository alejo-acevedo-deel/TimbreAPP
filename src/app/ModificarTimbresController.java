package app;

import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;
import Excepciones.IpYaExiste;
import app.Timbres.MisTimbres;
import app.Timbres.Timbre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class ModificarTimbresController {

    private Controlador controlador;

    @FXML
    private TextField txtNombreAgregar;
    @FXML
    private TextField txtIPAgregar;
    @FXML
    private TextField txtNombreModificar;
    @FXML
    private TextField txtIPModificar;
    @FXML
    private ComboBox comboTimbreModificar;
    @FXML
    private ComboBox comboTimbreBorrar;
    @FXML
    private Label lblNombreBorrar;
    @FXML
    private Label lblIPBorrar;


    public ModificarTimbresController() throws IOException{
        Stage modificarTimbresStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarTimbresView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        modificarTimbresStage.setTitle("Modificar Timbres");
        modificarTimbresStage.setScene(new Scene(root));
        modificarTimbresStage.show();
        this.actualizarComboBox();
    }


    public void agregarTimbre(ActionEvent actionEvent) throws IOException{
        String nombre = this.txtNombreAgregar.getText();
        String ip = this.txtIPAgregar.getText();
        this.controlador.agregarUnTimbre(nombre,ip);
    }

    public void modificarTimbre(ActionEvent actionEvent) throws IOException{
        String nombre = txtNombreModificar.getText();
        String IP = txtIPModificar.getText();
        try {
            this.misTimbres.chequearIps(IP);
            this.timbreModificar.setearIp(IP);
            this.timbreModificar.setearNombre(nombre);
        }catch (IpYaExiste ipYaExiste){
            new Alerta(ipYaExiste);
        }catch (FaltaIP faltaIP){
            new Alerta(faltaIP);
        }catch (FaltaNombre faltaNombre){
            new Alerta(faltaNombre);
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

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private void actualizarComboBox(){
        comboTimbreModificar.getItems().clear();
        comboTimbreModificar.getItems().addAll(this.misTimbres.obtenerTimbres());
        comboTimbreBorrar.getItems().clear();
        comboTimbreBorrar.getItems().addAll(this.misTimbres.obtenerTimbres());
    }


}
