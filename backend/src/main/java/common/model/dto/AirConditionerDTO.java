package common.model.dto;

import common.model.device.DeviceStatus;
import common.model.device.DeviceType;
import device.model.airconditioner.AirConditionerMode;

import java.net.URL;

/**
 * Class that holds the state of an air conditioner.
 */
public class AirConditionerDTO extends DeviceDTO {
    private Integer targetTemperature;
    private AirConditionerMode mode;

    public AirConditionerDTO() {
    }

    public AirConditionerDTO(String name, DeviceStatus status, DeviceType type, URL endpoint, Integer targetTemperature, AirConditionerMode mode) {
        super(name, status, type, endpoint);
        this.targetTemperature = targetTemperature;
        this.mode = mode;
        this.type = DeviceType.AIR_CONDITIONER;
    }

    public Integer getTargetTemperature() {
        return targetTemperature;
    }

    public AirConditionerMode getMode() {
        return mode;
    }

    public void setTargetTemperature(Integer targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public void setMode(AirConditionerMode mode) {
        this.mode = mode;
    }
}
