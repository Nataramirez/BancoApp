package co.edu.uniquindio.banco.modelo;

import co.edu.uniquindio.banco.modelo.enums.CategoriaTransaccion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa un banco y sus operaciones
 * @version 1.0
 * @author caflorezvi
 */
@Getter
@AllArgsConstructor
public class Banco {
    private final ArrayList<Usuario> usuarios;
    private final ArrayList<CuentaAhorros> cuentasAhorros;
    public static Banco INSTANCIA;

    public static Banco getInstancia(){
        if (INSTANCIA == null){
            INSTANCIA = new Banco();
        }
        return INSTANCIA;
    }

    private Banco() {
        usuarios = new ArrayList<>();
        cuentasAhorros = new ArrayList<>();
        llenarDatosPrueba();
    }

    /**
     * Método que llena el banco con datos de prueba
     */
    private void llenarDatosPrueba(){
        try {

            agregarUsuario("Carlos", "Calle 1", "123", "carlos@email.com", "123");
            agregarUsuario("Juan", "Calle 2", "456", "juan@email.com", "456");
            agregarCuentaAhorros("123", 1000);
            agregarCuentaAhorros("456", 2000);

            System.out.println(cuentasAhorros);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Método que agrega un usuario al banco y verifica que no exista un usuario con el mismo número de identificación
     * @param nombre nombre del usuario
     * @param direccion dirección del usuario
     * @param numeroIdentificacion número de identificación del usuario
     * @param correoElectronico correo electrónico del usuario
     * @param contrasena contraseña del usuario
     * @throws Exception si el número de identificación ya existe o si alguno de los campos es nulo o vacío
     */
    public void agregarUsuario(String nombre, String direccion, String numeroIdentificacion, String correoElectronico, String contrasena) throws Exception{

        if(numeroIdentificacion == null || numeroIdentificacion.isBlank()){
            throw new Exception("El número de identificación es obligatorio");
        }

        if(obtenerUsuario(numeroIdentificacion) != null){
            throw new Exception("Ya existe un usuario con el número de identificación: "+numeroIdentificacion);
        }

        if(nombre == null || nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if(correoElectronico == null || correoElectronico.isBlank()){
            throw new Exception("El correo electronico es obligatorio");
        }

        if(contrasena == null || contrasena.isBlank()){
            throw new Exception("La contraseña es obligatoria");
        }

        if(contrasena.length() < 3){
            throw new Exception("La contraseña debe tener mínimo 6 caracteres");
        }

        Usuario usuario = Usuario.builder()
                .nombre(nombre)
                .direccion(direccion)
                .numeroIdentificacion(numeroIdentificacion)
                .correoElectronico(correoElectronico)
                .contrasena(contrasena)
                .build();

        usuarios.add(usuario);
    }

    /**
     * Método que actualiza los datos de un usuario
     * @param nombre nombre del usuario
     * @param direccion dirección del usuario
     * @param numeroIdentificacion número de identificación del usuario
     * @param correoElectronico correo electrónico del usuario
     * @param contrasena contraseña del usuario
     */
    public void actualizarUsuario(String nombre, String direccion, String numeroIdentificacion, String correoElectronico, String contrasena) throws Exception{

        if(nombre == null || nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if(direccion == null || direccion.isBlank()){
            throw new Exception("La dirección es obligatoria");
        }

        if(correoElectronico == null || correoElectronico.isBlank()){
            throw new Exception("El correo electronico es obligatorio");
        }

        if(contrasena == null || contrasena.isBlank()){
            throw new Exception("La contraseña es obligatoria");
        }

        if(obtenerUsuario(numeroIdentificacion) == null){
            throw new Exception("No existe un usuario con el número de identificación: "+numeroIdentificacion);
        }

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNumeroIdentificacion().equals(numeroIdentificacion)) {
                Usuario usuario = new Usuario(nombre, direccion, numeroIdentificacion, correoElectronico, contrasena);
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    /**
     * Método que elimina un usuario del banco
     * @param numeroIdentificacion número de identificación del usuario
     * @throws Exception si el usuario no existe
     */
    public void eliminarUsuario(String numeroIdentificacion) throws Exception{
        Usuario usuario = obtenerUsuario(numeroIdentificacion);
        if (usuario != null) {
            usuarios.remove(usuario);
        }else{
            throw new Exception("El usuario no existe");
        }
    }

    /**
     * Método que agrega una cuenta de ahorros a un usuario
     * @param numeroIdentificacion número de identificación del usuario
     * @param numeroIdentificacion número de identificación del usuario
     * @param saldoInicial saldo inicial de la cuenta
     * @return número de cuenta
     * @throws Exception si no se encuentra el usuario
     */
    public String agregarCuentaAhorros(String numeroIdentificacion, float saldoInicial) throws Exception{
        Usuario propietario = obtenerUsuario(numeroIdentificacion);

        if(propietario != null){
            String numeroCuenta = crearNumeroCuenta();
            CuentaAhorros cuentaAhorros = new CuentaAhorros(numeroCuenta, propietario, saldoInicial);
            cuentasAhorros.add(cuentaAhorros);

            return numeroCuenta;
        }

        throw new Exception("No se encontró el usuario con el número de identificación: "+numeroIdentificacion);

    }

    /**
     * Método que crea un número de cuenta aleatorio y verifica que no exista en el sistema para evitar duplicados
     * @return número de cuenta
     */
    private String crearNumeroCuenta(){

        String numeroCuenta = generarNumeroCuenta();

        while(obtenerCuentaAhorros(numeroCuenta) != null){
            numeroCuenta = generarNumeroCuenta();
        }

        return numeroCuenta;
    }

    /**
     * Método que genera un número de cuenta aleatorio de 10 dígitos
     * @return número de cuenta
     */
    private String generarNumeroCuenta(){
        StringBuilder numeroCuenta = new StringBuilder();

        for(int i = 0; i < 10; i++){
            int numero = new Random().nextInt(10);
            numeroCuenta.append(numero);
        }

        return numeroCuenta.toString();
    }

    /**
     * Método que realiza la validación de un usuario de acuerdo a su número de identificación y contraseña para el acceso al sistema
     * @param numeroIdentificacion número de identificación del usuario
     * @param contrasena contraseña del usuario
     * @return usuario
     * @throws Exception si los datos de acceso son incorrectos
     */
    public Usuario validarUsuario(String numeroIdentificacion, String contrasena){
        Usuario usuario = obtenerUsuario(numeroIdentificacion);
        if(usuario != null){
            if(usuario.getContrasena().equals(contrasena)){
                return usuario;
            }
        }
        return null;
    }

    /**
     * Método que consulta el saldo de las cuentas de ahorros de un usuario
     * @param identificacion número de identificación del usuario
     * @param contrasena contraseña del usuario
     * @return lista de cuentas de ahorros
     * @throws Exception si los datos de acceso son incorrectos
     */
    public List<CuentaAhorros> consultarCuentasUsario(String identificacion, String contrasena) throws Exception{

        Usuario usuario = validarUsuario(identificacion, contrasena);

        if(usuario != null){
            List<CuentaAhorros> cuentas = new ArrayList<>();

            for(int i = 0; i < cuentasAhorros.size(); i++){
                if(cuentasAhorros.get(i).getPropietario().getNumeroIdentificacion().equals(identificacion)){
                    cuentas.add(cuentasAhorros.get(i));
                }
            }

            return cuentas;
        }

        return null;
    }

    /**
     * Método que realiza una transferencia entre cuentas de ahorros
     * @param numeroCuentaOrigen número de cuenta de origen
     * @param numeroCuentaDestino número de cuenta de destino
     * @param monto monto a transferir
     * @param categoria categoría de la transacción
     * @throws Exception si los números de cuenta no existen
     */
    public void realizarTransferencia(String numeroCuentaOrigen, String numeroCuentaDestino, float monto, CategoriaTransaccion categoria) throws Exception{
        CuentaAhorros cuentaOrigen = obtenerCuentaAhorros(numeroCuentaOrigen);
        CuentaAhorros cuentaDestino = obtenerCuentaAhorros(numeroCuentaDestino);

        if(cuentaOrigen != null && cuentaDestino != null){
            cuentaOrigen.transferir(monto, cuentaDestino, categoria);
        }else{
            throw new Exception("Error con los números de cuenta");
        }
    }

    /**
     * Método que obtiene un usuario de acuerdo a su número de identificación
     * @param numeroIdentificacion número de identificación del usuario
     * @return usuario o null si no existe
     */
    private Usuario obtenerUsuario(String numeroIdentificacion){
        for(int i = 0; i < usuarios.size(); i++){
            if(usuarios.get(i).getNumeroIdentificacion().equals(numeroIdentificacion)){
                return usuarios.get(i);
            }
        }
        return null;
    }

    /**
     * Método que obtiene una cuenta de ahorros de acuerdo a su número de cuenta
     * @param numeroCuenta número de cuenta
     * @return cuenta de ahorros o null si no existe
     */
    public CuentaAhorros obtenerCuentaAhorros(String numeroCuenta){
        for(int i = 0; i < cuentasAhorros.size(); i++){
            if(cuentasAhorros.get(i).getNumeroCuenta().equals(numeroCuenta)){
                return cuentasAhorros.get(i);
            }
        }
        return null;
    }

    /**
     * Método que obtiene una cuenta de ahorros de acuerdo al ID de su propietario
     * @param numeroIdentificacion número de cuenta
     * @return cuenta de ahorros o null si no existe
     */
    public CuentaAhorros obtenerCuentaAhorrosUsuario(String numeroIdentificacion){
        for(int i = 0; i < cuentasAhorros.size(); i++){
            if(cuentasAhorros.get(i).getPropietario().getNumeroIdentificacion().equals(numeroIdentificacion)){
                return cuentasAhorros.get(i);
            } 
        }
        return null;
    }

}
