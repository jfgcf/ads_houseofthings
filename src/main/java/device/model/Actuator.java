package device.model;

import common.model.device.DeviceStatus;
import common.model.device.SensorReading;
import common.model.dto.DeviceDTO;

/**
 * Class that represents an actuator device.
 */
public abstract class Actuator extends Device {

    /**
     * Constructor.
     */
    public Actuator(String name, DeviceStatus status) {
        super(name, status);
    }

    /**
     * Perform an action on sensor readings.
     */
    public abstract void onSensorReadingUpdate(SensorReading[] sensorReadings);

    /**
     * Edits the device with the data from the DTO.
     */
    public abstract void edit(DeviceDTO device);
}
