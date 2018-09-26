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
        HashMap<String, String> aux = new HashMap<>();
        this.chequearIps(ip);
        Timbre timbre = new Timbre(nombre, ip);
        aux.put("Nombre", nombre);
        aux.put("IP" , ip);
        super.add(timbre);
        try {
            csv.agregarAlCSV(aux);
        } catch (InformacionDifiereDeHeaderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void agregarTimbreCSV(String nombre, String ip) throws IpYaExiste, FaltaNombre, FaltaIP{
        Timbre timbre = new Timbre(nombre, ip);
        super.add(timbre);
    }

    public void modificarTimbre(String nombre, String ip, int indice) throws IpYaExiste, FaltaNombre, FaltaIP{
        HashMap<String, String> aux1 = new HashMap<>();
        HashMap<String, String> aux2 = new HashMap<>();
        aux1.put("Nombre", super.get(indice).obtenerNombre());
        aux1.put("IP" , super.get(indice).obtenerIp());
        aux2.put("Nombre", nombre);
        aux2.put("IP" , ip);
        super.get(indice).setearNombre(nombre);
        super.get(indice).setearIp(ip);
        try {
            csv.modificarDelCSV(aux1, aux2);
        } catch (InformacionDifiereDeHeaderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarTimbre(int indice){
        HashMap<String, String> aux = new HashMap<>();
        aux.put("Nombre", super.get(indice).obtenerNombre());
        aux.put("IP" , super.get(indice).obtenerIp());
        super.remove(indice);
        try {
            csv.borrarDelCSV(aux);
        } catch (InformacionDifiereDeHeaderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void agregarHorario(String horario)throws EstaDesconectado, NoHayTimbreSeleccionado{
        try {
            this.timbreSeleccionado.agregarHorario(horario);
        }catch (NullPointerException e){
            throw new NoHayTimbreSeleccionado();
        }
    }

    private void cargarDesdeCSV(){
        LinkedList<HashMap<String, String>> timbres;
        super.clear();
        try {
            timbres = csv.leerTodoElCSV();
            for(HashMap<String, String> timbre: timbres){
                this.agregarTimbreCSV(timbre.get("Nombre"), timbre.get("IP"));
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

    public void obtenerUnHorario() throws EstaDesconectado, NoHayTimbreSeleccionado {
        try {
            this.timbreSeleccionado.obtenerHorario();
        }catch (NullPointerException e){
            throw new NoHayTimbreSeleccionado();
        }
    }

    public void borrarUnHorario(int indice) throws NoHayTimbreSeleccionado {
        try {
            this.timbreSeleccionado.borrarUnHorario(indice);
        }catch (NullPointerException e){
            throw new NoHayTimbreSeleccionado();
        } catch (EstaDesconectado estaDesconectado) {
            estaDesconectado.printStackTrace();
        }
    }
}
