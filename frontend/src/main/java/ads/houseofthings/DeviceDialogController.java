package ads.houseofthings;

import ads.houseofthings.model.dao.DeviceDAO;
import ads.houseofthings.model.domain.Device;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeviceDialogController implements Initializable {

    @FXML
    private Label labelDeviceMode;
    @FXML
    private Label labelDeviceTarget;
    @FXML
    private Button buttonConfirm;
    @FXML
    private Button buttonCancel;
    @FXML
    private TextField textFieldDeviceTarget;
    @FXML
    private TextField textFieldDeviceMode;

    private Stage dialogStage;
    private boolean buttonConfirmClicked = false;
    private Device device;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Device getDevice() {
        return this.device;
    }

    public void setDevice(Device device) {
        this.device = device;
        this.textFieldDeviceMode.setText(device.getMode());
        this.textFieldDeviceTarget.setText(String.valueOf(device.getTargetTemperature()));
    }

    public boolean isButtonConfirmClicked() {
        return buttonConfirmClicked;
    }

    @FXML
    public void handleButtonConfirm() {
        device.setMode(textFieldDeviceMode.getText());
        device.setTargetTemperature(Integer.parseInt(textFieldDeviceTarget.getText()));

        buttonConfirmClicked = true;
        dialogStage.close();
    }

    @FXML
    public void handleButtonCancel() {
        getDialogStage().close();
    }
}