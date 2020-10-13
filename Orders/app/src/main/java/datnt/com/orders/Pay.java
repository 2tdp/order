package datnt.com.orders;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import datnt.com.orders.Order.Order;
import datnt.com.orders.Products.AddProduct;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class Pay extends AppCompatActivity {

    ImageView ivBack;
    ListView lvOrder;
    Button btnPay, btnBill;

    PayAdapter payAdapter;
    ArrayList<Order> listOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        ivBack = findViewById(R.id.imBack);
        lvOrder = findViewById(R.id.lvOrders);
        btnPay = findViewById(R.id.btnOk);
        btnBill = findViewById(R.id.btnBill);

        Intent revercedIntent = getIntent();
        String table = revercedIntent.getStringExtra("table");

        final SQLHelper sqlHelper = new SQLHelper(getBaseContext());
        listOrder = new ArrayList<>();
        listOrder.addAll(sqlHelper.getOrder(table));

        payAdapter = new PayAdapter(listOrder, getBaseContext());
        lvOrder.setAdapter(payAdapter);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listOrder != null) {
                    LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                    @SuppressLint("InflateParams")
                    View v = inflater.inflate(R.layout.table_pay, null);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(Pay.this);
                    builder.setTitle("Thanh toán")
                            .setView(v);
                    final EditText etPay = v.findViewById(R.id.etPay);
                    final TextView tvPay = v.findViewById(R.id.tvPay);
                    etPay.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            int sum = 0;
                            for (Order order : listOrder) {
                                sum = sum + Integer.parseInt(order.getPricePr()) * Integer.parseInt(order.getQuantityPr());
                            }
                            int a = Integer.parseInt(String.valueOf(charSequence)) - sum;
                            tvPay.setText(String.valueOf(a));
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    builder.setPositiveButton("Thanh toán", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int a = Integer.parseInt(tvPay.getText().toString());
                            if (a <= 0) {
                                Toast.makeText(Pay.this, "Tiền thành toán không đủ", Toast.LENGTH_SHORT).show();
                            } else {
                                for (Order o : listOrder) {
                                    sqlHelper.deleteDetailOrder(o.getIdPro());
                                    sqlHelper.deleteBat(o.getIdBat());
                                    sqlHelper.deleteOrders(o.getIdPro());
                                }
                                AlertDialog.Builder b = new AlertDialog.Builder(Pay.this);
                                b.setMessage("Chân thành cảm ơn quý khách! Hẹn gặp lại!")
                                        .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        })
                                        .create().show();
                            }
                        }
                    })
                            .create().show();
                } else {
                    Toast.makeText(Pay.this, "Chọn bàn sai!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "Order.txt";
                StringBuilder orderContent = new StringBuilder();
                int a = 0;
                for (Order order : listOrder) {
                    a = a + Integer.parseInt(order.getPricePr());
                }
                for (Order order : listOrder) {
                    orderContent.append("\nName: ").append(order.getNamePr()).append("\tQuantity: ").append(order.getQuantityPr()).append("\tPrice:  ").append(order.getPricePr()).append("\tTable: ").append(order.getTable()).append("\tPeople: ").append(order.getPeople());
                }
                orderContent.append("\n\nTotal: ").append(a);

                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(fileName, MODE_PRIVATE);
                    fos.write(orderContent.toString().getBytes());

                    Log.d("FilePath", "onClick: " + getFilesDir() + "/" + fileName);
                    Toast.makeText(Pay.this, "File saved at " + getFilesDir() + "/" + fileName, Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
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
