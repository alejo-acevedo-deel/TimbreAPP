package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AgregarHorariosController {

    @FXML
    TextField txtHora;
    @FXML
    TextField txtMinutos;
    @FXML
    RadioButton radioLargo;
    @FXML
    RadioButton radioCorto;


    private static final String CSV_FILE = "Horarios.csv";
    private CSVPrinter csvPrinter;
    private BufferedWriter writer;

    public void initialize() throws IOException {
        try{
            this.writer = Files.newBufferedWriter(Paths.get(this.CSV_FILE), StandardOpenOption.APPEND);
            this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        }catch (NoSuchFileException e) {
            this.writer = Files.newBufferedWriter(Paths.get(CSV_FILE));
            this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Hora", "Minutos","Largo"));
        }
    }

    public void agregarHorarios(ActionEvent actionEvent) throws IOException{
        String hora = txtHora.getText();
        String minutos = txtMinutos.getText();
        boolean largo = radioLargo.isSelected();
        this.csvPrinter.printRecord(hora,minutos, largo);
        this.csvPrinter.flush();
    }

    public void close() throws IOException{
        this.csvPrinter.flush();
    }

    public void unsetLargo(){
        radioLargo.setSelected(false);
    }

    public void unsetCorto(){
        radioCorto.setSelected(false);
    }


}
