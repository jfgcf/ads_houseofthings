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
    private Integer target ;
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

        String temperatureEnvVar = System.getenv("TARGET");
        if (temperatureEnvVar == null) {
            temperatureEnvVar = "25"; // default value
        }

        AirConditionerMode mode = AirConditionerMode.valueOf(modeEnvVar);
        Integer target = Integer.parseInt(temperatureEnvVar);

        this.setMode(mode);
        this.setTarget(target);
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        if (target == null) {
            throw new IllegalArgumentException("The air conditioner temperature is required.");
        }

        if (target < 10) {
            throw new IllegalArgumentException("The minimum temperature allowed is 10 ºC.");
        }

        if (target > 30) {
            throw new IllegalArgumentException("The maximum temperature allowed is 30 ºC.");
        }

        this.target = target;
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

                System.out.println("Target temperature: " + this.getTarget());

                System.out.println("Mode: " + this.getMode());

                boolean shouldTurnOn = (temperature > this.getTarget() && this.getMode().equals(AirConditionerMode.COOL)) ||
                        (temperature < this.getTarget() && this.getMode().equals(AirConditionerMode.HEAT));

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
                this.getEndpoint(), this.getTarget(), this.getMode());
    }

    @Override
    public void edit(DeviceDTO device) {
        AirConditionerDTO dto = (AirConditionerDTO) device;
        this.setTarget(dto.getTarget());
        this.setMode(dto.getMode());
    }
}
