package common.model.device;

import java.util.Objects;

/**
 * Class that represents a sensor reading.
 */
public class SensorReading {
    private final SensorReadingType type;
    private final Integer value;
    private final String deviceName;

    public SensorReading(SensorReadingType type, Integer value, String deviceName) {
        this.type = type;
        this.value = value;
        this.deviceName = deviceName;
    }

    public SensorReadingType getType() {
        return type;
    }
    public Integer getValue() {
        return value;
    }
    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorReading that = (SensorReading) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
