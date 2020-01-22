package sipphone.datamodel;

import externalDataModel.TimeFormatViews;

public class DataModelLastCallList {
    private Long id = null;
    private Long lp = null;
    private Long number = null;
    private String users_name = null;
    private String date_time_start = null;
    private String date_time_stop = null;
    private int status = 0;
    private Long conn_time = null;


    public DataModelLastCallList(Long id, Long lp, Long number, String users_name, String date_time_start, String date_time_stop, int status, Long conn_time) {
        this.id = id;
        this.lp = lp;
        this.number = number;
        this.users_name = users_name;
        this.date_time_start = date_time_start;
        this.date_time_stop = date_time_stop;
        this.status = status;
        this.conn_time = conn_time;
    }

    public Long getId() {
        return this.id;
    }

    public String getLp() {
        return this.lp.toString();
    }

    public  String getNumber() {
        return this.number.toString();
    }

    public String getUsers_name() {
        return this.users_name;
    }

    public String getDate_time_start() {
        return this.date_time_start;
    }

    public String getDate_time_stop() {
        return this.date_time_stop;
    }

    public int getStatus() {
        return this.status;
    }

    public String  getConn_time() {
        return TimeFormatViews.fullFormat(this.conn_time);
    }
}
