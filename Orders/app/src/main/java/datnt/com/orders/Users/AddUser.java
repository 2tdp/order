package datnt.com.orders.Users;

import androidx.appcompat.app.AppCompatActivity;
import datnt.com.orders.QuanLy;
import datnt.com.orders.R;
import datnt.com.orders.SQLHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class AddUser extends AppCompatActivity {

    EditText etUser, etPass, etFull;
    Button btnOk;
    RadioButton rbQL, rbPV, rbTN, rbB;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        ivBack = findViewById(R.id.ivBack);
        etUser = findViewById(R.id.etUserName);
        etPass = findViewById(R.id.etPassWord);
        etFull = findViewById(R.id.etName);
        btnOk = findViewById(R.id.btnAdd);
        rbQL = findViewById(R.id.rbManager);
        rbB = findViewById(R.id.rbB);
        rbPV = findViewById(R.id.rbPV);
        rbTN = findViewById(R.id.rbTN);

        final SQLHelper sqlHelper = new SQLHelper(getBaseContext());

        Intent revercedIntent = getIntent();
        final User user = (User) revercedIntent.getSerializableExtra("item");

        if (user != null) {
            etUser.setText(user.getUserName());
            etPass.setText(user.getPassWord());
            etFull.setText(user.getFullName());
            btnOk.setText("Sửa");
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname = etFull.getText().toString();
                String username = etUser.getText().toString();
                String password = etPass.getText().toString();
                String phucvu = rbPV.getText().toString();
                String bep = rbB.getText().toString();
                String thungan = rbTN.getText().toString();
                String quanly = rbQL.getText().toString();

                if (user != null) {
                    if (username.equals("") || password.equals("") || fullname.equals("")) {
                        Toast.makeText(AddUser.this, "Hãy điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else {
                        sqlHelper.updateUser(user.getIdUser(), username, password, fullname, null, null, null, null);

                        if (!rbPV.isChecked() && !rbB.isChecked() && !rbTN.isChecked() && !rbQL.isChecked()) {
                            Toast.makeText(AddUser.this, "Hãy chọn chức năng nhân viên!", Toast.LENGTH_SHORT).show();
                        }
                        if (rbQL.isChecked()) {
                            sqlHelper.updateRole(user.getIdRole(), fullname, quanly, null, null, null, null);
                        }
                        if (rbTN.isChecked()) {
                            sqlHelper.updateRole(user.getIdRole(), fullname, thungan, null, null, null, null);
                        }
                        if (rbB.isChecked()) {
                            sqlHelper.updateRole(user.getIdRole(), fullname, bep, null, null, null, null);
                        }
                        if (rbPV.isChecked()) {
                            sqlHelper.updateRole(user.getIdRole(), fullname, phucvu, null, null, null, null);
                        }
                    }

                    Toast.makeText(getBaseContext(), "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), QuanLy.class);
                    startActivity(intent);
                } else if (fullname.equals("") || username.equals("") || password.equals("") || (!rbQL.isChecked() && !rbB.isChecked() && !rbTN.isChecked() && !rbPV.isChecked())) {
                    Toast.makeText(AddUser.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<User> u = sqlHelper.getUserName(username);
                    if (u.size() != 0) {
                        for (User user1 : u) {
                            if (user1.getUserName().equals(username)) {
                                Toast.makeText(AddUser.this, "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        if (rbQL.isChecked()) {
                            long idUser = sqlHelper.insertUser(username, password, fullname, null, null, null, null);
                            long idRole = sqlHelper.insertRole(fullname, quanly, null, null, null, null);
                            sqlHelper.insertRole_User(idRole, idUser);

                            Toast.makeText(getBaseContext(), "Thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), QuanLy.class);
                            startActivity(intent);
                            finish();
                        }
                        if (rbTN.isChecked()) {
                            long idUser = sqlHelper.insertUser(username, password, fullname, null, null, null, null);
                            long idRole = sqlHelper.insertRole(fullname, thungan, null, null, null, null);
                            sqlHelper.insertRole_User(idRole, idUser);

                            Toast.makeText(getBaseContext(), "Thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), QuanLy.class);
                            startActivity(intent);
                            finish();
                        }
                        if (rbB.isChecked()) {
                            long idUser = sqlHelper.insertUser(username, password, fullname, null, null, null, null);
                            long idRole = sqlHelper.insertRole(fullname, bep, null, null, null, null);
                            sqlHelper.insertRole_User(idRole, idUser);

                            Toast.makeText(getBaseContext(), "Thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), QuanLy.class);
                            startActivity(intent);
                            finish();
                        }
                        if (rbPV.isChecked()) {
                            long idUser = sqlHelper.insertUser(username, password, fullname, null, null, null, null);
                            long idRole = sqlHelper.insertRole(fullname, phucvu, null, null, null, null);
                            sqlHelper.insertRole_User(idRole, idUser);

                            Toast.makeText(getBaseContext(), "Thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), QuanLy.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
