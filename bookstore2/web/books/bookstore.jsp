<%--
 * Copyright (c) 2002 Sun Microsystems, Inc.  All rights reserved.  U.S. 
 * Government Rights - Commercial software.  Government users are subject 
 * to the Sun Microsystems, Inc. standard license agreement and 
 * applicable provisions of the FAR and its supplements.  Use is subject 
 * to license terms.  
 * 
 * This distribution may include materials developed by third parties. 
 * Sun, Sun Microsystems, the Sun logo, Java and J2EE are trademarks 
 * or registered trademarks of Sun Microsystems, Inc. in the U.S. and 
 * other countries.  
 * 
 * Copyright (c) 2002 Sun Microsystems, Inc. Tous droits reserves.
 * 
 * Droits du gouvernement americain, utilisateurs gouvernementaux - logiciel
 * commercial. Les utilisateurs gouvernementaux sont soumis au contrat de 
 * licence standard de Sun Microsystems, Inc., ainsi qu'aux dispositions 
 * en vigueur de la FAR (Federal Acquisition Regulations) et des 
 * supplements a celles-ci.  Distribue par des licences qui en 
 * restreignent l'utilisation.
 * 
 * Cette distribution peut comprendre des composants developpes par des 
 * tierces parties. Sun, Sun Microsystems, le logo Sun, Java et J2EE 
 * sont des marques de fabrique ou des marques deposees de Sun 
 * Microsystems, Inc. aux Etats-Unis et dans d'autres pays.
--%>
<c:choose>
    <c:when test="${!empty sessionScope.account}">
        <table>
            <tr>
                <td>
                    <h2> Hello ! ${sessionScope.account.name}</h2>
                </td>
                <td>
                    <form name = "Logout" action = "/bookstore2/LoginHandler" method = "post">
                        <input type = "hidden" name = "logout" value = "true"/>
                        <input type="submit" name="loginout" id value = "loginout" size="5"/>
                    </form>
                </td>
            </tr>
        </table>
    </c:when>
    <c:otherwise>
        <table>
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
    </c:otherwise>
</c:choose>
<p><b><fmt:message key="What"/></b></p>


<jsp:useBean id="bookDB" class="com.sun.bookstore2.database.BookDB" scope="page" >

    <jsp:setProperty name="bookDB" property="database" value="${bookDBAO}" />


</jsp:useBean>



<jsp:setProperty name="bookDB" property="bookId" value="203" />



<p>
    <c:url var="url" value="/books/bookdetails" />
<blockquote><p><em><a href="${url}?bookId=203">${bookDB.book.title}</a></em>,
            <c:url var="url" value="/books/bookcatalog" />

    <fmt:message key="Talk"/></blockquote>

<p><b><a href="${url}?Add="><fmt:message key="Start"/></a></b>




