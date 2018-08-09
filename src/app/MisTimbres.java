package app;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MisTimbres {

    private static final String CSV_FILE = "Timbres.csv";

    private List<Timbre> timbres = new ArrayList<Timbre>();

    Receptores receptor;

    public MisTimbres(Receptores receptor){
        this.receptor = receptor;
        this.cargarDesdeCSV();
    }


    public void agregarTimbre(String nombre, String ip){
        Timbre timbre = new Timbre(nombre, ip);
        this.timbres.add(timbre);
        receptor.agregaronUnTimbre();
    }

    private void agregarTimbreSil(String nombre, String ip){
        Timbre timbre = new Timbre(nombre, ip);
        this.timbres.add(timbre);
    }

    public List<Timbre> obtenerTimbres() {
        return timbres;
    }


    public ListIterator<Timbre> iterator(){
        return timbres.listIterator();
    }

    private void cargarDesdeCSV(){
        this.timbres.clear();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Nombre","IP").withIgnoreHeaderCase().withSkipHeaderRecord().withTrim());
            for(CSVRecord csvRecord : csvParser){
                String nombre = csvRecord.get("Nombre");
                String ip = csvRecord.get("IP");
                this.agregarTimbreSil(nombre,ip);
            }
        }catch (IOException e){
        }
    }
}
