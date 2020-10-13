package datnt.com.orders.Order;

import java.io.Serializable;

import androidx.annotation.Nullable;

public class Order implements Serializable {

    private long idBat;
    private long idPro;
    private String table;
    private String people;
    private String namePr;
    private String pricePr;
    private String quantityPr;

    public Order(long idBat, long idPro, String table, String people, String namePr, String pricePr, String quantityPr) {
        this.idBat = idBat;
        this.idPro = idPro;
        this.table = table;
        this.people = people;
        this.namePr = namePr;
        this.pricePr = pricePr;
        this.quantityPr = quantityPr;
    }

    public long getIdBat() {
        return idBat;
    }

    public void setIdBat(long idBat) {
        this.idBat = idBat;
    }

    public long getIdPro() {
        return idPro;
    }

    public void setIdPro(long idPro) {
        this.idPro = idPro;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getNamePr() {
        return namePr;
    }

    public void setNamePr(String namePr) {
        this.namePr = namePr;
    }

    public String getPricePr() {
        return pricePr;
    }

    public void setPricePr(String pricePr) {
        this.pricePr = pricePr;
    }

    public String getQuantityPr() {
        return quantityPr;
    }

    public void setQuantityPr(String quantityPr) {
        this.quantityPr = quantityPr;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order order = (Order) obj;
        if (!namePr.equals(order.getNamePr())) {
            return false;
        }
        if (!pricePr.equals(order.getPricePr())) {
            return false;
        }
        if (!quantityPr.equals(order.getQuantityPr())) {
            return false;
        }
        return true;
    }
}
