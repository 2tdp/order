package datnt.com.orders;

import androidx.appcompat.app.AppCompatActivity;
import datnt.com.orders.Order.ListBatAdapter;
import datnt.com.orders.Order.Order;
import datnt.com.orders.Order.OrdersAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ListOrders extends AppCompatActivity {

    ImageView ivBack;
    ListView lvOrder;
    Button btnBill;

    SQLHelper sqlHelper;
    ArrayList<String> listBat, listTable;
    ArrayList<Order> order;
    OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders);

        ivBack = findViewById(R.id.ivBack);
        lvOrder = findViewById(R.id.lvOrders);
        btnBill = findViewById(R.id.btnBill);

        sqlHelper = new SQLHelper(getBaseContext());
        listBat = new ArrayList<>();
        listTable = new ArrayList<>();
        listBat.addAll(sqlHelper.getAllBat());
        String table = "";
        int count = 0;
        for (String a : listBat) {
            if (!table.equals(a)) {
                table = a;
                listTable.add(table);
            }
            count = count + listTable.size();
        }

        ordersAdapter = new OrdersAdapter(listTable, getBaseContext());
        lvOrder.setAdapter(ordersAdapter);

        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), Orders.class);
                intent.putExtra("item", listTable.get(i));
                startActivity(intent);
            }
        });

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String table : listTable){
                    order = new ArrayList<>();
                    order.addAll(sqlHelper.getOrder(table));

                    String fileName = "ListOrder.txt";
                    StringBuilder orderContent = new StringBuilder();
                    for (Order order : order) {
                        orderContent.append("\nName: ").append(order.getNamePr()).append("\tQuantity: ").append(order.getQuantityPr()).append("\tPrice:  ").append(order.getPricePr()).append("\tTable: ").append(order.getTable()).append("\tPeople: ").append(order.getPeople());
                    }

                    FileOutputStream fos = null;
                    File file = new File(getFilesDir(), fileName);
                    try {
                        fos = new FileOutputStream(file, true);
                        fos.write(orderContent.toString().getBytes());

                        Log.d("FilePath", "onClick: " + getFilesDir() + "/" + fileName);
                        Toast.makeText(ListOrders.this, "File saved at " + getFilesDir() + "/" + fileName, Toast.LENGTH_SHORT).show();
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
