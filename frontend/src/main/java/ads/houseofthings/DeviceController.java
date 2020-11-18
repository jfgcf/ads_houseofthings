package ads.houseofthings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import ads.houseofthings.model.dao.DeviceDAO;
import ads.houseofthings.model.domain.Device;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeviceController implements Initializable {

    @FXML
    private TableView<Device> tableViewDevices;
    @FXML
    private TableColumn<Device, String> tableColumnDeviceName;
    @FXML
    private TableColumn<Device, String> tableColumnDeviceStatus;
    @FXML
    private Button buttonChange;
    @FXML
    private Label labelDeviceName;
    @FXML
    private Label labelDeviceType;
    @FXML
    private Label labelDeviceMode;
    @FXML
    private Label labelDeviceTarget;
    @FXML
    private Label labelDeviceReadingType;
    @FXML
    private Label labelDeviceReadingValue;

    private List<Device> listDevices;
    private ObservableList<Device> observableListDevices;
    private final DeviceDAO deviceDAO = new DeviceDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        getDevices();

        selectTableViewItemDevices(null);

        tableViewDevices.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectTableViewItemDevices(newValue));

    }

    public void getDevices() {
        tableColumnDeviceName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnDeviceStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        listDevices = deviceDAO.listDevice();

        observableListDevices = FXCollections.observableArrayList(listDevices);
        tableViewDevices.setItems(observableListDevices);
    }

    public void selectTableViewItemDevices(Device device) {
        if (device != null) {
            labelDeviceName.setText(device.getName());
            labelDeviceType.setText(device.getType());
            labelDeviceMode.setText(device.getMode());
            if(device.getTargetTemperature() != null) {
                labelDeviceTarget.setText(String.valueOf(device.getTargetTemperature()));
                buttonChange.setDisable(false);
            } else {
                labelDeviceTarget.setText("");
                buttonChange.setDisable(true);
            }
            try {
                labelDeviceReadingType.setText(device.getReading().getType());
                labelDeviceReadingValue.setText(String.valueOf(device.getReading().getValue()));
            } catch (NullPointerException e){
                labelDeviceReadingType.setText("");
                labelDeviceReadingValue.setText("");
            }
        } else {
            labelDeviceName.setText("");
            labelDeviceType.setText("");
            labelDeviceMode.setText("");
            labelDeviceTarget.setText("");
            labelDeviceReadingType.setText("");
            labelDeviceReadingValue.setText("");
        }
    }

    @FXML
    public void handleButtonRefresh() throws IOException {
        getDevices();
    }

    @FXML
    public void handleButtonChange() throws IOException {
        Device device = tableViewDevices.getSelectionModel().getSelectedItem();
        if (device != null){
            boolean buttonConfirmClicked = showDeviceDialog(device);
            if (buttonConfirmClicked){
                deviceDAO.updateDevice(device);
                getDevices();;
            }
        }

    }

    public boolean showDeviceDialog(Device device) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DeviceDialogController.class.getResource("DeviceStatusDialogView.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Set a new trigger");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.sizeToScene();
        dialogStage.setWidth(316);
        dialogStage.setHeight(176);

        DeviceDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setDevice(device);
        dialogStage.showAndWait();

        return controller.isButtonConfirmClicked();

    }
}