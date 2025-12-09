package com.phonestore.utils;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.phonestore.model.FacebookPojo;

public class FacebookUtils {

    // 1. Kiểm tra kỹ lại 2 mã này (Đảm bảo không thừa dấu cách)
    public static final String FACEBOOK_APP_ID = "844269028190324";
    public static final String FACEBOOK_APP_SECRET = "76987cf61d2660ed2afa0622184151b3";

    // 2. Đường dẫn này PHẢI GIỐNG Y HỆT đường dẫn bạn đã khai báo trên Facebook Developers
    // (Không được sai một ký tự nào, kể cả dấu / cuối cùng)
    public static final String FACEBOOK_REDIRECT_URL = "http://localhost:8080/PhoneStore_war_exploded/login-facebook";

    public static final String FACEBOOK_LINK_GET_TOKEN = "https://graph.facebook.com/v19.0/oauth/access_token";

    public static String getToken(final String code) throws IOException {
        String link = String.format("%s?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s",
                FACEBOOK_LINK_GET_TOKEN, FACEBOOK_APP_ID, FACEBOOK_APP_SECRET, FACEBOOK_REDIRECT_URL, code);

        // Gửi request lên Facebook
        String response = Request.Get(link).execute().returnContent().asString();

        // In kết quả ra Console để kiểm tra lỗi
        System.out.println("Facebook Token Response: " + response);

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

        // Kiểm tra xem có lấy được access_token không
        if (jobj.has("access_token")) {
            return jobj.get("access_token").getAsString();
        } else {
            // Nếu lỗi, ném ra ngoại lệ để Servlet bắt được
            throw new IOException("Không lấy được Token. Lỗi từ Facebook: " + response);
        }
    }

    public static FacebookPojo getUserInfo(final String accessToken) throws IOException {
        String link = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        System.out.println("Facebook User Info: " + response); // In thông tin user ra console
        return new Gson().fromJson(response, FacebookPojo.class);
    }
}