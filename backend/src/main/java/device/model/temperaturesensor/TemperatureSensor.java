package device.model.temperaturesensor;

import common.Util;
import common.model.device.DeviceStatus;
import common.model.device.DeviceType;
import common.model.device.SensorReading;
import common.model.device.SensorReadingType;
import common.model.dto.DeviceDTO;
import device.model.Sensor;

/**
 * Class that represents a temperature sensor.
 */
public class TemperatureSensor extends Sensor {

    public TemperatureSensor(String name, DeviceStatus status) {
        super(name, status);
    }

    @Override
    public DeviceDTO toDTO() {
        return super.toDTO(DeviceType.TEMPERATURE_SENSOR);
    }

    @Override
    public SensorReading getReading() {
        // Random Temperature between 15C and 25C
        Integer value = Util.getRandomNumberUsingNextInt(15, 25);
        System.out.println("Generated a new temperature reading: " + value);
        return new SensorReading(SensorReadingType.TEMPERATURE, value, this.getName());
    }
}
