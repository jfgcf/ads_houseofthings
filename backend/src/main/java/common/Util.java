package common;

import com.google.gson.Gson;
import common.model.device.DeviceType;
import common.model.dto.AirConditionerDTO;
import common.model.dto.CurtainDTO;
import common.model.dto.DeviceDTO;
import common.model.dto.SensorDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Random;

/**
 * Static class with common utility methods.
 */
public final class Util {
    private static URL endpoint;

    /**
     * Returns the current endpoint URL.
     */
    public static URL getEndpoint() {
        try {
            if (endpoint == null) {
                endpoint = new URL(System.getenv("ENDPOINT"));
            }
            return endpoint;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing endpoint URL", e);
        }
    }

    /**
     * Returns the current endpoint's port number.
     */
    public static Integer getPort() {
        URL endpoint = getEndpoint();
        return endpoint.getPort();
    }


    /**
     * Transforms an object into a json string.
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }


    /**
     * Transforms the request object on a json object.
     */
    public static <T> T getJsonFromBody(HttpServletRequest request, Class<T> clazz) throws IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        return gson.fromJson(reader, clazz);
    }

    /**
     * Transforms the json string on a json object.
     */
    public static <T> T getJsonFromString(String json, Class<T> clazz) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    /**
     * From a hashmap containing the JSON data we transform into a DeviceDTO.
     */
    public static DeviceDTO map2dto(HashMap map) throws IOException {
        // parses type field
        DeviceType type = DeviceType.valueOf((String) map.get("type"));

        // Transforms the value back to json
        String deviceJson = Util.toJson(map);

        // transforms into the appropriate DTO
        DeviceDTO device = null;

        switch (type) {
            case AIR_CONDITIONER:
                device = getJsonFromString(deviceJson, AirConditionerDTO.class);
                break;
            case TEMPERATURE_SENSOR:
                device = getJsonFromString(deviceJson, SensorDTO.class);
                break;
            case CURTAIN:
                device = getJsonFromString(deviceJson, CurtainDTO.class);
                break;
            case AMBIENT_SENSOR:
                device = getJsonFromString(deviceJson, SensorDTO.class);
                break;
            default:
                throw new IllegalArgumentException("Unknown device type!");
        };

        return device;
    }

    /**
     * Builds a device from the request body.
     */
    public static DeviceDTO request2device(HttpServletRequest req) throws IOException {
        // initially loads the json into a map as we don't know which DTO to use
        HashMap map = getJsonFromBody(req, HashMap.class);

        // transforms the map into a dto
        return map2dto(map);
    }

    /**
     * Builds a device from the response json.
     */
    public static DeviceDTO response2device(String deviceJson) throws IOException {
        HashMap map = Util.getJsonFromString(deviceJson, HashMap.class);
        return Util.map2dto(map);
    }

    /**
     * Executes a POST request.
     */
    public static String post(URL url, Object body) {
        return request(url, "POST", body);
    }

     /**
     * Executes a GET request.
     */
    public static String get(URL url) {
        return request(url, "GET", null);
    }

    /**
     * Executes a PUT request.
     */
    public static String put(URL url, Object body) {
        return request(url, "PUT", body);
    }

    /**
     * Executes a POST request.
     */
    public static String request(URL url, String method, Object body) {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection)url.openConnection();
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);
            con.setRequestMethod(method);
            con.setRequestProperty("Accept", "application/json");

            if (body != null) {
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setDoOutput(true);
                String jsonInputString = toJson(body);
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            return response.toString();

        } catch(Exception e) {
            throw new RuntimeException("Error making request to " + url + ": " + e.getMessage(), e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    /**
     * Transforms the informed object to JSON and returns an response body.
     */
    public static void respondJson(Object obj, HttpServletResponse resp) throws IOException {
        // transform the object to json
        String json = Util.toJson(obj);

        // writes to the response body
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        printWriter.write(json);
        printWriter.close();
    }

    /**
     * Utility method to generate random numbers used by the subclasses
     */
    public static Integer getRandomNumberUsingNextInt(Integer min, Integer max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
