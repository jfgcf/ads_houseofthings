package ads.houseofthings;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private MenuItem menuItemDeviceStatus;

    @FXML
    private MenuItem menuItemTriggerStatus;

    @FXML
    private MenuItem menuItemRegisterHouse;

    @FXML
    private MenuItem menuItemRegisterDevice;

    @FXML
    private MenuItem menuItemDocumentation;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void handleMenuItemDeviceStatus() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("DeviceStatusView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}