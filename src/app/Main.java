package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private MainController mainController;
    private Controlador controlador;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainController = new MainController(primaryStage);
        this.controlador = new ControladorPrincipal();
        this.mainController.setControlador(this.controlador);
        this.controlador.setMainController(this.mainController);

    }
}
