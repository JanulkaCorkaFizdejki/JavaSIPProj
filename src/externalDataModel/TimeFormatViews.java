package externalDataModel;

public class TimeFormatViews {
    public static String fullFormat (Long time) {
        String format = "HH : MM : SS";

        String conv_time = (time <10) ? "0" + time.toString() : time.toString();

        if (time < 60) {
            format = "00 : 00 : " + conv_time;
        } else if (time >= 60 && time < 3600) {
            int seconds = (int) (time % 60);
            int minuts =  (int) (time / 60.0);

            String conv_seconds = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
            String conv_minuts = (minuts < 10) ? "0" + minuts : String.valueOf(minuts);
            format = "00 : " + conv_minuts + " : " + conv_seconds;
        }
        return format;
    }
}
