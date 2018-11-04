package app.Timbres;

import Excepciones.*;
import app.Horarios.Horario;
import app.Horarios.MisHorarios;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.function.Function;


class Timbre{

    private String nombre;
    private String ip;
    private static int PUERTO = 35;
    private TCPClient cliente;

    public Timbre(String nombre, String ip){
        this.nombre = nombre;
        this.ip = ip;
    }

    public void tranferiorHorarios(MisHorarios horarios) throws EstaDesconectado, NoSeRecibioRespuesta {
        for(Horario horario : horarios){
            try {
                this.enviar(horario);
                this.cliente.esperarRespuesta();
            } catch (SocketTimeoutException socketTimeOutException){
                throw new NoSeRecibioRespuesta();
            } catch (IOException e) {
                throw new EstaDesconectado();
            }
        }
    }

    private void enviar(String msg) throws IOException{
        cliente.enviarMensaje(msg);
    }
}
