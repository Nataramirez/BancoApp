package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de gestionar las acciones de la interfaz gráfica del panel del cliente.
 * @author caflorezvi
 */
public class PanelClienteControlador{
    @FXML
    private Label mensajeBienvenida;
    @FXML
    private Label numeroCuenta;
    @FXML
    private Button cerrarSesion;
    @FXML
    private Button consultar;
    @FXML
    private Button transferir;
    @FXML
    private TableView<Transaccion> tablaTransacciones;
    @FXML
    private TableColumn<Transaccion, String> colTipo;
    @FXML
    private TableColumn<Transaccion, String> colFecha;
    @FXML
    private TableColumn<Transaccion, String> colMonto;
    @FXML
    private TableColumn<Transaccion, String> colUsuario;
    @FXML
    private TableColumn<Transaccion, String> colCategoria;



    private final Banco banco = Banco.getInstancia();
    private Sesion sesion = Sesion.getInstancia();

    Usuario usuario = sesion.getUsuario();
    CuentaAhorros cuentaAhorros = banco.obtenerCuentaAhorrosUsuario(usuario.getNumeroIdentificacion());

    public void inicializarValores(){
        try {
            if(sesion.getUsuario() != null){
                mostrarMensajesPanel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void mostrarMensajesPanel (){
        mensajeBienvenida.setText(usuario.getNombre() + " bienvenido a su banco, aquí podrá ver sus transacciones");
        numeroCuenta.setText("Nro. Cuenta" + cuentaAhorros.getNumeroCuenta());
    }

    public void cerrarSesionActual(ActionEvent actionEvent) {
        sesion.cerrarSesion();
        navegarVentana("/login.fxml", "Banco - Iniciar Sesión");
        cerrarVentana();

    }

    public void consultarSaldo(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText("El saldo de su cuenta es $" + cuentaAhorros.getSaldo());
        alert.showAndWait();
    }

    public void initialize(URL location, ResourceBundle resources) {
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().toString()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        colMonto.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getMonto()));
        colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().toString()));
    }

    public void realizarTransferencia(ActionEvent actionEvent) {
        navegarVentana("/transferencia.fxml", "Banco - Realizar Transferencia");
    }

    public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);

            // Mostrar la nueva ventana
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de cerrar la ventana actual
     */
    public void cerrarVentana(){
        Stage stage = (Stage) cerrarSesion.getScene().getWindow();
        stage.close();
    }

}
