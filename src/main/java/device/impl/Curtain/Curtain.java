package device.impl.Curtain;

import device.Actuator;
import device.DeviceStatus;
import device.SensorReading;
import device.SensorReadingType;
import home.DeviceLocation;

public class Curtain extends Actuator {

    private CurtainPosition position = CurtainPosition.OPEN;
    private Integer lumensThreshold = 500;


    public Curtain(Integer id, String name, DeviceLocation location, DeviceStatus status) {
        super(id, name, location, status);
    }

    public CurtainPosition getPosition() {
        return position;
    }

    public void setPosition(CurtainPosition position) {
        this.position = position;
    }

    public Integer getLumensThreshold() {
        return lumensThreshold;
    }

    public void setLumensThreshold(Integer lumensThreshold) {
        this.lumensThreshold = lumensThreshold;
    }

    @Override
    public void onSensorReadingUpdate(SensorReading sensorReading) {
        if (sensorReading.getType().equals(SensorReadingType.LUMEN)) {
            boolean shouldClose = sensorReading.getValue() > this.getLumensThreshold();
            if (shouldClose) {
                this.setPosition(CurtainPosition.CLOSED);
            } else {
                this.setPosition(CurtainPosition.OPEN);
            }
        }
    }
}