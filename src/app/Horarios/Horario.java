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

    protected Horario(String hora, String minuto, boolean largo) throws FormatoHoraErroneo, FormatoMinutoErroneo {
        try{
            if(Integer.valueOf(hora)<0 || Integer.valueOf(hora)>24){
                throw new FormatoHoraErroneo();
            }
            if(Integer.valueOf(minuto)<0 || Integer.valueOf(minuto)>60){
                throw  new FormatoMinutoErroneo();
            }
        }catch(NumberFormatException e){
            throw new FormatoHoraErroneo();
        }
        this.hora = hora;
        this.minuto = minuto;
        this.largo = largo;
        this.silencios = "0";
        if(largo){
            this.view.setText(hora+" : "+minuto+"  Duracion Larga");
        }else{
            this.view.setText(hora+" : "+minuto+"  Duracion Corta");
        }
    }

    public CheckBox getView(){
        return this.view;
    }

    public void silenciarDurante(String dias){
        this.silencios = dias;
        this.view.setText(this.view.getText() + " Silenciado durante "+dias+" dias");
    }

    public String obtenerComoMensaje(){
        if(this.largo){
            return this.hora+":"+this.minuto+":L:"+this.silencios;
        }
        return this.hora+":"+this.minuto+":C:"+this.silencios;
    }


}
