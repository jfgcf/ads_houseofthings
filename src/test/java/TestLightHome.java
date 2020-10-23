import device.DeviceStatus;
import device.SensorReading;
import device.SensorReadingType;
import device.impl.LumenSensor;
import device.impl.smartlight.SmartLight;
import home.DeviceLocation;
import home.Home;
import org.junit.Assert;
import org.junit.Test;

public class TestLightHome {

    @Test
    public void updateDevices() {
        Home home = new Home();
        DeviceLocation bedroom = new DeviceLocation(1, "bedroom");

        home.addLocation(bedroom);

        SmartLight bedroomSmartLight = new SmartLight(1, "philips", bedroom, DeviceStatus.STANDBY);

        bedroom.addDevice(bedroomSmartLight);

        LumenSensor bedroomLightSensor = new LumenSensor(1, "light-sensor", bedroom, DeviceStatus.ON);

        // simulate a change in Lumen to 0
        // this should turn on the Smart Light
        bedroom.onSensorReadingUpdate(new SensorReading(bedroomLightSensor, SensorReadingType.LUMEN, 2));

        Assert.assertEquals(bedroomSmartLight.getStatus(), DeviceStatus.ON);

        // simulate a change in Lumen to 8
        // this should turn OFF the Smart Light
        bedroom.onSensorReadingUpdate(new SensorReading(bedroomLightSensor, SensorReadingType.LUMEN, 8));

        Assert.assertEquals(bedroomSmartLight.getStatus(), DeviceStatus.OFF);
    }
}
