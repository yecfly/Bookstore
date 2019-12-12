<%-- 
    Document   : login
    Created on : 2013-6-6, 1:17:32
    Author     : Yecfly
--%>

<center><em><table>
            <tr>
                <td>
                    <form name = "login" action = "/bookstore2/LoginHandler" method = "post">
                        user: <input type="text" name="username" size="10"/>
                        password:<input type="password" name="password" size="10"/>
                        <c:if test="${param.login == 0}">
                            <span style="color: red">wrong username or password! </span>
                        </c:if>
                        <input type="submit" name="login" id value = "login" size="5"/>
                    </form>
                </td>
                <td>
                    <form name = "register" action = "/bookstore2/books/register.jsp" method = "post">
                        <input type="submit" name="register" value = "register" size="5"/>
                    </form>
                </td>
            </tr>
        </table>
    </em></center>