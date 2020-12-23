<%-- 
    Document   : EL_JSP
    Created on : Dec 23, 2020, 2:41:49 PM
    Author     : chand
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

  <%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>  


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EL Examples Page</title>
    </head>
    <body>
        <%
            String [] arr = {"Hello", "World","Here"};
            for(String s:arr){
                out.println(s);
            }
            out.println("This is a test Tag Out");
            %>
            
            <c:out value="This is a test Tag Out" />
            <%--  for(int j=1; j<=3; j++) --%>
            <c:forEach var="j" begin="1" end="1">  
            <br>Item <c:out value="${j}"/><p>  
            </c:forEach>  
            ${fn:toUpperCase('hello')}
            ${fn:toLowerCase('HELLO')}
            
            <sql:setDataSource var="myDBAccess"  user="hr" password="hr"
                               url="jdbc:oracle:thin:@//localhost:1521/XEPDB1"
                               driver="oracle.jdbc.OracleDriver"/>
            
            <sql:query dataSource="${myDBAccess}" var="rs">
                SELECT USERID, PASSWORD, USERNAME, INUSE FROM HR.AUTHENTICATIONTBL
            </sql:query>
                
               <table border="2" width="100%" contenteditable="true" style="text-align: center;">  
            <tr>  
                <th>User_ID</th>  
                <th>Password</th>  
                <th>User Name</th>  
                <th>inUSE</th>  
                
            </tr>  
            <c:forEach var="row" items="${rs.rows}">  
                <tr>  
                    <td ><c:out value="${row.userid}" /></td>  
                    <td><c:out value='${row.password}'/></td>  
                    <td><c:out value='${row.username}'/></td>  
                    <td><c:out value='${row.inuse}'/></td>  
                </tr>  
            </c:forEach>  
        </table>  
   
            
    </body>
</html>
