package app.Timbres;

import Excepciones.*;
import app.CSV;
import app.Horarios.MisHorarios;
import app.UDPServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.*;

public class MisTimbres extends LinkedList<Timbre>{

    private static final String CSV_FILE = "Timbres.csv";
    private static final String[] CSV_HEADER = {"Nombre", "IP", "ID"};
    private static final String NONE_ID = "-";

    private CSV csv;
    private ObservableList view = FXCollections.observableArrayList();
    private Timbre timbreSeleccionado;
    private MisHorarios misHorarios = new MisHorarios();
    private HashMap<String, Timbre> timbresEncontrados = new HashMap();
    private UDPServer udpServer;

    public MisTimbres(){
        super();
        LinkedList<HashMap<String, String>> timbresGuardados;
        try {
            this.csv = new CSV(CSV_FILE, CSV_HEADER);
            timbresGuardados = this.csv.leerTodoElCSV();
            for(HashMap<String, String> timbre : timbresGuardados){
                this.agregarTimbreAlInicio(timbre.get(CSV_HEADER[0]), timbre.get(CSV_HEADER[1]), timbre.get(CSV_HEADER[2]));
                if(!NONE_ID.equals(timbre.get(CSV_HEADER[2]))){this.timbresEncontrados.put(timbre.get(CSV_HEADER[2]), this.get(this.size() - 1 ));}
            }
        } catch (FormatoIpErroneo formatoIpErroneo) {
            formatoIpErroneo.printStackTrace();
        } catch (FaltaNombre faltaNombre) {
            faltaNombre.printStackTrace();
        } catch (FaltaIP faltaIP) {
            faltaIP.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IpYaExiste ipYaExiste) {
            ipYaExiste.printStackTrace();
        }
        this.udpServer = new UDPServer(this);
        this.udpServer.setDaemon(true);
        this.udpServer.start();
    }

    public ObservableList getView(){
        return this.view;
    }

    public MisHorarios getMisHorarios(){ return this.misHorarios;}

