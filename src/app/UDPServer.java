package app;

import app.Timbres.MisTimbres;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer extends Thread {

    MisTimbres misTimbres;
    DatagramSocket socket;

    public UDPServer(MisTimbres misTimbres) {
        this.misTimbres = misTimbres;
        try {
            this.socket = new DatagramSocket(21027);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String mensaje;
        DatagramPacket paqueteRecibido = new DatagramPacket(new byte[1024], 1024);
        do {
            try {
                this.socket.receive(paqueteRecibido);
                mensaje = new String(paqueteRecibido.getData());
                this.misTimbres.sePublicoTimbre(mensaje.split(":")[0], mensaje.split(":")[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}