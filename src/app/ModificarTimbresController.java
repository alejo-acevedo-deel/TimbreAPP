package app;

import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;
import Excepciones.FormatoIpErroneo;
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

    @FXML
    private TextField txtNombreAgregar;
    @FXML
    private TextField txtIPAgregar;
    @FXML
    private ComboBox comboTimbreModificar;
    @FXML
    private TextField txtNombreModificar;
    @FXML
    private TextField txtIPModificar;
    @FXML
    private ComboBox comboTimbreBorrar;
    @FXML
    private Label lblNombreBorrar;
    @FXML
    private Label lblIPBorrar;


    private MisTimbres misTimbres;

    public ModificarTimbresController(MisTimbres misTimbres)throws IOException{
        this.misTimbres = misTimbres;
        this.loadView(new Stage());
        this.comboTimbreModificar.setItems(this.misTimbres.getView());
        this.comboTimbreBorrar.setItems(this.misTimbres.getView());
    }

    public void agregarTimbre(ActionEvent actionEvent){
        try {
            this.misTimbres.agregarTimbre(this.txtNombreAgregar.getText(), this.txtIPAgregar.getText());
        } catch (FaltaIP faltaIP) {
            new Alerta(faltaIP);
        } catch (FaltaNombre faltaNombre) {
            new Alerta(faltaNombre);
        } catch (FormatoIpErroneo formatoIpErroneo) {
            new Alerta(formatoIpErroneo);
        }
    }

    public void timbreSeleccionadoModificar(ActionEvent actionEvent){
        this.txtNombreModificar.setText(this.misTimbres.obtenerNombreDel(this.comboTimbreModificar.getSelectionModel().getSelectedIndex()));
        this.txtIPModificar.setText(this.misTimbres.obtenerIpDel(this.comboTimbreModificar.getSelectionModel().getSelectedIndex()));
    }

    public void timbreSeleccionadoBorrar(ActionEvent actionEvent){
        this.lblNombreBorrar.setText(this.misTimbres.obtenerNombreDel(this.comboTimbreBorrar.getSelectionModel().getSelectedIndex()));
        this.lblIPBorrar.setText(this.misTimbres.obtenerIpDel(this.comboTimbreBorrar.getSelectionModel().getSelectedIndex()));
    }

    public void modificarTimbre(ActionEvent actionEvent){
        int indice = this.comboTimbreModificar.getSelectionModel().getSelectedIndex();
        try {
            this.misTimbres.modificarNombreIpDel(indice, this.txtNombreModificar.getText(), this.txtIPModificar.getText());
            this.comboTimbreModificar.getSelectionModel().clearAndSelect(indice);
        } catch (FaltaNombre faltaNombre) {
            new Alerta(faltaNombre);
        } catch (FaltaIP faltaIP) {
            new Alerta(faltaIP);
        } catch (FormatoIpErroneo formatoIpErroneo) {
            new Alerta(formatoIpErroneo);
        }
    }

    public void borrarTimbre(ActionEvent actionEvent){
        this.misTimbres.borrar(this.comboTimbreBorrar.getSelectionModel().getSelectedIndex());
    }



    private void loadView(Stage stage)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarTimbresView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        stage.setTitle("Modificar Timbres");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

}
