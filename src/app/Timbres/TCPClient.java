package app.Timbres;

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
    private Thread thread;
    private Function funcion;

    TCPClient(String ip,int puerto){
        this.ip = ip;
        this.puerto = puerto;
        this.socket = new Socket();
    }

    public void conectar() throws IOException{
        this.socket.connect(new InetSocketAddress(ip, puerto), 5000);
        this.toServer = new PrintWriter(socket.getOutputStream());
        this.fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void setearFuncion(Function funcion){
        this.funcion = funcion;
    }


    public void desconectar()throws IOException{
        this.socket.close();
    }

    public void enviar(String mensaje) throws IOException{
        toServer.append(mensaje).append("\n");
        toServer.flush();
    }

    public boolean estaConectado(){
        return this.socket.isConnected();
    }

    public String esperarMensaje() throws IOException{
        return fromServer.readLine();
    }
}

