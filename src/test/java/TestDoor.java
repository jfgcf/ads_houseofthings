import device.DeviceStatus;
import device.SensorReading;
import device.SensorReadingType;
import device.impl.PositionSensor;
import device.impl.door.Door;
import home.DeviceLocation;
import home.Home;
import org.junit.Assert;
import org.junit.Test;

public class TestDoor {


    @Test
    public void updateDevices() {
        Home home = new Home();
        DeviceLocation bedroom = new DeviceLocation(1, "bedroom");

        home.addLocation(bedroom);

        //bedroom door closed or opened

        Door bedroomDoor = new Door(1, "portinha", bedroom, DeviceStatus.CLOSED);

        bedroom.addDevice(bedroomDoor);

        PositionSensor bedroomDoorSensor = new PositionSensor(1, "sensorportaquarto", bedroom, DeviceStatus.OPENED);


        //testa a alteração do estado da porta

        bedroom.onSensorReadingUpdate(new SensorReading(bedroomDoorSensor, SensorReadingType.POS, 0));

        Assert.assertEquals(bedroomDoor.getStatus(), DeviceStatus.CLOSED);

    }

}
