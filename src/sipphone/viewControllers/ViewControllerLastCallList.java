package sipphone.viewControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sipphone.DabatabaseManager;
import sipphone.SettingsDB;
import sipphone.datamodel.DataModelLastCallList;
import sipphone.settings.GlobalQueryDB;
import sipphone.settings.SettingsWindows;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewControllerLastCallList implements Initializable {

    public TableView<DataModelLastCallList> tbl_last_call;
    public TableColumn col_lp;
    public TableColumn col_numbers;
    public TableColumn col_users;
    public TableColumn col_start_call;
    public TableColumn col_stop_call;
    public TableColumn col_status_call;
    public TableColumn col_time_call;

    ObservableList<DataModelLastCallList> list = FXCollections.observableArrayList();

    public void newpage(String event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../last_call_list.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, SettingsWindows.WinBigChild1[0], SettingsWindows.WinBigChild1[1]));
        stage.setTitle("Ostatnie połączenia");
        stage.show();
    }

    private void loadData () throws SQLException {
        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
        ResultSet rs = DBM.select("empty", GlobalQueryDB.q_selectAllLastCallList, true);
        int iterator = 1;
        while (rs.next()) {
            Long lp = Long.valueOf(iterator);
            list.add(new DataModelLastCallList(rs.getLong("id"), lp, rs.getLong("numbers"), rs.getString("users"),
                    rs.getString("date_time_start"), rs.getString("date_time_stop"),
                    rs.getInt("status"), rs.getLong("conn_time")));
            iterator ++;
        }

        col_lp.setCellValueFactory(new PropertyValueFactory("Lp"));
        col_numbers.setCellValueFactory(new PropertyValueFactory("Number"));
        col_users.setCellValueFactory(new PropertyValueFactory("Users_name"));
        col_start_call.setCellValueFactory(new PropertyValueFactory("Date_time_start"));
        col_stop_call.setCellValueFactory(new PropertyValueFactory("Date_time_stop"));
        col_status_call.setCellValueFactory(new PropertyValueFactory("Status"));
        col_time_call.setCellValueFactory(new PropertyValueFactory("Conn_time"));


        list.remove(list);
        tbl_last_call.setItems(list);
        rs.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbl_last_call.setStyle("-fx-font: 10 arial; -fx-base: #b6e7c9;");
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String convertStatusCall(int status) {
        String opt_status = "Status";
        switch(status) {
            case 0:
                opt_status = "nieodebrane";
                break;
            case 1:
                opt_status = "odebrane";
                break;
            default:
                opt_status = opt_status;
        }
        return  opt_status;
    }
}
