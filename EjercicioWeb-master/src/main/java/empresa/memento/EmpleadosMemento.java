package empresa.memento;

import empresa.model.Empleados;

public class EmpleadosMemento {
    private final String dni;
    private final String nombre;
    private final String sexo;
    private final int categoria;
    private final int anyos;

    public EmpleadosMemento(Empleados empleado) {
        this.dni = empleado.getDni();
        this.nombre = empleado.getNombre();
        this.sexo = empleado.getSexo();
        this.categoria = empleado.getCategoria();
        this.anyos = empleado.getAnyos();
    }

    public void restaurar(Empleados empleado) {
        empleado.setDni(dni);
        empleado.setNombre(nombre);
        empleado.setSexo(sexo);
        empleado.setCategoria(categoria);
        empleado.setAnyos(anyos);
    }
}