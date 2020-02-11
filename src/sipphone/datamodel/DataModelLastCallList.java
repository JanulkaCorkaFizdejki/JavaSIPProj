package sipphone.datamodel;

import externalDataModel.TimeFormatViews;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


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


    enum StatusConnectType {
        incomingCallsReceived,
        incomingCallsMissed,
        outgoingCallsReceived,
        outgoingCallsMissed,
        empty
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

    public String  getConn_time() {
        return TimeFormatViews.fullFormat(this.conn_time);
    }


    public ImageView getStatus() {
        Image img = null;
        if (this.status == 0) {
            try {
                img = new Image(setImageStatus(StatusConnectType.incomingCallsReceived));
            } catch (Exception e) {
                System.out.println("No source" + e);
            }
        }
        else if (this.status == 1) {
            try {
                img = new Image(setImageStatus(StatusConnectType.incomingCallsMissed));
            } catch (Exception e) {
                System.out.println("No source" + e);
            }
        }
        else if (this.status == 2) {
            try {
                img = new Image(setImageStatus(StatusConnectType.outgoingCallsReceived));
            } catch (Exception e) {
                System.out.println("No source" + e);
            }
        }
        else if (this.status == 3) {
            try {
                img = new Image(setImageStatus(StatusConnectType.outgoingCallsMissed));
            } catch (Exception e) {
                System.out.println("No source" + e);
            }
        }
        else {
            try {
                img = new Image(setImageStatus(StatusConnectType.empty));
            } catch (Exception e) {
                System.out.println("No source" + e);
            }
        }

        ImageView imageView = new ImageView();
        imageView.setImage(img);
        return imageView;
    }


    private String setImageStatus(StatusConnectType statusType) {
        String url;
        switch (statusType) {
            case  incomingCallsReceived:
                url = "main/resources/incomingCallReceived.jpg";
                break;
            case incomingCallsMissed:
                url = "main/resources/incomingCallMissed.jpg";
                break;
            case outgoingCallsReceived:
                url = "main/resources/outgoingCallsReceived.jpg";
                break;
            case outgoingCallsMissed:
                url = "main/resources/outgoingCallsMissed.jpg";
            default:
                url = "main/resources/empty.jpg";
                break;
        }
        return url;
    }
}
