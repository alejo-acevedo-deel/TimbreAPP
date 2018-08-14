package app;

import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;

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

    public void conectar()throws IOException{
        cliente = new TCPClient(this.ip, this.PUERTO);
        cliente.start();
    }

    public void desconectar()throws IOException {
        this.cliente.desconectar();

    }
    public void enviar(String mensaje)throws IOException{
        this.cliente.enviar(mensaje);
    }

    public void setearReceptor(MainController receptor) {
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
