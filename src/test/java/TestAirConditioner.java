import device.DeviceStatus;
import device.SensorReading;
import device.SensorReadingType;
import device.impl.PositionSensor;
import device.impl.TemperatureSensor;
import device.impl.airconditioner.AirConditioner;
import device.impl.door.Door;
import home.DeviceLocation;
import home.Home;
import org.junit.Assert;
import org.junit.Test;

public class TestAirConditioner {

    @Test
    public void updateDevices() {
        Home home = new Home();
        DeviceLocation bedroom = new DeviceLocation(1, "bedroom");
        DeviceLocation livingRoom = new DeviceLocation(2, "living room");

        home.addLocation(bedroom);
        home.addLocation(livingRoom);

        AirConditioner bedroomAirConditioner = new AirConditioner(1, "split", bedroom, DeviceStatus.STANDBY);

        bedroom.addDevice(bedroomAirConditioner);

        AirConditioner livingRoomAirConditioner = new AirConditioner(2, "split", livingRoom, DeviceStatus.STANDBY);

        TemperatureSensor bedroomTempSensor = new TemperatureSensor(1, "some-temp-sensor", bedroom, DeviceStatus.ON);

        livingRoom.addDevice(livingRoomAirConditioner);

        // simulate a change in temperature of the bedroom to 28ºC
        // this should turn on the air conditioner that is on COOL mode with target temperature 25ºC
        bedroom.onSensorReadingUpdate(new SensorReading(bedroomTempSensor, SensorReadingType.TEMPERATURE, 28));

        Assert.assertEquals(bedroomAirConditioner.getStatus(), DeviceStatus.ON);
        Assert.assertEquals(livingRoomAirConditioner.getStatus(), DeviceStatus.STANDBY);


    }
}
