package app;

import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;
import Excepciones.IpYaExiste;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MisTimbres {

    private static final String CSV_FILE = "Timbres.csv";
    private static final String[] CSV_HEADER = {"Nombre", "IP"};

    private List<Timbre> timbres = new ArrayList<Timbre>();
    private CSV csv;

    Receptores receptor;

    public MisTimbres(Receptores receptor){
        this.receptor = receptor;
        try {
            this.csv = new CSV(CSV_FILE, CSV_HEADER);
            this.cargarDesdeCSV();
        }catch (IOException ioException){

        }

    }


    public void agregarTimbre(String nombre, String ip) throws IpYaExiste, FaltaNombre, FaltaIP{
        this.chequearIps(ip);
        Timbre timbre = new Timbre(nombre, ip);
        this.timbres.add(timbre);
        this.receptor.agregaronUnTimbre();
    }

    private void agregarTimbreSil(String nombre, String ip)throws FaltaNombre, FaltaIP{
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
        LinkedList<HashMap<String, String>> timbres;
        this.timbres.clear();
        try {
            timbres = csv.leerTodoElCSV();
            for(HashMap<String, String> timbre: timbres){
                this.agregarTimbreSil(timbre.get("Nombre"), timbre.get("IP"));
            }
        }catch (IOException ioException){

        }catch (FaltaNombre faltaNombre){

        }catch (FaltaIP faltaIP){

        }
    }

    public void chequearIps(String ip) throws IpYaExiste{
        for(Timbre timbre : this.timbres){
            System.out.println(timbre.obtenerIp());
            if(timbre.obtenerIp().compareTo(ip) == 0){
                throw new IpYaExiste();
            }
        }
    }

    public void borrarTimbre(Timbre timbre){
        this.timbres.remove(timbre);
    }
}
