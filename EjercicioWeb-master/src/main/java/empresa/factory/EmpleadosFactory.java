package empresa.factory;

import empresa.model.Empleados;

public class EmpleadosFactory {

    public static Empleados crearEmpleado(String dni, String nombre, String sexo, int categoria, int anyos) {
        Empleados empleado = new Empleados();
        empleado.setDni(dni);
        empleado.setNombre(nombre);
        empleado.setSexo(sexo);
        empleado.setCategoria(categoria);
        empleado.setAnyos(anyos);
        return empleado;
    }
}