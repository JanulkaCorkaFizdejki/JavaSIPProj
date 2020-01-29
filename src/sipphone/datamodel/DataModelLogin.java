package sipphone.datamodel;

public class DataModelLogin {
    private int status;
    private String token;
    private String uid;
    private String lang;

    public DataModelLogin (int status, String token, String uid, String lang) {
        this.status = status;
        this.token  = token;
        this.uid    = uid;
        this.lang   = lang;
    }

    public int getStatus    () { return this.status; }
    public  String getToken () { return  this.token; }
    public  String getUid   () { return  this.uid; }
    public  String getLang  () { return this.lang; }
}
