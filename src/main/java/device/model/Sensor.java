package device.model;

import common.model.device.DeviceStatus;
import common.model.device.DeviceType;
import common.model.device.SensorReading;
import common.model.dto.SensorDTO;

/**
 * Class that represents a sensor.
 */
public abstract class Sensor extends Device {
    public Sensor(String name, DeviceStatus status) {
        super(name, status);
    }

    public abstract SensorReading getReading();

    /**
     * Common DTO generation for sensors.
     */
    public SensorDTO toDTO(DeviceType type) {
        return new SensorDTO(this.getName(), this.getStatus(), type, this.getEndpoint(), this.getReading());
    }

}
