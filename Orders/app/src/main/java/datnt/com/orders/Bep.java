package datnt.com.orders;

import androidx.appcompat.app.AppCompatActivity;
import datnt.com.orders.Order.DetailOrder;
import datnt.com.orders.Order.ListBat;
import datnt.com.orders.Products.ListProducts;
import datnt.com.orders.Users.User;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class Bep extends AppCompatActivity {

    TextView tvName, tvLogout;
    Button btnEditPr, btnlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bep);

        tvName = findViewById(R.id.tvName);
        tvLogout = findViewById(R.id.tvLogout);
        btnEditPr = findViewById(R.id.btnEditPr);
        btnlist = findViewById(R.id.btnOrder);

        Intent revercedIntent = getIntent();
        User user = (User) revercedIntent.getSerializableExtra("user");

        if (user != null) {
            tvName.setText(user.getFullName());
        }

        AlarmBroadcastReverce alarm = new AlarmBroadcastReverce();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(alarm, intentFilter);
        Intent intent = new Intent(getBaseContext(), AlarmBroadcastReverce.class);

        Calendar calendar = Calendar.getInstance();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Bep.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000, pendingIntent);
            createNotificationChannel();
        }

        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ListBat.class);
                startActivity(intent);
            }
        });

        btnEditPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ListProducts.class);
                startActivity(intent);
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Bep.this);
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "remind";
            String description = "remind Order";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("55", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

        }
    }
}
