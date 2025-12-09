package com.phonestore.utils;

import java.io.IOException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.phonestore.model.GooglePojo;

public class GoogleUtils {
    // COPY THÔNG TIN TỪ GOOGLE CONSOLE CỦA BẠN DÁN VÀO ĐÂY
    public static final String GOOGLE_CLIENT_ID = "59981091402-upstrb9sq0tm8umk795aonl5ha1pivat.apps.googleusercontent.com";
    public static final String GOOGLE_CLIENT_SECRET = "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";

    // ĐƯỜNG DẪN NÀY PHẢI KHỚP TUYỆT ĐỐI VỚI "Authorized redirect URIs" TRÊN GOOGLE CONSOLE
    public static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/PhoneStore_war_exploded/login-google";

    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static final String GOOGLE_GRANT_TYPE = "authorization_code";

    public static String getToken(final String code) throws IOException {
        String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form()
                        .add("client_id", GOOGLE_CLIENT_ID)
                        .add("client_secret", GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", GOOGLE_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", GOOGLE_GRANT_TYPE)
                        .build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.get("access_token").getAsString();
    }

    public static GooglePojo getUserInfo(final String accessToken) throws IOException {
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        return new Gson().fromJson(response, GooglePojo.class);
    }
}