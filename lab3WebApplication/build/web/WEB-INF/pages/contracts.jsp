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
        <title>Контракты</title>
        
                <script language="javascript">
                
function editWorker(id){
    document.workerEditForm.action = "<%=request.getContextPath()+"/editWorker"%>"+"?id="+id+"&name="+document.workerEditForm.nameField.value+"&surname="+document.workerEditForm.surnameField.value+"&lastname="+document.workerEditForm.lastnameField.value;
    document.workerEditForm.submit();
//    window.parent.location = window.parent.location.href;
}

                </script>
        
    </head>
    <body>
        <form name="workerEditForm" method="post" align = "center">
            Текущее имя сотрудника: <input type="text" size="35"  name="nameField" value = " <%=request.getAttribute("name")%> "><br>
            Текущая фамилия сотрудника: <input type="text" size="35"  name="surnameField" value = " <%=request.getAttribute("surname")%> "><br>
            Текущее отчество сотрудника: <input type="text" size="35"  name="lastnameField" value = " <%=request.getAttribute("lastname")%> "><br>
            <input type="hidden" name="id" value=<%=  request.getAttribute("workerId") %>>
            <input type="button" value="Принять исправления" onclick="editWorker( <%= request.getAttribute("workerId") %> );">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </form>
    </body>
</html>
