package datnt.com.orders;

import androidx.appcompat.app.AppCompatActivity;
import datnt.com.orders.Order.Order;
import datnt.com.orders.Users.User;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ThuNgan extends AppCompatActivity {

    TextView tvName, tvLogout;
    Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_ngan);

        tvName = findViewById(R.id.tvName);
        tvLogout = findViewById(R.id.tvLogout);
        btnPay = findViewById(R.id.btnPay);

        final SQLHelper sqlHelper = new SQLHelper(getBaseContext());

        Intent revercedItent = getIntent();
        User user = (User) revercedItent.getSerializableExtra("user");

        if (user != null) {
            tvName.setText(user.getFullName());
        }

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(ThuNgan.this);
                @SuppressLint("InflateParams")
                View v = inflater.inflate(R.layout.view_table, null);
                final EditText ettable = v.findViewById(R.id.ettableNum);

                AlertDialog.Builder builder = new AlertDialog.Builder(ThuNgan.this);
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

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThuNgan.this);
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
