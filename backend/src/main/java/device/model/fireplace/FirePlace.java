package device.model.fireplace;

import common.model.device.DeviceType;
import common.model.dto.FirePlaceDTO;
import common.model.device.DeviceStatus;
import common.model.device.SensorReading;
import common.model.device.SensorReadingType;
import common.model.dto.DeviceDTO;
import device.model.Actuator;
import device.model.Device;

public class FirePlace extends Actuator{

    private Integer target ;


    public FirePlace(String name, DeviceStatus status) {
        super(name, status);

        String temperatureEnvVar = System.getenv("TARGET");
        if(temperatureEnvVar == null){
            temperatureEnvVar = "28"; // default
        }

        Integer target = Integer.parseInt(temperatureEnvVar);

        this.setTarget(target);

    }


    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {

        if (target == null) {
            throw new IllegalArgumentException("The desired room temperature is required.");
        }

        if (target > 30) {
            throw new IllegalArgumentException("The maximum temperature is 30ºC");
        }

        this.target = target;
    }

    public void onSensorReadingUpdate(SensorReading[] sensorReadings) {
        for (SensorReading sensorReading : sensorReadings) {
            if (sensorReading.getType().equals(SensorReadingType.TEMPERATURE)) {
                Integer temperature = sensorReading.getValue();
                System.out.println("Received a new temperature reading: " + temperature);

                System.out.println("Target temperature: " + this.getTarget());

                boolean FirePlaceOn = (temperature < this.getTarget());

                if (FirePlaceOn) {
                    System.out.println("Fireplace " + this.getName() + ": ON");
                    this.setStatus(DeviceStatus.ON);
                } else {
                    System.out.println("FirePlace " + this.getName() + ": OFF");
                    this.setStatus(DeviceStatus.OFF);
                }
            }
        }
    }

    @Override
    public FirePlaceDTO toDTO(){
        return new FirePlaceDTO(this.getName(),this.getStatus(),DeviceType.FIREPLACE,
                this.getEndpoint(),this.getTarget());
    }

    @Override
    public void edit(DeviceDTO device) {
        FirePlaceDTO dto = (FirePlaceDTO) device;
        this.setTarget(dto.getTarget());
    }

}
