package com.phonestore.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    // --- THAY ĐỔI CÁC THÔNG SỐ NÀY CHO PHÙ HỢP ---
    private static final String HOSTNAME = "localhost";
    private static final String PORT = "3306";
    private static final String DBNAME = "phonestore_db";
    private static final String USERNAME = "root"; // User của MySQL
    private static final String PASSWORD = ""; // Mật khẩu của MySQL
    // ---------------------------------------------

    /**
     * Lấy và trả về một đối tượng Connection đến cơ sở dữ liệu.
     * @return Một đối tượng Connection hoặc null nếu có lỗi.
     */
    public static Connection getConnection() {
        // Tạo chuỗi kết nối (connection string)
        String url = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DBNAME
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


        try {
            // Nạp driver JDBC của MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Tạo và trả về kết nối
            return DriverManager.getConnection(url, USERNAME, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            // In ra lỗi nếu có vấn đề xảy ra (rất hữu ích khi debug)
            e.printStackTrace();
            return null;
        }
    }

    // --- (Tùy chọn) Đoạn code để kiểm tra kết nối ---
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Kết nối cơ sở dữ liệu thất bại!");
        }
    }
}