package device;

public class SensorReading {
    private Sensor sensor;
    private SensorReadingType type;
    private Integer value;

    public SensorReading(Sensor sensor, SensorReadingType type, Integer value) {
        this.sensor = sensor;
        this.type = type;
        this.value = value;
    }

    public SensorReadingType getType() {
        return type;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public Integer getValue() {
        return value;
    }
}
