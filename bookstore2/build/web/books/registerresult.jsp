<%-- 
    Document   : registerresult
    Created on : 2013-5-23, 0:16:24
    Author     : Yecfly
--%>

<%@page contentType="text/html" pageEncoding="GBK"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>JSP Page</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${param.success == 1}">
                <h2>Finish register!</h2>
                <p><a href="/bookstore2/books/bookstore"> Return Home</a></p>
            </c:when>
            <c:otherwise>
                <h2>Fail to register!</h2>
                <p><a href="/bookstore2/books/bookstore"> Return Home</a></p>
            </c:otherwise>
        </c:choose>
    </body>
</html>