    public void seleccionarTimbre(int indice) throws FormatoMinutoErroneo, NoSeRecibioRespuesta, FormatoHoraErroneo, EstaDesconectado, NoSeConecto, NoHayTimbreSeleccionado {
        if(this.timbreSeleccionado != null){
            this.timbreSeleccionado.desconectar();
        }
        this.misHorarios.borrarHorariosTodos();
        if(indice<0){
            this.timbreSeleccionado = null;
            return;
        }
        super.get(indice).conectar();
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

    public void agregarTimbre(String nombre, String ip) throws FaltaIP, FaltaNombre, FormatoIpErroneo, IpYaExiste {
        this.agregarTimbreAlInicio(nombre, ip, this.NONE_ID);
        this.guardarTimbre(nombre, ip, this.NONE_ID);
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
        timbreModificar.put(CSV_HEADER[2],super.get(indice).getId());
        super.get(indice).configurarNombre(nombre);
        super.get(indice).configurarIP(ip);
        this.view.set(indice, super.get(indice));
        HashMap timbreModificado = new HashMap();
        timbreModificado.put(CSV_HEADER[0],super.get(indice).getNombre());
        timbreModificado.put(CSV_HEADER[1],super.get(indice).getIp());
        timbreModificado.put(CSV_HEADER[2],super.get(indice).getId());
        try {
            this.csv.modificarDelCSV(timbreModificar, timbreModificado);
        } catch (InformacionDifiereDeHeaderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void borrar (int indice){
        if(indice<0){return;}
        HashMap timbreBorrar = new HashMap();
        timbreBorrar.put(CSV_HEADER[0],super.get(indice).getNombre());
        timbreBorrar.put(CSV_HEADER[1],super.get(indice).getIp());
        timbreBorrar.put(CSV_HEADER[2], super.get(indice).getId());
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

    public ObservableList obtenerEstados() throws IOException, EstaDesconectado {
        ObservableList estados = FXCollections.observableArrayList();
        for(Timbre timbre : this){
            estados.add(timbre.obtenerEstado());
        }
        return estados;
    }

    private void agregarTimbreAlInicio(String nombre, String ip, String id) throws FaltaIP, FaltaNombre, FormatoIpErroneo, IpYaExiste {
        for(Timbre viejos : this){
            if(ip.equals(viejos.getIp())){
                throw new IpYaExiste();
            }
        }
        Timbre aux = new Timbre(nombre, ip, id);
        super.add(aux);
        this.view.add(aux);
    }

    private void guardarTimbre(String nombre, String ip, String id){
        HashMap nuevoTimbre = new HashMap();
        nuevoTimbre.put(CSV_HEADER[0], nombre);
        nuevoTimbre.put(CSV_HEADER[1], ip);
        nuevoTimbre.put(CSV_HEADER[2], id);
        try {
            this.csv.agregarAlCSV(nuevoTimbre);
        } catch (InformacionDifiereDeHeaderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Exception> configurarHoraAutomaticamete() throws IOException, EstaDesconectado {
        LinkedList<Exception> exceptions = new LinkedList<>();
        try{
            timbreSeleccionado.configurarHoraAutomaticamente();
        }catch (NullPointerException e) {
            for (Timbre timbre : this) {
                try {
                    timbre.conectar();
                    timbre.configurarHoraAutomaticamente();
                    timbre.desconectar();
                }catch (NoSeConecto noSeConecto){
                    exceptions.add(noSeConecto);
                }
            }
        }
        return exceptions;
    }

    public LinkedList<Exception> configurarHoraManualmente(String hora, String minutos, int dia) throws FormatoMinutoErroneo, FormatoHoraErroneo, EstaDesconectado, IOException, FaltaDiaDeSemana {
        LinkedList<Exception> exceptions = new LinkedList<>();
        try {
            timbreSeleccionado.configurarHoraManualmente(hora, minutos, dia);
        }catch (NullPointerException e){
            for(Timbre timbre : this){
                try{
                    timbre.conectar();
                    timbre.configurarHoraManualmente(hora, minutos, dia);
                    timbre.desconectar();
                }catch (NoSeConecto noSeConecto){
                    exceptions.add(noSeConecto);
                }
            }
        }
        return exceptions;
    }

    public LinkedList<Exception> configurarDuracion(String larga, String corta) throws FormatoDeDuracionErroneo, IOException, EstaDesconectado {
        LinkedList<Exception> exceptions = new LinkedList<>();
        try {
            timbreSeleccionado.configurarDuracion(larga, corta);
        }catch (NullPointerException e){
            for(Timbre timbre : this){
                try{
                    timbre.conectar();
                    timbre.configurarDuracion(larga, corta);
                    timbre.desconectar();
                }catch (NoSeConecto noSeConecto){
                    exceptions.add(noSeConecto);
                }
            }
        }
        return exceptions;
    }

    public LinkedList<Exception> configurarLibres(LinkedList<RadioButton> radioDias) throws NoSeConecto, IOException, EstaDesconectado {
        LinkedList<Exception> exceptions = new LinkedList<>();
        try {
            timbreSeleccionado.configurarLibres(radioDias);
        }catch (NullPointerException e){
            for(Timbre timbre : this){
                try {
                    timbre.conectar();
                    timbre.configurarLibres(radioDias);
                    timbre.desconectar();
                }catch (NoSeConecto noSeConecto){
                    exceptions.add(noSeConecto);
                }
            }
        }
        return exceptions;
    }

    public LinkedList<Exception> activarVacaciones() throws IOException, EstaDesconectado, NoSeConecto {
        LinkedList<Exception> exceptions = new LinkedList<>();
        try {
            timbreSeleccionado.activarVacaciones();
        }catch (NullPointerException e){
            for(Timbre timbre : this){
                try {
                    timbre.conectar();
                    timbre.activarVacaciones();
                    timbre.desconectar();
                }catch (NoSeConecto noSeConecto){
                    exceptions.add(noSeConecto);
                }
            }
        }
        return exceptions;
    }

    public LinkedList<Exception> desactivarVacaciones() throws IOException, EstaDesconectado, NoSeConecto {
        LinkedList<Exception> exceptions = new LinkedList<>();
        try {
            timbreSeleccionado.desactivarVacaciones();
        }catch (NullPointerException e){
            for(Timbre timbre : this){
                try {
                    timbre.conectar();
                    timbre.desactivarVacaciones();
                    timbre.desconectar();
                }catch (NoSeConecto noSeConecto){
                    exceptions.add(noSeConecto);
                }
            }
        }
        return exceptions;
    }

    public void silenciar() throws IOException, EstaDesconectado, FormatoMinutoErroneo, NoSeRecibioRespuesta, FormatoHoraErroneo, NoHayTimbreSeleccionado {
        Iterator indicesIteratos = this.misHorarios.obtenerIndicesSeleccionados().descendingIterator();
        for(Integer i : this.misHorarios.obtenerIndicesSeleccionados()){
            this.timbreSeleccionado.silenciar(i);
        }
        this.obtenerHorarios();
    }

    public void desilenciar() throws IOException, EstaDesconectado, FormatoMinutoErroneo, NoSeRecibioRespuesta, FormatoHoraErroneo, NoHayTimbreSeleccionado {
        Iterator indicesIteratos = this.misHorarios.obtenerIndicesSeleccionados().descendingIterator();
        for(Integer i : this.misHorarios.obtenerIndicesSeleccionados()){
            this.timbreSeleccionado.desilenciar(i);
        }
        this.obtenerHorarios();
    }

    public void silenciarTodos() throws IOException, EstaDesconectado, FormatoMinutoErroneo, NoSeRecibioRespuesta, FormatoHoraErroneo, NoHayTimbreSeleccionado {
        for(int i = 0; i < this.misHorarios.size(); i++){
            this.timbreSeleccionado.silenciar(0);
        }
        this.obtenerHorarios();
    }

    public void sePublicoTimbre(String id, String ip){
        if(timbresEncontrados.containsKey(id)){
            try {
                timbresEncontrados.get(id).configurarIP(ip);
                return;
            } catch (FaltaIP | FormatoIpErroneo faltaIP) {
            }
        }
        try {
            this.agregarTimbreAlInicio("Timbre " + String.valueOf(this.size()), ip, id);
        } catch (FaltaIP | FaltaNombre | FormatoIpErroneo | IpYaExiste faltaIP) {
        }
        this.timbresEncontrados.put(id, this.get(this.size() - 1));
        this.guardarTimbre("Timbre " + String.valueOf(this.size()), ip, id);
    }
}
