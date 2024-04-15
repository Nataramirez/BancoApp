package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.controlador.observador.Observable;
import co.edu.uniquindio.banco.modelo.Banco;
import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.enums.CategoriaTransaccion;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Clase que se encarga de controlar la creación de transferencias entre cuentas
 * @author caflorezvi
 */
public class TransferenciaControlador {

    @FXML
    public ComboBox<CategoriaTransaccion> txtCategoria;

    @FXML
    public TextField txtMonto;
    @FXML
    public TextField txtCuenta;
    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();
    private Observable observable;


    @FXML
    public void initialize(){
        txtCategoria.setItems(FXCollections.observableArrayList(CategoriaTransaccion.values()));
    }

    public void transferir() throws Exception {
        System.out.println(sesion.getUsuario().getNumeroIdentificacion());
        boolean isMonto = txtMonto.getText().isBlank();
        boolean isCuenta = txtCuenta.getText().isBlank();
        boolean isCategoria = txtCategoria.getValue() == null;
        System.out.println(banco.obtenerCuentaAhorrosUsuario(sesion.getUsuario().getNumeroIdentificacion()));

        String cuentaOrigen = banco.obtenerCuentaAhorrosUsuario(sesion.getUsuario().getNumeroIdentificacion()).getNumeroCuenta();
        System.out.println(cuentaOrigen);
        if (!isMonto && !isCuenta && !isCategoria){
            if (banco.obtenerCuentaAhorros(txtCuenta.getText()) == null){
                crearAlerta("El número de cuenta al que desea transferir no existe. Por favor intente nuevamente.", Alert.AlertType.ERROR);
            }else {
                float monto = Float.parseFloat(txtMonto.getText());
                banco.realizarTransferencia(
                        cuentaOrigen,
                        txtCuenta.getText(),
                        monto,
                        txtCategoria.getValue()
                );
                observable.notificar();
                cerrarVentana();
            }



        }else {
            crearAlerta("Todos los campos son obligatorios", Alert.AlertType.WARNING);
        }
    }

    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void inicializarObservable(Observable observable) {
        this.observable = observable;
    }

    public void cerrarVentana(){
        Stage trasferencia = (Stage) txtCuenta.getScene().getWindow();
        trasferencia.close();
    }
}
