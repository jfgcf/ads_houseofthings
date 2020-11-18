package common.model.dto;
import common.model.device.DeviceStatus;
import common.model.device.DeviceType;

import java.net.URL;

public class CurtainDTO extends DeviceDTO {

    private Integer targetAmbient;
    private Double relativePosition;

    public CurtainDTO() {

    }

    public CurtainDTO(String name, DeviceStatus status, DeviceType type, URL endpoint, Integer targetAmbient, Double relativePosition) {
        super(name, status, type, endpoint);
        this.targetAmbient = targetAmbient;
        this.relativePosition = relativePosition;
        this.type = DeviceType.CURTAIN; // TO DO
    }

    public Integer getTargetAmbient() {
        return this.targetAmbient;
    }

    public Double getRelativePosition() {
        return this.relativePosition;
    }

    public void setTargetAmbient(Integer targetAmbient) {
        this.targetAmbient = targetAmbient;
    }

    public void setRelativePosition(Double relativePosition) {
        this.relativePosition = relativePosition;
    }

}