<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession currentSession = request.getSession(false);
    if (currentSession == null || currentSession.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String username = (String) currentSession.getAttribute("user");
    String role = (String) currentSession.getAttribute("role");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome to the System</title>
</head>
<body>
    <h2>Welcome, <%= username %></h2>
    <p>You are logged in as <%= role %></p>
    <p><a href="logout.jsp">Logout</a></p>
</body>
</html>
