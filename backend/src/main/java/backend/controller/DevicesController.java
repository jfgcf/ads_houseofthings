package backend.controller;

import backend.model.Home;
import common.Util;
import common.model.dto.DeviceDTO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller that returns the current list of devices.
 */
@WebServlet(name = "DevicesController", urlPatterns = {"/devices"})
public class DevicesController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        System.out.println("GET /devices");
        try {
            Home.refreshAll();
            Util.respondJson(Home.getDevices(), resp);
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        System.out.println("POST /devices");
        try {
            // transforms the map into a dto
            DeviceDTO device = Util.request2device(req);

            // adds the device to the list
            Home.addDevice(device);

            Util.respondJson("OK", resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        System.out.println("PUT /devices");
        try {
            // transforms the map into a dto
            DeviceDTO device = Util.request2device(req);
            Home.editDevice(device);

            Util.respondJson("OK", resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
