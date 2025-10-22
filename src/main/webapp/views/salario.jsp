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
<h1>Listar Productos</h1>
<table border="1">
    <tr>
        <td>ID</td>
        <td>DNI</td>
        <td>Sueldo Base</td>
        <td>Sueldo</td>
    </tr>
    <c:forEach var="ejercicio" items="${lista}">
        <tr>
            <td>${nominas.id}</td>
            <td>
                <a href="ejercicio?opcion=editar&dni=${nominas.dni}">
                        ${nominas.dni}
                </a>
            </td>
            <td>${nominas.sueldo_base}</td>
            <td>${nominas.sueldo}</td>
        </tr>

    </c:forEach>
</table>
</body>
</html>
