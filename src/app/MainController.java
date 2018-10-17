package app;

import app.Horarios.MisHorarios;
import app.Timbres.MisTimbres;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController{

    @FXML
    private ListView horariosUsuariosView;

    public MainController(Stage primaryStage)throws IOException {
        this.loadView(primaryStage);
    }

    private void loadView(Stage stage)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        stage.setTitle("Controlador de Timbres");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
