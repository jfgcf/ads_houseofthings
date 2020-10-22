package home;

import java.util.*;

public class Home {
    private Integer id;
    private Set<DeviceLocation> locations = new HashSet<DeviceLocation>();

    public Set<DeviceLocation> getLocations() {
        return locations;
    }

    public void addLocation(DeviceLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("The house location cannot be null.");
        }
        this.locations.add(location);
    }

    public void removeLocation(DeviceLocation location) {
        this.locations.remove(location);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("The house id cannot be null.");
        }
        this.id = id;
    }

    public void startSensorReadings() {
        for (DeviceLocation location: this.getLocations()) {
            location.startSensorReadings();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Home home = (Home) o;
        return id.equals(home.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
