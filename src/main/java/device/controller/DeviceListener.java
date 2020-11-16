package device.controller;


import device.model.DeviceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This listener registers the device on the backend when the device server starts.
 */
@WebListener
public class DeviceListener implements ServletContextListener {

    @Override
    public final void contextInitialized(final ServletContextEvent sce) {
        DeviceFactory.getDevice();
    }

    @Override
    public final void contextDestroyed(final ServletContextEvent sce) {

    }
}
