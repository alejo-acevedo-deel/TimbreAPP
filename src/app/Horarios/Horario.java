package app.Horarios;

import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;
import Excepciones.SilenciosNegativos;
import com.sun.glass.ui.Pixels;
import javafx.scene.control.CheckBox;

class Horario extends CheckBox{

    private String hora;
    private String minutos;
    private boolean largo;
    private String silencios;
    private String titulo;

    Horario(String hora, String minutos, boolean largo, String silencios)throws FormatoHoraErroneo, FormatoMinutoErroneo{
        this.create(hora, minutos, largo, silencios);
    }

    Horario(String hora, String minutos, boolean largo)throws FormatoHoraErroneo, FormatoMinutoErroneo{
        this.create(hora, minutos, largo, "0");
    }

    private void create(String hora, String minutos, boolean largo, String silencios) throws FormatoHoraErroneo, FormatoMinutoErroneo{
        if(Integer.parseInt(hora) < 0 || Integer.parseInt(hora) >= 24){
            throw new FormatoHoraErroneo();
        }
        if(Integer.parseInt(minutos) < 0 || Integer.parseInt(minutos) >= 60){
            throw new FormatoMinutoErroneo();
        }
        this.hora = hora;
        this.minutos = minutos;
        this.largo = largo;
        this.silencios = silencios;
        if(this.largo){
            this.titulo = this.hora + " : " + this.minutos + "    Duracion Larga";

        }else {
            this.titulo = this.hora + " : " + this.minutos + "    Duracion Corta";
        }
        try{
            this.silenciarDurante(silencios);
        }catch (SilenciosNegativos silenciosNegativos){

        }
    }

    public String obtenerHora(){
        return this.hora;
    }

    public String obtenerMinutos(){
        return this.minutos;
    }

    public boolean esLargo(){
        return this.largo;
    }

    public String obtenerLargo(){
        if(this.esLargo()){
            return "L";
        }
        return "C";
    }

    public String obtenerSilencion(){
        return this.silencios;
    }

    public String paraEnviar(){
        return this.obtenerHora()+":"+this.obtenerMinutos()+":"+this.obtenerLargo();
    }

    public void silenciarDurante(String dias)throws SilenciosNegativos{
        if(Integer.parseInt(dias)<0){
            throw new SilenciosNegativos();
        }
        this.silencios = dias;
        if(Integer.parseInt(dias)==0){
            this.setText(this.titulo);
        }else{
            this.setText(this.titulo + "Silenciado durante :" + this.silencios + " dias");
        }
    }

}
