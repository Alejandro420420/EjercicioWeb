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

import empresa.dao.NominasDao;
import empresa.model.Nominas;

@WebServlet(description = "Administra peticiones para la tabla nominas", urlPatterns = { "/nominas" })
public class NominasController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public NominasController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcion = request.getParameter("opcion");

        if (opcion.equals("crear")) {
            System.out.println("Usted ha presionado la opción crear");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
            requestDispatcher.forward(request, response);

        } else if (opcion.equals("listar")) {

            NominasDao nominasDAO = new NominasDao();
            List<Nominas> lista = new ArrayList<>();

            try {
                lista = nominasDAO.obtenerNominas();
                for (Nominas nomina : lista) {
                    System.out.println(nomina);
                }

                request.setAttribute("lista", lista);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcion.equals("editar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("Editar id: " + id);
            NominasDao nominasDAO = new NominasDao();
            Nominas n = new Nominas();

            try {
                n = nominasDAO.obtenerNomina(id);
                System.out.println(n);
                request.setAttribute("nomina", n);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcion.equals("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            NominasDao nominasDAO = new NominasDao();

            try {
                nominasDAO.eliminar(id);
                System.out.println("Registro eliminado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (opcion.equals("consultarSalario")) {
            System.out.println("Usted ha presionado la opción consultar salario");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/salario.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcion = request.getParameter("opcion");

        if (opcion.equals("guardar")) {
            NominasDao nominasDAO = new NominasDao();
            Nominas nomina = new Nominas();

            nomina.setDni(request.getParameter("dni"));
            nomina.setSueldoBase(Double.parseDouble(request.getParameter("sueldoBase")));
            nomina.setSueldo(Double.parseDouble(request.getParameter("sueldo")));

            try {
                nominasDAO.guardar(nomina);
                System.out.println("Registro guardado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcion.equals("editar")) {
            Nominas nomina = new Nominas();
            NominasDao nominasDAO = new NominasDao();

            nomina.setId(Integer.parseInt(request.getParameter("id")));
            nomina.setDni(request.getParameter("dni"));
            nomina.setSueldoBase(Double.parseDouble(request.getParameter("sueldoBase")));
            nomina.setSueldo(Double.parseDouble(request.getParameter("sueldo")));

            try {
                nominasDAO.editar(nomina);
                System.out.println("Registro editado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcion.equals("detalle")) {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("Ver detalle id: " + id);
            NominasDao nominasDAO = new NominasDao();
            Nominas n = new Nominas();

            try {
                n = nominasDAO.obtenerNomina(id);
                System.out.println(n);
                request.setAttribute("nomina", n);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/salario.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (opcion.equals("consultarSalario")) {
            String dni = request.getParameter("dni");
            System.out.println("Consultar salario para DNI: " + dni);
            NominasDao nominasDAO = new NominasDao();

            try {
                Nominas nomina = nominasDAO.obtenerNominaPorDni(dni);
                
                // Verificar si se encontró la nómina
                if (nomina != null && nomina.getDni() != null && !nomina.getDni().isEmpty()) {
                    System.out.println("Nómina encontrada: " + nomina);
                    request.setAttribute("nomina", nomina);
                } else {
                    System.out.println("No se encontró nómina para el DNI: " + dni);
                    request.setAttribute("error", "No se encontró información salarial para el DNI: " + dni);
                }
                
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/salario.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Error al consultar la base de datos: " + e.getMessage());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/salario.jsp");
                requestDispatcher.forward(request, response);
            }
        }
    }
}