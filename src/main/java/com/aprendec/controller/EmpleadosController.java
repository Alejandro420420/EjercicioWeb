package com.aprendec.controller;

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

import com.aprendec.dao.EmpleadosDao;
import com.aprendec.model.Empleados;

@WebServlet(description = "Administra peticiones para la tabla productos", urlPatterns = { "/ejercicio" })
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
                request.setAttribute("ejercicio", p);
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

            empleados.setDni(request.getParameter("dni"));
            empleados.setNombre(request.getParameter("nombre"));
            empleados.setSexo(request.getParameter("sexo"));
            empleados.setCategoria(request.getParameter("categoria"));
            empleados.setAnyos(Integer.parseInt(request.getParameter("anyos")));

            try {
                empleadosDAO.guardar(empleados);
                System.out.println("Registro guardado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcion.equals("editar")) {
            Empleados empleados = new Empleados();
            EmpleadosDao empleadosDAO = new EmpleadosDao();

            empleados.setDni(request.getParameter("dni"));
            empleados.setNombre(request.getParameter("nombre"));
            empleados.setSexo(request.getParameter("sexo"));
            empleados.setCategoria(request.getParameter("categoria"));
            empleados.setAnyos(Integer.parseInt(request.getParameter("anyos")));

            try {
                empleadosDAO.editar(empleados);
                System.out.println("Registro editado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else if (opcion.equals("sueldo")) {
            String dni = request.getParameter("dni");
            System.out.println("Editar dni: " + dni);
            EmpleadosDao empleadosDAO = new EmpleadosDao();
            Empleados p = new Empleados();

            try {
                p = empleadosDAO.obtenerProducto(dni);
                System.out.println(p);
                request.setAttribute("ejercicio", p);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/sueldo.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        }
}
