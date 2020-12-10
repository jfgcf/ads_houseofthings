import common.model.device.DeviceStatus;
import common.model.device.SensorReading;
import common.model.device.SensorReadingType;
import device.model.curtain.Curtain;
import device.model.ambientsensor.AmbientSensor;
import org.junit.Assert;
import org.junit.Test;

public class CurtainTest {

    @Test
    public void shouldTurnOn() {
        Curtain bedroomCurtain = new Curtain("bedroom-curtain", DeviceStatus.CLOSED);
        AmbientSensor bedroomAmbientSensor = new AmbientSensor("bedroom-ambient-sensor", DeviceStatus.ON);

        // this should turn on the curtain with target temperature 500 lux
        bedroomCurtain.onSensorReadingUpdate(new SensorReading[]{
                new SensorReading(SensorReadingType.AMBIENT, 500, bedroomAmbientSensor.getName())});

        Assert.assertEquals(bedroomCurtain.getStatus(), DeviceStatus.CLOSED);
    }
}