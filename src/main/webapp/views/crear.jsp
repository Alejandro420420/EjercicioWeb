<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Crear Producto</title>
</head>
<body>
<h1>Crear Producto</h1>
<form action="ejercicio" method="post">
    <input type="hidden" name="opcion" value="guardar">
    <table border="1">
        <tr>
            <td>DNI:</td>
            <td><input type="text" name="dni" size="50" required></td>
        </tr>
        <tr>
            <td>Nombre:</td>
            <td><input type="text" name="nombre" size="50" required></td>
        </tr>
        <tr>
            <td>Sexo:</td>
            <td>
                <select name="sexo" required>
                    <option value="">--Seleccione--</option>
                    <option value="M">Masculino</option>
                    <option value="F">Femenino</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Categoria:</td>
            <td><input type="text" name="categoria" size="50" required></td>
        </tr>
        <tr>
            <td>Años:</td>
            <td><input type="number" name="anyos" min="0" required></td>
        </tr>
    </table>
    <input type="submit" value="Guardar">
</form>
</body>
</html>

