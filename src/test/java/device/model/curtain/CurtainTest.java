package device.model.curtain;

import common.model.device.DeviceStatus;
import common.model.device.SensorReading;
import common.model.device.SensorReadingType;
import device.model.airconditioner.AirConditioner;
import device.model.temperaturesensor.TemperatureSensor;
import org.junit.Assert;
import org.junit.Test;

public class CurtainTest {

    @Test
    public void shouldTurnOn() {
        Curtain bedroomCurtain = new Curtain("curtain1", DeviceStatus.STANDBY);


        bedroomCurtain.onSensorReadingUpdate(new SensorReading[]{
                new SensorReading(SensorReadingType.CURTAIN, 1, bedroomCurtain.getName())});

        Assert.assertEquals(bedroomCurtain.getStatus(), DeviceStatus.ON);
    }
}

