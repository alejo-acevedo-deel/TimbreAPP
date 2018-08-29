package Test;

import app.TCPClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TCPClientTest {

    String IP = "localhost";
    int PUERTO = 4000;

    @Test
    public void conectionTest()throws IOException{
        TCPClient cliente = new TCPClient(IP, PUERTO);
        cliente.conectar();
        cliente.enviar("HOLA");
        cliente.desconectar();
    }

}
