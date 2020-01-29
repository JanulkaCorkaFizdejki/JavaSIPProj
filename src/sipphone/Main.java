package sipphone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sipphone.model.NetworkDataManager;
import sipphone.settings.SettingsDataNetwork;
import sipphone.settings.SettingsWindows;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("FELG - SP");
        primaryStage.setScene(new Scene(root, SettingsWindows.WinMain[0], SettingsWindows.WinMain[1]));
        primaryStage.show();
    }


    public static void main(String[] args)  {
        launch(args);
    }
}
