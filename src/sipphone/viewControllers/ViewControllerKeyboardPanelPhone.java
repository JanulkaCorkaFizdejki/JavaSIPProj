package sipphone.viewControllers;

import externalDataModel.AuxiliaryMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sipphone.DabatabaseManager;
import sipphone.SettingsDB;
import sipphone.datamodel.DataModelSimpleContactList;
import sipphone.model.CurrentConnect;
import sipphone.settings.SettingsWindows;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;



public class ViewControllerKeyboardPanelPhone implements Initializable {

    public Label lbl_display;
    public Button key_0;
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
        lbl_display.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9; -fx-background-color: #EEEEEE");
        // key_0.setStyle("-fx-background-image: url(\"@../main/resources/phone_2.jpg\")");
        // lbl_display.setBackground( new java.awt.Color(0xcca6a6));

    }

    public void btn_select_num(ActionEvent actionEvent) {

        String text_display = lbl_display.getText();
        String text = ((Button) actionEvent.getSource()).getText();

        if (text_display.length() > 0) {
            if (text_display.length() == 1 && text_display.equalsIgnoreCase("+")) {
                if (!text.equalsIgnoreCase("0")) {
                    lbl_display.setText(text_display + text);
                }
            } else {
                lbl_display.setText(text_display + text);
            }
        } else {
            if (!text.equalsIgnoreCase("0")) {
                lbl_display.setText(text_display + text);
            }
        }
    }


    public void btn_zero_plus_pressed(MouseEvent mouseEvent) {
        if (onMousePlusKeyPressLimiter == 0) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    onMousePlusKeyPressLimiter = 1;
                    timer.cancel();
                }
            };
            timer.schedule(task, 500, 500);
        }
    }

    public void btn_zero_plus_released(MouseEvent mouseEvent) {
        String text_display = lbl_display.getText();
        if (text_display.length() == 0 && onMousePlusKeyPressLimiter == 1) {
            lbl_display.setText("+");
            onMousePlusKeyPressLimiter = 0;
        }
    }

    public void btn_back_clear(ActionEvent actionEvent) {
        String text = ((Button) actionEvent.getSource()).getText();
        String text_display = lbl_display.getText();

        if (text.equalsIgnoreCase("CLS")) {
            if(text_display.length() != 0) {
                lbl_display.setText("");
            }
        } else {
            if(text_display.length() != 0) {
                lbl_display.setText(AuxiliaryMethods.removeLastChar(text_display));
            }
        }
    }

    public void btn_call_start(ActionEvent actionEvent) throws SQLException {
        String text_display = lbl_display.getText();

        if (text_display.length() != 0) {
            long number = 0;
            String plus = String.valueOf(text_display.charAt(0));
            if (plus.equalsIgnoreCase("+")) {
                try {
                    number =  Long.parseLong(AuxiliaryMethods.removeFirstChar(text_display));
                } catch (Exception e) {
                    System.out.println("Bad parse to long");
                }
            } else {
                try {
                    number =  Long.parseLong(text_display);
                } catch (Exception e) {
                    System.out.println("Bad parse to long");
                }
            }

            if (number > 0) {

                String query = "SELECT users.id, user_name, number FROM " + SettingsDB.DBTableSip.users + " INNER JOIN " + SettingsDB.DBTableSip.numbers + " on users.id = numbers.id_users WHERE numbers.number = "+number;
                System.out.println(query);
                DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
                ResultSet rs =  DBM.select("NONE", query, true);

                ArrayList<DataModelSimpleContactList> contact_list = new ArrayList<DataModelSimpleContactList>();

                while(rs.next()) {
                   contact_list.add(new DataModelSimpleContactList(rs.getLong("id"), rs.getLong("number"), rs.getString("user_name")));
                   break;
                }

                if (contact_list.size() > 0) {
                    String query_1 = "INSERT INTO "+ SettingsDB.DBTableSip.connect_list +" (numbers, users, date_time_start, date_time_stop, status) "+
                            "VALUES ("+ contact_list.get(0).getNumber() +", \"" + contact_list.get(0).getUserName() + "\", datetime(\"now\"), datetime(\"now\"), 0)";
                    System.out.println(query_1);
                } else {

                }


//                if (DBM.insert(query)) {
//                    query = "SELECT id FROM " + SettingsDB.DBTableSip.connect_list + " WHERE id=(SELECT MAX(id) FROM " + SettingsDB.DBTableSip.connect_list + ") LIMIT 1";
//                    ResultSet rs = DBM.select("none", query, true);
//                    long id = 0;
//                    do {
//                        id = Long.parseLong(rs.getString("id"));
//                        break;
//                    } while (rs.next());
//                    rs.close();
//                    CurrentConnect.id_current_connect = id;
//                }
            }
        }
    }
}
