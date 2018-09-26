package app;

import app.Horarios.MisHorarios;
import app.Timbres.MisTimbres;

public interface Controlador{

    void agregarUnTimbre(String nombre, String ip);

    void modificarUnTimbre(String nombre, String ip, int indice);

    void eliminarUnTimbre(int indice);

    void agregarUnHorarioUsuario(String hora, String minuto, boolean largo);

    void borrarUnHorarioUsuario(int indice);

    void agregarUnHorarioTimbre(String hora, String minuto, boolean largo, String silencios);

    void borrarUnHorarioTimbre(int indice);

    void obtenerHorariosTimbre();

    void enviarHorario();

    void recibirHorario(String horario);

    void mostrarAgregarHorariosView();

    void mostrarModificarTimbresView();

    void seleccionarUnTimbre(int indice);

    void setMainController(MainController mainController);

    MisTimbres obtenerMisTimbres();

    void horarioEnviado(String o);

    void horarioBorrado(String o);
}
