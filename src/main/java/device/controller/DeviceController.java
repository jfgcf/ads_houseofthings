package device.controller;

import common.Util;
import common.model.dto.DeviceDTO;
import device.model.Actuator;
import device.model.Device;
import device.model.DeviceFactory;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller that handles device CRUD.
 */
@WebServlet(name = "DeviceController", urlPatterns = {"/device"})
public class DeviceController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        Util.respondJson(DeviceFactory.getDevice().toDTO(), resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // transforms the map into a dto
        DeviceDTO deviceUpdate = Util.request2device(req);
        Device device = DeviceFactory.getDevice();

        if (device instanceof Actuator) {
            ((Actuator) device).edit(deviceUpdate);
        }

        Util.respondJson("OK", resp);
    }
}


