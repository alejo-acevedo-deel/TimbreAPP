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

   private MisTimbres listaTimbres = new MisTimbres();
   private Timbre timbreSeleccionado;

   private MisHorarios listaHorarios = new MisHorarios();

   private AgregarHorariosController agregarHorariosController;

   public void initialize(){
       actualizarComboTimbres();
       actualizarListaView(this.listaSubir);
   }

   public void actualizarComboTimbres(){
       this.listaTimbres.cargarDesdeCSV();
       this.comboTimbres.getItems().clear();
       this.comboTimbres.getItems().addAll(this.listaTimbres.obtenerTimbres());
   }

   public void actualizarListaView(ListView<Horario> listaView){
       this.listaHorarios.cargarDesdeCSV();
       listaView.getItems().clear();
       listaView.getItems().addAll(listaHorarios.obtenerTimbres());
   }


   public void agregarHorarios(ActionEvent actionEvent) throws Exception{
       agregarHorariosController = new AgregarHorariosController(listaHorarios);
       Stage agregarTimbreStage = new Stage();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarHorariosView.fxml"));
       loader.setController(agregarHorariosController);
       Parent root = loader.load();
       agregarTimbreStage.setTitle("Agregar Horarios");
       agregarTimbreStage.setScene(new Scene(root));
       agregarTimbreStage.show();
       agregarTimbreStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
           @Override
           public void handle(WindowEvent event) {
               actualizarListaView(listaSubir);
           }
       });
   }

   public void borrarHorarios(ActionEvent actionEvent) throws Exception{
       List<Horario> horarios = this.listaSubir.getSelectionModel().getSelectedItems();
       this.listaHorarios.borrar(horarios);
   }

   public void modificarTimbres(ActionEvent actionEvent) throws Exception{
       Stage modificarTimbreStage = new Stage();
       Parent root = FXMLLoader.load(getClass().getResource("ModificarTimbresView.fxml"));
       modificarTimbreStage.setTitle("Modificar Timbres");
       modificarTimbreStage.setScene(new Scene(root));
       modificarTimbreStage.show();
       modificarTimbreStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
