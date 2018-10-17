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

}
