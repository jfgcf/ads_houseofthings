package device.impl.smartlight;

import device.Actuator;
import device.DeviceStatus;
import device.SensorReading;
import device.SensorReadingType;
import home.DeviceLocation;

public class SmartLight extends Actuator {

    private Integer targetLumen = 5;

    public SmartLight(Integer id, String name, DeviceLocation location, DeviceStatus status) {
        super(id, name, location, status);
    }

    public Integer getTargetLumen() {
        return targetLumen;
    }

    public void setTargetLumen(Integer targetLumen) {
        if (targetLumen == null || targetLumen < 0 || targetLumen > 10) {
            throw new IllegalArgumentException("Choose a number from 0 (completely dark) to 10 (bright)");
        }

        this.targetLumen = targetLumen;
    }

    @Override
    public void onSensorReadingUpdate(SensorReading sensorReading) {
        if (sensorReading.getType().equals(SensorReadingType.LUMEN)) {
            Integer lumen = sensorReading.getValue();

            if (lumen < this.getTargetLumen()) {
                System.out.println("Smart Light ID " + this.getId() + ": ON");
                this.setStatus(DeviceStatus.ON);
            } else {
                System.out.println("Smart Light ID " + this.getId() + ": OFF");
                this.setStatus(DeviceStatus.OFF);
            }
        }
    }
}
