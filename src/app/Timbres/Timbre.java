package app.Timbres;

import Excepciones.EstaDesconectado;
import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;
import Excepciones.NoSeConecto;

import java.io.IOException;
import java.util.function.Function;


class Timbre{

    private String nombre;
    private String ip;
    private static int PUERTO = 35;
    private TCPClient cliente;
    private Receptores receptor;

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
                receptor.seEnvioUnaAlarma((String) o );
                return null;
            }
        });
        this.enviar(mensaje);
    }

    public void obtenerHorario()throws EstaDesconectado{
        this.cliente.setearFuncion(new Function() {
            @Override
            public Object apply(Object o) {
                receptor.seRecibioUnaAlarma((String) o);
                return null;
            }
        });
        this.enviar("A?/");
    }

    public boolean estaConectado(){
        return this.cliente.estaConectado();
    }

    public void setearReceptor(Receptores receptor) {
        this.receptor = receptor;
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
