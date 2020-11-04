package service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class SlackUtil {


    //Sends a POST Request , JSON format to SlackWebhook BOT
    public static void ToSlack(String message) {
        String query_url = ReadAPKeyTXT(); //return api url using the method to read local file.
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
            InputStream in = new BufferedInputStream(conn.getInputStream()); //enviar o pedido POST
            in.close();
            conn.disconnect(); //desconectar
        } catch (Exception e) {
            System.out.println("Error sending request"); //in case of error
        }
    }

    //to read SlackAPI from local file. File is local due to safety reasons.
    public static String ReadAPKeyTXT(){

        String data= "";

        try {
            File SlackAPIKey = new File("/Users/jfgcf/Documents/SlackAPI.txt");
            Scanner myReader = new Scanner(SlackAPIKey);
            data = myReader.nextLine(); //read first line of the file
            myReader.close();
            //exception in case file is not found
        } catch (FileNotFoundException e) {
            System.out.println("File not Found.");
            e.printStackTrace();
        }

        return data;


    }

}
