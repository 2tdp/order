package datnt.com.orders.Products;

import java.io.Serializable;

import androidx.annotation.Nullable;
import datnt.com.orders.Order.Pro;

public class Product implements Serializable {

    private long idPr;
    private String namePr;
    private String imagePr;
    private long pricePr;
    private long discountPr;
    private long quantityPr;
    private String statusPr;
    private String createDate;
    private String modifiedDate;
    private String createBy;
    private String modifiedBy;

    Product() {
    }

    public Product(long idPr, String namePr, String imagePr, long pricePr, long discountPr, long quantityPr, String statusPr, String createDate, String modifiedDate, String createBy, String modifiedBy) {
        this.idPr = idPr;
        this.namePr = namePr;
        this.imagePr = imagePr;
        this.pricePr = pricePr;
        this.discountPr = discountPr;
        this.quantityPr = quantityPr;
        this.statusPr = statusPr;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.createBy = createBy;
        this.modifiedBy = modifiedBy;
    }

    public long getIdPr() {
        return idPr;
    }

    public void setIdPr(long idPr) {
        this.idPr = idPr;
    }

    public String getNamePr() {
        return namePr;
    }

    public void setNamePr(String namePr) {
        this.namePr = namePr;
    }

    public String getImagePr() {
        return imagePr;
    }

    public void setImagePr(String imagePr) {
        this.imagePr = imagePr;
    }

    public long getPricePr() {
        return pricePr;
    }

    public void setPricePr(long pricePr) {
        this.pricePr = pricePr;
    }

    public long getDiscountPr() {
        return discountPr;
    }

    public void setDiscountPr(long discountPr) {
        this.discountPr = discountPr;
    }

    public long getQuantityPr() {
        return quantityPr;
    }

    public void setQuantityPr(long quantityPr) {
        this.quantityPr = quantityPr;
    }

    public String getStatusPr() {
        return statusPr;
    }

    public void setStatusPr(String statusPr) {
        this.statusPr = statusPr;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product pro = (Product) obj;
        if (!namePr.equals(pro.getNamePr())) {
            return false;
        }
        if (pricePr != pro.getPricePr()) {
            return false;
        }
        if (quantityPr == pro.getQuantityPr()) {
            return false;
        }
        return true;
    }
}
