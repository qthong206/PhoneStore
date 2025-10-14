package com.phonestore.filter;

import org.mindrot.jbcrypt.BCrypt;

public class GeneratePassword {
    public static void main(String[] args) {
        String passwordToHash = "12345";
        String hashedPassword = BCrypt.hashpw(passwordToHash, BCrypt.gensalt(12));

        System.out.println("Mật khẩu gốc: " + passwordToHash);
        System.out.println("Chuỗi hash (sao chép chuỗi này): " + hashedPassword);
    }
}