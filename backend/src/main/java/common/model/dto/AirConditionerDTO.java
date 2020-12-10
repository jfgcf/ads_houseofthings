package common.model.dto;

import common.model.device.DeviceStatus;
import common.model.device.DeviceType;
import device.model.airconditioner.AirConditionerMode;

import java.net.URL;

/**
 * Class that holds the state of an air conditioner.
 */
public class AirConditionerDTO extends DeviceDTO {
    private Integer target;
    private AirConditionerMode mode;

    public AirConditionerDTO() {
    }

    public AirConditionerDTO(String name, DeviceStatus status, DeviceType type, URL endpoint, Integer target, AirConditionerMode mode) {
        super(name, status, type, endpoint);
        this.target = target;
        this.mode = mode;
        this.type = DeviceType.AIR_CONDITIONER;
    }

    public Integer getTarget() {
        return target;
    }

    public AirConditionerMode getMode() {
        return mode;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public void setMode(AirConditionerMode mode) {
        this.mode = mode;
    }
}
