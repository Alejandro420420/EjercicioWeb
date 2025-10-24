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

import empresa.dao.EmpleadosDao;
import empresa.model.Empleados;

@WebServlet(description = "Administra peticiones para la tabla productos", urlPatterns = { "/empleados" })
public class EmpleadosController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmpleadosController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcion = request.getParameter("opcion");

        if (opcion.equals("crear")) {
            System.out.println("Usted ha presionado la opción crear");
            List<Integer> categorias = new ArrayList<>();
            for (int i = 5000; i <= 23000; i += 2000) {
                categorias.add(i);
            }
            request.setAttribute("categorias", categorias);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
            requestDispatcher.forward(request, response);

        } else if (opcion.equals("listar")) {

            EmpleadosDao empleadosDAO = new EmpleadosDao();
            List<Empleados> lista = new ArrayList<>();

            try {
                lista = empleadosDAO.obtenerProductos();
                for (Empleados empleados : lista) {
                    System.out.println(empleados);
                }

                request.setAttribute("lista", lista);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");

                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcion.equals("editar")) {
            String dni = request.getParameter("dni");
            System.out.println("Editar dni: " + dni);
            EmpleadosDao empleadosDAO = new EmpleadosDao();
            Empleados p = new Empleados();

            try {
                p = empleadosDAO.obtenerProducto(dni);
                System.out.println(p);
                request.setAttribute("empleados", p);
                List<Integer> categorias = new ArrayList<>();
                for (int i = 5000; i <= 23000; i += 2000) {
                    categorias.add(i);
                }
                request.setAttribute("categorias", categorias);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcion.equals("eliminar")) {
            String dni = request.getParameter("dni");
            EmpleadosDao empleadosDAO = new EmpleadosDao();

            try {
                empleadosDAO.eliminar(dni);
                System.out.println("Registro eliminado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcion = request.getParameter("opcion");

        if (opcion.equals("guardar")) {
            EmpleadosDao empleadosDAO = new EmpleadosDao();
            Empleados empleados = new Empleados();
            NominasDao nominasDAO = new NominasDao();

            empleados.setDni(request.getParameter("dni"));
            empleados.setNombre(request.getParameter("nombre"));
            empleados.setSexo(request.getParameter("sexo"));
            empleados.setCategoria(Integer.parseInt(request.getParameter("categoria")));
            empleados.setAnyos(Integer.parseInt(request.getParameter("anyos")));

            try {
                empleadosDAO.guardar(empleados);
                System.out.println("Registro guardado satisfactoriamente...");
                double sueldoBase = empleados.getCategoria();
                double sueldo = (empleados.getCategoria() + 5000) * empleados.getAnyos();

                Nominas nomina = new Nominas();
                nomina.setDni(empleados.getDni());
                nomina.setSueldoBase(sueldoBase);
                nomina.setSueldo(sueldo);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcion.equals("editar")) {
            EmpleadosDao empleadosDAO = new EmpleadosDao();
            NominasDao nominasDAO = new NominasDao();

            Empleados empleados = new Empleados();
            empleados.setDni(request.getParameter("dni"));
            empleados.setNombre(request.getParameter("nombre"));
            empleados.setSexo(request.getParameter("sexo"));
            empleados.setCategoria(Integer.parseInt(request.getParameter("categoria")));
            empleados.setAnyos(Integer.parseInt(request.getParameter("anyos")));

            try {
                // Editar empleado
                empleadosDAO.editar(empleados);
                System.out.println("Empleado editado correctamente.");

                // Recalcular sueldo
                double sueldoBase = empleados.getCategoria();
                double sueldo = (empleados.getCategoria() + 5000) * empleados.getAnyos();

                // Verificar si ya existe nómina
                Nominas nomina = nominasDAO.obtenerNominaPorDni(empleados.getDni());
                if (nomina != null && nomina.getDni() != null) {
                    // Actualizar nómina existente
                    nomina.setSueldoBase(sueldoBase);
                    nomina.setSueldo(sueldo);
                    nominasDAO.editar(nomina);
                    System.out.println("Nómina actualizada correctamente.");
                } else {
                    // Crear una nueva nómina
                    nomina = new Nominas();
                    nomina.setDni(empleados.getDni());
                    nomina.setSueldoBase(sueldoBase);
                    nomina.setSueldo(sueldo);
                    nominasDAO.guardar(nomina);
                    System.out.println("Nómina creada correctamente (nuevo registro).");
                }

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
