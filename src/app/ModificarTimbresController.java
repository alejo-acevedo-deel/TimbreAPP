package app;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
    private CSVPrinter csvPrinter;
    private BufferedWriter writer;

    public void initialize() throws  IOException{
        try{
            this.writer = Files.newBufferedWriter(Paths.get(this.CSV_FILE), StandardOpenOption.APPEND);
            this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        }catch (NoSuchFileException e) {
            this.writer = Files.newBufferedWriter(Paths.get(CSV_FILE));
            this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Nombre", "IP"));
        }
    }

    public void agregarTimbre(ActionEvent actionEvent) throws IOException{
        String nombre = txtNombre.getText();
        String IP = txtIP.getText();
        this.csvPrinter.printRecord(nombre,IP);
        this.csvPrinter.flush();
    }

    public void close() throws IOException{
        this.csvPrinter.flush();
    }

}
