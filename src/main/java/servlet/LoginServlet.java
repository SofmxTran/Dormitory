package servlet;

import dao.UserDAO;
import model.User;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Check user credentials against database
            User user = userDAO.getUser(username, password);

            if (user != null) {
                // Invalidate any existing session to prevent hijacking
                HttpSession session = request.getSession(true); // true to create a new session if one doesn't exist
                session.setAttribute("user", user);  // Store user info in session

                // Redirect to the appropriate dashboard based on user role
                if (user.getRole().equals("manager")) {
                    response.sendRedirect("managerDashboard.jsp");
                } else {
                    response.sendRedirect("studentDashboard.jsp");
                }
            } else {
                // If user is not found, show error message
                request.setAttribute("error", "Invalid credentials");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle database errors or other exceptions
            request.setAttribute("error", "Database error");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
