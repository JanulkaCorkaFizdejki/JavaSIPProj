package externalDataModel;

import java.lang.reflect.Array;
import java.util.List;

public class TimeFormatViews {

    public static String fullFormat (Long time) {
        String format = "HH : MM : SS";
        if (time < 60) {
            String conv_time = (time <10) ? "0" + time.toString() : time.toString();
            format = "00 : 00 : " + conv_time;
        } else if (time >= 60 && time < 3600) {
            TimeFormatViews timeFormatViews = new TimeFormatViews();
            String [] minSec = timeFormatViews.getMinSec(time);
            format = "00 : " + minSec[1] + " : " + minSec[0];
        } else if (time >= 3600) {
            TimeFormatViews timeFormatViews = new TimeFormatViews();
            String [] hourMinSec = timeFormatViews.getHourMinSec(time);
            format = hourMinSec[2]+" : " + hourMinSec[1] + " : " + hourMinSec[0];
        }
        return format;
    }

    public String[] getMinSec (Long time) {
        String[] opt_min_sec = new String[2];

        int seconds = (int) (time % 60);
        int minuts =  (int) (time / 60);
        
        opt_min_sec[0] = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
        opt_min_sec[1] = (minuts < 10) ? "0" + minuts : String.valueOf(minuts);

        return opt_min_sec;
    }

    public  String[] getHourMinSec(Long time) {
        String[] opt_hour_min_sec = new String[3];

        int hours = (int) (time / 3600);
        int rest = (int) (time % 3600);

        String[] minSec = this.getMinSec((long) rest);

        opt_hour_min_sec[0] = minSec[0];
        opt_hour_min_sec[1] = minSec[1];
        opt_hour_min_sec[2] = (hours < 10) ? "0" + hours : String.valueOf(hours);

        return  opt_hour_min_sec;
    }
}
