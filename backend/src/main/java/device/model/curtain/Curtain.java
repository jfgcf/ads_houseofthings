package device.model.curtain;

import common.model.device.DeviceType;
import common.model.dto.CurtainDTO;
import common.model.device.DeviceStatus;
import common.model.device.SensorReading;
import common.model.device.SensorReadingType;
import common.model.dto.DeviceDTO;
import device.model.Actuator;
import device.model.Device;

/**
 * Class that represents a curtain device.
 */
public class Curtain extends Actuator {
    private Integer target;
    private CurtainMode mode;

    /**
     * Constructor.
     */
    public Curtain(String name, DeviceStatus status) {
        super(name, status);

        // parses the environment variables


        String ambientEnvVar = System.getenv("TARGET");
        if (ambientEnvVar == null) {
            ambientEnvVar = "500"; // measured in lux (SI)
                                   // 320–500 -> Office lighting
                                   // https://en.wikipedia.org/wiki/Lux
        }


        Integer target = Integer.parseInt(ambientEnvVar);
        this.setTarget(target);
    }

    public Integer getTarget() { return target; }

    public void setTarget(Integer target) {
        if (target == null) {
            throw new IllegalArgumentException("The curtain ambient lighting is required.");
        }

        if (target < 0) { // 0 -> darkness, my old friend
            throw new IllegalArgumentException("The minimum ambient lighting allowed is 0 lux.");
        }

        if (target > 100000) { // 100,000 -> direct sunlight
            throw new IllegalArgumentException("The maximum ambient lighting allowed is 100000 lux.");
        }

        this.target = target;
    }




    @Override
    public void onSensorReadingUpdate(SensorReading[] sensorReadings) {
        for (SensorReading sensorReading : sensorReadings) {
            if (sensorReading.getType().equals(SensorReadingType.AMBIENT)) {
                Integer ambient = sensorReading.getValue();
                System.out.println("Received a new ambient lighting reading: " + ambient);

                System.out.println("Target ambient lighting: " + this.getTarget());



                boolean shouldTurnOn = (ambient > this.getTarget());

                if (shouldTurnOn) {
                    System.out.println("Curtain " + this.getName() + ": OPENED");
                    this.setStatus(DeviceStatus.OPENED);
                } else {
                    System.out.println("Curtain " + this.getName() + ": CLOSED");
                    this.setStatus(DeviceStatus.CLOSED);
                }
            }
        }
    }

    @Override
    public CurtainDTO toDTO() {
        return new CurtainDTO(this.getName(), this.getStatus(), DeviceType.CURTAIN,
                this.getEndpoint(), this.getTarget());
    }

    @Override
    public void edit(DeviceDTO device) {
        CurtainDTO dto = (CurtainDTO) device;
        this.setTarget(dto.getTarget());

    }
}