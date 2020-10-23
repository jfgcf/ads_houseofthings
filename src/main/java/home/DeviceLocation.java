package home;

import device.*;

import java.util.*;

public class DeviceLocation {
    private Integer id;
    private String name;
    private Set<Device> devices;

    public DeviceLocation(Integer id, String name) {
        this.setName(name);
        this.id = id;
        this.devices = new HashSet<Device>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid location name");
        }
        this.name = name;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void addDevice(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("The device cannot be null");
        }
        this.devices.add(device);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("Invalid location ID");
        }
        this.id = id;
    }

    public void startSensorReadings() {
        for (Device device: this.getDevices()) {
            if (device instanceof Sensor) {
                Sensor sensor = (Sensor) device;
                if (sensor.getStatus().equals(DeviceStatus.ON) || sensor.getStatus().equals(DeviceStatus.STANDBY)) {
                    sensor.startSensorReading();
                }
            }
        }
    }

    public void onSensorReadingUpdate(SensorReading sensorReading) {
        for (Device device : this.getDevices()) {
            if (device instanceof Actuator) {
                Actuator actuator = (Actuator) device;
                if (actuator.getStatus().equals(DeviceStatus.ON) || actuator.getStatus().equals(DeviceStatus.STANDBY )|| actuator.getStatus().equals(DeviceStatus.OPENED)| actuator.getStatus().equals(DeviceStatus.CLOSED)) {
                    actuator.onSensorReadingUpdate(sensorReading);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceLocation location = (DeviceLocation) o;
        return id.equals(location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
