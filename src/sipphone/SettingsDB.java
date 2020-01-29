package sipphone;

public class SettingsDB {
    public static String username = "user";
    public static String password = "password";
    public static String dbname = "db_sip_felg";

    // TABLES MAPS
    public static enum DBTableSip {
        numbers,
        users,
        connect_list,
        connect_time,
        user_auth
    }
}
