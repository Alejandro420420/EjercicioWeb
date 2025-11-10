package empresa.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import empresa.dao.EmpleadosDao;
import empresa.dao.NominasDao;
import empresa.factory.EmpleadosFactory;
import empresa.factory.NominasFactory;
import empresa.memento.EmpleadosMemento;
import empresa.memento.GuardarEstadoMemento;
import empresa.model.Empleados;
import empresa.model.Nominas;

@WebServlet(description = "FrontController para empleados y nominas", urlPatterns = { "/controller" })
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FrontController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String entidad = request.getParameter("entidad");
        String opcion = request.getParameter("opcion");

        if ("empleados".equals(entidad)) {
            handleEmpleadosGet(request, response, opcion);
        } else if ("nominas".equals(entidad)) {
            handleNominasGet(request, response, opcion);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Entidad no encontrada");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String entidad = request.getParameter("entidad");
        String opcion = request.getParameter("opcion");

        if ("empleados".equals(entidad)) {
            handleEmpleadosPost(request, response, opcion);
        } else if ("nominas".equals(entidad)) {
            handleNominasPost(request, response, opcion);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Entidad no encontrada");
        }
    }

    // ==================== MÉTODOS GET ====================
    private void handleEmpleadosGet(HttpServletRequest request, HttpServletResponse response, String opcion)
            throws ServletException, IOException {

        EmpleadosDao empleadosDAO = new EmpleadosDao();

        try {
            if ("crear".equals(opcion)) {
                List<Integer> categorias = new ArrayList<>();
                for (int i = 5000; i <= 23000; i += 2000) {
                    categorias.add(i);
                }
                request.setAttribute("categorias", categorias);
                RequestDispatcher rd = request.getRequestDispatcher("/views/crear.jsp");
                rd.forward(request, response);

            } else if ("listar".equals(opcion)) {
                List<Empleados> lista = empleadosDAO.obtenerProductos();
                request.setAttribute("lista", lista);
                RequestDispatcher rd = request.getRequestDispatcher("/views/listar.jsp");
                rd.forward(request, response);

            } else if ("editar".equals(opcion)) {
                String dni = request.getParameter("dni");
                Empleados p = empleadosDAO.obtenerProducto(dni);
                request.setAttribute("empleados", p);

                List<Integer> categorias = new ArrayList<>();
                for (int i = 5000; i <= 23000; i += 2000) {
                    categorias.add(i);
                }
                request.setAttribute("categorias", categorias);

                RequestDispatcher rd = request.getRequestDispatcher("/views/editar.jsp");
                rd.forward(request, response);

            } else if ("eliminar".equals(opcion)) {
                String dni = request.getParameter("dni");
                empleadosDAO.eliminar(dni);
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleNominasGet(HttpServletRequest request, HttpServletResponse response, String opcion)
            throws ServletException, IOException {

        NominasDao nominasDAO = new NominasDao();

        try {
            if ("crear".equals(opcion)) {
                RequestDispatcher rd = request.getRequestDispatcher("/views/crear.jsp");
                rd.forward(request, response);

            } else if ("listar".equals(opcion)) {
                List<Nominas> lista = nominasDAO.obtenerNominas();
                request.setAttribute("lista", lista);
                RequestDispatcher rd = request.getRequestDispatcher("/views/listar.jsp");
                rd.forward(request, response);

            } else if ("editar".equals(opcion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Nominas n = nominasDAO.obtenerNomina(id);
                request.setAttribute("nomina", n);
                RequestDispatcher rd = request.getRequestDispatcher("/views/editar.jsp");
                rd.forward(request, response);

            } else if ("eliminar".equals(opcion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                nominasDAO.eliminar(id);
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);

            } else if ("consultarSalario".equals(opcion)) {
                RequestDispatcher rd = request.getRequestDispatcher("/views/salario.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==================== MÉTODOS POST ====================
    private void handleEmpleadosPost(HttpServletRequest request, HttpServletResponse response, String opcion)
            throws ServletException, IOException {

        EmpleadosDao empleadosDAO = new EmpleadosDao();
        NominasDao nominasDAO = new NominasDao();

        try {
            if ("guardar".equals(opcion)) {
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

                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);

            } else if ("editar".equals(opcion)) {
                String dni = request.getParameter("dni");

                EmpleadosDao empleadosDAOo = new EmpleadosDao();

                //Recuperar empleado antes de editar
                Empleados empleadoActual = empleadosDAOo.obtenerProducto(dni);

                // 2Guardar estado anterior
                empresa.memento.GuardarEstadoMemento gestorMementos =
                        (empresa.memento.GuardarEstadoMemento) getServletContext().getAttribute("gestorMementos");
                if (gestorMementos == null) {
                    gestorMementos = new empresa.memento.GuardarEstadoMemento();
                    getServletContext().setAttribute("gestorMementos", gestorMementos);
                }
                gestorMementos.guardarEstado(new empresa.memento.EmpleadosMemento(empleadoActual));

                // Aplicar cambios del formulario
                Empleados empleados = new Empleados();
                empleados.setDni(request.getParameter("dni"));
                empleados.setNombre(request.getParameter("nombre"));
                empleados.setSexo(request.getParameter("sexo"));
                empleados.setCategoria(Integer.parseInt(request.getParameter("categoria")));
                empleados.setAnyos(Integer.parseInt(request.getParameter("anyos")));

                empleadosDAOo.editar(empleados);

                // Recalcular nómina
                NominasDao nominasDAOO = new NominasDao();
                double sueldoBase = empleados.getCategoria();
                double sueldo = (empleados.getCategoria() + 5000) * empleados.getAnyos();

                Nominas nomina = nominasDAOO.obtenerNominaPorDni(empleados.getDni());
                if (nomina != null && nomina.getDni() != null) {
                    nomina.setSueldoBase(sueldoBase);
                    nomina.setSueldo(sueldo);
                    nominasDAOO.editar(nomina);
                } else {
                    nomina = new Nominas();
                    nomina.setDni(empleados.getDni());
                    nomina.setSueldoBase(sueldoBase);
                    nomina.setSueldo(sueldo);
                    nominasDAOO.guardar(nomina);
                }

                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            }
            else if ("deshacer".equals(opcion)) {
                GuardarEstadoMemento gestorMementos =
                        (GuardarEstadoMemento) getServletContext().getAttribute("gestorMementos");

                if (gestorMementos != null) {
                    EmpleadosMemento estadoAnterior = gestorMementos.recuperarUltimo();

                    if (estadoAnterior != null) {

                        Empleados empleadoRestaurado = new Empleados();
                        estadoAnterior.restaurar(empleadoRestaurado);


                        EmpleadosDao empleadosDAOo = new EmpleadosDao();
                        empleadosDAOo.editar(empleadoRestaurado);


                        NominasDao nominasDAOo = new NominasDao();
                        Nominas nomina = nominasDAOo.obtenerNominaPorDni(empleadoRestaurado.getDni());
                        if (nomina != null) {
                            double sueldoBase = empleadoRestaurado.getCategoria();
                            double sueldo = (empleadoRestaurado.getCategoria() + 5000) * empleadoRestaurado.getAnyos();
                            nomina.setSueldoBase(sueldoBase);
                            nomina.setSueldo(sueldo);
                            nominasDAOo.editar(nomina);
                        }

                        // Mostrar mensaje de confirmación
                        request.setAttribute("mensaje", "Se deshicieron los últimos cambios del empleado con DNI: " + empleadoRestaurado.getDni());
                    } else {
                        request.setAttribute("mensaje", "No hay cambios previos que deshacer.");
                    }
                } else {
                    request.setAttribute("mensaje", "No se encontró historial de cambios.");
                }

                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void handleNominasPost(HttpServletRequest request, HttpServletResponse response, String opcion)
            throws ServletException, IOException {

        NominasDao nominasDAO = new NominasDao();

        try {
            if ("guardar".equals(opcion)) {
                Nominas nomina = new Nominas();
                nomina.setDni(request.getParameter("dni"));
                nomina.setSueldoBase(Double.parseDouble(request.getParameter("sueldoBase")));
                nomina.setSueldo(Double.parseDouble(request.getParameter("sueldo")));

                nominasDAO.guardar(nomina);
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);

            } else if ("editar".equals(opcion)) {
                Nominas nomina = new Nominas();
                nomina.setId(Integer.parseInt(request.getParameter("id")));
                nomina.setDni(request.getParameter("dni"));
                nomina.setSueldoBase(Double.parseDouble(request.getParameter("sueldoBase")));
                nomina.setSueldo(Double.parseDouble(request.getParameter("sueldo")));

                nominasDAO.editar(nomina);
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);

            } else if ("detalle".equals(opcion) || "consultarSalario".equals(opcion)) {
                String dni = request.getParameter("dni");
                Nominas nomina = nominasDAO.obtenerNominaPorDni(dni);

                if (nomina != null && nomina.getDni() != null && !nomina.getDni().isEmpty()) {
                    request.setAttribute("nomina", nomina);
                } else {
                    request.setAttribute("error", "No se encontró información salarial para el DNI: " + dni);
                }

                RequestDispatcher rd = request.getRequestDispatcher("/views/salario.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al consultar la base de datos: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/views/salario.jsp");
            rd.forward(request, response);
        }
    }
}
