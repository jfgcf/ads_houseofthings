package ads.houseofthings.model.dao;

import ads.houseofthings.model.domain.Device;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DeviceDAO {

    private static final String JSON_URL = "http://localhost:8080/devices";

    public List<Device> listDevice() {
        List<Device> deviceList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            deviceList = new Gson().fromJson(readUrl(JSON_URL), new TypeToken<List<Device>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceList;
    }

    public boolean updateDevice(Device device) throws IOException {
        URL url;
        try {
            url = new URL(JSON_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            OutputStream outputStream = new ByteArrayOutputStream();
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            writer.beginObject();
            writer.name("targetTemperature").value(device.getTargetTemperature());
            writer.name("mode").value(device.getMode());
            writer.name("name").value(device.getName());
            writer.name("status").value(device.getStatus());
            writer.name("type").value(device.getType());
            writer.name("endpoint").value(device.getEndpoint());
            writer.endObject();
            writer.close();
            connection.getInputStream();
            return true;
        } catch (MalformedURLException | ProtocolException e) {
            return false;
        }
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}