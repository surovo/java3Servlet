<%-- 
    Document   : contracts
    Created on : 26.12.2013, 3:09:49
    Author     : ivan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form name="name" method="post" align = "center">
            Текущее имя сотрудника: <input type="text" size="35"  name="nameField" value = " <%=request.getAttribute("name")%> "><br>
            Текущая фамилия сотрудника: <input type="text" size="35"  name="surnameField" value = " <%=request.getAttribute("surname")%> "><br>
            Текущее отчество сотрудника: <input type="text" size="35"  name="lastnameField" value = " <%=request.getAttribute("lastname")%> "><br>
            
        </form>
    </body>
</html>
