package launch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import common.Util;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main {
    public static void setupBackendServer(List<String> packages) {
        // loads the backend servlets
        packages.add("backend");
    }

    public static void setupDeviceServer(List<String> packages) {
        // loads the device servlets
        packages.add("device");

        // makes sure all the variables we need are set
        if (System.getenv("DEVICE_NAME") == null) {
            throw new IllegalArgumentException("Required DEVICE_NAME environment variable not set!");
        }

        if (System.getenv("DEVICE_TYPE") == null) {
            throw new IllegalArgumentException("Required DEVICE_TYPE environment variable not set!");
        }

        if (System.getenv("BACKEND_ENDPOINT") == null) {
            throw new IllegalArgumentException("Required BACKEND_ENDPOINT environment variable not set!");
        }
    }

    public static void main(String[] args) throws Exception {
        // these are the packages that tomcat will look for classes
        List<String> packages = new ArrayList<>();
        packages.add("common");

        String serverType = System.getenv("SERVER_TYPE");

        if (serverType == null) {
            throw new IllegalArgumentException("The arguments backend or device must be provided.");
        }

        if (System.getenv("ENDPOINT") == null) {
            throw new IllegalArgumentException("Required ENDPOINT environment variable not set!");
        }

        Integer webPort = Util.getPort();

        // depending of the type of the server we call one of the setup methods
        switch (serverType.trim().toLowerCase()) {
            case "backend" -> setupBackendServer(packages);
            case "device" -> setupDeviceServer(packages);
            default -> throw new IllegalArgumentException("Unknown server type: " + args[0]);
        }

        // below is the code to start the API server
        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(webPort);

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        // loads the packages we selected
        for(String pkg : packages) {
            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                    additionWebInfClasses.getAbsolutePath(),"/" + pkg));
        }
        ctx.setResources(resources);

        // starts tomcat to listen to API calls
        tomcat.start();
        System.out.println("Started " + serverType + " on port " + webPort);
        tomcat.getServer().await();
    }
}