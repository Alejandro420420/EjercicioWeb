package empresa.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import empresa.conexion.Conexion;
import empresa.model.Empleados;

public class EmpleadosDao {
    private Connection connection;
    private PreparedStatement statement;
    private boolean estadoOperacion;

    // guardar producto
    public boolean guardar(Empleados empleados) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            connection.setAutoCommit(false);
            sql = "INSERT INTO empleados (dni, nombre, sexo, categoria, anyos) VALUES(?,?,?,?,?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, empleados.getDni());
            statement.setString(2, empleados.getNombre());
            statement.setString(3, empleados.getSexo());
            statement.setInt(4, empleados.getCategoria());
            statement.setInt(5, empleados.getAnyos());

            estadoOperacion = statement.executeUpdate() > 0;

            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }

        return estadoOperacion;
    }

    // editar producto
    public boolean editar(Empleados empleados) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();
        try {
            connection.setAutoCommit(false);
            sql = "UPDATE empleados SET nombre=?, sexo=?, categoria=?, anyos=? WHERE dni=?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, empleados.getNombre());
            statement.setString(2, empleados.getSexo());
            statement.setInt(3, empleados.getCategoria());
            statement.setInt(4, empleados.getAnyos());
            statement.setString(5, empleados.getDni());

            estadoOperacion = statement.executeUpdate() > 0;
            connection.commit();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }

        return estadoOperacion;
    }

    // eliminar producto
    public boolean eliminar(String dni) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();
        try {
            connection.setAutoCommit(false);
            sql = "DELETE FROM empleados WHERE dni=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, dni);

            estadoOperacion = statement.executeUpdate() > 0;
            connection.commit();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }

        return estadoOperacion;
    }

    // obtener lista de productos
    public List<Empleados> obtenerProductos() throws SQLException {
        ResultSet resultSet = null;
        List<Empleados> listaEmpleados = new ArrayList<>();

        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            sql = "SELECT * FROM empleados";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Empleados p = new Empleados();
                p.setDni(resultSet.getString("dni"));
                p.setNombre(resultSet.getString("nombre"));
                p.setSexo(resultSet.getString("sexo"));
                p.setCategoria(resultSet.getInt("categoria"));
                p.setAnyos(resultSet.getInt("anyos"));
                listaEmpleados.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaEmpleados;
    }

    // obtener producto
    public Empleados obtenerProducto(String dni) throws SQLException {
        ResultSet resultSet = null;
        Empleados p = new Empleados();

        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            sql = "SELECT * FROM empleados WHERE dni = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, dni);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                p.setDni(resultSet.getString("dni"));
                p.setNombre(resultSet.getString("nombre"));
                p.setSexo(resultSet.getString("sexo"));
                p.setCategoria(resultSet.getInt("categoria"));
                p.setAnyos(resultSet.getInt("anyos"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }
    
    // obtener conexion pool
    private Connection obtenerConexion() throws SQLException {
        return Conexion.getConnection();
    }
}
