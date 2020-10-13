package datnt.com.orders;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import datnt.com.orders.Order.Order;
import datnt.com.orders.Order.StatusBat;
import datnt.com.orders.Products.Product;
import datnt.com.orders.Users.User;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String DB_App = "Orders.db";

    public SQLHelper(Context context) {
        super(context, DB_App, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryCrTableUser = "CREATE TABLE user (" +
                "id integer primary key autoincrement," +
                "username TEXT(150) NOT NULL," +
                "password TEXT(150) NOT NULL," +
                "fullname TEXT(150)," +
                "createddate TEXT," +
                "modifieddate TEXT," +
                "createdby TEXT(255)," +
                "modifiedby TEXT(255)" +
                ");";

        String queryCrTableRole = "CREATE TABLE role(\n" +
                "\t\t\tid integer NOT NULL PRIMARY KEY autoincrement,\n" +
                "\t\t\tname TEXT(255) NOT NULL,\n" +
                "\t\t\tcode TEXT(255) NOT NULL,\n" +
                "\t\t\tcreateddate TEXT,\n" +
                "\t\t\tmodifieddate TEXT,\n" +
                "\t\t\tcreatedby TEXT(255),\n" +
                "\t\t\tmodifiedby TEXT(255)\n" +
                "\t\t);";

        String queryCrTableUser_Role = "CREATE TABLE user_role(\n" +
                "\t\t\tid integer NOT NULL PRIMARY KEY autoincrement,\n" +
                "\t\t\troleid BIGINT NOT NULL, \n" +
                "\t\t\tuserid BIGINT NOT NULL,\n" +
                "FOREIGN KEY(roleid) REFERENCES role(id)," +
                "FOREIGN KEY(userid) REFERENCES user(id)\t\t);";

        String queryCrTableProducts = "CREATE TABLE products(\n" +
                "\t\t\tid integer NOT NULL PRIMARY KEY autoincrement,\n" +
                "\t\t\tnameproduct TEXT(255) NOT NULL,\t\t\n" +
                "\t\t\timage TEXT(255),\n" +
                "\t\t\tprice INT(11) NOT NULL,\n" +
                "\t\t\tdiscount INT(11),\n" +
                "\t\t\tquantity INT(11),\n" +
                "\t\t\tstatus TEXT \n," +
                "\t\t\tcreateddate TEXT,\n" +
                "\t\t\tmodifieddate TEXT,\n" +
                "\t\t\tcreatedby TEXT(255),\n" +
                "\t\t\tmodifiedby TEXT(255)\n" +
                "\t\t);";

        String queryCrTableOrders = "CREATE TABLE orders(\n" +
                "\t\t\tid integer NOT NULL PRIMARY KEY autoincrement,\n" +
                "\t\t\tbatid integer NOT NULL,\n" +
                "\t\t\tdetailorderid integer NOT NULL ,\n" +
                "\t\t\tcreateddate TEXT,\n" +
                "\t\t\tmodifieddate TEXT,\n" +
                "\t\t\tcreatedby TEXT(255),\n" +
                "\t\t\tmodifiedby TEXT(255));";

        String queryCrTableBat = "CREATE TABLE bat(\n" +
                "\t\t\tid integer NOT NULL PRIMARY KEY autoincrement,\n" +
                "\t\t\tnamebat TEXT(255) NOT NULL,\t\t\n" +
                "\t\t\tquantityuser INT(11) NOT NULL,\n" +
                "\t\t\tcreateddate TEXT,\n" +
                "\t\t\tmodifieddate TEXT,\n" +
                "\t\t\tcreatedby TEXT(255),\n" +
                "\t\t\tmodifiedby TEXT(255)\n" +
                "\t\t);";

        String queryCrTableStatusBat = "CREATE TABLE status_bat(" +
                "id integer NOT NULL PRIMARY KEY autoincrement," +
                "namebat TEXT," +
                "status TEXT);";

        String queryCrTableDetailOrder = "CREATE TABLE detail_order(\n" +
                "\t\t\tid integer NOT NULL PRIMARY KEY autoincrement,\n" +
                "productid integer NOT NULL, " +
                "batid integer NOT NULL, " +
                "nameproduct TEXT," +
                "price INT," +
                "discount INT," +
                "quantity INT," +
                "createddate TEXT, " +
                "modifieddate TEXT, " +
                "createdby TEXT, " +
                "modifiedby TEXT," +
                "FOREIGN KEY(batid) REFERENCES bat(id)," +
                "FOREIGN KEY(productid) REFERENCES products(id));";

        db.execSQL(queryCrTableUser);
        db.execSQL(queryCrTableUser_Role);
        db.execSQL(queryCrTableProducts);
        db.execSQL(queryCrTableRole);
        db.execSQL(queryCrTableOrders);
        db.execSQL(queryCrTableBat);
        db.execSQL(queryCrTableStatusBat);
        db.execSQL(queryCrTableDetailOrder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion == newVersion) {
            db.execSQL("drop if exists user");
            db.execSQL("drop if exists role");
            db.execSQL("drop if exists products");
            db.execSQL("drop if exists orders");
            db.execSQL("drop if exists bat");
            db.execSQL("drop if exists detail_order");
            db.execSQL("drop if exists user_role");

            onCreate(db);
        }
    }

    public long insertUser(String userName, String passWord, String fullName, String createdDate, String modifiedDate, String createBy, String modifiedBy) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("fullname", fullName);
        values.put("username", userName);
        values.put("password", passWord);
        values.put("createddate", createdDate);
        values.put("modifieddate", modifiedDate);
        values.put("createdby", createBy);
        values.put("modifiedby", modifiedBy);

        long id = db.insert("user", null, values);
        return id;
    }

    public long insertRole(String name, String code, String createdDate, String modifiedDate, String createBy, String modifiedBy) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("code", code);
        values.put("createddate", createdDate);
        values.put("modifieddate", modifiedDate);
        values.put("createdby", createBy);
        values.put("modifiedby", modifiedBy);

        long idRole = db.insert("role", null, values);
        return idRole;
    }

    public long insertRole_User(long idRole, long idUser) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("roleid", idRole);
        values.put("userid", idUser);

        long idRoleUser = db.insert("user_role", null, values);
        return idRoleUser;
    }

    public long insertProduct(String namePr, long pricePr, String pathImagePr, long discountPr, long quantity, String status, String createDate, String modifiedDate, String createBy, String modifiedBy) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nameproduct", namePr);
        values.put("price", pricePr);
        values.put("image", pathImagePr);
        values.put("discount", discountPr);
        values.put("quantity", quantity);
        values.put("status", status);
        values.put("createddate", createDate);
        values.put("modifieddate", modifiedDate);
        values.put("createdby", createBy);
        values.put("modifiedby", modifiedBy);

        long idProducts = db.insert("products", null, values);
        return idProducts;
    }

    public long insertOder(long batid, long productid, String createddate, String modifieddate, String createdby, String modifiedby) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("batid", batid);
        values.put("detailorderid", productid);
        values.put("createddate", createddate);
        values.put("modifieddate", modifieddate);
        values.put("createdby", createdby);
        values.put("modifiedby", modifiedby);

        long id = db.insert("orders", null, values);

        return id;
    }

    public long insertBat(String name, long quantityUser, String createddate, String modifieddate, String createdby, String modifiedby) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("namebat", name);
        values.put("quantityuser", quantityUser);
        values.put("createddate", createddate);
        values.put("modifieddate", modifieddate);
        values.put("createdby", createdby);
        values.put("modifiedby", modifiedby);

        long id = db.insert("bat", null, values);

        return id;
    }

    public long insertStatusBat(String namebat, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("namebat", namebat);
        values.put("status", status);

        long id = db.insert("status_bat", null, values);

        return id;
    }

    public long insertDetailOrder(long productid, long batid, String namePr, long pricePr, long quantity, String createDate, String modifiedDate, String createBy, String modifiedBy) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("productid", productid);
        values.put("batid", batid);
        values.put("nameproduct", namePr);
        values.put("price", pricePr);
        values.put("quantity", quantity);
        values.put("createddate", createDate);
        values.put("modifieddate", modifiedDate);
        values.put("createdby", createBy);
        values.put("modifiedby", modifiedBy);

        long idDetail = db.insert("detail_order", null, values);
        return idDetail;
    }


    //Xóa
    public void deleteUser(long idUser) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("user", "id = ?", new String[]{String.valueOf(idUser)});
    }

    public void deleteRole(long idRole) {

        SQLiteDatabase db = getWritableDatabase();

        db.delete("role", "id = ?", new String[]{String.valueOf(idRole)});
    }

    public void deleteUser_Role(long idRole) {

        SQLiteDatabase db = getWritableDatabase();

        db.delete("user_role", "roleid = ?", new String[]{String.valueOf(idRole)});
    }

    public void deleteProduct(long idPr) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("products", "id = ?", new String[]{String.valueOf(idPr)});
    }

    public void deleteBat(long idBat) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("bat", "id = ?", new String[]{String.valueOf(idBat)});
    }

    public void deleteOrders(long idPr) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("orders", "detailorderid = ?", new String[]{String.valueOf(idPr)});
    }

    public void deleteStatusBat(long id) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("status_bat", "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteDetailOrder(long idPr) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("detail_order", "id = ?", new String[]{String.valueOf(idPr)});
    }

    //Sửa
    public void updateUser(long idUser, String userName, String passWord, String fullName, String createdDate, String modifiedDate, String createBy, String modifiedBy) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", userName);
        values.put("password", passWord);
        values.put("fullname", fullName);
        values.put("createddate", createdDate);
        values.put("modifieddate", modifiedDate);
        values.put("createdby", createBy);
        values.put("modifiedby", modifiedBy);

        db.update("user", values, "id = ?", new String[]{String.valueOf(idUser)});
    }

    public void updateRole(long idRole, String name, String code, String createdDate, String modifiedDate, String createBy, String modifiedBy) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("code", code);
        values.put("createddate", createdDate);
        values.put("modifieddate", modifiedDate);
        values.put("createdby", createBy);
        values.put("modifiedby", modifiedBy);

        db.update("role", values, "id = ?", new String[]{String.valueOf(idRole)});
    }

    public void updateProduct(long idProducts, String namePr, long pricePr, String pathImagePr, long discountPr, long quantity, String status, String createDate, String modifiedDate, String createBy, String modifiedBy) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nameproduct", namePr);
        values.put("price", pricePr);
        values.put("image", pathImagePr);
        values.put("discount", discountPr);
        values.put("quantity", quantity);
        values.put("status", status);
        values.put("createddate", createDate);
        values.put("modifieddate", modifiedDate);
        values.put("createdby", createBy);
        values.put("modifiedby", modifiedBy);

        db.update("products", values, "id = ?", new String[]{String.valueOf(idProducts)});
    }

    public void updatetBat(long id, String name, long quantityUser, String createddate, String modifieddate, String createdby, String modifiedby) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("namebat", name);
        values.put("quantityuser", quantityUser);
        values.put("createddate", createddate);
        values.put("modifieddate", modifieddate);
        values.put("createdby", createdby);
        values.put("modifiedby", modifiedby);

        db.update("bat", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void updateStatusBat(long id, String nameBat, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("namebat", nameBat);
        values.put("status", status);

        db.update("status_bat", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void updateDetailOrder(long productid, long batid, String namePr, long pricePr, long quantity, String createDate, String modifiedDate, String createBy, String modifiedBy) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("batid", batid);
        values.put("nameproduct", namePr);
        values.put("price", pricePr);
        values.put("quantity", quantity);
        values.put("createddate", createDate);
        values.put("modifieddate", modifiedDate);
        values.put("createdby", createBy);
        values.put("modifiedby", modifiedBy);

        db.update("detail_order", values, "id = ?", new String[]{String.valueOf(productid)});
    }


    //get
    public ArrayList<String> getAllBat() {
        SQLiteDatabase db = getWritableDatabase();

        ArrayList<String> nameTable = new ArrayList<>();

        long idBat;
        String name, status, createdDate, modifiedDate, createdBy, modifiedBy;

        @SuppressLint("Recycle")
        Cursor cursor = db.query(false, "bat", null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex("namebat"));

            nameTable.add(name);
        }
        return nameTable;
    }

    public ArrayList<StatusBat> getAllStatusBat() {
        SQLiteDatabase db = getWritableDatabase();

        ArrayList<StatusBat> listBat = new ArrayList<>();

        long id;
        String namebat, status;

        @SuppressLint("Recycle")
        Cursor cursor = db.query(false, "status_bat", null, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            id = cursor.getLong(cursor.getColumnIndex("id"));
            namebat = cursor.getString(cursor.getColumnIndex("namebat"));
            status = cursor.getString(cursor.getColumnIndex("status"));

            listBat.add(new StatusBat(id, namebat, status));
        }

        return listBat;
    }

    public ArrayList<String> getBat(String table) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<String> tableList = new ArrayList<>();

        String bat = "";

        @SuppressLint("Recycle")
        Cursor cursor = db.query(false, "bat", null, "namebat = ?", new String[]{table}, null, null, null, null);

        while (cursor.moveToNext()) {
            bat = cursor.getString(cursor.getColumnIndex("namebat"));

            tableList.add(bat);
        }

        return tableList;
    }

    public ArrayList<Product> getPr(String namePr) {

        SQLiteDatabase db = getWritableDatabase();

        ArrayList<Product> product = new ArrayList<>();
        long idPr, pricePr, discountPr, quantityPr;
        String imagePr, statusPr, createddatePr, modifieddatePr, createdbyPr, modifiedbyPr;

        @SuppressLint("Recycle")
        Cursor cursor = db.query(false, "products", null, "nameproduct = ?", new String[]{namePr}, null, null, null, null);
        while (cursor.moveToNext()) {
            idPr = cursor.getLong(cursor.getColumnIndex("id"));
            imagePr = cursor.getString(cursor.getColumnIndex("image"));
            pricePr = cursor.getLong(cursor.getColumnIndex("price"));
            discountPr = cursor.getLong(cursor.getColumnIndex("discount"));
            quantityPr = cursor.getLong(cursor.getColumnIndex("quantity"));
            statusPr = cursor.getString(cursor.getColumnIndex("status"));
            createddatePr = cursor.getString(cursor.getColumnIndex("createddate"));
            createdbyPr = cursor.getString(cursor.getColumnIndex("createdby"));
            modifieddatePr = cursor.getString(cursor.getColumnIndex("modifieddate"));
            modifiedbyPr = cursor.getString(cursor.getColumnIndex("modifiedby"));

            product.add(new Product(idPr, namePr, imagePr, pricePr, discountPr, quantityPr, statusPr, createddatePr, modifieddatePr, createdbyPr, modifiedbyPr));
        }

        return product;
    }

    public ArrayList<Product> getAllProduct() {

        SQLiteDatabase db = getWritableDatabase();

        ArrayList<Product> pr = new ArrayList<>();
        String fullName, image, status;
        long idPr, price, discount, quantityPr;

        @SuppressLint("Recycle")
        Cursor cursor = db.query(false, "products", null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            idPr = cursor.getLong(cursor.getColumnIndex("id"));
            fullName = cursor.getString(cursor.getColumnIndex("nameproduct"));
            image = cursor.getString(cursor.getColumnIndex("image"));
            price = cursor.getLong(cursor.getColumnIndex("price"));
            discount = cursor.getLong(cursor.getColumnIndex("discount"));
            status = cursor.getString(cursor.getColumnIndex("status"));
            quantityPr = cursor.getLong(cursor.getColumnIndex("quantity"));

            pr.add(new Product(idPr, fullName, image, price, discount, quantityPr, status, null, null, null, null));
        }
        return pr;
    }

    public ArrayList<User> getAllUserName() {

        SQLiteDatabase db = getWritableDatabase();

        String sql = "SELECT userid, roleid, fullname, username, password, code FROM user_role INNER JOIN user, role ON user_role.userid = user.id AND user_role.roleid = role.id";


        ArrayList<User> listUsers = new ArrayList<>();
        long idUser, idRole;
        String fullName, userName, passWord, code;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            fullName = cursor.getString(cursor.getColumnIndex("fullname"));
            userName = cursor.getString(cursor.getColumnIndex("username"));
            passWord = cursor.getString(cursor.getColumnIndex("password"));
            code = cursor.getString(cursor.getColumnIndex("code"));
            idUser = cursor.getLong(cursor.getColumnIndex("userid"));
            idRole = cursor.getLong(cursor.getColumnIndex("roleid"));

            listUsers.add(new User(idUser, idRole, fullName, userName, passWord, code, null, null, null, null));
        }
        return listUsers;
    }

    public ArrayList<Order> getOrder(String table) {

        SQLiteDatabase db = getWritableDatabase();


        ArrayList<Order> listOrder = new ArrayList<>();

        String sql = "SELECT detailorderid, orders.batid, quantityuser, namebat, nameproduct, price, quantity FROM orders INNER JOIN bat, detail_order ON orders.batid = bat.id AND orders.detailorderid = detail_order.id WHERE namebat = ?";

        long idBat, idPr, quantityUser, quantityPr, pricePr;
        String namePr, nameBat;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sql, new String[]{table});

        while (cursor.moveToNext()) {
            quantityPr = cursor.getLong(cursor.getColumnIndex("quantity"));
            quantityUser = cursor.getLong(cursor.getColumnIndex("quantityuser"));
            pricePr = cursor.getLong(cursor.getColumnIndex("price"));
            namePr = cursor.getString(cursor.getColumnIndex("nameproduct"));
            nameBat = cursor.getString(cursor.getColumnIndex("namebat"));
            idBat = cursor.getLong(cursor.getColumnIndex("batid"));
            idPr = cursor.getLong(cursor.getColumnIndex("detailorderid"));

            listOrder.add(new Order(idBat, idPr, nameBat, String.valueOf(quantityUser), namePr, String.valueOf(pricePr), String.valueOf(quantityPr)));
        }

        return listOrder;
    }

    public ArrayList<User> getUser(String name) {

        SQLiteDatabase db = getWritableDatabase();

        String sql = "SELECT userid, roleid, fullname, username, password, code FROM user_role INNER JOIN user, role ON user_role.userid = user.id AND user_role.roleid = role.id WHERE fullname = ?";


        ArrayList<User> listUsers = new ArrayList<>();
        long idUser, idRole;
        String fullName, userName, passWord, code;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sql, new String[]{name});

        while (cursor.moveToNext()) {
            fullName = cursor.getString(cursor.getColumnIndex("fullname"));
            userName = cursor.getString(cursor.getColumnIndex("username"));
            passWord = cursor.getString(cursor.getColumnIndex("password"));
            code = cursor.getString(cursor.getColumnIndex("code"));
            idUser = cursor.getLong(cursor.getColumnIndex("userid"));
            idRole = cursor.getLong(cursor.getColumnIndex("roleid"));

            listUsers.add(new User(idUser, idRole, fullName, userName, passWord, code, null, null, null, null));
        }

        return listUsers;
    }

    public ArrayList<User> getUserName(String user) {

        SQLiteDatabase db = getWritableDatabase();

        String sql = "SELECT userid, roleid, fullname, username, password, code FROM user_role INNER JOIN user, role ON user_role.userid = user.id AND user_role.roleid = role.id WHERE username = ?";


        ArrayList<User> listUsers = new ArrayList<>();
        long idUser, idRole;
        String fullName, userName, passWord, code;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sql, new String[]{user});

        while (cursor.moveToNext()) {
            fullName = cursor.getString(cursor.getColumnIndex("fullname"));
            userName = cursor.getString(cursor.getColumnIndex("username"));
            passWord = cursor.getString(cursor.getColumnIndex("password"));
            code = cursor.getString(cursor.getColumnIndex("code"));
            idUser = cursor.getLong(cursor.getColumnIndex("userid"));
            idRole = cursor.getLong(cursor.getColumnIndex("roleid"));

            listUsers.add(new User(idUser, idRole, fullName, userName, passWord, code, null, null, null, null));
        }

        return listUsers;
    }

    public User login(String userName, String passWord) {

        SQLiteDatabase db = getWritableDatabase();

        String sql = "SELECT fullname, username, password, code FROM user_role INNER JOIN user, role ON user_role.userid = user.id AND user_role.roleid = role.id WHERE username = ? AND password = ?";

        User user = null;

        String fullName, code;


        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sql, new String[]{userName, passWord});

        if (cursor.moveToNext()) {
            fullName = cursor.getString(cursor.getColumnIndex("fullname"));
            code = cursor.getString(cursor.getColumnIndex("code"));

            user = new User(0, 0, fullName, userName, passWord, code, null, null, null, null);
        }

        return user;
    }
}
