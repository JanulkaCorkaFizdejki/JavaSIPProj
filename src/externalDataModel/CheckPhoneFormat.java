package externalDataModel;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckPhoneFormat {

    public static boolean allowChar (String ch) {
        ArrayList<String> allowCharsList = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "=", "+"));
        return allowCharsList.contains(ch);
    }

    public static boolean allowCode (String ch) {
        ArrayList<String> allowCodeList = new ArrayList<String>(Arrays.asList("BACK_SPACE", "LEFT", "RIGHT", "UP", "DOWN", "TAB", "CAPS"));
        return allowCodeList.contains(ch);
    }
}
