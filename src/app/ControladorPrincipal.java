package app;

import Excepciones.EstaDesconectado;
import Excepciones.NoSeConecto;
import app.Horarios.MisHorarios;
import app.Timbres.MisTimbres;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorPrincipal implements Controlador {

    private MainController mainController;
    private AgregarHorariosController agregarHorariosController;
    private ModificarTimbresController modificarTimbresController;

    private MisHorarios horariosUsuario;
    private MisHorarios horariosTimbre;

    private MisTimbres misTimbres;

    public ControladorPrincipal(){

    }

    @Override
    public void agregarUnTimbre(String nombre, String ip) {

    }

    @Override
    public void modificarUnTimbre(String nombre, String ip) {

    }

    @Override
    public void eliminarUnTimbre(String nombre, String i) {

    }

    @Override
    public void agregarUnHorario(String hora, String minuto, boolean largo, MisHorarios misHorarios) {

    }

    @Override
    public void borrarUnHorario(int indice, MisHorarios misHorarios) {

    }

    @Override
    public void agregarUnHorarioUsuario(String hora, String minuto, boolean largo) {

    }

    @Override
    public void borrarUnHorarioUsuario(int indice) {

    }

    @Override
    public void agregarUnHorarioTimbre(String hora, String minuto, boolean largo) {

    }

    @Override
    public void borrarUnHorarioTimbre(int indice) {

    }

    @Override
    public void enviarHorario() {

    }

    @Override
    public void recibirHorario(String horario) {

    }

    @Override
    public void mostrarAgregarHorariosView() {
        try{
            this.agregarHorariosController = new AgregarHorariosController();
            this.agregarHorariosController.setControlador(this);
        }catch (IOException e){
            new Alerta("UPS!! Algo Salio Mal");
        }
    }

    @Override
    public void mostrarModificarTimbresView() {
        try{
            this.modificarTimbresController = new ModificarTimbresController();
            this.modificarTimbresController.setControlador(this);
        }catch (IOException e){
            new Alerta("UPS!! Algo Salio Mal");
        }
    }

    @Override
    public void seleccionarUnTimbre(int indice) {
        try {
            this.misTimbres.seleccionarTimbre(indice);
        }catch (NoSeConecto noSeConecto) {
            new Alerta(noSeConecto);
        }
    }

    @Override
    public MisTimbres obtenerMisTimbres() {
        return this.misTimbres;
    }
}
