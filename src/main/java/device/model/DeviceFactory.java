package device.model;

import common.Util;
import common.model.device.DeviceStatus;
import common.model.device.DeviceType;
import device.model.airconditioner.AirConditioner;
import device.model.curtain.Curtain;
import device.model.temperaturesensor.TemperatureSensor;
import device.service.BackendService;

import java.net.URL;

/**
 * Factory responsible for creating the server's device.
 */
public final class DeviceFactory {
    private static Device device;

    /**
     * Initializes the server's device.
     */
    public static synchronized Device getDevice() {
        try {
            if (device == null) {
                System.out.println("Initializing the server's device...");
                Device tmp = null;

                String name = System.getenv("DEVICE_NAME");
                DeviceType type = DeviceType.valueOf(System.getenv("DEVICE_TYPE"));

                switch (type) {
                    case AIR_CONDITIONER:
                        tmp = new AirConditioner(name, DeviceStatus.STANDBY);
                        break;
                    case TEMPERATURE_SENSOR:
                        tmp = new TemperatureSensor(name, DeviceStatus.ON);
                        break;
                    case CURTAIN:
                        tmp = new Curtain(name, DeviceStatus.CLOSED);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown device type!");
                };

                // registers this device on the backend
                BackendService.addDevice(tmp);

                // only assign to the static variable after everything works
                device = tmp;
                System.out.println("Device initialized");
            }
            return device;
        } catch(Exception e) {
            throw new RuntimeException("Error initializing device", e);
        }
    }
}
