package sipphone;

import externalDataModel.AuxiliaryMethods;
import externalDataModel.CheckPhoneFormat;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sipphone.settings.InputFieldsMaxlength;
import sipphone.viewControllers.ButtonStyle;
import sipphone.viewControllers.ToolpitView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class ViewContollerAddUser implements Initializable {

    public TextField lbl_number;
    public TextField lbl_user_name;
    public Button btn_add_user;
    public Button btn_go_back;
    public Pane pane_form_addUser;
    public Label lbl_error_format_txt;


    @FXML
    public void addUserActn(javafx.scene.input.MouseEvent mouseEvent)  {

        String num_txt =  lbl_number.getText().trim();
        String t =  num_txt.replaceAll("\\s+","");
        try {
            long number_user = Long.parseLong(t);
            String user_name =  lbl_user_name.getText().trim();

            if (user_name != "") {
                DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
                String query = "INSERT INTO " + SettingsDB.DBTableSip.users.toString() + " (user_name) VALUES (\"" + user_name + "\")";
                if (DBM.insert(query)) {
                    String selQuery = "SELECT * FROM "+ SettingsDB.DBTableSip.users + " WHERE id=(SELECT MAX(id) FROM "+ SettingsDB.DBTableSip.users + ")";
                    ResultSet rs = DBM.select(SettingsDB.DBTableSip.users.toString(), selQuery, true);

                    int id = 0;
                    do {
                        id = rs.getInt("id");
                        break;
                    } while (rs.next());
                    rs.close();

                    String addNum = "INSERT INTO " + SettingsDB.DBTableSip.numbers.toString() + " (number, id_users) VALUES (\"" + number_user + "\", \"" + id + "\")";

                    if (DBM.insert(addNum)) {
                        lbl_user_name.setText("");
                        lbl_number.setText("");
                        this.goBack(mouseEvent);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void goBack (Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_go_back.setStyle(ButtonStyle.round(40));
        btn_go_back.setTooltip(ToolpitView.tooltip("Wróć do widoku głównego"));
        pane_form_addUser.setStyle("-fx-background-color: #e1e1e1");
        lbl_error_format_txt.setVisible(false);
    }

    public void btnGoBack(ActionEvent actionEvent) throws IOException {
        this.goBack(actionEvent);
    }


    public void inputName(ActionEvent actionEvent) {
    }


    public void inputNumberActn(KeyEvent keyEvent) {
        String lbl_txt = lbl_number.getText();

        lbl_number.setTooltip(null);

        if (lbl_txt.length() > InputFieldsMaxlength.getLength(InputFieldsMaxlength.inputType.number)) {
            lbl_number.setText(AuxiliaryMethods.removeLastChar(lbl_txt));
            lbl_number.positionCaret(lbl_txt.length());
            this.errorTextFormatView();
            lbl_number.setTooltip(ToolpitView.tooltipErr("Maksymalna liczba znaków w tym polu wynosi " + InputFieldsMaxlength.getLength(InputFieldsMaxlength.inputType.number)));
            return;
        }

        if (CheckPhoneFormat.allowChar(keyEvent.getText())) {
            String ch = (keyEvent.getText().trim().equalsIgnoreCase("=")) ? "+" : keyEvent.getText().trim();
        } else {
            if (lbl_txt.length() > 0) {
                if (!CheckPhoneFormat.allowCode(keyEvent.getCode().toString())) {
                    this.errorTextFormatView();
                }
            }
        }
    }

    private void errorTextFormatView () {
        lbl_error_format_txt.setVisible(true);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                lbl_error_format_txt.setVisible(false);
                timer.cancel();
            }
        };
        timer.schedule(task, 500, 500);
    }

}
