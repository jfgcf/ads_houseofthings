package common.model.dto;

import common.model.device.DeviceStatus;
import common.model.device.DeviceType;
import common.model.device.SensorReading;

import java.net.URL;

/**
 * Class that holds the state of a sensor.
 */
public class SensorDTO extends DeviceDTO {
    private SensorReading reading;

    public SensorDTO(String name, DeviceStatus status, DeviceType type, URL endpoint, SensorReading reading) {
        super(name, status, type, endpoint);
        this.reading = reading;
    }

    public SensorReading getReading() {
        return reading;
    }

    public void setReading(SensorReading reading) {
        this.reading = reading;
    }
}
