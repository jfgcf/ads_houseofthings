import device.DeviceStatus;
import device.impl.Curtain.Curtain;
import device.impl.Curtain.CurtainPosition;
import home.DeviceLocation;
import home.Home;
import org.junit.Assert;
import org.junit.Test;

public class TestCurtain {

    @Test
    public void updateDevices() {
        Home home = new Home();
        DeviceLocation bedroom = new DeviceLocation(1, "bedroom");
        DeviceLocation livingRoom = new DeviceLocation(2, "living room");

        home.addLocation(bedroom);
        home.addLocation(livingRoom);

        Curtain bedroomCurtain = new Curtain(1, "Curtain1", bedroom, DeviceStatus.STANDBY);

        bedroom.addDevice(bedroomCurtain);

        Curtain livingRoomCurtain = new Curtain(2, "Curtain2", livingRoom, DeviceStatus.STANDBY);

        livingRoom.addDevice(bedroomCurtain);

        Assert.assertEquals(bedroomCurtain.getPosition(), CurtainPosition.OPEN);

    }
}