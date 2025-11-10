package empresa.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import empresa.conexion.Conexion;
import empresa.model.Nominas;

public class NominasDao {
    private Connection connection;
    private PreparedStatement statement;
    private boolean estadoOperacion;

    public boolean guardar(Nominas nomina) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            connection.setAutoCommit(false);
            sql = "INSERT INTO nominas (dni, sueldo_base, sueldo) VALUES(?,?,?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, nomina.getDni());
            statement.setDouble(2, nomina.getSueldoBase());
            statement.setDouble(3, nomina.getSueldo());

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

    // editar nomina
    public boolean editar(Nominas nomina) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();
        try {
            connection.setAutoCommit(false);
            sql = "UPDATE nominas SET dni=?, sueldo_base=?, sueldo=? WHERE id=?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, nomina.getDni());
            statement.setDouble(2, nomina.getSueldoBase());
            statement.setDouble(3, nomina.getSueldo());
            statement.setInt(4, nomina.getId());

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

    // eliminar nomina
    public boolean eliminar(int id) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();
        try {
            connection.setAutoCommit(false);
            sql = "DELETE FROM nominas WHERE id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

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

    // obtener lista de nominas
    public List<Nominas> obtenerNominas() throws SQLException {
        ResultSet resultSet = null;
        List<Nominas> listaNominas = new ArrayList<>();

        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            sql = "SELECT * FROM nominas";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Nominas n = new Nominas();
                n.setId(resultSet.getInt("id"));
                n.setDni(resultSet.getString("dni"));
                n.setSueldoBase(resultSet.getDouble("sueldo_base"));
                n.setSueldo(resultSet.getDouble("sueldo"));
                listaNominas.add(n);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaNominas;
    }

    // obtener nomina por id
    public Nominas obtenerNomina(int id) throws SQLException {
        ResultSet resultSet = null;
        Nominas n = new Nominas();

        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            sql = "SELECT * FROM nominas WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                n.setId(resultSet.getInt("id"));
                n.setDni(resultSet.getString("dni"));
                n.setSueldoBase(resultSet.getDouble("sueldo_base"));
                n.setSueldo(resultSet.getDouble("sueldo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    // obtener nomina por dni
    public Nominas obtenerNominaPorDni(String dni) throws SQLException {
        ResultSet resultSet = null;
        Nominas n = new Nominas();

        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            sql = "SELECT * FROM nominas WHERE dni = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, dni);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                n.setId(resultSet.getInt("id"));
                n.setDni(resultSet.getString("dni"));
                n.setSueldoBase(resultSet.getDouble("sueldo_base"));
                n.setSueldo(resultSet.getDouble("sueldo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }
    
    // obtener conexion pool
    private Connection obtenerConexion() throws SQLException {
        return Conexion.getConnection();
    }
}
