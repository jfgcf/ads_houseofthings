package device.impl.airconditioner;

import device.Actuator;
import device.DeviceStatus;
import device.SensorReading;
import device.SensorReadingType;
import home.DeviceLocation;

public class AirConditioner extends Actuator {

    private Integer targetTemperature = 25;
    private AirConditionerMode mode = AirConditionerMode.COOL;

    public AirConditioner(Integer id, String name, DeviceLocation location, DeviceStatus status) {
        super(id, name, location, status);
    }

    public Integer getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Integer targetTemperature) {
        if (targetTemperature == null) {
            throw new IllegalArgumentException("The air conditioner temperature is required.");
        }

        if (targetTemperature < 10) {
            throw new IllegalArgumentException("The minimum temperature allowed id 10ºC");
        }

        if (targetTemperature > 30) {
            throw new IllegalArgumentException("The maximum temperature allowed id 30ºC");
        }

        this.targetTemperature = targetTemperature;
    }

    public AirConditionerMode getMode() {
        if (mode == null) {
            throw new IllegalArgumentException("The air conditioner mode is required");
        }
        return mode;
    }

    public void setMode(AirConditionerMode mode) {
        this.mode = mode;
    }


    @Override
    public void onSensorReadingUpdate(SensorReading sensorReading) {
        if (sensorReading.getType().equals(SensorReadingType.TEMPERATURE)) {
            Integer temperature = sensorReading.getValue();

            boolean shouldTurnOn = (temperature > this.getTargetTemperature() && this.getMode().equals(AirConditionerMode.COOL)) ||
                    (temperature < this.getTargetTemperature() && this.getMode().equals(AirConditionerMode.HEAT));

            if (shouldTurnOn) {
                System.out.println("Air Conditioner ID " + this.getId() + ": ON");
                this.setStatus(DeviceStatus.ON);
            } else {
                System.out.println("Air Conditioner ID " + this.getId() + ": STANDBY");
                this.setStatus(DeviceStatus.STANDBY);
            }
        }
    }
}
