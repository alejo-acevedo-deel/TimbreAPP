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
