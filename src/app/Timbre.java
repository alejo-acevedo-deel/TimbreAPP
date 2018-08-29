package app;

import Excepciones.EstaDesconectado;
import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;
import Excepciones.NoSeConecto;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;


public class Timbre{

    private String nombre;
    private String ip;
    private static int PUERTO = 4000;
    private TCPClient cliente;

    public Timbre(String nombre, String ip) throws FaltaNombre, FaltaIP{
        this.setearNombre(nombre);
        this.setearIp(ip);
        this.cliente = new TCPClient(ip, PUERTO);
    }

    public void setearNombre(String nombre) throws FaltaNombre{
        if(nombre.isEmpty()){
            throw new FaltaNombre();
        }
        this.nombre = nombre;
    }

    public void setearIp(String ip)throws FaltaIP{
        if(ip.isEmpty()){
            throw new FaltaIP();
        }
        this.ip = ip;
    }

    public String obtenerIp() {
        return ip;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public void conectar()throws NoSeConecto{
        try {
            this.cliente.conectar();
            this.cliente.start();
        } catch (IOException e) {
            throw new NoSeConecto();
        }
    }

    public void desconectar()throws EstaDesconectado{
        try {
            this.cliente.desconectar();
        } catch (IOException e) {
            throw  new EstaDesconectado();
        }
    }

    public void enviar(String mensaje)throws EstaDesconectado{
        try {
            this.cliente.enviar(mensaje);
        } catch (IOException e) {
            throw new EstaDesconectado();
        }
    }

    public void agregarHorario(Horario horario)throws EstaDesconectado{
        String mensaje = "A+/"+horario.obtenerHora()+":"+horario.obtenerMinutos()+":"+horario.obtenerLargo();
        enviar(mensaje);
    }

    public boolean estaConectado(){
        return this.cliente.estaConectado();
    }

    public void setearReceptor(Receptores receptor) {
        this.cliente.setearReceptor(receptor);
    }

    public String getName(){
        return this.nombre;
    }

    public String toString(){
        return this.obtenerNombre();
    }

    public String joiner(){
        return this.nombre + "," + this.ip;
    }

}
