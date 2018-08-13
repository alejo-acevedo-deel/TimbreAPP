package app;

import Excepciones.IpYaExiste;
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


    public void agregarTimbre(String nombre, String ip) throws IpYaExiste{
        this.chequearIps(ip);
        Timbre timbre = new Timbre(nombre, ip);
        this.timbres.add(timbre);
        this.receptor.agregaronUnTimbre();
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
    }

    public void chequearIps(String ip) throws IpYaExiste{
        for(Timbre timbre : this.timbres){
            System.out.println(timbre.obtenerIp());
            if(timbre.obtenerIp().compareTo(ip) == 0){
                throw new IpYaExiste();
            }
        }
    }
}
