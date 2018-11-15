package app.Timbres;

import Excepciones.*;
import app.CSV;
import app.Horarios.MisHorarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.*;

public class MisTimbres extends LinkedList<Timbre>{

    private static final String CSV_FILE = "Timbres.csv";
    private static final String[] CSV_HEADER = {"Nombre", "IP"};
    private CSV csv;
    private ObservableList view = FXCollections.observableArrayList();
    private Timbre timbreSeleccionado;
    private MisHorarios misHorarios = new MisHorarios();

    public MisTimbres(){
        super();
        LinkedList<HashMap<String, String>> timbresGuardados;
        try {
            this.csv = new CSV(CSV_FILE, CSV_HEADER);
            timbresGuardados = this.csv.leerTodoElCSV();
            for(HashMap<String, String> timbre : timbresGuardados){
                this.agregarTimbreAlInicio(timbre.get(CSV_HEADER[0]), timbre.get(CSV_HEADER[1]));
            }
        } catch (FormatoIpErroneo formatoIpErroneo) {
            formatoIpErroneo.printStackTrace();
        } catch (FaltaNombre faltaNombre) {
            faltaNombre.printStackTrace();
        } catch (FaltaIP faltaIP) {
            faltaIP.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList getView(){
        return this.view;
    }

    public MisHorarios getMisHorarios(){ return this.misHorarios;}

    public void seleccionarTimbre(int indice) throws FormatoMinutoErroneo, NoSeRecibioRespuesta, FormatoHoraErroneo, EstaDesconectado, NoSeConecto, NoHayTimbreSeleccionado {
        this.misHorarios.borrarHorariosTodos();
        if(indice<0){return;}
        super.get(indice).conectar();
        this.obtenerHorarios();
        this.timbreSeleccionado = super.get(indice);
    }

    public void tranferirHorarios(LinkedList horarios) throws EstaDesconectado, EnvioDeHorariosTruncado, FormatoHoraErroneo, NoSeRecibioRespuesta, FormatoMinutoErroneo, NoHayTimbreSeleccionado {
        if(this.timbreSeleccionado == null){
            throw new NoHayTimbreSeleccionado();
        }
        this.timbreSeleccionado.tranferiorHorarios(horarios);
        this.obtenerHorarios();
    }

    public void obtenerHorarios() throws EstaDesconectado, NoSeRecibioRespuesta, FormatoMinutoErroneo, FormatoHoraErroneo, NoHayTimbreSeleccionado {
        if(this.timbreSeleccionado == null){
            throw new NoHayTimbreSeleccionado();
        }
        this.misHorarios.borrarHorariosTodos();
        this.misHorarios.agregarHorariosDesdeMsg(this.timbreSeleccionado.obtenerHorarios());
    }

    public void agregarTimbre(String nombre, String ip) throws FaltaIP, FaltaNombre, FormatoIpErroneo {
        this.agregarTimbreAlInicio(nombre, ip);
        this.guardarTimbre(nombre, ip);
    }

    public String obtenerNombreDel(int indice){
        if(indice<0){return "";}
        return super.get(indice).getNombre();
    }

    public String obtenerIpDel(int indice){
        if(indice<0){return "";}
        return super.get(indice).getIp();
    }

    public void modificarNombreIpDel(int indice, String nombre, String ip) throws FaltaNombre, FaltaIP, FormatoIpErroneo {
        if(indice<0){return;}
        HashMap timbreModificar = new HashMap();
        timbreModificar.put(CSV_HEADER[0],super.get(indice).getNombre());
        timbreModificar.put(CSV_HEADER[1],super.get(indice).getIp());
        super.get(indice).configurarNombre(nombre);
        super.get(indice).configurarIP(ip);
        this.view.set(indice, super.get(indice));
        HashMap timbreModificado = new HashMap();
        timbreModificado.put(CSV_HEADER[0],super.get(indice).getNombre());
        timbreModificado.put(CSV_HEADER[1],super.get(indice).getIp());
        try {
            this.csv.modificarDelCSV(timbreModificar, timbreModificado);
        } catch (InformacionDifiereDeHeaderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modificarNombreDel(int indice, String nombre) throws FaltaNombre {
        if(indice<0){return;}
        super.get(indice).configurarNombre(nombre);
        this.view.set(indice, super.get(indice));

    }

    public void modificarIpDel(int indice, String ip) throws FaltaIP, FormatoIpErroneo {
        if(indice<0){return;}
        super.get(indice).configurarIP(ip);
        this.view.set(indice, super.get(indice));
    }


    public void borrar (int indice){
        if(indice<0){return;}
        HashMap timbreBorrar = new HashMap();
        timbreBorrar.put(CSV_HEADER[0],super.get(indice).getNombre());
        timbreBorrar.put(CSV_HEADER[1],super.get(indice).getIp());
        this.view.remove(indice);
        super.remove(indice);
        try {
            this.csv.borrarDelCSV(timbreBorrar);
        } catch (InformacionDifiereDeHeaderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void borrarHorariosSeleccionados() throws IOException, EstaDesconectado, FormatoMinutoErroneo, NoSeRecibioRespuesta, FormatoHoraErroneo, NoHayTimbreSeleccionado {
        Iterator indicesIteratos = this.misHorarios.obtenerIndicesSeleccionados().descendingIterator();
        for(Integer i : this.misHorarios.obtenerIndicesSeleccionados()){
            this.timbreSeleccionado.borrarHorario(i);
        }
        this.obtenerHorarios();
    }

    public void borrarHorariosTodos() throws IOException, EstaDesconectado, FormatoMinutoErroneo, NoSeRecibioRespuesta, FormatoHoraErroneo, NoHayTimbreSeleccionado {
        for(int i = 0; i < this.misHorarios.size(); i++){
            this.timbreSeleccionado.borrarHorario(0);
        }
        this.obtenerHorarios();
    }

    private void agregarTimbreAlInicio(String nombre, String ip) throws FaltaIP, FaltaNombre, FormatoIpErroneo {
        Timbre aux = new Timbre(nombre, ip);
        super.add(aux);
        this.view.add(aux);
    }

    private void guardarTimbre(String nombre, String ip){
        HashMap nuevoTimbre = new HashMap();
        nuevoTimbre.put(CSV_HEADER[0], nombre);
        nuevoTimbre.put(CSV_HEADER[1], ip);
        try {
            this.csv.agregarAlCSV(nuevoTimbre);
        } catch (InformacionDifiereDeHeaderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ObservableList obtenerEstados() throws IOException, EstaDesconectado {
        ObservableList estados = FXCollections.observableArrayList();
        for(Timbre timbre : this){
            estados.add(timbre.obtenerEstado());
        }
        return estados;
    }

}
