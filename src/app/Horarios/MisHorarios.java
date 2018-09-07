package app.Horarios;

import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;

import java.util.LinkedList;

public class MisHorarios extends LinkedList<Horario>{

    public MisHorarios(){}

    public void agregarHorario(String hora, String minutos, boolean largo)throws FormatoHoraErroneo, FormatoMinutoErroneo{
        this.agregarHorario(hora, minutos, largo, "0");
    }

    public void agregarHorario(String hora, String minutos, boolean largo, String silencios)throws FormatoHoraErroneo, FormatoMinutoErroneo{
        Horario horario = new Horario(hora, minutos, largo, silencios);
        super.add(horario);
    }
}
