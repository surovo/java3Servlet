<%-- 
    Document   : contracts
    Created on : 25.12.2013, 0:59:28
    Author     : ivan
--%>

<%@page import="Workers.Worker"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page language="java" import="java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:h="http://java.sun.com/jsf/html" xmlns:f="http://java.sun.com/jsf/core">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Налоговая служба (вариант 19) Козодой Иван группа В05-121</title>

        <script language="javascript">
                
function deleteWorker(id, form){
    document.forms["deleteWorkerForm"+id].action = "<%=request.getContextPath()+"/deleteWorker"%>"+"?id="+id;
    document.forms["deleteWorkerForm"+id].submit();
//    document.deleteWorkerForm.action = "<%=request.getContextPath()+"/deleteWorker"%>"+"?id="+id;
//    document.deleteWorkerForm.submit();
}

function checkNameAndAdd()
{
                   if (chkEdit()) {
                          
                          document.newWorkerForm.submit(); 
                   }
}

function chkEdit()
{
     if (document.newWorkerForm.nameField.value=="")
                 return false;
     else
                 return true;
}

function showContracts(id){
//    document.forms["contractsForm"+id].action = "<%=request.getContextPath()+"/contracts"%>"+id;
    document.forms["contractsForm"+id].submit();
}

        </script>
        
    </head>
    <body>

<form name="newWorkerForm" action="<%=request.getContextPath()+"/addWorker"%>" method="post" align = "center">
    Имя нового сотрудника: <input type="text" size="35"  name="nameField" value = >
    <input type="button" value="Добавить запись" onclick="checkNameAndAdd();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</form>


<table width="60%" border="0" align="center" bgcolor="#eeeedd"
                   style="color: blue;font-style: oblique; border-left: solid olive; border-right: solid olive; border-bottom: solid olive; border-top: solid olive; font-weight: 700">
    <tr bgcolor="lightgray">
        <td>Уникальный номер сотрудника</td>
        <td>Фамилия</td>
        <td>Имя</td>
        <td>Отчество</td>
        <td>Тип</td>
        <td>Всего выплат</td>
        <td>Всего Налогов</td>
        <td>Итого</td>
        <td>Правка и контракты</td>
        <td>Действие</td>
    </tr>
<%Iterator itr;%>
<% ArrayList data= (ArrayList)request.getAttribute("accountList");
for (itr=data.iterator(); itr.hasNext(); )
{
%>
<% Worker w = (Worker)itr.next(); %>
<tr>
<td> <%= w.getUniqueWorkerId() %></td>
<td>  <%= w.getWorkerSurName() %>  </td>
<td> <%= w.getWorkerName() %> </td>
<td> <%= w.getWorkerLastName() %>  </td>
<td> <%= w.getType() %></td>
<td> <%= w.getRawSummWithDate(null, null) %></td>
<td> <%= w.getLevy() %></td>
<td><%= w.getTotalWithDate(null, null) %> </td>
<td>

    <form name="contractsForm<%= w.getUniqueWorkerId() %>" action="<%=request.getContextPath()+"/contracts"%>" method="get" align = "center">
        <input type="button" value="Править" onclick="showContracts( <%=  w.getUniqueWorkerId() %> );">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="hidden" name="id" value=<%=  w.getUniqueWorkerId() %>>
    </form>

</td>
<td>

    <form name="deleteWorkerForm<%= w.getUniqueWorkerId() %>" action="<%=request.getContextPath()+"/deleteWorker"%>" method="post" align = "center">
        <input type="button" value="Удалить" onclick="deleteWorker( <%= w.getUniqueWorkerId() %> );">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </form>

</td>
</tr>
<%} %>


</table>
    </body>
</html>
