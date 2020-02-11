package sipphone.viewControllers;

public class ButtonStyle {
    public static String round = "-fx-background-radius: 5em; " +
            "-fx-min-width: 20px; " +
            "-fx-min-height: 20px; " +
            "-fx-max-width: 20px; " +
            "-fx-max-height: 20px; " +
            "-fx-padding: 0px;";

    public  static String round(int size) {
        return "-fx-background-radius: 5em; " +
                "-fx-min-width: "+ size +"px; " +
                "-fx-min-height: "+ size +"px; " +
                "-fx-max-width: "+ size +"px; " +
                "-fx-max-height: "+ size +"px; " +
                "-fx-padding: 0px;";
    }
}
