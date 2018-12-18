package app.Horarios;

import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;
import Excepciones.SilenciosNegativos;
import com.sun.glass.ui.Pixels;
import javafx.scene.control.CheckBox;

class Horario extends CheckBox{

    private String hora;
    private String minuto;
    private boolean largo;
    private String silencios;
    private CheckBox view = new CheckBox();

    public Horario(String hora, String minuto, boolean largo) throws FormatoHoraErroneo, FormatoMinutoErroneo {
        this.configurarHora(hora);
        this.configurarMinuto(minuto);
        this.largo = largo;
        this.silencios = "0";
        if(largo){
            this.view.setText(this.hora+" : "+this.minuto+"  Duracion Larga");
        }else{
            this.view.setText(this.hora+" : "+this.minuto+"  Duracion Corta");
        }
    }

    public CheckBox getView(){
        return this.view;
    }

    public void silenciarDurante(String dias){
        if(Integer.valueOf(dias) <= 0){return;}
        this.silencios = dias;
        if(dias.equals("255")){
            this.view.setText(this.view.getText() + " Silenciado");
        }else{
            this.view.setText(this.view.getText() + " Silenciado durante "+dias+" dias");
        }
    }

    public String obtenerComoMensaje(){
        if(this.largo){
            return this.hora+":"+this.minuto+":L";
        }
        return this.hora+":"+this.minuto+":C";
    }

    private void configurarHora(String hora) throws FormatoHoraErroneo {
        try {
            if (Integer.valueOf(hora) < 0 || Integer.valueOf(hora) > 24) {
                throw new FormatoHoraErroneo();
            }
        }catch (NumberFormatException e){
            throw new FormatoHoraErroneo();
        }
        if(hora.length()<2){
            this.hora = "0"+hora;
        }else {
            this.hora = hora;
        }
    }

    private void configurarMinuto(String minuto) throws FormatoMinutoErroneo {
        try{
            if(Integer.valueOf(minuto)<0 || Integer.valueOf(minuto)>60){
                throw  new FormatoMinutoErroneo();
            }
        }catch(NumberFormatException e){
            throw new FormatoMinutoErroneo();
        }
        if(minuto.length()<2){
            this.minuto = "0"+minuto;
        }else {
            this.minuto = minuto;
        }
    }
}
