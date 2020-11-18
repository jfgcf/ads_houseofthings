package device.model.airconditioner;

import common.model.device.DeviceType;
import common.model.dto.AirConditionerDTO;
import common.model.device.DeviceStatus;
import common.model.device.SensorReading;
import common.model.device.SensorReadingType;
import common.model.dto.DeviceDTO;
import device.model.Actuator;
import device.model.Device;

/**
 * Class that represents an air conditioner device.
 */
public class AirConditioner extends Actuator {
    private Integer targetTemperature ;
    private AirConditionerMode mode;

    /**
     * Constructor.
     */
    public AirConditioner(String name, DeviceStatus status) {
        super(name, status);

        // parses the environment variables
        String modeEnvVar = System.getenv("MODE");
        if (modeEnvVar == null) {
            modeEnvVar = "COOL"; // default value
        }

        String temperatureEnvVar = System.getenv("TARGET_TEMPERATURE");
        if (temperatureEnvVar == null) {
            temperatureEnvVar = "25"; // default value
        }

        AirConditionerMode mode = AirConditionerMode.valueOf(modeEnvVar);
        Integer targetTemperature = Integer.parseInt(temperatureEnvVar);

        this.setMode(mode);
        this.setTargetTemperature(targetTemperature);
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
        return mode;
    }

    public void setMode(AirConditionerMode mode) {
        this.mode = mode;
    }


    @Override
    public void onSensorReadingUpdate(SensorReading[] sensorReadings) {
        for (SensorReading sensorReading : sensorReadings) {
            if (sensorReading.getType().equals(SensorReadingType.TEMPERATURE)) {
                Integer temperature = sensorReading.getValue();
                System.out.println("Received a new temperature reading: " + temperature);

                System.out.println("Target temperature: " + this.getTargetTemperature());

                System.out.println("Mode: " + this.getMode());

                boolean shouldTurnOn = (temperature > this.getTargetTemperature() && this.getMode().equals(AirConditionerMode.COOL)) ||
                        (temperature < this.getTargetTemperature() && this.getMode().equals(AirConditionerMode.HEAT));

                if (shouldTurnOn) {
                    System.out.println("Air Conditioner " + this.getName() + ": ON");
                    this.setStatus(DeviceStatus.ON);
                } else {
                    System.out.println("Air Conditioner " + this.getName() + ": STANDBY");
                    this.setStatus(DeviceStatus.STANDBY);
                }
            }
        }
    }


    @Override
    public AirConditionerDTO toDTO() {
        return new AirConditionerDTO(this.getName(), this.getStatus(), DeviceType.AIR_CONDITIONER,
                this.getEndpoint(), this.getTargetTemperature(), this.getMode());
    }

    @Override
    public void edit(DeviceDTO device) {
        AirConditionerDTO dto = (AirConditionerDTO) device;
        this.setTargetTemperature(dto.getTargetTemperature());
        this.setMode(dto.getMode());
    }
}
