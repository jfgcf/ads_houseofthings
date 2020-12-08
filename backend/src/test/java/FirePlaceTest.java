import common.model.device.DeviceStatus;
import common.model.device.SensorReading;
import common.model.device.SensorReadingType;
import device.model.fireplace.FirePlace;
import device.model.temperaturesensor.TemperatureSensor;
import org.junit.Assert;
import org.junit.Test;

public class FirePlaceTest {

    @Test
    public void shouldTurnOn() {
        FirePlace bedroomfire = new FirePlace("fire1", DeviceStatus.OFF);
        TemperatureSensor bedroomTempSensor = new TemperatureSensor("some-temp-sensor", DeviceStatus.ON);

        // this should be turned on
        bedroomfire.onSensorReadingUpdate(new SensorReading[]{
                new SensorReading(SensorReadingType.TEMPERATURE, 27, bedroomTempSensor.getName())});

        Assert.assertEquals(bedroomfire.getStatus(), DeviceStatus.ON);
    }

}
