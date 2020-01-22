package sipphone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sipphone.datamodel.DataModelNumberList;
import sipphone.model.CurrentConnect;
import sipphone.settings.GlobalQueryDB;
import sipphone.settings.SettingsWindows;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewControllerNumbersList implements Initializable {
    public TableColumn col_lp;
    public TableColumn col_number;
    public TableColumn col_user_name;
    ObservableList <DataModelNumberList> list = FXCollections.observableArrayList();
    public TableView<DataModelNumberList> tbl_numbersList;

    void newpage (String event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("numbers_list.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, SettingsWindows.WinMainChild[0], SettingsWindows.WinMainChild[1]));
        stage.setTitle("Lista Kontaktów");
        stage.show();
    }

    private void loadData () throws SQLException {
        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);

        ResultSet rs = DBM.select("empty", GlobalQueryDB.q_selectAll, true);
        int iterator = 1;
        while (rs.next()) {
            String lp = Integer.toString(iterator);
            list.add(new DataModelNumberList(rs.getLong("id"), lp, rs.getString("number"), (rs.getString("user_name"))));
            iterator ++;
        }

        col_lp.setCellValueFactory(new PropertyValueFactory("Lp"));
        col_number.setCellValueFactory(new PropertyValueFactory("Number"));
        col_user_name.setCellValueFactory(new PropertyValueFactory("UserName"));

        list.remove(list);
        tbl_numbersList.setItems(list);
        rs.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleNumListEv(javafx.scene.input.MouseEvent mouseEvent) throws SQLException {
        TablePosition pos = (TablePosition) tbl_numbersList.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        String query = "INSERT INTO "+ SettingsDB.DBTableSip.connect_list +" (numbers, users, date_time_start, date_time_stop, status) " +
                "VALUES ("+ tbl_numbersList.getItems().get(index).getNumber() +", \""+ tbl_numbersList.getItems().get(index).getUserName()
                +"\", datetime(\"now\"), datetime(\"now\"), 0)";
        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
        if (DBM.insert(query)) {
            query = "SELECT id FROM "+ SettingsDB.DBTableSip.connect_list +" WHERE id=(SELECT MAX(id) FROM "+SettingsDB.DBTableSip.connect_list+") LIMIT 1";
           ResultSet rs = DBM.select("none", query, true);
            long id = 0;
            do {
                id = Long.parseLong(rs.getString("id"));
                break;
            } while (rs.next());
            rs.close();
            CurrentConnect.id_current_connect = id;
        } else {

        }
    }
}
