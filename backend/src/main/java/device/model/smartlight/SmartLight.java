package device.model.smartlight;

import common.model.device.DeviceType;
import common.model.dto.SmartLightDTO;
import common.model.device.DeviceStatus;
import common.model.device.SensorReading;
import common.model.device.SensorReadingType;
import common.model.dto.DeviceDTO;
import device.model.Actuator;
import device.model.Device;

/**
 * Class that represents a light device.
 */
public class SmartLight extends Actuator {
    private Integer target;

    /**
     * Constructor.
     */
    public SmartLight(String name, DeviceStatus status) {
        super(name, status);

        // parses the environment variables


        String luxEnvVar = System.getenv("TARGET");
        if (luxEnvVar == null) {
            luxEnvVar = "500"; // measured in lux (SI)
                                   // 320–500 -> Office lighting
                                   // https://en.wikipedia.org/wiki/Lux
        }


        Integer target = Integer.parseInt(luxEnvVar);
        this.setTarget(target);
    }

    public Integer getTarget() { return target; }

    public void setTarget(Integer target) {
        if (target == null) {
            throw new IllegalArgumentException("The lux lighting is required.");
        }

        if (target < 0) { // 0 -> darkness, my old friend
            throw new IllegalArgumentException("The minimum lux allowed is 0 lux.");
        }

        if (target > 100000) { // 100,000 -> direct sunlight
            throw new IllegalArgumentException("The maximum lux allowed is 100000 lux.");
        }

        this.target = target;
    }




    @Override
    public void onSensorReadingUpdate(SensorReading[] sensorReadings) {
        for (SensorReading sensorReading : sensorReadings) {
            if (sensorReading.getType().equals(SensorReadingType.LUX)) {
                Integer lux = sensorReading.getValue();
                System.out.println("Received a new lux reading: " + lux);

                System.out.println("Target lux value: " + this.getTarget());



                boolean shouldTurnOn = (lux < this.getTarget());

                if (shouldTurnOn) {
                    System.out.println("SmartLight " + this.getName() + ": ON");
                    this.setStatus(DeviceStatus.ON);
                } else {
                    System.out.println("SmartLight " + this.getName() + ": OFF");
                    this.setStatus(DeviceStatus.OFF);
                }
            }
        }
    }

    @Override
    public SmartLightDTO toDTO() {
        return new SmartLightDTO(this.getName(), this.getStatus(), DeviceType.LIGHT,
                this.getEndpoint(), this.getTarget());
    }

    @Override
    public void edit(DeviceDTO device) {
        SmartLightDTO dto = (SmartLightDTO) device;
        this.setTarget(dto.getTarget());

    }
}