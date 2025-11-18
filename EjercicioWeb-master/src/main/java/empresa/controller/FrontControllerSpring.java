package empresa.controller;

import empresa.dao.EmpleadosDao;
import empresa.dao.NominasDao;
import empresa.factory.EmpleadosFactory;
import empresa.factory.NominasFactory;
import empresa.memento.EmpleadosMemento;
import empresa.memento.GuardarEstadoMemento;
import empresa.model.Empleados;
import empresa.model.Nominas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/controller")
public class FrontControllerSpring {

    @Autowired
    private HttpServletRequest request; // Para acceder al contexto y atributos si es necesario

    private EmpleadosDao empleadosDAO = new EmpleadosDao();
    private NominasDao nominasDAO = new NominasDao();

    // ==================== MÉTODOS GET ====================
    @GetMapping
    public String handleGet(
            @RequestParam("entidad") String entidad,
            @RequestParam("opcion") String opcion,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) Integer id,
            Model model) throws SQLException {

        switch (entidad) {
            case "empleados":
                return handleEmpleadosGet(opcion, dni, model);
            case "nominas":
                return handleNominasGet(opcion, id, model);
            default:
                model.addAttribute("error", "Entidad no encontrada");
                return "error"; // página de error genérica
        }
    }

    private String handleEmpleadosGet(String opcion, String dni, Model model) throws SQLException {
        switch (opcion) {
            case "crear":
                List<Integer> categorias = new ArrayList<>();
                for (int i = 5000; i <= 23000; i += 2000) categorias.add(i);
                model.addAttribute("categorias", categorias);
                return "crear"; // /views/crear.jsp
            case "listar":
                model.addAttribute("lista", empleadosDAO.obtenerProductos());
                return "listar";
            case "editar":
                Empleados p = empleadosDAO.obtenerProducto(dni);
                model.addAttribute("empleados", p);

                categorias = new ArrayList<>();
                for (int i = 5000; i <= 23000; i += 2000) categorias.add(i);
                model.addAttribute("categorias", categorias);

                return "editar";
            case "eliminar":
                empleadosDAO.eliminar(dni);
                return "index";
            default:
                model.addAttribute("error", "Opción no válida");
                return "error";
        }
    }

    private String handleNominasGet(String opcion, Integer id, Model model) throws SQLException {
        switch (opcion) {
            case "crear":
                return "crear";
            case "listar":
                model.addAttribute("lista", nominasDAO.obtenerNominas());
                return "listar";
            case "editar":
                Nominas n = nominasDAO.obtenerNomina(id);
                model.addAttribute("nomina", n);
                return "editar";
            case "eliminar":
                nominasDAO.eliminar(id);
                return "index";
            case "consultarSalario":
                return "salario";
            default:
                model.addAttribute("error", "Opción no válida");
                return "error";
        }
    }

    // ==================== MÉTODOS POST ====================
    @PostMapping
    public String handlePost(
            @RequestParam("entidad") String entidad,
            @RequestParam("opcion") String opcion,
            Model model) throws SQLException {

        switch (entidad) {
            case "empleados":
                return handleEmpleadosPost(opcion, model);
            case "nominas":
                return handleNominasPost(opcion, model);
            default:
                model.addAttribute("error", "Entidad no encontrada");
                return "error";
        }
    }

    private String handleEmpleadosPost(String opcion, Model model) throws SQLException {
        switch (opcion) {
            case "guardar":
                Empleados empleados = EmpleadosFactory.crearEmpleado(
                        request.getParameter("dni"),
                        request.getParameter("nombre"),
                        request.getParameter("sexo"),
                        Integer.parseInt(request.getParameter("categoria")),
                        Integer.parseInt(request.getParameter("anyos"))
                );
                empleadosDAO.guardar(empleados);

                double sueldoBase = empleados.getCategoria();
                double sueldo = (empleados.getCategoria() + 5000) * empleados.getAnyos();

                Nominas nomina = NominasFactory.crearNomina(empleados.getDni(), sueldoBase, sueldo);

                return "index";

            case "editar":
                String dni = request.getParameter("dni");

                Empleados empleadoActual = empleadosDAO.obtenerProducto(dni);

                GuardarEstadoMemento gestorMementos =
                        (GuardarEstadoMemento) request.getServletContext().getAttribute("gestorMementos");
                if (gestorMementos == null) {
                    gestorMementos = new GuardarEstadoMemento();
                    request.getServletContext().setAttribute("gestorMementos", gestorMementos);
                }
                gestorMementos.guardarEstado(new EmpleadosMemento(empleadoActual));

                Empleados empleadosEditados = new Empleados();
                empleadosEditados.setDni(dni);
                empleadosEditados.setNombre(request.getParameter("nombre"));
                empleadosEditados.setSexo(request.getParameter("sexo"));
                empleadosEditados.setCategoria(Integer.parseInt(request.getParameter("categoria")));
                empleadosEditados.setAnyos(Integer.parseInt(request.getParameter("anyos")));

                empleadosDAO.editar(empleadosEditados);

                // Recalcular nómina
                Nominas nominaEditada = nominasDAO.obtenerNominaPorDni(dni);
                sueldoBase = empleadosEditados.getCategoria();
                sueldo = (empleadosEditados.getCategoria() + 5000) * empleadosEditados.getAnyos();
                if (nominaEditada != null) {
                    nominaEditada.setSueldoBase(sueldoBase);
                    nominaEditada.setSueldo(sueldo);
                    nominasDAO.editar(nominaEditada);
                } else {
                    nominaEditada = new Nominas();
                    nominaEditada.setDni(dni);
                    nominaEditada.setSueldoBase(sueldoBase);
                    nominaEditada.setSueldo(sueldo);
                    nominasDAO.guardar(nominaEditada);
                }

                return "index";

            case "deshacer":
                gestorMementos = (GuardarEstadoMemento) request.getServletContext().getAttribute("gestorMementos");

                if (gestorMementos != null) {
                    EmpleadosMemento estadoAnterior = gestorMementos.recuperarUltimo();
                    if (estadoAnterior != null) {
                        Empleados empleadoRestaurado = new Empleados();
                        estadoAnterior.restaurar(empleadoRestaurado);

                        empleadosDAO.editar(empleadoRestaurado);

                        Nominas nominaRestaurada = nominasDAO.obtenerNominaPorDni(empleadoRestaurado.getDni());
                        if (nominaRestaurada != null) {
                            sueldoBase = empleadoRestaurado.getCategoria();
                            sueldo = (empleadoRestaurado.getCategoria() + 5000) * empleadoRestaurado.getAnyos();
                            nominaRestaurada.setSueldoBase(sueldoBase);
                            nominaRestaurada.setSueldo(sueldo);
                            nominasDAO.editar(nominaRestaurada);
                        }

                        model.addAttribute("mensaje", "Se deshicieron los últimos cambios del empleado con DNI: " + empleadoRestaurado.getDni());
                    } else {
                        model.addAttribute("mensaje", "No hay cambios previos que deshacer.");
                    }
                } else {
                    model.addAttribute("mensaje", "No se encontró historial de cambios.");
                }

                return "index";

            default:
                model.addAttribute("error", "Opción no válida");
                return "error";
        }
    }

    private String handleNominasPost(String opcion, Model model) throws SQLException {
        switch (opcion) {
            case "guardar":
                Nominas nomina = new Nominas();
                nomina.setDni(request.getParameter("dni"));
                nomina.setSueldoBase(Double.parseDouble(request.getParameter("sueldoBase")));
                nomina.setSueldo(Double.parseDouble(request.getParameter("sueldo")));
                nominasDAO.guardar(nomina);
                return "index";

            case "editar":
                Nominas nominaEdit = new Nominas();
                nominaEdit.setId(Integer.parseInt(request.getParameter("id")));
                nominaEdit.setDni(request.getParameter("dni"));
                nominaEdit.setSueldoBase(Double.parseDouble(request.getParameter("sueldoBase")));
                nominaEdit.setSueldo(Double.parseDouble(request.getParameter("sueldo")));
                nominasDAO.editar(nominaEdit);
                return "index";

            case "detalle":
            case "consultarSalario":
                String dni = request.getParameter("dni");
                Nominas nom = nominasDAO.obtenerNominaPorDni(dni);
                if (nom != null && nom.getDni() != null && !nom.getDni().isEmpty()) {
                    model.addAttribute("nomina", nom);
                } else {
                    model.addAttribute("error", "No se encontró información salarial para el DNI: " + dni);
                }
                return "salario";

            default:
                model.addAttribute("error", "Opción no válida");
                return "error";
        }
    }
}
