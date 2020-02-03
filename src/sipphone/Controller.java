package sipphone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sipphone.datamodel.DataModelNumberList;
import sipphone.settings.GlobalQueryDB;
import sipphone.viewControllers.ToolpitView;
import sipphone.viewControllers.ViewControllerKeyboardPanelPhone;
import sipphone.viewControllers.ViewControllerLastCallList;
import sipphone.viewControllers.ViewControllerLoginPanel;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public TabPane tab_pane;
    public TableView<DataModelNumberList> pane_tbl_contact_list;
    public TableColumn pane_tbl_contact_list_col_lp;
    public TableColumn pane_tbl_contact_list_col_name;
    public TableColumn pane_tbl_contact_list_col_number;
    public Button btn_add_user;

    // Controllers Obj
    ViewControllerNumbersList viewControllerNumbersList                 = new ViewControllerNumbersList();
    ViewContollerAddUser viewContollerAddUser                           = new ViewContollerAddUser();
    ViewControllerRemoveNumber viewControllerRemoveNumber               = new ViewControllerRemoveNumber();
    ViewControllerLastCallList viewControllerLastCalltList              = new ViewControllerLastCallList();
    ViewControllerKeyboardPanelPhone viewControllerKeyboardPanelPhone   = new ViewControllerKeyboardPanelPhone();
    ViewControllerLoginPanel viewControllerLoginPanel                   = new ViewControllerLoginPanel();


    ObservableList<DataModelNumberList> contact_list = FXCollections.observableArrayList();

    private boolean set_login_status = false;
    public Label login_status;
    // webphone wobj = new webphone();

    public void start_btn(ActionEvent actionEvent) throws IOException, NoSuchAlgorithmException {
//        wobj.API_SetParameter("serveraddress", "46.105.182.20");
//        wobj.API_SetParameter("username", "JSSIP");
//        wobj.API_SetParameter("password", "wAXNEBaXDJ");
//        wobj.API_Start();
//        wobj.API_Call(-1, "48668741112");
        // ConfirmBox.display("TYTUL", "Message", SettingsWindows.WinType.error);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.showAndWait();
//        final String iv = "1234567812345678"; // This has to be 16 characters
////        final String iv2 = "12345678123456781234567812345678";
////        final String secretKey = "____";
////        final Crypto crypto = new Crypto();
////
////        final String encryptedData = crypto.encrypt("Message.", iv, secretKey);
////        System.out.println(encryptedData);
////
////        final String decryptedData = crypto.decrypt("[B@4f0d65xxxb", iv2, secretKey);
////        System.out.println(decryptedData);
        // Md5.getMd5("eloelś");
//        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
//        if (CurrentConnect.id_current_connect > 0) {
//            String query = "UPDATE connect_list SET date_time_stop=datetime(\"now\") WHERE id="+CurrentConnect.id_current_connect;
//            DBM.update(query);
//            CurrentConnect.id_current_connect = 0;
//        }

//        NetworkDataManager networkDataManager = new NetworkDataManager(SettingsDataNetwork.felgApiBaseURL_AUTH);
//        DataModelLogin dataModelLogin = networkDataManager.logIn("99613711", "Hobson123");
//
//        System.out.println(dataModelLogin.getStatus());

    }

    public void btn_numbers(ActionEvent actionEvent) throws IOException {
        viewControllerNumbersList.newpage("show");
    }

    public void btn_adduser(ActionEvent actionEvent) throws IOException {
        viewContollerAddUser.newpage("show");
    }

    public void btn_remove_number(ActionEvent actionEvent) throws IOException {
        viewControllerRemoveNumber.newpage("show");
    }

    public void btn_last_call_list(ActionEvent actionEvent) throws IOException {
        viewControllerLastCalltList.newpage("show");
    }

    public void btn_keyboard_panel(ActionEvent actionEvent) throws IOException {
        viewControllerKeyboardPanelPhone.newpage("show");
    }

    public void btn_login_panel(ActionEvent actionEvent) throws IOException {
        viewControllerLoginPanel.newpage("show");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        btn_add_user.setTooltip(ToolpitView.tooltip("Dodaj użytkownia do listy kontaków"));
        try {
            start ();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void start() throws SQLException {
                this.loadContactList();

//               tab_pane.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
//                   if (tab_pane.getSelectionModel().getSelectedIndex() ==  0) {
//                       try {
//                           this.loadContactList();
//                       } catch (SQLException e) {
//                           e.printStackTrace();
//                       }
//                   }
//               });
    }


    private void loadContactList() throws SQLException {
        contact_list.remove(contact_list);

        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);

        ResultSet rs = DBM.select("empty", GlobalQueryDB.q_selectAll, true);
        int iterator = 1;
        while (rs.next()) {
            String lp = Integer.toString(iterator);
            contact_list.add(new DataModelNumberList(rs.getLong("id"), lp, rs.getString("number"), (rs.getString("user_name"))));
            iterator ++;
        }

        pane_tbl_contact_list_col_lp.setCellValueFactory(new PropertyValueFactory("Lp"));
        pane_tbl_contact_list_col_name.setCellValueFactory(new PropertyValueFactory("UserName"));
        pane_tbl_contact_list_col_number.setCellValueFactory(new PropertyValueFactory("Number"));


        pane_tbl_contact_list.setItems(contact_list);
        rs.close();
    }



//        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
//        String query = "SELECT status FROM user_auth LIMIT 1";
//        ResultSet rs = DBM.select("empty", query, true);
//
//        while (rs.next()) {
//            this.set_login_status = rs.getBoolean ("status");
//            break;
//        }
//
//        if (this.set_login_status) {
//            System.out.println("ZALOGOWANY");
//            login_status.setText("ZALOGOWANY");
//        } else {
//            System.out.println("NIEZALOGOWANY");
//            login_status.setText("NIEZALOGOWANY");
//        }

}
