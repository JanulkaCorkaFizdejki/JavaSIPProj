package externalDataModel;

public class AuxiliaryMethods {

    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public static String removeFirstChar(String str) {
        return str.substring(1, str.length());
    }
}
