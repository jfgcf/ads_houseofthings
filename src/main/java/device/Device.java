package device;

import home.DeviceLocation;

import java.util.Objects;

public abstract class Device {
    private Integer id;
    private String name;
    private DeviceLocation location;
    private DeviceStatus status;

    public Device(Integer id, String name, DeviceLocation location, DeviceStatus status) {
        this.setName(name);
        this.setLocation(location);
        this.setStatus(status);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DeviceLocation getLocation() {
        return location;
    }

    public Integer getId() {
        return id;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw  new IllegalArgumentException("The device name is required;");
        }
        this.name = name;
    }

    public void setId(Integer id) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("Invalid device ID");
        }
        this.id = id;
    }

    public void setStatus(DeviceStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("The device status cannot be null");
        }
        this.status = status;
    }

    public void setLocation(DeviceLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("The device location is required");
        }

        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return id.equals(device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
