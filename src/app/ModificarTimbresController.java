package app;

import app.Timbres.MisTimbres;
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
    }


    public void agregarTimbre(ActionEvent actionEvent) throws IOException{
        String nombre = this.txtNombreAgregar.getText();
        String ip = this.txtIPAgregar.getText();
        this.controlador.agregarUnTimbre(nombre,ip);
    }

    public void modificarTimbre(ActionEvent actionEvent) throws IOException{
        if(this.comboTimbreModificar.getSelectionModel().getSelectedItem()==null){
            new Alerta("Seleccione un timbre antes de proseguir");
        }
        String nombre = txtNombreModificar.getText();
        String ip = txtIPModificar.getText();
        this.controlador.modificarUnTimbre(nombre, ip, this.comboTimbreModificar.getSelectionModel().getSelectedIndex());
    }

    public void timbreSeleccionadoModificar(ActionEvent actionEvent){
        if(this.comboTimbreModificar.getSelectionModel().getSelectedItem() == null){
            this.txtNombreModificar.setText("");
            this.txtIPModificar.setText("");
            return;
        }
        this.txtNombreModificar.setText(this.comboTimbreModificar.getSelectionModel().getSelectedItem().toString().split(" - ")[0]);
        this.txtIPModificar.setText(this.comboTimbreModificar.getSelectionModel().getSelectedItem().toString().split(" - ")[1]);
    }

    public void borrarTimbre(ActionEvent actionEvent) throws Exception{
        if(this.comboTimbreBorrar.getSelectionModel().getSelectedItem()==null){
            new Alerta("Seleccione un timbre antes de proseguir");
        }
        this.controlador.eliminarUnTimbre(this.comboTimbreModificar.getSelectionModel().getSelectedIndex());
    }

    public void timbreSeleccionadoBorrar(ActionEvent actionEvent){
        if(this.comboTimbreBorrar.getSelectionModel().getSelectedItem()==null){
            this.lblNombreBorrar.setText("");
            this.lblIPBorrar.setText("");
            return;
        }
        this.lblNombreBorrar.setText(this.comboTimbreBorrar.getSelectionModel().getSelectedItem().toString().split(" - ")[0]);
        this.lblIPBorrar.setText(this.comboTimbreBorrar.getSelectionModel().getSelectedItem().toString().split(" - ")[1]);
    }


    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void actualizarComboBox(MisTimbres misTimbres){
        comboTimbreModificar.getItems().clear();
        comboTimbreModificar.getItems().addAll(misTimbres);
        comboTimbreBorrar.getItems().clear();
        comboTimbreBorrar.getItems().addAll(misTimbres);
    }


}
