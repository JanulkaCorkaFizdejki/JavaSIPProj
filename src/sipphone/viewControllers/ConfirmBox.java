package sipphone.viewControllers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import sipphone.settings.SettingsWindows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmBox {

    static boolean ansver;

    public static void display (String title, String message, SettingsWindows.WinType winType) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(SettingsWindows.WinConfirmBox[0]);
        window.setMinHeight(SettingsWindows.WinConfirmBox[1]);


        Button btn_ok = new Button("Ok");
        btn_ok.setMinWidth(100.0);;

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        Label label = new Label();
        label.setText(message);
        vBox.getChildren().add(label);
        vBox.getChildren().add(btn_ok);
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
    }
}
