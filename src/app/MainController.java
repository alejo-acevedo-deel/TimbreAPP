package app;



import Excepciones.EstaDesconectado;
import Excepciones.FormatoHoraErroneo;
import Excepciones.FormatoMinutoErroneo;
import Excepciones.NoSeConecto;
import app.Horarios.Horario;
import app.Horarios.MisHorarios;
import app.Timbres.MisTimbres;
import app.Timbres.Timbre;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class MainController{

    Controlador controlador;

   @FXML
   private ListView horariosUsuariosView;
   @FXML
   private ListView horariosTimbreView;
   @FXML
   private ComboBox misTimbresView;

   public MainController(Stage primaryStage) throws Exception{
       FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
       loader.setController(this);
       Parent root = loader.load();
       primaryStage.setTitle("Timbre");
       primaryStage.setScene(new Scene(root));
       primaryStage.show();
   }

   public void setControlador(Controlador controlador){
       this.controlador = controlador;
   }

   public void mostrarAgregarHorariosView(ActionEvent actionEvent){
       this.controlador.mostrarAgregarHorariosView();
   }

   public void borrarHorariosUsuario(ActionEvent actionEvent){
       while(!this.horariosUsuariosView.getSelectionModel().getSelectedItems().isEmpty()){
           this.horariosUsuariosView.getItems().remove(this.horariosUsuariosView.getSelectionModel().getSelectedIndex());
           this.controlador.borrarUnHorarioUsuario(this.horariosUsuariosView.getSelectionModel().getSelectedIndex());
       }
   }

   public void borrarTodosHorariosUsuario(ActionEvent actionEvent){
       while(!this.horariosUsuariosView.getItems().isEmpty()){
           this.horariosUsuariosView.getItems().remove(0);
           this.controlador.borrarUnHorarioUsuario(0);
       }
   }

   public void seleccionarTimbre(ActionEvent actionEvent){
       if(this.misTimbresView.getValue() == null){
           return;
       }
       this.controlador.seleccionarUnTimbre(this.misTimbresView.getSelectionModel().getSelectedIndex());
   }

   public void mostrarModificarTimbresView(){
       this.controlador.mostrarModificarTimbresView();
   }

   public void enviarHorario(){
       if(misHorarios.obtenerHorarios().isEmpty()){
           return;
       }
       Horario horario = this.misHorarios.obtenerHorarios().get(0);
       try {
           this.timbreSeleccionado.agregarHorario(horario);
           this.misHorarios.borrarHorario(horario);
           this.timbreHorarios.agregarHorario(horario);
       }catch (EstaDesconectado estaDesconectado){
           new Alerta(estaDesconectado);
       }catch (FormatoHoraErroneo formatoHoraErroneo){

       }catch (FormatoMinutoErroneo formatoMinutoErroneo){

       }catch (NullPointerException nullPointerException){
           new Alerta("No hay ningun timbre seleccionado");
       }
   }

   public void modificarTimbres(ActionEvent actionEvent) throws Exception{
        this.modificarTimbresController = new ModificarTimbresController(this.misTimbres);
   }

   public void obtenerHorario(){
       try {
           this.timbreSeleccionado.obtenerHorario();
       }catch (EstaDesconectado estaDesconectado){
           new Alerta(estaDesconectado);
       }catch (NullPointerException nullPointerException){
           new Alerta("No hay ningun timbre seleccionado");
       }
   }

   private void actualizarComboTimbres(){
       Platform.runLater(
               () -> {
                   this.comboTimbres.getItems().clear();
                   this.comboTimbres.getItems().addAll(this.misTimbres.obtenerTimbres());
               }
       );
   }

   private void actualizarListaView(){
       Platform.runLater(
               () -> {
                   this.listaSubir.getItems().clear();
                   this.listaSubir.getItems().addAll(misHorarios.obtenerHorarios());
                   this.listaBajados.getItems().clear();
                   this.listaBajados.getItems().addAll(timbreHorarios.obtenerHorarios());
               }
       );
   }
}
