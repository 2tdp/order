package datnt.com.orders;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import datnt.com.orders.Order.Order;
import datnt.com.orders.Order.StatusBat;

public class AlarmBroadcastReverce extends BroadcastReceiver {

    ArrayList<StatusBat> listBat;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Alarm: ", "onReceive: ");

        SQLHelper sqlHelper = new SQLHelper(context);
        listBat = new ArrayList<>();

        listBat.addAll(sqlHelper.getAllStatusBat());

        for (StatusBat status : listBat) {
            if (status.getStatus().equals("Done")) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "05");
                    builder.setDefaults(Notification.DEFAULT_ALL)
                            .setSmallIcon(R.drawable.restaurant)
                            .setContentTitle("Đơn bàn " + status.getNamebat())
                            .setContentText("Đã xong")
                            .setPriority(Notification.PRIORITY_DEFAULT)
                            .setChannelId("05")
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                    notificationManagerCompat.notify(1, builder.build());
                    sqlHelper.deleteStatusBat(status.getId());
                }
            }

            if (status.getStatus().equals("New")) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "55");
                    builder.setDefaults(Notification.DEFAULT_ALL)
                            .setSmallIcon(R.drawable.restaurant)
                            .setContentTitle("Đơn bàn " + status.getNamebat())
                            .setContentText("Order")
                            .setPriority(Notification.PRIORITY_DEFAULT)
                            .setChannelId("55")
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                    notificationManagerCompat.notify(2, builder.build());
                    sqlHelper.deleteStatusBat(status.getId());
                }
            }
        }
    }
}
