package app;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MisHorarios {

    private static final String CSV_FILE = "Horarios.csv";

    private List<Horario> horarios = new ArrayList<Horario>();
    private Receptores receptor;

    public MisHorarios(Receptores receptor){
        this.receptor = receptor;
        this.cargarDesdeCSV();
    }

    public void agregarHorario(String hora, String minutos, boolean largo){
        Horario horario = new Horario(hora, minutos, largo);
        this.horarios.add(horario);
        receptor.agregaronUnHorario();
    }

    private void agregarHorarioSil(String hora, String minutos, boolean largo){
        Horario horario = new Horario(hora, minutos, largo);
        this.horarios.add(horario);
    }

    public List<Horario> obtenerHorarios() {
        return horarios;
    }


    public ListIterator<Horario> iterator(){
        return horarios.listIterator();
    }

    private void cargarDesdeCSV(){
        this.horarios.clear();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Hora","Minutos","Largo").withIgnoreHeaderCase().withSkipHeaderRecord().withTrim());
            for(CSVRecord csvRecord : csvParser){
                String hora = csvRecord.get("Hora");
                String minutos = csvRecord.get("Minutos");
                boolean largo = csvRecord.get("Largo").equals("true");
                this.agregarHorarioSil(hora,minutos,largo);
            }
        }catch (IOException e){
        }
    }

    public void borrarHorario(List<Horario> horarios) {
        this.horarios.removeAll(horarios);
    }

    public void settearReceptor(Receptores receptor){
        this.receptor = receptor;
    }

}