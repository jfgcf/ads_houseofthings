package service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SlackUtil {

    //Sends a POST Request , JSON format to SlackWebhook BOT
    public static void ToSlack(String message) {
        String query_url = ""; //Desligado até arranjar solução para n expor a API Key
        String json = SlackMessage.Slackmessageconnect(message);
        try {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e); //in case of error
        }
    }

}
