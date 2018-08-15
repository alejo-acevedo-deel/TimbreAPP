package app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPClient extends Thread{
    private String ip;
    private int puerto;
    private SocketAddress direccion;
    private Socket socket;
    private DataOutputStream toServer;
    private BufferedReader fromServer;
    private Thread thread;
    public Receptores receptor;

    public TCPClient(String ip,int puerto){
        this.ip = ip;
        this.puerto = puerto;
        this.socket = new Socket();
    }

    public void conectar() throws IOException{
        this.socket.connect(new InetSocketAddress(ip, puerto));
        this.toServer = new DataOutputStream(socket.getOutputStream());
        this.fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void setearReceptor(Receptores receptor){
        this.receptor = receptor;
    }


    public void desconectar()throws IOException{
        this.thread.destroy();
        this.socket.close();
    }

    public void enviar(String mensaje) throws IOException{
        toServer.writeUTF(mensaje);
    }

    public boolean estaConectado(){
        return this.socket.isConnected();
    }


    @Override
    public void run() {
        String mensaje;
        System.out.println("Listener Corriendo");
        do{
            try {
                mensaje = fromServer.readLine();
                receptor.llegoUnMensaje(mensaje);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }while(true);
    }
}

