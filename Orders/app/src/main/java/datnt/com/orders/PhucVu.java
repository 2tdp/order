package datnt.com.orders;

import androidx.appcompat.app.AppCompatActivity;
import datnt.com.orders.Order.AddOrder;
import datnt.com.orders.Order.DetailOrder;
import datnt.com.orders.Order.ListBat;
import datnt.com.orders.Users.User;

import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class PhucVu extends AppCompatActivity {

    Button btnAdd, btnEdit;
    TextView tvName, tvLogout;

    SQLHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phuc_vu);

        tvName = findViewById(R.id.tvName);
        btnAdd = findViewById(R.id.btnPick);
        btnEdit = findViewById(R.id.btnEdit);
        tvLogout = findViewById(R.id.tvLogout);

        Intent revercedIntent = getIntent();
        User user = (User) revercedIntent.getSerializableExtra("user");

        if (user != null) {
            tvName.setText(user.getFullName());
        }

        sqlHelper = new SQLHelper(getBaseContext());

        AlarmBroadcastReverce alarm = new AlarmBroadcastReverce();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(alarm, intentFilter);
        Intent intent = new Intent(getBaseContext(), AlarmBroadcastReverce.class);

        Calendar calendar = Calendar.getInstance();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(PhucVu.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000, pendingIntent);
            createNotificationChannel();
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddOrder.class);
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(PhucVu.this);
                @SuppressLint("InflateParams")
                View v = inflater.inflate(R.layout.view_table, null);
                final EditText ettable = v.findViewById(R.id.ettableNum);

                AlertDialog.Builder builder = new AlertDialog.Builder(PhucVu.this);
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
                                    Toast.makeText(PhucVu.this, "Tên bàn sai!", Toast.LENGTH_SHORT).show();
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

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhucVu.this);
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
            NotificationChannel channel = new NotificationChannel("05", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

        }
    }
}
