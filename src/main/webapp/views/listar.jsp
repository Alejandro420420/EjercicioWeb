<%--
  Created by IntelliJ IDEA.
  User: usuario
  Date: 08/10/2025
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Listar Productos</title>
</head>
<body>
<h1>Listar Productos</h1>
<table border="1">
    <tr>
        <td>Id</td>
        <td>Nombre</td>
        <td>Cantidad</td>
        <td>Precio</td>
        <td>Fecha Creacion</td>
        <td>Fecha Actualizacion</td>
        <td>Accion</td>
    </tr>
    <c:forEach var="producto" items="${lista}">
        <tr>
            <td>
                <a href="productos?opcion=meditar&id=<c:out value="${ producto.id}"></c:out>">
                    <c:out value="${ producto.id}"></c:out>
                </a>
            </td>
            <td><c:out value="${ producto.nombre}"></c:out></td>
            <td><c:out value="${ producto.cantidad}"></c:out></td>
            <td><c:out value="${ producto.precio}"></c:out></td>
            <td><c:out value="${ producto.fechaCrear}"></c:out></td>
            <td><c:out value="${ producto.fechaActualizar}"></c:out></td>
            <td>
                <a href="productos?opcion=eliminar&id=<c:out value="${ producto.id}"></c:out>">
                    Eliminar
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>