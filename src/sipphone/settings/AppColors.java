package sipphone.settings;

public class AppColors {

    public enum Colors {
        red,
        green,
        yellow
    }

    public static String getColor (Colors color) {
        String opt_color = "#111111";

        switch (color) {
            case red:
                opt_color = "#ff0000";
                break;
            case green:
                opt_color = "#008000";
                break;
            case yellow:
                opt_color = "#ffbd2f";
                break;
            default:
                opt_color = opt_color;
                break;
        }
        return opt_color;
    }
}
