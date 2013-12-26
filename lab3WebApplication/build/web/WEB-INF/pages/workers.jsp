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
        <title>Пример веб-приложения на JSP и сервлетах</title>

        <script language="javascript">
         
    
        function makeRequest(url,nz,tgt)
        {

	var httpRequest;

	if (window.XMLHttpRequest) { // Mozilla, Safari, ...
		httpRequest = new XMLHttpRequest();
		if (httpRequest.overrideMimeType) {
			httpRequest.overrideMimeType('text/xml');
					}
	}
	else if (window.ActiveXObject) { // IE
		try {
			httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e) {
			try {
				httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e) {}
		}
	}

	if ((httpRequest==null)||(httpRequest==undefined)) {
		tgt.innerHTML='Невозможно создать XML-объект!';
		return false;
	}

	httpRequest.onreadystatechange = function() { alertContents(httpRequest,tgt); };
	httpRequest.open('GET', url+'?id='+nz, true);
	httpRequest.send('');

}
/* Значения readyState
0 (Неинициализирован)
1 (Инициализирован)
2 (Отправлен)
3 (Загружается)
4 (Загружен)
 */
            
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
    Имя нового сотрудника: <input type="text" size="35"  name="nameField">
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
<td> <input  type =   "text" name = "surname" value = <%= w.getWorkerSurName() %>  ></td>
<td> <input  type =   "text" name = "name" value = <%= w.getWorkerName() %>  ></td>
<td> <input  type =   "text" name = "lastname" value = <%= w.getWorkerLastName() %>  ></td>
<td> <%= w.getType() %></td>
<td> <%= w.getRawSummWithDate(null, null) %></td>
<td> <%= w.getLevy() %></td>
<td><%= w.getTotalWithDate(null, null) %> </td>
<td>

    <form name="contractsForm<%= w.getUniqueWorkerId() %>" action="<%=request.getContextPath()+"/contracts"%>" method="get" align = "center">
        <!--<input type="button" value="ghfdbnm" onclick="makeRequest(' <%= request.getContextPath()+"/deleteWorker" %> ' , ' <%= w.getUniqueWorkerId() %> ' , null );">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
        <input type="button" value="Удалить" onclick="showContracts( <%=  w.getUniqueWorkerId() %> );">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
