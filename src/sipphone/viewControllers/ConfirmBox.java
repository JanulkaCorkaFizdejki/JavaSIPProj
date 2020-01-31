package sipphone.viewControllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class ConfirmBox {

    enum AlertType {
        WARNING,
        CONFIRMATION,
        ERROR,
        INFORMATION,
        NONE
    }

    public static void simpleAlert (String title, String header, String context, AlertType alertType) {
        Alert alert;

        switch (alertType) {
            case WARNING:
                alert = new Alert(Alert.AlertType.WARNING);
                break;
            case CONFIRMATION:
                alert = new Alert(Alert.AlertType.CONFIRMATION);
            case ERROR:
                alert = new Alert(Alert.AlertType.ERROR);
            case INFORMATION:
                alert = new Alert(Alert.AlertType.INFORMATION);
            case NONE:
                alert = new Alert(Alert.AlertType.NONE);
            default:
                alert = new Alert(Alert.AlertType.WARNING);
                break;
        }

        alert.setResizable(false);
        alert.getButtonTypes().clear();
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        ButtonType buttonOk = new ButtonType("OK");
        alert.getButtonTypes().add(buttonOk);
        alert.showAndWait();
    }
}
