package Test;

import app.Timbres.TCPClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TCPClientTest {

    final String IP = "localhost";
    final int PUERTO = 4000;

    @Test
    public void conectionTest()throws IOException{
        TCPClient cliente = new TCPClient(IP, PUERTO);
        cliente.conectar();
        cliente.enviar("HOLA");
        System.out.println(3);
        //cliente.desconectar();
        System.out.println(4);
    }

}
