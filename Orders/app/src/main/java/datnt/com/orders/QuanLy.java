package datnt.com.orders;

import androidx.appcompat.app.AppCompatActivity;
import datnt.com.orders.Order.AddOrder;
import datnt.com.orders.Order.Order;
import datnt.com.orders.Products.ListProducts;
import datnt.com.orders.Users.ListUser;
import datnt.com.orders.Users.User;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuanLy extends AppCompatActivity {

    Button btnOrder, btnEdit, btnPay;
    ImageView ivMore;
    TextView tvName, tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);

        btnOrder = findViewById(R.id.btnPick);
        btnEdit = findViewById(R.id.btnEdit);
        btnPay = findViewById(R.id.btnPay);
        ivMore = findViewById(R.id.ivMore);
        tvName = findViewById(R.id.tvName);
        tvLogout = findViewById(R.id.tvLogout);

        final SQLHelper sqlHelper = new SQLHelper(getBaseContext());

        Intent revercedIntent = getIntent();
        final User user = (User) revercedIntent.getSerializableExtra("user");

        if (user != null) {
            tvName.setText(user.getFullName());
        }

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddOrder.class);
                startActivityForResult(intent, 2);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(QuanLy.this);
                @SuppressLint("InflateParams")
                View v = inflater.inflate(R.layout.view_table, null);
                final EditText ettable = v.findViewById(R.id.ettableNum);

                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLy.this);
                builder.setTitle("Chọn bàn cần sửa: ")
                        .setView(v)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ArrayList<String> listBat = sqlHelper.getAllBat();
                                if (listBat.size() != 0) {
                                    for (String bat : listBat) {
                                        if (ettable.getText().toString().equals(bat)) {
                                            Intent intent = new Intent(getBaseContext(), AddOrder.class);
                                            intent.putExtra("bat", bat);
                                            startActivityForResult(intent, 8);
                                        }
                                    }
                                } else {
                                    Toast.makeText(QuanLy.this, "Tên bàn sai!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(QuanLy.this);
                @SuppressLint("InflateParams")
                View v = inflater.inflate(R.layout.view_table, null);
                final EditText ettable = v.findViewById(R.id.ettableNum);

                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLy.this);
                builder.setTitle("Chọn bàn cần thanh toán: ")
                        .setView(v)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getBaseContext(), Pay.class);
                                intent.putExtra("table", ettable.getText().toString());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
            }
        });

        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(QuanLy.this, ivMore);
                popupMenu.getMenuInflater().inflate(R.menu.more, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.itDSPer:
                                Intent intent = new Intent(getBaseContext(), ListUser.class);
                                intent.putExtra("user", user);
                                startActivityForResult(intent, 3);
                                break;
                            case R.id.itDSPro:
                                Intent intent1 = new Intent(getBaseContext(), ListProducts.class);
                                startActivity(intent1);
                                break;
                            case R.id.itDSOr:
                                Intent intent2 = new Intent(getBaseContext(), ListOrders.class);
                                startActivity(intent2);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLy.this);
                builder.setTitle("Bạn muốn đăng xuất?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
            }
        });
    }
}
