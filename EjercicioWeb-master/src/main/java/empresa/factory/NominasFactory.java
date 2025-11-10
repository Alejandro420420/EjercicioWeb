package empresa.factory;

import empresa.model.Nominas;

public class NominasFactory {

    public static Nominas crearNomina(String dni, double sueldoBase, double sueldo) {
        Nominas nomina = new Nominas();
        nomina.setDni(dni);
        nomina.setSueldoBase(sueldoBase);
        nomina.setSueldo(sueldo);
        return nomina;
    }
}