package app;

public class HiloLambda extends Thread {
    Runnable funcion;
    public HiloLambda(Runnable funcion) {
        this.funcion=funcion;
    }

    public void run() {
        funcion.run();
    }
}