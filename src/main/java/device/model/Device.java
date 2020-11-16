package device.model;

import common.Util;
import common.model.device.DeviceStatus;
import common.model.dto.DeviceDTO;

import java.net.URL;
import java.util.Objects;

/**
 * Class that represents a device.
 */
public abstract class Device {
    private String name;
    private DeviceStatus status;

    public Device(String name, DeviceStatus status) {
        this.setName(name);
        this.setStatus(status);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public DeviceStatus getStatus() {
        return status;
    }


    /**
     * @return the current device endpoint.
     */
    public URL getEndpoint() {
        return Util.getEndpoint();
    }

    /**
     * Generates a DTO with the device state.
     */
    public abstract DeviceDTO toDTO();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return name.equals(device.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
