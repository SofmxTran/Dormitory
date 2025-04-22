<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession session = request.getSession();
    session.invalidate();
    response.sendRedirect("login.jsp");
%>
