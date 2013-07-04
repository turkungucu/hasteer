<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglib/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<logic:messagesPresent message="false">
    <div class="messageCriticalWide">
        <ul id="dots">
            <html:messages id="error">
                <li><bean:write name="error" filter="false"/></li>
            </html:messages>
        </ul>
    </div>
</logic:messagesPresent>

<logic:messagesPresent message="true">
    <div class="messageSuccessWide">
        <html:messages id="msg" message="true">
            <bean:write name="msg" filter="false"/><br>
        </html:messages>
    </div>
</logic:messagesPresent>

<c:choose>
    <c:when test="${TasksForm.cmd eq null}">
        <table border="1">
            <tr><th>Task Id</th><th>Task name</th><th>Class name</th><th>Delay</th><th>Period</th><th colspan="3">Action</th></tr>
            <c:forEach var="t" items="${TasksForm.tasks}">
                <tr>
                    <td><c:out value="${t.taskId}" /></td>
                    <td><c:out value="${t.taskName}" /></td>
                    <td><c:out value="${t.className}" /></td>
                    <td><c:out value="${t.delay}" /></td>
                    <td><c:out value="${t.period}" /></td>
                    <td>
                        <c:choose>
                            <c:when test="${t.enabled eq true}">
                                <a href="/Hasteer/internal/Tasks.do?cmd=disable&taskId=<c:out value="${t.taskId}" />">Disable</a>
                            </c:when>
                            <c:otherwise>
                                <a href="/Hasteer/internal/Tasks.do?cmd=enable&taskId=<c:out value="${t.taskId}" />">Enable</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><a href="/Hasteer/internal/Tasks.do?cmd=view&taskId=<c:out value="${t.taskId}" />">Edit</a></td>
                    <td><a href="/Hasteer/internal/Tasks.do?cmd=remove&taskId=<c:out value="${t.taskId}" />">Remove</a></td>
                </tr>
            </c:forEach>
        </table>
        <p><html:link action="/internal/Tasks.do?cmd=view">Add New Task</html:link></p>
    </c:when>
    <c:otherwise>
        <html:form action="/internal/Tasks" method="post">
            <table>
                <tr>
                    <td id="label">
                        Task name:
                    </td>
                    <td>
                        <html:text property="taskName" />
                    </td>
                </tr>
                <tr>
                    <td id="label">
                        Class name:
                    </td>
                    <td>
                        <html:text property="className" />
                    </td>
                </tr>
                <tr>
                    <td id="label">
                        Delay:
                    </td>
                    <td>
                        <html:text property="delay" />
                    </td>
                </tr>
                <tr>
                    <td id="label">
                        Period:
                    </td>
                    <td>
                        <html:text property="period" />
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <html:submit value="Submit" styleClass="red-button" />
                    </td>
                </tr>
            </table>
            <p><html:link action="/internal/Tasks.do">Back to Tasks</html:link></p>
            <html:hidden property="taskId" />
            <html:hidden property="cmd" value="submit" />
        </html:form>
    </c:otherwise>
</c:choose>
