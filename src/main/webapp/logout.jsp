<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession session = request.getSession();
    session.invalidate(); // Hủy session
    response.sendRedirect("login.jsp"); // Quay lại trang login
%>
