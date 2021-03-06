package sipphone.viewControllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sipphone.DabatabaseManager;
import sipphone.SettingsDB;
import sipphone.datamodel.DataModelLogin;
import sipphone.model.NetworkDataManager;
import sipphone.settings.AppColors;
import sipphone.settings.SettingsDataNetwork;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ViewControllerLoginPanel implements Initializable {

    public Pane pane_img_loader;
    public Pane pane_login;
    public TextField txtfield_login;
    public TextField txtfield_password;
    public Pane pane_top_login;
    public Button btn_logIn;
    public CheckBox check_edit_fields;
    public Circle icon_status;
    public Pane pane_delete;
    public Button btn_go_back;
    private boolean isIssetUserDB = false;
    private boolean get_login_status = false;
    public Label lbl_login_status;
    public ProgressIndicator progressIndicator = new ProgressIndicator();
    private boolean loadStatus = false;



    enum LoginView {
        SHOW_TOP,
        SHOW_BOTTOM,
        SHOW_ALL,
        HIDE_ALL
    }

    enum TypeAuth {
        FIRST_LOGIN,
        UPDATE_LOGIN
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressIndicator.setPrefSize(14.0, 14.0);
        pane_img_loader.getChildren().add(progressIndicator);
        progressIndicator.setVisible(false);


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

            if (!status) {
                this.paneLoginToggle(LoginView.SHOW_BOTTOM);
                txtfield_login.setText(rs.getString("uid"));
                txtfield_login.setDisable(true);
                txtfield_password.setText(rs.getString("password"));
                txtfield_password.setDisable(true);

            } else {
                get_login_status = true;
                this.paneLoginToggle(LoginView.SHOW_TOP);
            }
            rs.close();
        } else {
            check_edit_fields.setVisible(false);
            this.paneLoginToggle(LoginView.SHOW_BOTTOM);
            lbl_login_status.setText("UŻYTKOWNIK NIE ISTNIEJE");
            icon_status.setFill(Color.valueOf(AppColors.getColor(AppColors.Colors.red)));
            this.loginPassdordTxtFieldDisable(false);
            txtfield_password.setText("");
            txtfield_login.setText("");
        }
    }

    public void btn_logout(ActionEvent actionEvent) throws SQLException {
        if (get_login_status) {
            String query = "UPDATE " + SettingsDB.DBTableSip.user_auth + " SET status=0";
            DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
            DBM.update(query);
            get_login_status = false;
            check_edit_fields.setVisible(true);
            this.loadDataView();
        }
    }

    public void btn_logIn(ActionEvent actionEvent) throws SQLException {
        if (isIssetUserDB) {
            if (txtfield_login.getText().equalsIgnoreCase("") == true || txtfield_password.getText().equalsIgnoreCase("") == true) {
                ConfirmBox.simpleAlert("Błąd danych", "Wprowadź dane!", "Wprowadź poprawną nawę użytkownika i hasło, \naby się zalogować.", ConfirmBox.AlertType.WARNING);
                return;
            } else {
                this.getUserData(TypeAuth.UPDATE_LOGIN);
                check_edit_fields.setVisible(true);
            }
        } else {
            if (txtfield_login.getText().equalsIgnoreCase("") == true || txtfield_password.getText().equalsIgnoreCase("") == true) {
                ConfirmBox.simpleAlert("Błąd danych", "Wprowadź dane!", "Wprowadź poprawną nawę użytkownika i hasło, \naby się zalogować.", ConfirmBox.AlertType.WARNING);
                return;
            } else {
                this.getUserData(TypeAuth.FIRST_LOGIN);
            }
        }
    }

    private void getUserData (TypeAuth typeAuth) {
        progressIndicator.setVisible(true);
        this.loginPassdordTxtFieldDisable(true);
        btn_logIn.setDisable(true);
        check_edit_fields.setDisable(true);

        String login = txtfield_login.getText();
        String password = txtfield_password.getText();

        btn_go_back.setDisable(true);
        Runnable runnable = () -> {

            this.loadStatus = true;

            NetworkDataManager networkDataManager = new NetworkDataManager(SettingsDataNetwork.felgApiBaseURL_AUTH);
            DataModelLogin dataModelLogin = networkDataManager.logIn(login, password);

            if (dataModelLogin.getStatus() == 1) {

                if (typeAuth == TypeAuth.FIRST_LOGIN) {
                    String lang = (dataModelLogin.getLang().equalsIgnoreCase("") == true) ? System.getProperty("user.language") : dataModelLogin.getLang();
                    String query = "INSERT INTO " + SettingsDB.DBTableSip.user_auth + " (uid, token, status, lang, password, apiid) VALUES (\"" +
                            login + "\", \"" + dataModelLogin.getToken() + "\", \"" + dataModelLogin.getStatus() + "\", \"" + lang.toUpperCase() + "\", \"" + password + "\", \"" + dataModelLogin.getUid() + "\")";

                    DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
                    DBM.insert(query);

                } else if (typeAuth == TypeAuth.UPDATE_LOGIN) {
                    String query = "UPDATE " + SettingsDB.DBTableSip.user_auth + " SET status=1, token=\"" + dataModelLogin.getToken() + "\"";

                    DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
                    DBM.update(query);
                }

                Platform.runLater(()->{
                    try {
                        this.loadDataView();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    btn_go_back.setDisable(false);
                    btn_logIn.setDisable(false);
                    progressIndicator.setVisible(false);
                    check_edit_fields.setDisable(false);
                });

            } else {
                Platform.runLater(() -> {
                    btn_go_back.setDisable(false);
                    txtfield_password.setDisable(false);
                    txtfield_login.setDisable(false);
                    btn_logIn.setDisable(false);
                    btn_logIn.setDisable(false);
                    progressIndicator.setVisible(false);
                    check_edit_fields.setDisable(false);
                    ConfirmBox.simpleAlert("Błąd danych", "Nieprawidłowe dane!", "Twoje hasło lub login jest nieprawidłowy. \nWprowadź poprawne dany, aby się zalogować.", ConfirmBox.AlertType.WARNING);
                });
            }

            this.loadStatus = false;
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    public void deleteUser(ActionEvent actionEvent) throws SQLException {
        String query = "DELETE FROM " + SettingsDB.DBTableSip.user_auth;
        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
        DBM.delete(query);
        isIssetUserDB = false;
        this.loadDataView();
    }


    public void checkBoxClick(ActionEvent actionEvent) {
       if (check_edit_fields.isSelected()) {
            this.loginPassdordTxtFieldDisable(false);
       } else {
           this.loginPassdordTxtFieldDisable(true);
       }
    }

    private void loginPassdordTxtFieldDisable (boolean disable) {
            txtfield_login.setDisable(disable);
            txtfield_password.setDisable(disable);
    }

    public void btnGoBack(ActionEvent actionEvent) throws IOException {
        if (!loadStatus) this.goBack(actionEvent);
    }


    private void goBack (Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../sample.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    private void paneLoginToggle (LoginView loginView)  {
        switch (loginView) {
            case SHOW_TOP:
                pane_top_login.setVisible(true);
                pane_login.setVisible(false);
                lbl_login_status.setText("ZALOGOWANY");
                icon_status.setFill(Color.valueOf(AppColors.getColor(AppColors.Colors.green)));
                pane_delete.setVisible(false);
                break;
            case SHOW_BOTTOM:
                pane_top_login.setVisible(false);
                pane_login.setVisible(true);
                lbl_login_status.setText("WYLOGOWANY");
                icon_status.setFill(Color.valueOf(AppColors.getColor(AppColors.Colors.yellow)));
                check_edit_fields.setSelected(false);
                if (!isIssetUserDB) {
                    pane_delete.setVisible(false);
                } else {
                    pane_delete.setVisible(true);
                }
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

}
