package app.Timbres;

public class Estado {

    private boolean seConecto = false;
    private String hora = "";
    private String duracionLarga = "";
    private String duracionCorta = "";
    private String vacaciones = "";
    private String diasLibres = "";
    private String nombre = "";
    private String ip = "";

    public Estado(){}

    public String getHora(){ return this.hora;}

    public String getDuracionLarga(){ return this.duracionLarga;}

    public String getDuracionCorta(){ return this.duracionCorta;}

    public String getVacaciones(){
        return this.vacaciones;
    }

    public String getDiasLibres() { return this.diasLibres;}

    public String getNombre() { return this.nombre;}

    public String getIp() { return this.ip;}

    public boolean getSeConecto() { return this.seConecto;}

    public void setHora(String hora){ this.hora = hora;}

    public void setDuracionLarga(String duracionLarga){ this.duracionLarga = duracionLarga;}

    public void setDuracionCorta(String duracionCorta){ this.duracionCorta = duracionCorta;}

    public void setVacaciones(String vacaciones){
        this.vacaciones = vacaciones;
    }

    public void setDiasLibres(String diasLibres) { this.diasLibres = diasLibres;}

    public void setSeConecto(){this.seConecto = true;}

    public void setNombre(String nombre){ this.nombre = nombre;}

    public void setIp(String ip){ this.ip = ip;}
}
