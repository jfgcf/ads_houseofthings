package ads.houseofthings.model.domain;

import java.io.Serializable;

public class DeviceReading implements Serializable {

    public String type;
    public Number value;
    public String deviceName;

    public DeviceReading() {
    }

    public DeviceReading(String type, Number value, String deviceName) {
        this.type = type;
        this.value = value;
        this.deviceName = deviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}