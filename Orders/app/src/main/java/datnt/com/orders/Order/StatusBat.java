package datnt.com.orders.Order;

public class StatusBat {

    private long id;
    private String namebat;
    private String status;

    public StatusBat(long id, String namebat, String status) {
        this.id = id;
        this.namebat = namebat;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamebat() {
        return namebat;
    }

    public void setNamebat(String namebat) {
        this.namebat = namebat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
