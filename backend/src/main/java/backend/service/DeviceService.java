package backend.service;

import common.Util;
import common.model.device.DeviceStatus;
import common.model.device.SensorReading;
import common.model.dto.DeviceDTO;

import java.net.URL;
import java.util.Set;

public class DeviceService {

    /**
     * Refreshes the data of a device.
     */
    public static DeviceDTO refresh(DeviceDTO device) {
        try {
            String getDeviceEndpoint = device.getEndpoint() + "/device";
            String json = Util.get(new URL(getDeviceEndpoint));
            return Util.response2device(json);
        } catch(Exception e) {
            System.out.println("Unable to refresh device" + device.getName() + ": " + e.getMessage());
            device.setStatus(DeviceStatus.ERROR);
            return device;
        }
    }

    /**
     * Sends a list of sensor readings to the device.
     */
    public static DeviceDTO postSensorReadings(DeviceDTO device, Set<SensorReading> readings) {
        try {
            String getDeviceEndpoint = device.getEndpoint() + "/device/reading";
            String json = Util.post(new URL(getDeviceEndpoint), readings.toArray(new SensorReading[0]));
            return Util.response2device(json);
        } catch(Exception e) {
            System.out.println("Unable to post sensor readings" + device.getName() + ": " + e.getMessage());
            device.setStatus(DeviceStatus.ERROR);
            return device;
        }
    }

    /**
     * Edits a device.
     */
    public static void edit(DeviceDTO device) {
        try {
            String getDeviceEndpoint = device.getEndpoint() + "/device";
            Util.put(new URL(getDeviceEndpoint), device);
        } catch(Exception e) {
            System.out.println("Unable to edit device" + device.getName() + ": " + e.getMessage());
            device.setStatus(DeviceStatus.ERROR);
        }
    }
}
