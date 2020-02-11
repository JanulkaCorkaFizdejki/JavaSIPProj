package sipphone;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DabatabaseManager {
    private String db_name;
    private Connection conn = null;

    enum kindQuery {
        custom
    }

    public  DabatabaseManager (String db_name) {
        this.db_name = db_name;
    }

    public void createDataBase () {
        try {
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:"+db_name+".db");
            System.out.println("Baza");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Boolean isConnect () {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:"+db_name+".db");
            System.out.println(this.conn.getMetaData());
            this.conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Connection problem");
            return true;
        }
    }

    // SELECT ALL
    public ArrayList<String> select (String tableName) {
        ArrayList<String> optList = new ArrayList<String>();
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:../../" + this.db_name + ".db");
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

            while (rs.next()) {
                String num = rs.getString("number");
                optList.add(num);
            }
            this.conn.close();
            return optList;
        } catch (Exception e) {
            System.out.println(e);
            return optList;
        }
    }

    // SELECT ALL QUERY OPTION (true: )
    public ResultSet select (String tableName, String queryOption, boolean queryAll) {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:../../" + this.db_name + ".db");
            Statement stmt = this.conn.createStatement();
            ResultSet rs = (queryAll) ? stmt.executeQuery(queryOption) : stmt.executeQuery("SELECT * FROM " + tableName + " WHERE " + queryOption);
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet selectCustom (String query) {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:../../" + this.db_name + ".db");
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public boolean insert (String query) {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:../../" + this.db_name + ".db");
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            return (pstmt.executeUpdate() == 1) ? true : false;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public void insert(int phoneNumber, String table_name) {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:../../" + this.db_name + ".db");
            String insertRow = "INSERT INTO " + table_name.trim() + " (number) VALUES(" + phoneNumber + ")";
            PreparedStatement pstmt = this.conn.prepareStatement(insertRow);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete (String query) {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:../../" + this.db_name + ".db");
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            pstmt.executeUpdate();
            this.conn.close();
        } catch (SQLException e) {
            System.out.println("DELETE EX: " + e);
        }
    }

    public boolean update (String query) {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:../../" + this.db_name + ".db");
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            return (pstmt.executeUpdate() == 1) ? true : false;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
