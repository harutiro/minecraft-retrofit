package com.example.examplemod.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

public class HttpApi {
    /// APIのベースURL
    public String baseUrl = "https://example.com/";
    private static final Logger LOGGER = LogManager.getLogger();

    public String getData(String path) {
        // APIのURLを指定
        String apiUrl = baseUrl + path; // APIの実際のURLに置き換える

        try {
            // URLオブジェクトを作成
            URL url = new URL(apiUrl);

            // HttpURLConnectionを作成
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // レスポンスを取得
            int responseCode = conn.getResponseCode();
            LOGGER.info("HTTPステータスコード: " + responseCode);

            // レスポンスボディを取得
            String responseBody = "";
            try (java.io.InputStream in = conn.getInputStream()) {
                byte[] body = in.readAllBytes();
                responseBody = new String(body);
            }
            // 接続を閉じる
            conn.disconnect();

            return responseBody;
        } catch (Exception e) {
            LOGGER.error("GETリクエスト送信中にエラーが発生しました。", e);
            return "";
        }
    }

    public void postData(String jsonBody, String path) {
        // APIのURLを指定
        String apiUrl = baseUrl + path; // APIの実際のURLに置き換える

        try {
            // URLオブジェクトを作成
            URL url = new URL(apiUrl);

            // HttpURLConnectionを作成
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // リクエストボディを書き込む
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // レスポンスを取得
            int responseCode = conn.getResponseCode();
            LOGGER.info("HTTPステータスコード: " + responseCode);

            // 接続を閉じる
            conn.disconnect();
        } catch (Exception e) {
            LOGGER.error("POSTリクエスト送信中にエラーが発生しました。", e);
        }
    }
}
