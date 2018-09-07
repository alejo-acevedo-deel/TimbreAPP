package app;

import app.Horarios.MisHorarios;
import app.Timbres.MisTimbres;

public interface Controlador{

    void agregarUnTimbre(String nombre, String ip);

    void modificarUnTimbre(String nombre, String ip);

    void eliminarUnTimbre(String nombre, String i);

    void agregarUnHorario(String hora, String minuto, boolean largo, MisHorarios misHorarios);

    void borrarUnHorario(int indice, MisHorarios misHorarios);

    void agregarUnHorarioUsuario(String hora, String minuto, boolean largo);

    void borrarUnHorarioUsuario(int indice);

    void agregarUnHorarioTimbre(String hora, String minuto, boolean largo);

    void borrarUnHorarioTimbre(int indice);

    void enviarHorario();

    void recibirHorario(String horario);

    void mostrarAgregarHorariosView();

    void mostrarModificarTimbresView();

    void seleccionarUnTimbre(int indice);

    MisTimbres obtenerMisTimbres();

}
