package sipphone.datamodel;

public class DataModelNumberList {
    private long Id = 0;
    private String Lp = null;
    private String Number = null;
    private String UserName = null;

    public DataModelNumberList (long Id,  String lp, String number, String userName) {
        this.Id = Id;
        this.Lp = lp;
        this.Number = number;
        this.UserName = userName;
    }

    public long getId() {return this.Id;}
    public String getLp() {return this.Lp;}
    public String getNumber() {return this.Number;}
    public String getUserName() {return this.UserName;}
}
