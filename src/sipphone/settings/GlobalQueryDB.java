package sipphone.settings;

import sipphone.SettingsDB;

public class GlobalQueryDB {
    public static String q_selectAll = "SELECT * FROM " + SettingsDB.DBTableSip.users.toString() + " INNER JOIN " +
            SettingsDB.DBTableSip.numbers.toString() + " ON " +
            SettingsDB.DBTableSip.users.toString() + ".id = " + SettingsDB.DBTableSip.numbers.toString() + ".id_users ORDER BY id DESC";

    // SELECT * FROM connect_list INNER JOIN connect_time ON connect_list.id = connect_time.id_connect_list
    public static String q_selectAllLastCallList = "SELECT * FROM "+ SettingsDB.DBTableSip.connect_list.toString() +
            " INNER JOIN "+ SettingsDB.DBTableSip.connect_time.toString() +" ON connect_list.id = connect_time.id_connect_list ORDER BY id DESC";
}
