package sipphone.viewControllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sipphone.DabatabaseManager;
import sipphone.SettingsDB;
import sipphone.datamodel.DataModelLogin;
import sipphone.model.NetworkDataManager;
import sipphone.settings.SettingsDataNetwork;
import sipphone.settings.SettingsWindows;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewControllerLoginPanel implements Initializable {



    enum LoginView {
        SHOW_TOP,
        SHOW_BOTTOM,
        SHOW_ALL,
        HIDE_ALL
    }

    public ImageView img_loader;
    public Pane pane_login;
    public TextField txtfield_login;
    public TextField txtfield_password;
    public Pane pane_top_login;
    private boolean isIssetUserDB = false;
    private boolean get_login_status = false;
    public Label lbl_login_status;

    public void newpage(String event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../login_panel.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, SettingsWindows.WinMainChild[0], SettingsWindows.WinMainChild[1]));
        stage.setTitle("Panel logowania");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        img_loader.setVisible(false);
        try {
            loadDataView();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loadDataView () throws SQLException {

        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
        String query = "SELECT COUNT(token) AS rowcount FROM " + SettingsDB.DBTableSip.user_auth;

        ResultSet rs = DBM.select("empty", query, true);
        int count = rs.getInt("rowcount");
        rs.close();

        if (count == 1) isIssetUserDB = true;

        if (isIssetUserDB) {
            query = "SELECT * FROM " + SettingsDB.DBTableSip.user_auth;
            rs = DBM.select("empty", query, true);
            boolean status = rs.getBoolean("status");

            if (status == false) {
                this.paneLoginToggle(LoginView.SHOW_BOTTOM);
                txtfield_login.setText(rs.getString("uid"));
                txtfield_login.setDisable(true);
                txtfield_password.setText("***************");
                txtfield_password.setDisable(true);
            } else {
                get_login_status = true;
                this.paneLoginToggle(LoginView.SHOW_TOP);
            }
            rs.close();
        } else {
            this.paneLoginToggle(LoginView.SHOW_BOTTOM);
        }
    }

    public void btn_logout(ActionEvent actionEvent) throws SQLException {
        if (get_login_status) {
            String query = "UPDATE " + SettingsDB.DBTableSip.user_auth + " SET status=0";
            DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
            DBM.update(query);
            get_login_status = false;
            this.loadDataView();
        }
    }

    public void btn_logIn(ActionEvent actionEvent) throws SQLException {
        if (isIssetUserDB) {
            String query = "UPDATE " + SettingsDB.DBTableSip.user_auth + " SET status=1";
            DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
            DBM.update(query);
            this.loadDataView();
        } else {
            img_loader.setVisible(true);

            if (txtfield_login.getText().equalsIgnoreCase("") == true || txtfield_password.getText().equalsIgnoreCase("") == true) {

                System.out.println("BARK DANYCH");
                img_loader.setVisible(false);
                return;
            } else {
                String login = txtfield_login.getText();
                String password = txtfield_password.getText();

                NetworkDataManager networkDataManager = new NetworkDataManager(SettingsDataNetwork.felgApiBaseURL_AUTH);
                Platform.runLater(() -> {
                    DataModelLogin dataModelLogin = networkDataManager.logIn(login, password);

                    if (dataModelLogin.getStatus() == 1) {
                        String lang = (dataModelLogin.getLang().equalsIgnoreCase("") == true) ? System.getProperty("user.language") : dataModelLogin.getLang();
                        String query = "INSERT INTO " + SettingsDB.DBTableSip.user_auth + " (uid, token, status, lang) VALUES (\"" +
                                dataModelLogin.getUid() + "\", \"" + dataModelLogin.getToken() + "\", \"" + dataModelLogin.getStatus() + "\", \"" + lang + "\")";

                        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
                        DBM.insert(query);

                        try {
                            this.httpLoaderClear();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } else {
                        img_loader.setVisible(false);
                        System.out.println("ZŁY LOGIN LUB HASŁO");
                    }
                });
            }
        }
    }

    private void paneLoginToggle (LoginView loginView)  {
        switch (loginView) {
            case SHOW_TOP:
                pane_top_login.setVisible(true);
                pane_login.setVisible(false);
                lbl_login_status.setText("ZALOGOWANY");
                break;
            case SHOW_BOTTOM:
                pane_top_login.setVisible(false);
                pane_login.setVisible(true);
                lbl_login_status.setText("WYLOGOWANY");
                break;
            case SHOW_ALL:
                pane_top_login.setVisible(true);
                pane_login.setVisible(true);
                break;
            case HIDE_ALL:
                pane_top_login.setVisible(false);
                pane_login.setVisible(false);
                break;
        }
    }

    private void httpLoaderClear () throws SQLException {
        this.img_loader.setVisible(false);
        this.loadDataView();
    }
}
