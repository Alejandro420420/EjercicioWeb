package empresa.conexion;
import java.sql.*;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {
    private static BasicDataSource dataSource = null;

    public static Connection getConnection() throws SQLException {
        final String USER = "root";
        final String PASS = "usuario";
        final String DB_NAME = "ejercicio";
        final String CONN_URL = "jdbc:mariadb://localhost:3306/" + DB_NAME;
        Connection conn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MariaDB no encontrado", e);
        }
        conn = DriverManager.getConnection(CONN_URL, USER, PASS);
        return conn;

    }

    public static void close(Connection conn) throws SQLException {
        if (conn != null)
            conn.close();
    }

    public static void close(Statement st) throws SQLException {
        if (st != null)
            st.close();
    }

    public static void close(ResultSet rs) throws SQLException {
        if (rs != null)
            rs.close();
    }
}
