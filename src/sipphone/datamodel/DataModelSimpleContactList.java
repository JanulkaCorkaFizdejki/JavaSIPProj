package sipphone.datamodel;

public class DataModelSimpleContactList {
    private long id;
    private long number;
    private String userName;

    public DataModelSimpleContactList (long id, long number, String userName) {
        this.id = id;
        this.number = number;
        this.userName = userName;
    }

    public String getId() {
        return String.valueOf(this.id);
    }

    public String getNumber() {
        return String.valueOf(this.number);
    }

    public String getUserName() {
        return this.userName;
    }

}
