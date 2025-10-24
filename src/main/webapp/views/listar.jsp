<%--
  Created by IntelliJ IDEA.
  User: usuario
  Date: 08/10/2025
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listar Empleados</title>
</head>
<body>
<h1>Listar Empleados</h1>
<table border="1">
    <tr>
        <td>DNI</td>
        <td>Nombre</td>
        <td>Sexo</td>
        <td>Categoria</td>
        <td>Años</td>
        <td>Acción</td>
    </tr>
    <c:forEach var="empleados" items="${lista}">
        <tr>
            <td>
                <a href="empleados?opcion=editar&dni=${empleados.dni}">
                        ${empleados.dni}
                </a>
            </td>
            <td>${empleados.nombre}</td>
            <td>${empleados.sexo}</td>
            <td>${empleados.categoria}</td>
            <td>${empleados.anyos}</td>
            <td>
                <a href="empleados?opcion=eliminar&dni=${empleados.dni}">
                    Eliminar
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="volver">
    <a href="${pageContext.request.contextPath}/index.jsp">← Volver al menú principal</a>
</div>
</body>
</html>
