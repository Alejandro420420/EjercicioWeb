<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Consultar Salario</title>
</head>
<body>
<h1>Consultar Salario de Empleado</h1>


<div class="formulario">
    <h3>Ingrese el DNI del empleado</h3>
    <form action="${pageContext.request.contextPath}/nominas" method="post">
        <input type="hidden" name="opcion" value="consultarSalario">
        <div class="campo">
            <label for="dni">DNI:</label>
            <input type="text" id="dni" name="dni" required placeholder="Ej: 32000031R">
        </div>
        <button type="submit" class="boton">Consultar Salario</button>
    </form>
</div>


<c:if test="${not empty nomina}">
    <div class="resultado">
        <h3>Información del Salario</h3>
        <p><strong>ID Nómina:</strong> ${nomina.id}</p>
        <p><strong>DNI:</strong> ${nomina.dni}</p>
        <p><strong>Sueldo Base:</strong> ${nomina.sueldoBase} €</p>
        <p><strong>Sueldo Total:</strong> ${nomina.sueldo} €</p>
    </div>
</c:if>


<c:if test="${not empty error}">
    <div class="error">
        <strong>Error:</strong> ${error}
    </div>
</c:if>

<div class="volver">
    <a href="${pageContext.request.contextPath}/index.jsp">← Volver al menú principal</a>
</div>
</body>
</html>