package common.model.dto;

import common.model.device.DeviceStatus;
import common.model.device.DeviceType;

import java.net.URL;

public class FirePlaceDTO extends DeviceDTO {
    private Integer target;

    public FirePlaceDTO() {
    }


    public FirePlaceDTO(String name, DeviceStatus status, DeviceType type, URL endpoint, Integer target) {
        super(name, status, type, endpoint);
        this.target = target;
        this.type = DeviceType.FIREPLACE;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

}
