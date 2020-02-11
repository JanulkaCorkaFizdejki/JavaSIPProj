package sipphone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sipphone.datamodel.DataModelLastCallList;
import sipphone.datamodel.DataModelNumberList;
import sipphone.settings.AppColors;
import sipphone.settings.GlobalQueryDB;
import sipphone.viewControllers.ToolpitView;
import sipphone.viewControllers.ViewControllerKeyboardPanelPhone;
import sipphone.viewControllers.ViewControllerLastCallList;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public TabPane tab_pane;
    public TableView<DataModelNumberList> pane_tbl_contact_list;
    public TableColumn pane_tbl_contact_list_col_lp;
    public TableColumn pane_tbl_contact_list_col_name;
    public TableColumn pane_tbl_contact_list_col_number;
    public Button btn_add_user;
    public Pane pane_login_bar;
    public Circle icon_login_status;
    public Label lbl_login_status;
    public TextField lbl_search;
    public MenuBar MainMenu;
    public MenuItem addUser;
    public Tab tab_lane_last_connect;
    public TableColumn tbl_last_conn_col_lp;
    public TableColumn tbl_last_conn_col_number;
    public TableColumn tbl_last_conn_col_user;
    public TableColumn tbl_last_conn_col_time_start;
    public TableColumn tbl_last_conn_col_time_stop;
    public TableColumn tbl_last_conn_col_status;
    public TableColumn tbl_last_conn_col_time_counter;
    public TableView<DataModelLastCallList> tbl_last_connection;
    // ///////////////////////////////////////////////////////////////////////////////////////////
    ArrayList<Long> idList = new ArrayList<Long>();

    public void tabPaneLastConnActn(Event event) {
    }

    public void tabPaneParentActn(MouseEvent mouseEvent) throws SQLException {
       if (tab_lane_last_connect.isSelected()) {
           this.lastCallListLoader();
       }
    }


    public void onclickRow(MouseEvent mouseEvent) {

        if (mouseEvent.getButton() ==  MouseButton.SECONDARY) {
            ContextMenu contextMenu = new ContextMenu();

            // ZADZWOŃ MenuItem
            MenuItem menuItemCall = new MenuItem("Zadzwoń");
            menuItemCall.setOnAction((ActionEvent e) -> {

                TablePosition position = (TablePosition) pane_tbl_contact_list.getSelectionModel().getSelectedCells().get(0);
                int index = position.getRow();

                // DataModelNumberList dataModelNumberList = pane_tbl_contact_list.getItems().get(index);
                System.out.println(tbl_last_conn_col_number.getCellObservableValue(index));


//                try {
//                    getCallViewController(e, menuItemCall);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
            });

            // USUŃ MenuItem
            MenuItem menuItemDelete = new MenuItem("Usuń");
            menuItemDelete.setOnAction((ActionEvent e) -> {
                System.out.println("DELETE");
                TablePosition position = (TablePosition) pane_tbl_contact_list.getSelectionModel().getSelectedCells().get(0);
                int index = position.getRow();
                DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
                String query_num = "DELETE FROM " + SettingsDB.DBTableSip.numbers + " WHERE id_users="+idList.get(index);
                String query_us = "DELETE FROM " + SettingsDB.DBTableSip.users + " WHERE id="+idList.get(index);
                DBM.delete(query_num);
                DBM.delete(query_us);

                try {
                    this.loadContactList();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            contextMenu.getItems().addAll(menuItemCall, menuItemDelete);
            contextMenu.show(tbl_last_connection, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        }
    }


    enum LoginStatus {
        login,
        logout,
        notuser
    }


    ObservableList<DataModelNumberList> contact_list        = FXCollections.observableArrayList();
    ObservableList<DataModelLastCallList> last_call_list    = FXCollections.observableArrayList();

    // webphone wobj = new webphone();

    public void lnkLogInPane(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login_panel.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Panel logowania");
        window.show();
    }

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
        ViewControllerNumbersList viewControllerNumbersList                 = new ViewControllerNumbersList();
        viewControllerNumbersList.newpage("show");
    }

    public void btn_adduser(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_user.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage window;

        if (addUser.equals(actionEvent.getSource())) {
            window = (Stage) MainMenu.getScene().getWindow();
        } else {
            window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        }

        window.setScene(scene);
        window.setTitle("Dodaj kontakt");
        window.show();
    }


    public void btn_remove_number(ActionEvent actionEvent) throws IOException {
        ViewControllerRemoveNumber viewControllerRemoveNumber               = new ViewControllerRemoveNumber();
        viewControllerRemoveNumber.newpage("show");
    }

    public void btn_last_call_list(ActionEvent actionEvent) throws IOException {
        ViewControllerLastCallList viewControllerLastCalltList              = new ViewControllerLastCallList();
        viewControllerLastCalltList.newpage("show");
//
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("last_call_list.fxml"));
//        Parent root = (Parent) fxmlLoader.load();
//        Scene scene = new Scene(root);
//        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        window.setScene(scene);
//        window.setTitle("Ostatnie połączenia");
//        window.show();
    }

    public void btn_keyboard_panel(ActionEvent actionEvent) throws IOException {
        ViewControllerKeyboardPanelPhone viewControllerKeyboardPanelPhone   = new ViewControllerKeyboardPanelPhone();
        viewControllerKeyboardPanelPhone.newpage("show");
    }

    public void btn_login_panel(ActionEvent actionEvent) throws IOException {
//        ViewControllerLoginPanel viewControllerLoginPanel                   = new ViewControllerLoginPanel();
//        viewControllerLoginPanel.newpage("show");
    }

    public void lblSearchActn(KeyEvent keyEvent) throws SQLException {

        if (lbl_search.getText().length() < 1) {
            this.loadContactList();
            return;
        }

        String txt = lbl_search.getText();
        String query = "";

        try {
            long num_tel = Long.parseLong(txt);
            query = "SELECT users.user_name, numbers.number FROM users INNER JOIN numbers ON users.id = numbers.id_users WHERE number LIKE '%" + num_tel +"%'";
            this.loadContactList(query);
        } catch (NumberFormatException | SQLException e) {
            query = "SELECT  users.user_name, numbers.number FROM users INNER JOIN numbers ON users.id = numbers.id_users WHERE user_name LIKE '%" + txt.trim() +"%'";
            this.loadContactList(query);
        }

    }

// ///////////////////////////////////////////////////////////////////////////////////////////
    // ------------------------------------------------------------------------------------------------------
    public void getCallViewController (ActionEvent event, MenuItem menuItem) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("call_view.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);

        if (menuItem.equals(event.getSource())) {
            Stage window = (Stage) MainMenu.getScene().getWindow();
            window.setScene(scene);
            window.setTitle("CALL");
            window.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane_login_bar.setStyle("-fx-background-color: #DDDDDD");
        btn_add_user.setTooltip(ToolpitView.tooltip("Dodaj użytkownia do listy kontaków"));
        try {
            start ();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void start() throws SQLException {
        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
        String query = "SELECT COUNT(token) AS rowcount FROM " + SettingsDB.DBTableSip.user_auth;
        ResultSet rs = DBM.select("empty", query, true);
        int count = rs.getInt("rowcount");
        rs.close();
        if (count == 0) {
            setLoginStatus(LoginStatus.notuser);
        } else {
            query = "SELECT * FROM " + SettingsDB.DBTableSip.user_auth;
            rs = DBM.select("empty", query, true);
            boolean status = rs.getBoolean("status");
            if (status == true) {
                setLoginStatus(LoginStatus.login);
            } else {
                setLoginStatus(LoginStatus.logout);
            }
            rs.close();
        }
        this.loadContactList();
    }


    private void loadContactList() throws SQLException {
        contact_list.clear();
        idList.clear();

        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);

        ResultSet rs = DBM.select("empty", GlobalQueryDB.q_selectAll, true);
        int iterator = 1;
        while (rs.next()) {
            String lp = Integer.toString(iterator);
            idList.add(rs.getLong("id"));
            contact_list.add(new DataModelNumberList(rs.getLong("id"), lp, rs.getString("number"), (rs.getString("user_name"))));
            iterator ++;
        }

        pane_tbl_contact_list_col_lp.setCellValueFactory(new PropertyValueFactory("Lp"));
        pane_tbl_contact_list_col_name.setCellValueFactory(new PropertyValueFactory("UserName"));
        pane_tbl_contact_list_col_number.setCellValueFactory(new PropertyValueFactory("Number"));

        pane_tbl_contact_list.setItems(contact_list);
        rs.close();
    }

    private void loadContactList (String query) throws SQLException {
        contact_list.clear();

        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
        ResultSet rs = DBM.selectCustom(query);
        int iterator = 1;
        while (rs.next()) {
            String lp = Integer.toString(iterator);
            contact_list.add(new DataModelNumberList(iterator, lp, rs.getString("number"), (rs.getString("user_name"))));
            iterator ++;
        }

        pane_tbl_contact_list_col_lp.setCellValueFactory(new PropertyValueFactory("Lp"));
        pane_tbl_contact_list_col_name.setCellValueFactory(new PropertyValueFactory("UserName"));
        pane_tbl_contact_list_col_number.setCellValueFactory(new PropertyValueFactory("Number"));

        pane_tbl_contact_list.setItems(contact_list);
        rs.close();
    }


    private void lastCallListLoader() throws SQLException {

        last_call_list.clear();

        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
        ResultSet rs = DBM.select("empty", GlobalQueryDB.q_selectAllLastCallList, true);
        int iterator = 1;
        while (rs.next()) {
            Long lp = Long.valueOf(iterator);
            last_call_list.add(new DataModelLastCallList(rs.getLong("id"), lp, rs.getLong("numbers"), rs.getString("users"),
                    rs.getString("date_time_start"), rs.getString("date_time_stop"),
                    rs.getInt("status"), rs.getLong("conn_time")));
            iterator ++;
        }
        tbl_last_conn_col_lp.setCellValueFactory(new PropertyValueFactory("Lp"));

        tbl_last_conn_col_number.setCellValueFactory(new PropertyValueFactory("Number"));
        tbl_last_conn_col_user.setCellValueFactory(new PropertyValueFactory("Users_name"));
        tbl_last_conn_col_time_start.setCellValueFactory(new PropertyValueFactory("Date_time_start"));
        tbl_last_conn_col_time_stop.setCellValueFactory(new PropertyValueFactory("Date_time_stop"));
        tbl_last_conn_col_status.setCellValueFactory(new PropertyValueFactory<ImageView, ImageView>("Status"));
        tbl_last_conn_col_time_counter.setCellValueFactory(new PropertyValueFactory("Conn_time"));

        tbl_last_connection.setItems(last_call_list);
        rs.close();
        
    }

    private void setLoginStatus (LoginStatus loginStatus) {
        switch (loginStatus) {
            case login:
                icon_login_status.setFill(Color.valueOf(AppColors.getColor(AppColors.Colors.green)));
                lbl_login_status.setText("UŻYTKOWNIK ZALOGOWANY");
                break;
            case logout:
                icon_login_status.setFill(Color.valueOf(AppColors.getColor(AppColors.Colors.yellow)));
                lbl_login_status.setText("UŻYTKOWNIK NIEZALOGOWANY");
                break;
            case notuser:
                icon_login_status.setFill(Color.valueOf(AppColors.getColor(AppColors.Colors.red)));
                lbl_login_status.setText("UŻYTKOWNIK NIE ISTNIEJE");
                break;
        }
    }

}
