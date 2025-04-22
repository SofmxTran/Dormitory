package servlet;

import dao.RoomDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;

public class BookRoomServlet extends HttpServlet {
    private RoomDAO roomDAO = new RoomDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null && user.getRole().equals("student")) {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            try {
                boolean booked = roomDAO.bookRoom(user.getId(), roomId);
                if (booked) {
                    request.setAttribute("message", "Room booked successfully");
                } else {
                    request.setAttribute("error", "Room is already booked");
                }
                request.getRequestDispatcher("studentDashboard.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Booking failed");
                request.getRequestDispatcher("studentDashboard.jsp").forward(request, response);
            }
        }
    }
}