<%-- 
    Document   : register
    Created on : 2013-5-23, 0:04:41
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
        <p>
        <h1>
            Input your information:
        </h1>
    </p>
    <form name = "register" action = "/bookstore2/RegisterHandler" method = "post">
        <table>
            <tr>
                <td>
                    user:
                </td>
                <td>
                    <input type="text" name="username" size="20"/>
                </td>
            </tr>
            <tr>
                <td>
                    password:
                </td>
                <td>
                    <input type="password" name="password" size="20"/>
                </td>
            </tr>
            <tr>
                <td>
                    email:
                </td>
                <td>
                    <input type="text" name="email" size="10"/>
                    <c:if test="${!empty requestScope.error}">
                        <span style="color: red">${requestScope.infoR} input error! </span>
                    </c:if>
                </td>
            </tr>
        </table>
        <input type="submit" name="register" value = "register" size="10"/>
    </form>
    <p>
        <a href="/bookstore2/books/bookstore" > go back </a>
    </p>
</body>
</html>
