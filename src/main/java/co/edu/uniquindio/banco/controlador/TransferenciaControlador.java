package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Banco;
import co.edu.uniquindio.banco.modelo.enums.CategoriaTransaccion;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

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
    private final Banco banco;

    public TransferenciaControlador() {
        this.banco = Banco.getInstancia();
    }


    @FXML
    public void initialize(){
        txtCategoria.setItems(FXCollections.observableArrayList(CategoriaTransaccion.values()));
    }

    public void transferir(ActionEvent actionEvent) {
        boolean isMonto = txtMonto.getText().isBlank();
        boolean isCuenta = txtCuenta.getText().isBlank();
        boolean isCategoria = txtCategoria.getValue() != null;
        if (!isMonto && !isCuenta){
            if (banco.obtenerCuentaAhorros(txtCuenta.getText()) == null){
                crearAlerta("El número de cuenta al que desea transferir no existe. Por favor intente nuevamente.", Alert.AlertType.ERROR);
            }
            crearAlerta("ya puede transferir", Alert.AlertType.INFORMATION);



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

    public void inizializarObservable(){

    }
}
