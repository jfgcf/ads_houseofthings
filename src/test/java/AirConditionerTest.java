import common.model.device.DeviceStatus;
import common.model.device.SensorReading;
import common.model.device.SensorReadingType;
import device.model.airconditioner.AirConditioner;
import device.model.temperaturesensor.TemperatureSensor;
import org.junit.Assert;
import org.junit.Test;

public class AirConditionerTest {

    @Test
    public void shouldTurnOn() {
        AirConditioner bedroomAirConditioner = new AirConditioner("split", DeviceStatus.STANDBY);
        TemperatureSensor bedroomTempSensor = new TemperatureSensor("some-temp-sensor", DeviceStatus.ON);

        // this should turn on the air conditioner that is on COOL mode with target temperature 25ºC
        bedroomAirConditioner.onSensorReadingUpdate(new SensorReading[]{
                new SensorReading(SensorReadingType.TEMPERATURE, 28, bedroomTempSensor.getName())});

        Assert.assertEquals(bedroomAirConditioner.getStatus(), DeviceStatus.ON);
    }
}