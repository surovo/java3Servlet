<%-- 
    Document   : contracts
    Created on : 26.12.2013, 3:09:49
    Author     : ivan
--%>

<%@page import="Contract.AbstractContract"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
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

function addContract(id){
    document.newContractForm.action = "<%=request.getContextPath()+"/addContract"%>"+"?id="+id+"&post="+document.newContractForm.postField.value+"&workplace="+document.newContractForm.workplaceField.value+"&budget="+document.newContractForm.budgetFlag.checked;
    document.newContractForm.submit();
}

function deleteContract(id, contractId){
    document.forms["deleteContractForm"+contractId].action = "<%=request.getContextPath()+"/deleteContract"%>"+"?id="+id+"&contractId="+contractId;
    document.forms["deleteContractForm"+contractId].submit();
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
        <br>
        
        
                <form name="newContractForm" method="post" align = "center">
            Должность: <input type="text" size="35"  name="postField" ><br>
            Место работы: <input type="text" size="35"  name="workplaceField" ><br>
            Бюджетный<input type="checkbox" name="budgetFlag" value="ON" checked="checked" />
            <input type="hidden" name="id" value=<%=  request.getAttribute("workerId") %>>
            <input type="button" value="Добавить контракт" onclick="addContract( <%= request.getAttribute("workerId") %> );">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </form>
        <br>
        
        <table width="60%" border="0" align="center" bgcolor="#eeeedd"
                   style="color: blue;font-style: oblique; border-left: solid olive; border-right: solid olive; border-bottom: solid olive; border-top: solid olive; font-weight: 700">
    <tr bgcolor="lightgray">
        <td> Тип </td>
        <td> Должность </td>
        <td> Место работы </td>
        <td> Сумма выплат </td>
        <td> Сумма налогов </td>
        <td> Итого </td>
        <td> Действие </td>
    </tr>
<%Iterator itr;%>
<% ArrayList data= (ArrayList)request.getAttribute("contractsList");
for (itr=data.iterator(); itr.hasNext(); )
{
%>
<% AbstractContract c = (AbstractContract)itr.next(); %>
<tr>
<td> <%= c.getType() %></td>
<td>  <%= c.getPost() %>  </td>
<td> <%= c.getWorkPlace() %> </td>
<td> <%= c.getRawSummWithDate(null, null) %>  </td>
<td> <%=  c.getRawSummWithDate(null, null) - c.getTotalWithDate(null, null) %></td>
<td> <%= c.getTotalWithDate(null, null) %></td>
<td>
        <form name="deleteContractForm<%= c.getContractUniqueId() %>" action="<%=request.getContextPath()+"/deleteContract"%>" method="post" align = "center">
        <input type="button" value="Удалить контракт" onclick="deleteContract(<%=  request.getAttribute("workerId") %> , <%= c.getContractUniqueId() %> );">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </form>
</td>
<td>  </td>

<%} %>


</table>

<br>

        
        
    </body>
</html>
