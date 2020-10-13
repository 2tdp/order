package datnt.com.orders;

import androidx.appcompat.app.AppCompatActivity;
import datnt.com.orders.Order.Order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Orders extends AppCompatActivity {

    ImageView ivBack;
    ListView lvOrder;
    Button btnBill;

    PayAdapter payAdapter;
    ArrayList<Order> listOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ivBack = findViewById(R.id.imBack);
        lvOrder = findViewById(R.id.lvOrders);
        btnBill = findViewById(R.id.btnBill);

        Intent revercedIntent = getIntent();
        String table = revercedIntent.getStringExtra("item");

        final SQLHelper sqlHelper = new SQLHelper(getBaseContext());
        listOrder = new ArrayList<>();
        listOrder.addAll(sqlHelper.getOrder(table));

        payAdapter = new PayAdapter(listOrder, getBaseContext());
        lvOrder.setAdapter(payAdapter);

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "ListOrder.txt";
                StringBuilder orderContent = new StringBuilder();
                for (Order order : listOrder) {
                    orderContent.append("\nName: ").append(order.getNamePr()).append("\tQuantity: ").append(order.getQuantityPr()).append("\tPrice:  ").append(order.getPricePr()).append("\tTable: ").append(order.getTable()).append("\tPeople: ").append(order.getPeople());
                }

                FileOutputStream fos = null;
                File file = new File(getFilesDir(), fileName);
                try {
                    fos = new FileOutputStream(file, true);
                    fos.write(orderContent.toString().getBytes());

                    Log.d("FilePath", "onClick: " + getFilesDir() + "/" + fileName);
                    Toast.makeText(Orders.this, "File saved at " + getFilesDir() + "/" + fileName, Toast.LENGTH_SHORT).show();
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
