package device.impl.door;

import device.Actuator;
import device.DeviceStatus;
import device.SensorReading;
import device.SensorReadingType;
import home.DeviceLocation;

public class Door extends Actuator{


    public Door(Integer id, String name, DeviceLocation location, DeviceStatus status) {
        super(id, name, location, status);
    }

    @Override
    public void onSensorReadingUpdate(SensorReading sensorReading) {

        if (sensorReading.getType().equals(SensorReadingType.POS)) {
            Integer position = sensorReading.getValue(); //obter posição

            if (position == 0){
                System.out.println("DOOR ID " + this.getId() + ": CLOSED");
                this.setStatus(DeviceStatus.CLOSED);}
            else{
                System.out.println("DOOR ID " + this.getId() + ": OPENED");
                this.setStatus(DeviceStatus.OPENED);}
        }
    }



}
