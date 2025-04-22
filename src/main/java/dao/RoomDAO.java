package dao;

import model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    public synchronized boolean bookRoom(int userId, int roomId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.setAutoCommit(false);
        try {
            // Check if room is available
            String checkSql = "SELECT is_occupied FROM rooms WHERE room_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, roomId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && !rs.getBoolean("is_occupied")) {
                // Update room status
                String updateSql = "UPDATE rooms SET is_occupied = TRUE WHERE room_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, roomId);
                updateStmt.executeUpdate();

                // Create booking record
                String bookSql = "INSERT INTO bookings (user_id, room_id, booking_date) VALUES (?, ?, CURDATE())";
                PreparedStatement bookStmt = conn.prepareStatement(bookSql);
                bookStmt.setInt(1, userId);
                bookStmt.setInt(2, roomId);
                bookStmt.executeUpdate();

                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public List<Room> getAvailableRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE is_occupied = FALSE";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("room_id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setCapacity(rs.getInt("capacity"));
                room.setOccupied(rs.getBoolean("is_occupied"));
                rooms.add(room);
            }
        }
        return rooms;
    }
}