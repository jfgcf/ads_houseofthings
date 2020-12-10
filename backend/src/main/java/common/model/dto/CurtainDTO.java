package common.model.dto;

import common.model.device.DeviceStatus;
import common.model.device.DeviceType;

import java.net.URL;

/**
 * Class that holds the state of a curtain.
 */
public class CurtainDTO extends DeviceDTO {
    private Integer target;

    public CurtainDTO() {

    }

    public CurtainDTO(String name, DeviceStatus status, DeviceType type, URL endpoint, Integer target){
        super(name, status, type, endpoint);
        this.target = target;
        this.type = DeviceType.CURTAIN;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }


}