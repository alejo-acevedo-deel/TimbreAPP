package app;

import Excepciones.*;
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
        this.horariosUsuario = new MisHorarios();
        this.horariosTimbre = new MisHorarios();
        this.misTimbres = new MisTimbres();

    }

    @Override
    public void agregarUnTimbre(String nombre, String ip) {
        try{
            this.misTimbres.agregarTimbre(nombre, ip);
        } catch (IpYaExiste ipYaExiste) {
            new Alerta(ipYaExiste);
        } catch (FaltaNombre faltaNombre) {
            new Alerta(faltaNombre);
        } catch (FaltaIP faltaIP) {
            new Alerta(faltaIP);
        }
        this.modificarTimbresController.actualizarComboBox(misTimbres);
        this.mainController.actualizarComboTimbres(misTimbres);
    }

    @Override
    public void modificarUnTimbre(String nombre, String ip, int indice) {
        try {
            this.misTimbres.modificarTimbre(nombre, ip, indice);
        } catch (IpYaExiste ipYaExiste) {
            new Alerta(ipYaExiste);
        } catch (FaltaNombre faltaNombre) {
            new Alerta(faltaNombre);
        } catch (FaltaIP faltaIP) {
            new Alerta(faltaIP);
        }
        this.modificarTimbresController.actualizarComboBox(misTimbres);
        this.mainController.actualizarComboTimbres(misTimbres);
    }

    @Override
    public void eliminarUnTimbre(int indice) {
        this.misTimbres.eliminarTimbre(indice);
        this.modificarTimbresController.actualizarComboBox(misTimbres);
        this.mainController.actualizarComboTimbres(misTimbres);
    }

    @Override
    public void agregarUnHorarioUsuario(String hora, String minuto, boolean largo) {
        try {
            this.horariosUsuario.agregarHorario(hora, minuto, largo);
        } catch (FormatoHoraErroneo formatoHoraErroneo) {
            new Alerta(formatoHoraErroneo);
        } catch (FormatoMinutoErroneo formatoMinutoErroneo) {
            new Alerta(formatoMinutoErroneo);
        }
        this.mainController.actualizarHorariosUsuariosView(this.horariosUsuario);
    }

    @Override
    public void borrarUnHorarioUsuario(int indice) {
        this.horariosUsuario.remove(indice);
        this.mainController.actualizarHorariosUsuariosView(this.horariosUsuario);
    }

    @Override
    public void agregarUnHorarioTimbre(String hora, String minuto, boolean largo, String silencios) {
        try {
            this.horariosTimbre.agregarHorario(hora, minuto, largo, silencios);
        } catch (FormatoHoraErroneo formatoHoraErroneo) {
            new Alerta(formatoHoraErroneo);
        } catch (FormatoMinutoErroneo formatoMinutoErroneo) {
            new Alerta(formatoMinutoErroneo);
        }
        this.mainController.actualizarHorariosTimbreView(this.horariosTimbre);
    }


    @Override
    public void borrarUnHorarioTimbre(int indice) {
        try {
            this.misTimbres.borrarUnHorario(indice);
        } catch (NoHayTimbreSeleccionado noHayTimbreSeleccionado) {
            new Alerta( noHayTimbreSeleccionado);
        }
        this.horariosTimbre.remove(indice);
        this.mainController.actualizarHorariosTimbreView(this.horariosTimbre);
    }

    @Override
    public void obtenerHorariosTimbre() {
        this.horariosTimbre = new MisHorarios();
        try {
            this.misTimbres.obtenerUnHorario();
        } catch (EstaDesconectado estaDesconectado) {
            new Alerta(estaDesconectado);
        } catch (NoHayTimbreSeleccionado noHayTimbreSeleccionado) {
            new Alerta(noHayTimbreSeleccionado);
        }
    }

    @Override
    public void enviarHorario() {
        if(this.horariosUsuario.isEmpty()){
            this.obtenerHorariosTimbre();
            return;
        }
        try {
            this.misTimbres.agregarHorario(this.horariosUsuario.aEnviar());
        } catch (EstaDesconectado estaDesconectado) {
            new Alerta(estaDesconectado);
        } catch (NoHayTimbreSeleccionado noHayTimbreSeleccionado) {
            new Alerta(noHayTimbreSeleccionado);
        }

    }

    @Override
    public void recibirHorario(String horario) {
        if(horario.equals("FIN")){
            this.mainController.actualizarHorariosTimbreView(this.horariosTimbre);
            return;
        }
        String horarioRecibido[] = horario.split(":");
        this.agregarUnHorarioTimbre(horarioRecibido[0], horarioRecibido[1], horarioRecibido[2].equals("L"), horarioRecibido[3]);
        try {
            this.misTimbres.obtenerUnHorario();
        } catch (EstaDesconectado estaDesconectado) {
            new Alerta(estaDesconectado);
        } catch (NoHayTimbreSeleccionado noHayTimbreSeleccionado) {
            new Alerta(noHayTimbreSeleccionado);
        }
    }

    @Override
    public void mostrarAgregarHorariosView() {
        try{
            this.agregarHorariosController = new AgregarHorariosController();
            this.agregarHorariosController.setControlador(this);
        }catch (IOException e){
            new Alerta("UPS!! Algo Salio Mal");
            e.printStackTrace();
        }
    }

    @Override
    public void mostrarModificarTimbresView() {
        try{
            this.modificarTimbresController = new ModificarTimbresController();
            this.modificarTimbresController.setControlador(this);
            this.modificarTimbresController.actualizarComboBox(misTimbres);
        }catch (IOException e){
            new Alerta("UPS!! Algo Salio Mal");
            e.printStackTrace();
        }
    }

    @Override
    public void seleccionarUnTimbre(int indice) {
        try {
            this.misTimbres.seleccionarTimbre(indice);
            this.obtenerHorariosTimbre();
        }catch (NoSeConecto noSeConecto) {
            new Alerta(noSeConecto);
            this.mainController.actualizarComboTimbres(misTimbres);
        }
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        this.mainController.actualizarComboTimbres(misTimbres);
    }

    @Override
    public MisTimbres obtenerMisTimbres() {
        return this.misTimbres;
    }

    @Override
    public void horarioEnviado(String respuesta) {
        if(respuesta.equals("OK")) {
            this.borrarUnHorarioUsuario(0);
        }
        this.enviarHorario();
    }

    @Override
    public void horarioBorrado(String respuesta) {
        if(respuesta.equals("OK")) {
            this.borrarUnHorarioTimbre(1);
        }
    }
}
