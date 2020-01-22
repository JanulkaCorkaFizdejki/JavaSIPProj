package sipphone;
import javafx.event.ActionEvent;
import sipphone.model.CurrentConnect;
import sipphone.viewControllers.ViewControllerLastCallList;
import webphone. *;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class Controller {
    webphone wobj = new webphone();

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
        DabatabaseManager DBM = new DabatabaseManager(SettingsDB.dbname);
        if (CurrentConnect.id_current_connect > 0) {
            String query = "UPDATE connect_list SET date_time_stop=datetime(\"now\") WHERE id="+CurrentConnect.id_current_connect;
            DBM.update(query);
            CurrentConnect.id_current_connect = 0;
        }

    }

    public void btn_numbers(ActionEvent actionEvent) throws IOException {
        ViewControllerNumbersList viewControllerNumbersList = new ViewControllerNumbersList();
        viewControllerNumbersList.newpage("show");
    }

    public void btn_adduser(ActionEvent actionEvent) throws IOException {
        ViewContollerAddUser viewContollerAddUser = new ViewContollerAddUser();
        viewContollerAddUser.newpage("show");
    }

    public void btn_remove_number(ActionEvent actionEvent) throws IOException {
        ViewControllerRemoveNumber viewControllerRemoveNumber = new ViewControllerRemoveNumber();
        viewControllerRemoveNumber.newpage("show");
    }

    public void btn_last_call_list(ActionEvent actionEvent) throws IOException {
        ViewControllerLastCallList viewControllerLastCalltList = new ViewControllerLastCallList();
        viewControllerLastCalltList.newpage("show");
    }
}
