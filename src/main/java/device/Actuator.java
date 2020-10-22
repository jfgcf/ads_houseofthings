package device;

import home.DeviceLocation;

public abstract class Actuator extends Device {
    public Actuator(Integer id, String name, DeviceLocation location, DeviceStatus status) {
        super(id, name, location, status);
    }

    public abstract void onSensorReadingUpdate(SensorReading sensorReading);
}
