package datnt.com.orders.Users;

import java.io.Serializable;

import androidx.annotation.Nullable;
import datnt.com.orders.Products.Product;

public class User implements Serializable {

    private long idUser;
    private long idRole;
    private String fullName;
    private String userName;
    private String passWord;
    private String role;
    private String createDate;
    private String modifiedDate;
    private String createBy;
    private String modifiedBy;

    User() {
    }

    public User(long idUser, long idRole, String fullName, String userName, String passWord, String role, String createDate, String modifiedDate, String createBy, String modifiedBy) {
        this.idUser = idUser;
        this.idRole = idRole;
        this.fullName = fullName;
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.createBy = createBy;
        this.modifiedBy = modifiedBy;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdRole() {
        return idRole;
    }

    public void setIdRole(long idRole) {
        this.idRole = idRole;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        User user = (User) obj;
        if (!userName.equals(user.getUserName())) {
            return false;
        }
        if (!passWord.equals(user.getPassWord())) {
            return false;
        }
        if (!fullName.equals(user.getFullName())) {
            return false;
        }
        return true;
    }
}
