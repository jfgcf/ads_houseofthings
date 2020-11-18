package backend.model;

import backend.service.DeviceService;
import common.model.device.SensorReading;
import common.model.dto.DeviceDTO;
import common.model.dto.SensorDTO;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents the device's Home.
 */
public final class Home {
    private static final Set<DeviceDTO> devices = new HashSet<>();

    /**
     * Returns all the known devices.
     */
    public static Set<DeviceDTO> getDevices() {
        return Collections.unmodifiableSet(devices);
    }

    /**
     * Returns the devices depending if they are or are not a sensor.
     */
    public static Set<DeviceDTO> getDevices(boolean isSensor) {
        Set<DeviceDTO> result = new HashSet<>();
        for (DeviceDTO dto : getDevices()) {
            if (dto instanceof SensorDTO == isSensor) {
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * Adds a device to the list.
     */
    public static void addDevice(DeviceDTO device) {
        System.out.println("Adding / updating device name: " + device.getName());
        // make sure we replace the device when an item with the same hash is already present
        devices.remove(device);
        devices.add(device);
    }

    /**
     * Edits a device.
     */
    public static void editDevice(DeviceDTO device) {
        System.out.println("Editing device name: " + device.getName());

        DeviceDTO targetDevice = null;
        for (DeviceDTO deviceDTO: devices) {
            if (deviceDTO.equals(device)) {
                targetDevice = deviceDTO;
                break;
            }
        }
        if (targetDevice == null) {
            throw new RuntimeException("Device not found");
        }
        DeviceService.edit(device);
    }


    /**
     * Refreshes all the device information.
     *
     */
    public static void refreshAll() {
        System.out.println("Started refreshing devices...");
        Set<SensorReading> updatedReadings = new HashSet<>();

        // First we refresh the sensors readings
        Set<DeviceDTO> sensors = getDevices(true);
        for(DeviceDTO sensor: sensors) {
            // calls the device backend to get the updated sensor data
            SensorDTO updatedSensor = (SensorDTO) DeviceService.refresh(sensor);
            // adds the reading to the set
            updatedReadings.add(updatedSensor.getReading());
            // replaces the existing sensor with the updated one
            addDevice(updatedSensor);
        }

        // then pass the readings to the other devices
        Set<DeviceDTO> nonSensors = getDevices(false);
        for(DeviceDTO device: nonSensors) {
            // calls the device backend to post the readings and get the updated device data
            DeviceDTO updatedDevice = DeviceService.postSensorReadings(device, updatedReadings);
            // replaces the existing device with the updated one
            addDevice(updatedDevice);
        }
        System.out.println("finished refreshing devices.");
    }
}
