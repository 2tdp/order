package datnt.com.orders.Order;

import androidx.annotation.Nullable;

public class Pro {
    private long idPr;
    private String name;
    private String price;
    private String quantity;


    public Pro(long idPr, String name, String price, String quantity) {
        this.idPr = idPr;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public long getIdPr() {
        return idPr;
    }

    public void setIdPr(long idPr) {
        this.idPr = idPr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pro pro = (Pro) obj;
        if (!name.equals(pro.name)) {
            return false;
        }
        if (!price.equals(pro.getPrice())) {
            return false;
        }
        if (!quantity.equals(pro.getQuantity())) {
            return false;
        }
        return true;
    }
}
