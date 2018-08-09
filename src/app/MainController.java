package app;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.net.telnet.EchoOptionHandler;

import java.io.IOException;
import java.util.List;

public class MainController implements Receptores{

   @FXML
   Button btnEnviar;
   @FXML
   Button btnAgregarHorarios;
   @FXML
   Button btnModificarTimbres;
   @FXML
   ListView<Horario> listaSubir;
   @FXML
   ComboBox<Timbre> comboTimbres;

   private MisTimbres listaTimbres = new MisTimbres(this);
   private Timbre timbreSeleccionado;

   private MisHorarios listaHorarios = new MisHorarios(this);;

   private AgregarHorariosController agregarHorariosController;
   private ModificarTimbresController modificarTimbresController;

   public MainController(Stage primaryStage) throws Exception{
       FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
       loader.setController(this);
       Parent root = loader.load();
       primaryStage.setTitle("Timbre");
       primaryStage.setScene(new Scene(root));
       primaryStage.show();
       actualizarComboTimbres();
       actualizarListaView(this.listaSubir);
   }

   public void actualizarComboTimbres(){
       this.comboTimbres.getItems().clear();
       this.comboTimbres.getItems().addAll(this.listaTimbres.obtenerTimbres());
   }

   public void actualizarListaView(ListView<Horario> listaView){
       listaView.getItems().clear();
       listaView.getItems().addAll(listaHorarios.obtenerHorarios());
   }

   public void agregarHorarios(ActionEvent actionEvent) throws Exception{
       Stage agregarHorariosStage = new Stage();
       this.agregarHorariosController = new AgregarHorariosController(this.listaHorarios, agregarHorariosStage);
       agregarHorariosStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
           @Override
           public void handle(WindowEvent event) {
               actualizarListaView(listaSubir);
           }
       });
   }

   public void borrarHorarios(ActionEvent actionEvent) throws Exception{
       List<Horario> horarios = this.listaSubir.getSelectionModel().getSelectedItems();
       this.listaHorarios.borrarHorario(horarios);
   }

   public void modificarTimbres(ActionEvent actionEvent) throws Exception{
       Stage modificarTimbresStage = new Stage();
       this.modificarTimbresController = new ModificarTimbresController(this.listaTimbres, modificarTimbresStage);
       modificarTimbresStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
           @Override
           public void handle(WindowEvent event) {
               actualizarComboTimbres();
           }
       });
   }

   public void conectarTimbre(){
       try {
           if(this.timbreSeleccionado != null){
                this.timbreSeleccionado.desconectar();
       }
       this.timbreSeleccionado = this.comboTimbres.getSelectionModel().getSelectedItem();
       this.timbreSeleccionado.conectar();
       this.timbreSeleccionado.setearReceptor(this);
       }catch (IOException e){
           System.out.println(e.getMessage());
           System.out.println("No se pudo Conectar/Desconectar");
       }
   }

    @Override
    public void llegoUnMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public void agregaronUnTimbre() {
        actualizarComboTimbres();
    }

    @Override
    public void agregaronUnHorario() {
        actualizarListaView(listaSubir);
    }

    public void enviarHorarios(){
       try{
           this.timbreSeleccionado.enviar("Hola Manola\n");
       }catch(IOException e){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("ERROR DE CONEXION");
           alert.setContentText(e.getMessage());
           alert.showAndWait();
           System.out.println(e.getMessage());
       }catch(NullPointerException f){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("ERROR DE CONEXION");
           alert.setHeaderText(null);
           alert.setContentText("No ha seleccionado ningun timbre");
           alert.showAndWait();
           System.out.println(f.getMessage());
       }
    }
}
