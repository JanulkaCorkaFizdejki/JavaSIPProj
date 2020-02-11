package sipphone.viewControllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewControllerCall implements Initializable {
    public Label number;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            System.out.println(resourceBundle);
    }


    public void btnGoBack(ActionEvent event) throws IOException {
        this.goBack(event);
    }

    private void goBack (Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../sample.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
