<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Editar Empleado</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<h1>Editar Empleado</h1>

<form action="controller" method="post">
    <input type="hidden" name="entidad" value="empleados">
    <input type="hidden" name="opcion" value="editar">

    <table border="1">
        <tr>
            <td>DNI:</td>
            <td>
                <input type="text" name="dni" value="${empleados.dni}" readonly>
            </td>
        </tr>
        <tr>
            <td>Nombre:</td>
            <td>
                <input type="text" name="nombre" value="${empleados.nombre}">
            </td>
        </tr>
        <tr>
            <td>Sexo:</td>
            <td>
                <select name="sexo" required>
                    <option value="">--Seleccione--</option>
                    <option value="M" ${empleados.sexo == 'M' ? 'selected' : ''}>Masculino</option>
                    <option value="F" ${empleados.sexo == 'F' ? 'selected' : ''}>Femenino</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Categoría:</td>
            <td>
                <select name="categoria" required>
                    <option value="">--Seleccione--</option>
                    <c:forEach var="cat" items="${categorias}">
                        <option value="${cat}" ${cat == empleados.categoria ? 'selected' : ''}>${cat}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Años:</td>
            <td>
                <input type="number" name="anyos" value="${empleados.anyos}">
            </td>
        </tr>
    </table>
    <div class="volver">
        <a href="${pageContext.request.contextPath}/index.jsp">? Volver al menú principal</a>
    </div>
    <input type="submit" value="Guardar">
</form>

</body>
</html>
