package sipphone;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sipphone.settings.SettingsWindows;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ViewContollerAddUser implements Initializable {

    public TextField lbl_number;
    public TextField lbl_user_name;
    private FXMLLoader fxmlLoader = null;
    private String WinTittle = "Dodaj kontakt";

    public ViewContollerAddUser () {

        this.fxmlLoader = new FXMLLoader(getClass().getResource("add_user.fxml"));
    }

    void newpage (String event) throws IOException {
        Parent root = (Parent) this.fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, SettingsWindows.WinMainChild[0], SettingsWindows.WinMainChild[1]));
        stage.setTitle(this.WinTittle);
        stage.show();
    }


    @FXML
    public void addUserActn(javafx.scene.input.MouseEvent mouseEvent) {


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
                        
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
