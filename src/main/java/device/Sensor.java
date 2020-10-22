package device;

import home.DeviceLocation;

import java.util.Random;

public abstract class Sensor extends Device {
    public Sensor(Integer id, String name, DeviceLocation location, DeviceStatus status) {
        super(id, name, location, status);
    }

    protected abstract SensorReading getReading();

    public void startSensorReading() {
        SensorReading reading = this.getReading();
        // notify the observer of a new reading
        this.getLocation().onSensorReadingUpdate(reading);
    }


    // utility method to generate random numbers used by the subclasses

    protected Integer getRandomNumberUsingNextInt(Integer min, Integer max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
