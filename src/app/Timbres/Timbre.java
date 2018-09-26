package app.Timbres;

import Excepciones.EstaDesconectado;
import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;
import Excepciones.NoSeConecto;
import app.Controlador;

import java.io.IOException;
import java.util.function.Function;


class Timbre{

    private String nombre;
    private String ip;
    private static int PUERTO = 35;
    private TCPClient cliente;
    private Controlador controlador;

    Timbre(String nombre, String ip) throws FaltaNombre, FaltaIP{
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

    public void agregarHorario(String horario)throws EstaDesconectado{
        String mensaje = "A+/"+horario;
        this.cliente.setearFuncion(new Function() {
            @Override
            public Object apply(Object o) {
                controlador.horarioEnviado((String) o);
                return null;
            }
        });
        this.enviar(mensaje);
    }

    public void obtenerHorario()throws EstaDesconectado{
        this.cliente.setearFuncion(new Function() {
            @Override
            public Object apply(Object o) {
                controlador.recibirHorario((String) o);
                return null;
            }
        });
        this.enviar("A?/");
    }

    public boolean estaConectado(){
        return this.cliente.estaConectado();
    }

    public void setearControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public String getName(){
        return this.nombre;
    }

    public String toString(){
        return this.obtenerNombre() + " - " + this.obtenerIp();
    }

    public String joiner(){
        return this.nombre + "," + this.ip;
    }

    public void borrarUnHorario(int indice)throws EstaDesconectado {
        String mensaje = "A-/" + ((char) indice);
        this.cliente.setearFuncion(new Function() {
            @Override
            public Object apply(Object o) {
                controlador.horarioBorrado((String) o);
                return null;
            }
        });
        this.enviar(mensaje);
    }
}
