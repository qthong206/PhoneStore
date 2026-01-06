package com.phonestore.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    private final String fromEmail = "22130275@st.hcmuaf.edu.vn";
    private final String appPassword = "lxbr epnb oqtz zlff";

    public void sendPasswordResetEmail(String toEmail, String fullName, String newPassword) {
        // 1. Cấu hình Server SMTP của Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // 2. Xác thực tài khoản
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            // 3. Tạo nội dung thư
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Khôi phục mật khẩu - PhoneStore");

            String htmlContent = "<div style='font-family: Arial, sans-serif;'>"
                    + "<h3>Chào " + fullName + ",</h3>"
                    + "<p>Bạn đã yêu cầu cấp lại mật khẩu cho tài khoản tại <b>PhoneStore</b>.</p>"
                    + "<p>Mật khẩu mới của bạn là: <span style='color:red; font-weight:bold;'>" + newPassword + "</span></p>"
                    + "<p>Vui lòng đăng nhập và đổi mật khẩu ngay để bảo mật.</p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");

            // 4. Gửi mail
            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendWelcomeEmail(String toEmail, String fullName) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Chào mừng bạn đến với PhoneStore!");

            String htmlContent = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #e0e0e0; padding: 20px;'>"
                    + "<h2 style='color: #2c3e50;'>Chào mừng thành viên mới!</h2>"
                    + "<p>Xin chào <strong>" + fullName + "</strong>,</p>"
                    + "<p>Cảm ơn bạn đã lựa chọn đăng nhập và trải nghiệm dịch vụ tại <strong>PhoneStore</strong> qua tài khoản Google.</p>"
                    + "<p>Tài khoản của bạn đã được khởi tạo thành công. Bạn có thể bắt đầu mua sắm những sản phẩm công nghệ mới nhất ngay bây giờ.</p>"
                    + "<div style='text-align: center; margin: 30px 0;'>"
                    + "<a href='http://localhost:8080/PhoneStore_war_exploded/home' style='background-color: #3498db; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px;'>Khám phá ngay</a>"
                    + "</div>"
                    + "<p style='font-size: 0.9rem; color: #7f8c8d;'>Trân trọng,<br>Đội ngũ hỗ trợ PhoneStore</p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            System.out.println("Welcome Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}