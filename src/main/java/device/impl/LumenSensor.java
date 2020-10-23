package device.impl;

import device.DeviceStatus;
import device.Sensor;
import device.SensorReading;
import device.SensorReadingType;
import home.DeviceLocation;

public class LumenSensor extends Sensor {

    public LumenSensor(Integer id, String name, DeviceLocation location, DeviceStatus status) {
        super(id, name, location, status);
    }

    @Override
    protected SensorReading getReading() {
        return new SensorReading(this, SensorReadingType.LUMEN, super.getRandomNumberUsingNextInt(0, 10));
    }
}
