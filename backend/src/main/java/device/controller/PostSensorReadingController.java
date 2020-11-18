package device.controller;

import common.Util;
import common.model.device.SensorReading;
import device.model.Actuator;
import device.model.Device;
import device.model.DeviceFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller that notifies the device about a sensor reading.
 */
@WebServlet(name = "PostSensorReadingController", urlPatterns = {"/device/reading"})
public class PostSensorReadingController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("POST /device/reading");

        Device device = DeviceFactory.getDevice();

        if (device instanceof Actuator) {
            SensorReading[] reading = Util.getJsonFromBody(req, SensorReading[].class);
            ((Actuator) device).onSensorReadingUpdate(reading);
        }

        Util.respondJson(device.toDTO(), resp);
    }
}
