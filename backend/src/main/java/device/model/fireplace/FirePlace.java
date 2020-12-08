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

    private Integer targetTemperature ;


    public FirePlace(String name, DeviceStatus status) {
        super(name, status);

        String temperatureEnvVar = System.getenv("TARGET_TEMPERATURE_FIREPLACE");
        if(temperatureEnvVar == null){
            temperatureEnvVar = "28"; // default
        }

        Integer onTemperature = Integer.parseInt(temperatureEnvVar);

        this.setTargetTemperature(onTemperature);

    }


    public Integer getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Integer targetTemperature) {

        if (targetTemperature == null) {
            throw new IllegalArgumentException("The desired room temperature is required.");
        }

        if (targetTemperature < 25) {
            throw new IllegalArgumentException("The minimum temperature is 25ºC");
        }

        if (targetTemperature > 38) {
            throw new IllegalArgumentException("The maximum temperature is 38ºC");
        }

        this.targetTemperature = targetTemperature;
    }

    public void onSensorReadingUpdate(SensorReading[] sensorReadings) {
        for (SensorReading sensorReading : sensorReadings) {
            if (sensorReading.getType().equals(SensorReadingType.TEMPERATURE)) {
                Integer temperature = sensorReading.getValue();
                System.out.println("Received a new temperature reading: " + temperature);

                System.out.println("Target temperature: " + this.getTargetTemperature());

                boolean FirePlaceOn = (temperature < this.getTargetTemperature());

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
                this.getEndpoint(),this.getTargetTemperature());
    }

    @Override
    public void edit(DeviceDTO device) {
        FirePlaceDTO dto = (FirePlaceDTO) device;
        this.setTargetTemperature(dto.getTargetTemperature());
    }

}
