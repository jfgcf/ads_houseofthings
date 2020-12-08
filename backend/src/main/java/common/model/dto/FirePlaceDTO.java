package common.model.dto;

import common.model.device.DeviceStatus;
import common.model.device.DeviceType;

import java.net.URL;

public class FirePlaceDTO extends DeviceDTO {


    private Integer targetTemperature;


    public FirePlaceDTO() {
    }


    public FirePlaceDTO(String name, DeviceStatus status, DeviceType type, URL endpoint, Integer targetTemperature) {
        super(name, status, type, endpoint);
        this.targetTemperature = targetTemperature;
        this.type = DeviceType.FIREPLACE;
    }

    public Integer getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Integer targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

}
