package empresa.memento;

import java.util.Stack;

public class GuardarEstadoMemento {
    private Stack<EmpleadosMemento> history = new Stack<>();

    public void guardarEstado(EmpleadosMemento memento) {
        history.push(memento);
    }

    public EmpleadosMemento recuperarUltimo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }
}