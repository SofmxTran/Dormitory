<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Kiểm tra session và xác nhận người dùng đã đăng nhập
    HttpSession currentSession = request.getSession(false);  // Lấy session hiện tại nếu có, nếu không có thì trả về null

    if (currentSession == null || currentSession.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");  // Nếu session hoặc attribute "user" không tồn tại, chuyển hướng về login
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
