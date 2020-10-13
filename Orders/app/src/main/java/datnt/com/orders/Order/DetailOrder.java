package datnt.com.orders.Order;

import androidx.appcompat.app.AppCompatActivity;
import datnt.com.orders.AlarmBroadcastReverce;
import datnt.com.orders.Bep;
import datnt.com.orders.PayAdapter;
import datnt.com.orders.R;
import datnt.com.orders.SQLHelper;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class DetailOrder extends AppCompatActivity {

    TextView tvTitle;
    ImageView ivBack;
    ListView lvOrder;
    Button btnDone;

    ArrayList<Order> order;
    SQLHelper sqlHelper;
    PayAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        tvTitle = findViewById(R.id.tvName);
        ivBack = findViewById(R.id.imBack);
        lvOrder = findViewById(R.id.lvOrder);
        btnDone = findViewById(R.id.btnDone);


        Intent revercedIntent = getIntent();
        final String table = revercedIntent.getStringExtra("item");
        tvTitle.setText("Đơn " + table);

        sqlHelper = new SQLHelper(getBaseContext());
        order = new ArrayList<>();
        order.addAll(sqlHelper.getOrder(table));

        adapter = new PayAdapter(order, getBaseContext());
        lvOrder.setAdapter(adapter);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Order o : order) {
                    sqlHelper.insertStatusBat(o.getTable(), "Done");

                    Toast.makeText(DetailOrder.this, "Done", Toast.LENGTH_SHORT).show();
                    finish();
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
