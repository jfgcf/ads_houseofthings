package device.service;

import common.Util;
import device.model.Device;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Service responsible for the backend communication.
 */
public class BackendService {

    /**
     * Calls the backend API to add a device.
     */
    public static void addDevice(Device device) throws MalformedURLException {
        String postDeviceEndpoint = System.getenv("BACKEND_ENDPOINT") + "/devices";
        String response = Util.post(new URL(postDeviceEndpoint), device.toDTO());
        System.out.println("Post device backend response: " + response);
    }
}
