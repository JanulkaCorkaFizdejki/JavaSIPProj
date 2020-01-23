package sipphone.viewControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sipphone.settings.SettingsWindows;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class ViewControllerKeyboardPanelPhone implements Initializable {

    public Label lbl_display;
    private int onMousePlusKeyPressLimiter = 0;


    public void newpage(String event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../keyboard_panel_phone.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, SettingsWindows.WinBigChild1[0], SettingsWindows.WinBigChild1[1]));
        stage.setTitle("Wybierz numer");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_display.setText("");
        lbl_display.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
    }

    public void btn_select_num(ActionEvent actionEvent) {

        String text_display = lbl_display.getText();

        String text = ((Button) actionEvent.getSource()).getText();


        if (!text.equalsIgnoreCase("CLS") && !text.equalsIgnoreCase("BACK")) {
                if (text_display.length() > 0) {
                    lbl_display.setText(text_display + text);
                } else {
                    if (!text.equalsIgnoreCase("0")) {
                        lbl_display.setText(text_display + text);
                    }
                }
        } else {
            if (text.equalsIgnoreCase("CLS")){
                lbl_display.setText("");
            } else {
                if (text_display.length() > 0) {
                    lbl_display.setText(removeLastChar(text_display));
                }
            }
        }
    }



    public void btn_call_start(ActionEvent actionEvent) {
        String text_display = lbl_display.getText();

        if (text_display.length() != 0) {
            System.out.println("CALL OK");
        }
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public void btn_zero_plus_pressed(MouseEvent mouseEvent) {
        Timer timer = new Timer();
        SomeTask someTask = new SomeTask();
        timer.schedule(someTask, 500);
    }

    public void btn_zero_plus_released(MouseEvent mouseEvent) {
        String text_display = lbl_display.getText();
        if (text_display.length() == 0 && onMousePlusKeyPressLimiter == 1) {
            lbl_display.setText("+");
            onMousePlusKeyPressLimiter = 0;
        } else {
            lbl_display.setText("");
        }
    }

    public class  SomeTask extends TimerTask {
        @Override
        public void run() {
            onMousePlusKeyPressLimiter = 1;
            this.cancel();
        }
    }
}
