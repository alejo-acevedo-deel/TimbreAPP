package app.Timbres;

import Excepciones.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;

class Timbre {

    static String[] dias = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado" } ;

    private String nombre = "";
    private String ip = "";
    private String titulo = "";
    private static int PUERTO = 35;
    private TCPClient cliente;
    private Estado estado;


    public Timbre(String nombre, String ip) throws FaltaNombre, FaltaIP, FormatoIpErroneo {
        this.configurarNombre(nombre);
        this.configurarIP(ip);
        this.titulo = this.nombre + " - " + this.ip + "";
    }

    public void configurarNombre(String nombre) throws FaltaNombre {
        if(nombre.isEmpty()){ throw new FaltaNombre();}
        this.nombre = nombre;
        this.titulo = this.nombre + " - " + this.ip + "";
    }

    public void configurarIP(String ip) throws FaltaIP, FormatoIpErroneo {
        if(ip.isEmpty()){ throw new FaltaIP();}
        if(ip.chars().filter(ch -> ch =='.').count() != 3){ throw new FormatoIpErroneo();}
        this.ip = ip;
        this.titulo = this.nombre + " - " + this.ip + "";
    }

    public void tranferiorHorarios(LinkedList<String> horariosMsg) throws EstaDesconectado, EnvioDeHorariosTruncado {
        int enviados = 0;
        for(String horario : horariosMsg){
            try {
                this.enviar("A+/" + horario);
                this.cliente.esperarRespuesta();
                enviados++;
            } catch (SocketTimeoutException socketTimeOutException){
                throw new EnvioDeHorariosTruncado(enviados);
            } catch (IOException e) {
                throw new EstaDesconectado(this.nombre);
            }
        }
    }

    public LinkedList<String> obtenerHorarios() throws EstaDesconectado, NoSeRecibioRespuesta {
        LinkedList<String> horarios = new LinkedList<>();
        String respuesta = "";
        while(!respuesta.equals("FIN")){
            try {
                this.enviar("A?/");
                respuesta = this.cliente.esperarRespuesta();
                horarios.add(respuesta);
            } catch (SocketTimeoutException socketTimeoutException){
                throw new NoSeRecibioRespuesta();
            } catch (IOException e) {
                throw new EstaDesconectado(this.nombre);
            }
        }
        return horarios;
    }

    public void conectar() throws NoSeConecto {
        this.cliente = new TCPClient(this.ip, this.PUERTO);
        try {
            this.cliente.conectar();
        } catch (IOException e) {
            throw new NoSeConecto(this.nombre);
        }
    }

    public String toString(){
        return this.titulo;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getIp(){
        return this.ip;
    }


    public void borrarHorario(Integer indice) throws IOException, EstaDesconectado {
        this.enviar("A-/"+String.valueOf(indice));
        this.cliente.esperarRespuesta();
    }

    public Estado obtenerEstado() throws IOException, EstaDesconectado {
        this.estado = new Estado();
        this.estado.setNombre(this.nombre);
        this.estado.setIp(this.ip);
        try {
            this.conectar();
            this.estado.setSeConecto();
            this.obtenetHora();
            this.obtenerDuracion();
            this.obtenerVacaciones();
            this.obtenerDiasLibres();
        }catch (NoSeConecto noSeConecto){
        }
        return this.estado;
    }

    private void obtenerDuracion() throws IOException, EstaDesconectado {
        String duracion;
        this.enviar("D?/");
        duracion = this.cliente.esperarRespuesta();
        this.estado.setDuracionLarga(duracion.split("/")[0] + " Segundos");
        this.estado.setDuracionCorta(duracion.split("/")[1] + " Segundos");
    }

    private void obtenetHora() throws IOException, EstaDesconectado {
        String[] hora;
        this.enviar("H?/");
        hora = this.cliente.esperarRespuesta().split(":");
        if(hora[0].length()<2){hora[0] = "0"+hora[0];}
        if(hora[1].length()<2){hora[1] = "0"+hora[1];}
        this.estado.setHora(hora[0] + " : " + hora[1] + " " + this.dias[Integer.valueOf(hora[2])-1]);
    }

    private void obtenerVacaciones() throws IOException, EstaDesconectado {
        this.enviar("V?/");
        String vacaciones = this.cliente.esperarRespuesta();
        if(vacaciones.equals("Off")){
            this.estado.setVacaciones("Desactivado");
        }else{
            this.estado.setVacaciones("Activado");
        }
    }

    private void obtenerDiasLibres() throws IOException, EstaDesconectado {
        String diasLibres = "";
        String[] diasLibresArray;
        this.enviar("L?/");
        diasLibresArray = this.cliente.esperarRespuesta().split("");
        try {
            for(String dia : diasLibresArray){
                diasLibres = diasLibres + " " + this.dias[Integer.valueOf(dia)-1];
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        this.estado.setDiasLibres(diasLibres);
    }

    private void enviar(String msg) throws IOException, EstaDesconectado {
        try {
            cliente.enviarMensaje(msg);
        }catch (NullPointerException e){
            throw new EstaDesconectado(this.nombre);
        }
    }
}
