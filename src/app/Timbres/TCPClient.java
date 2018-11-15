package app.Timbres;

import org.omg.CORBA.TIMEOUT;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Callable;
import java.util.function.Function;

class TCPClient extends Thread{
    private String ip;
    private int puerto;
    private SocketAddress direccion;
    private Socket socket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private static int TIMEOUT = 5000;
    private static int SO_TIMEOUT = 5000;

    TCPClient(String ip,int puerto){
        this.ip = ip;
        this.puerto = puerto;
        this.socket = new Socket();
    }

    public void conectar() throws IOException{
        this.socket.connect(new InetSocketAddress(ip, puerto), TIMEOUT);
        this.toServer = new PrintWriter(socket.getOutputStream());
        this.fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket.setSoTimeout(SO_TIMEOUT);
    }


    public void desconectar()throws IOException{
        this.socket.close();
    }

    public void enviarMensaje(String mensaje) throws IOException{
        toServer.append(mensaje).append("\n");
        toServer.flush();
    }

    public boolean estaConectado(){
        return this.socket.isConnected();
    }

    public String esperarRespuesta() throws IOException{
        String respuesta = fromServer.readLine();
        System.out.println(respuesta);
        return respuesta;
    }
}

