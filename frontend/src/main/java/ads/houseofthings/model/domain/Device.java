package ads.houseofthings.model.domain;

import java.io.Serializable;

public class Device implements Serializable {

    public String name;
    public String type;
    public String status;
    public String mode;
    public DeviceReading reading;
    public Number targetTemperature;
    public String endpoint;

    public Device() {
    }

    public Device(String name, String status, String type, String mode, DeviceReading reading, Number targetTemperature, String endpoint) {
        this.name = name;
        this.status = status;
        this.type = type;
        this.mode = mode;
        this.reading = reading;
        this.targetTemperature = targetTemperature;
        this.endpoint = endpoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Number getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Number targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public DeviceReading getReading() {
        return reading;
    }

    public void setReading(DeviceReading reading) {
        this.reading = reading;
    }
}