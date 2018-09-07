package app.Timbres;

import Excepciones.*;
import app.CSV;

import java.io.IOException;
import java.util.*;

public class MisTimbres extends LinkedList<Timbre>{

    private static final String CSV_FILE = "Timbres.csv";
    private static final String[] CSV_HEADER = {"Nombre", "IP"};
    private CSV csv;

    private Timbre timbreSeleccionado;

    public MisTimbres(){
        try {
            this.csv = new CSV(CSV_FILE, CSV_HEADER);
            this.cargarDesdeCSV();
        }catch (IOException ioException){

        }
    }


    public void agregarTimbre(String nombre, String ip) throws IpYaExiste, FaltaNombre, FaltaIP{
        this.chequearIps(ip);
        Timbre timbre = new Timbre(nombre, ip);
        super.add(timbre);
    }

    public void seleccionarTimbre(int indice) throws NoSeConecto {
        if(timbreSeleccionado != null){
            try {
                timbreSeleccionado.desconectar();
            }catch (EstaDesconectado estaDesconectado){

            }
        }
        timbreSeleccionado = this.get(indice);
        timbreSeleccionado.conectar();
    }

    private void cargarDesdeCSV(){
        LinkedList<HashMap<String, String>> timbres;
        super.clear();
        try {
            timbres = csv.leerTodoElCSV();
            for(HashMap<String, String> timbre: timbres){
                this.agregarTimbre(timbre.get("Nombre"), timbre.get("IP"));
            }
        }catch (IOException ioException){

        }catch (FaltaNombre faltaNombre){

        }catch (FaltaIP faltaIP){

        }catch (IpYaExiste ipYaExiste){

        }
    }

    private void chequearIps(String ip) throws IpYaExiste{
        for(Timbre timbre : this){
            System.out.println(timbre.obtenerIp());
            if(timbre.obtenerIp().compareTo(ip) == 0){
                throw new IpYaExiste();
            }
        }
    }

}
