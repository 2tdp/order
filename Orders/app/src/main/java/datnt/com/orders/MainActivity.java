package datnt.com.orders;

import androidx.appcompat.app.AppCompatActivity;
import datnt.com.orders.Products.Product;
import datnt.com.orders.Users.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etUser, etPass;
    Button btnLogin;

    ArrayList<User> listUserName;
    ArrayList<Product> listPrName;
    ArrayList<User> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);

        listUserName = new ArrayList<>();
        listUser = new ArrayList<>();
        listPrName = new ArrayList<>();

        final SQLHelper sqlHelper = new SQLHelper(getBaseContext());
        listUserName.addAll(sqlHelper.getAllUserName());
        listPrName.addAll(sqlHelper.getAllProduct());

        if (listUserName.size() == 0) {
            sqlHelper.insertUser("QL001", "12345678", "NguyenTienD", null, null, null, null);
            sqlHelper.insertRole("NguyenTienD", "Quản lý", null, null, null, null);
            sqlHelper.insertRole_User(1, 1);

            sqlHelper.insertUser("NVPV", "12345678", "PhamThiP", null, null, null, null);
            sqlHelper.insertRole("PhamThiP", "Nhân viên phục vụ", null, null, null, null);
            sqlHelper.insertRole_User(2, 2);

            sqlHelper.insertUser("NVB", "12345678", "NguyenVanB", null, null, null, null);
            sqlHelper.insertRole("NguyenVanB", "Nhân viên bếp", null, null, null, null);
            sqlHelper.insertRole_User(3, 3);

            sqlHelper.insertUser("NVTN", "12345678", "DongVanN", null, null, null, null);
            sqlHelper.insertRole("DongVanN", "Nhân viên thu ngân", null, null, null, null);
            sqlHelper.insertRole_User(4, 4);
        }
        if (listPrName.size() == 0) {
            sqlHelper.insertProduct("Cơm", 15000, null, 3000, 50, "Còn hàng", null, null, null, null);
            sqlHelper.insertProduct("Cháo", 20000, null, 5000, 0, "Hết hàng", null, null, null, null);
            sqlHelper.insertProduct("Gà rán", 25000, null, 0, 100, "Còn hàng", null, null, null, null);
            sqlHelper.insertProduct("Canh", 30000, null, 3000, 0, "Hết hàng", null, null, null, null);
            sqlHelper.insertProduct("Phở bò", 35000, null, 0, 30, "Còn hàng", null, null, null, null);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUser.getText().toString();
                String password = etPass.getText().toString();

                User user = sqlHelper.login(username, password);
                if (user != null) {
                    if (user.getRole().equals("Quản lý")) {
                        Intent intent = new Intent(getBaseContext(), QuanLy.class);
                        intent.putExtra("user", user);
                        startActivityForResult(intent, 1);
                    }
                    if (user.getRole().equals("Nhân viên phục vụ")) {
                        Intent intent = new Intent(getBaseContext(), PhucVu.class);
                        intent.putExtra("user", user);
                        startActivityForResult(intent, 1);
                    }
                    if (user.getRole().equals("Nhân viên bếp")) {
                        Intent intent = new Intent(getBaseContext(), Bep.class);
                        intent.putExtra("user", user);
                        startActivityForResult(intent, 1);
                    }
                    if (user.getRole().equals("Nhân viên thu ngân")) {
                        Intent intent = new Intent(getBaseContext(), ThuNgan.class);
                        intent.putExtra("user", user);
                        startActivityForResult(intent, 1);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
