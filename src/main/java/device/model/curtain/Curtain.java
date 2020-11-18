package device.model.curtain;

import common.model.device.DeviceStatus;
import common.model.device.DeviceType;
import common.model.device.SensorReading;
import common.model.dto.AirConditionerDTO;
import common.model.dto.CurtainDTO;
import common.model.dto.DeviceDTO;
import device.model.Actuator;

public class Curtain extends Actuator {

    private Double relativePosition;
    private Integer targetAmbient;


    /**
     * Constructor.
     */

    public Curtain(String name, DeviceStatus status) {
        super(name, status);

        this.relativePosition = relativePosition;
        this.targetAmbient = targetAmbient;
    }

    public Integer getTargetAmbient(){return targetAmbient;}
    public Double getRelativePosition() {return relativePosition;}

    public void setRelativePosition(Double pos) {

        if (pos > 1.0) return;
        if (pos < 0.0) return;

        this.relativePosition = pos;
    }

    public void setTargetAmbient(Integer targetAmbient){

    }
    @Override
    public void onSensorReadingUpdate(SensorReading[] sensorReadings) {
        for (SensorReading sensorReading : sensorReadings) {

            Integer ambient = sensorReading.getValue();
            System.out.println("Received a new ambient reading: " + ambient);

            boolean shouldMove = ambient != this.getTargetAmbient(); // should include a tolerance

            if (shouldMove) {

                // device is on
                System.out.println("Curtain " + this.getName() + ": ON");
                this.setStatus(DeviceStatus.ON);

                // if ambient illuminance less than target and curtain is not already fully opened then incrementaly OPEN the curtain
                if (ambient < this.getTargetAmbient() && this.getRelativePosition() < 1.0) this.setRelativePosition(this.getRelativePosition() + 0.05);

                // if ambient illuminance more than target and curtain is not already fully closed then incrementaly CLOSE the curtain
                if (ambient > this.getTargetAmbient() && this.getRelativePosition() > 0.0) this.setRelativePosition(this.getRelativePosition() - 0.05);

            } else {
                System.out.println("Curtain " + this.getName() + ": STANDBY");
                this.setStatus(DeviceStatus.STANDBY);
            }
        }

    }

    @Override
    public void edit(DeviceDTO device) {
        CurtainDTO dto = (CurtainDTO) device;
        this.setRelativePosition(dto.getRelativePosition());
        this.setTargetAmbient(dto.getTargetAmbient());

    }

    @Override
    public DeviceDTO toDTO() {
        return new CurtainDTO(this.getName(), this.getStatus(), DeviceType.CURTAIN,
                this.getEndpoint(), this.getTargetAmbient(), this.getRelativePosition());
    }
}
