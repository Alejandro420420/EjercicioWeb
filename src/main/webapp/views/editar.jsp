<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Editar Empleado</title>
</head>
<body>
<h1>Editar Empleado</h1>

<form action="ejercicio" method="post">
    <input type="hidden" name="opcion" value="editar">

    <table border="1">
        <tr>
            <td>DNI:</td>
            <td>
                <input type="text" name="dni" value="${ejercicio.dni}" readonly>
            </td>
        </tr>
        <tr>
            <td>Nombre:</td>
            <td>
                <input type="text" name="nombre" value="${ejercicio.nombre}">
            </td>
        </tr>
        <tr>
            <td>Sexo:</td>
            <td>
                <input type="text" name="sexo" value="${ejercicio.sexo}">
            </td>
        </tr>
        <tr>
            <td>Categoría:</td>
            <td>
                <input type="text" name="categoria" value="${ejercicio.categoria}">
            </td>
        </tr>
        <tr>
            <td>Años:</td>
            <td>
                <input type="number" name="anyos" value="${ejercicio.anyos}">
            </td>
        </tr>
    </table>

    <input type="submit" value="Guardar">
</form>

</body>
</html>
