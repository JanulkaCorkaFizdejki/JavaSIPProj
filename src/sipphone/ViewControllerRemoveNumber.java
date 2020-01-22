package sipphone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sipphone.datamodel.DataModelNumberList;
import sipphone.settings.GlobalQueryDB;
import sipphone.settings.SettingsWindows;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewControllerRemoveNumber implements Initializable {
    ArrayList <Integer> idList = new ArrayList<Integer>();
    public TableColumn col_lp;
    public TableColumn col_number;
    public TableColumn col_user_name;
    ObservableList <DataModelNumberList> list = FXCollections.observableArrayList();
    public TableView<DataModelNumberList> tbl_numbersList;

    void newpage (String event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("numbers_list_remove.fxml"));
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
            list.add(new DataModelNumberList(rs.getLong("id"), lp + ".", rs.getString("number"), (rs.getString("user_name"))));
            idList.add(Integer.parseInt(rs.getString("id")));
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
        if (this.deleteRow(tbl_numbersList.getItems().get(index).getNumber(), tbl_numbersList.getItems().get(index).getUserName())) {
            DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
            String query_num = "DELETE FROM " + SettingsDB.DBTableSip.numbers + " WHERE id_users="+idList.get(index);
            String query_us = "DELETE FROM " + SettingsDB.DBTableSip.users + " WHERE id="+idList.get(index);
            DBM.delete(query_num);
            DBM.delete(query_us);
            tbl_numbersList.getItems().clear();
            this.loadData();
        }
    }

    private boolean deleteRow (String phoneMumber, String userName) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setResizable(false);
        alert.getButtonTypes().clear();
        alert.setTitle("Usuwanie kontaku");
        alert.setHeaderText("Czy chcesz usunąć  ten kontakt?");
        alert.setContentText(String.format("- nr telefonu: %s\n- nazwa użytkownika: %s", phoneMumber, userName));
        ButtonType buttonOk = new ButtonType("OK");
        ButtonType buttonCancel = new ButtonType("Anuluj");
        alert.getButtonTypes().addAll(buttonOk, buttonCancel);
        Optional<ButtonType> btn_res = alert.showAndWait();
        alert.setOnCloseRequest(event->alert.close());
        return (btn_res.get() == buttonOk) ? true : false;
    }
}
