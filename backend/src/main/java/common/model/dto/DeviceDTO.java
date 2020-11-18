package common.model.dto;

import common.model.device.DeviceStatus;
import common.model.device.DeviceType;

import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * Class that holds the state of a device.
 */
public abstract class DeviceDTO implements Serializable {
    private String name;
    private DeviceStatus status;
    protected DeviceType type;
    private URL endpoint;

    public DeviceDTO() {}

    public DeviceDTO(String name, DeviceStatus status, DeviceType type, URL endpoint) {
        this.name = name;
        this.status = status;
        this.endpoint = endpoint;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public URL getEndpoint() {
        return endpoint;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public void setEndpoint(URL endpoint) {
        this.endpoint = endpoint;
    }

    public DeviceType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return name.equals(deviceDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
