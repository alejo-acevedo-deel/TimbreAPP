package app;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    TextField txtNombre;
    @FXML
    TextField txtIP;

    private static final String CSV_FILE = "Timbres.csv";
    private MisTimbres misTimbres;

    public ModificarTimbresController(MisTimbres misTimbres, Stage modificarTimbresStage) throws IOException{
        this.misTimbres = misTimbres;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarTimbresView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        modificarTimbresStage.setTitle("Modificar Timbres");
        modificarTimbresStage.setScene(new Scene(root));
        modificarTimbresStage.show();
    }

    public void initialize() throws  IOException{
    }

    public void agregarTimbre(ActionEvent actionEvent) throws IOException{
        String nombre = txtNombre.getText();
        String IP = txtIP.getText();
        this.misTimbres.agregarTimbre(nombre, IP);
    }
}
