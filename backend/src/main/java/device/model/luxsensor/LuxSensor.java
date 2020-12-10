package device.model.luxsensor;

import common.Util;
import common.model.device.DeviceStatus;
import common.model.device.DeviceType;
import common.model.device.SensorReading;
import common.model.device.SensorReadingType;
import common.model.dto.DeviceDTO;
import device.model.Sensor;

/**
 * Class that represents a ambient lighting sensor.
 */
public class LuxSensor extends Sensor {

    public LuxSensor(String name, DeviceStatus status) {
        super(name, status);
    }

    @Override
    public DeviceDTO toDTO() {
        return super.toDTO(DeviceType.LUX_SENSOR);
    }

    @Override
    public SensorReading getReading() {
        // Random ambient lighting between 0 lux and 100,000 lux
        Integer value = Util.getRandomNumberUsingNextInt(0, 100000);
        System.out.println("Generated a new ambient lighting reading: " + value + " lux");
        return new SensorReading(SensorReadingType.LUX, value, this.getName());
    }
}