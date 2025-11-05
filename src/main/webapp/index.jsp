<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Menú de Opciones</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<h1>Menu de Opciones Productos</h1>
<table border="1">
    <tr>
        <td><a href="controller?entidad=empleados&opcion=crear"> Implementar un empleado</a></td>
    </tr>
    <tr>
        <td><a href="controller?entidad=empleados&opcion=listar"> Listar Empleados</a></td>
    </tr>
    <tr>
        <td><a href="controller?entidad=nominas&opcion=consultarSalario"> Consultar Salario por DNI</a></td>
    </tr>
</table>
</body>
</html>