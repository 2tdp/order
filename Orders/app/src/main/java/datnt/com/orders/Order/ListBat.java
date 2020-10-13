package datnt.com.orders.Order;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import datnt.com.orders.R;
import datnt.com.orders.SQLHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class ListBat extends AppCompatActivity implements ListBatAdapter.OnItemListener {

    ImageView ivBack;

    SQLHelper sqlHelper;
    ArrayList<String> listBat, listTable;
    ArrayList<StatusBat> listStatus;
    ListBatAdapter listOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        ivBack = findViewById(R.id.ivBack);

        sqlHelper = new SQLHelper(getBaseContext());
        listBat = new ArrayList<>();
        listStatus = new ArrayList<>();
        listTable = new ArrayList<>();
        listBat.addAll(sqlHelper.getAllBat());
        listStatus.addAll(sqlHelper.getAllStatusBat());

        int table = -1;
        for (String a : listBat) {
            if (listStatus.size() == 0) {
                if (listTable.size() == 0) {
                    listTable.add(a);
                } else {
                    table = listTable.indexOf(a);
                    if (table == -1) {
                        listTable.add(a);
                    }
                }
                initView(listTable);
            } else {
                for (StatusBat b : listStatus) {
                    table = listTable.indexOf(a);
                    if (!b.getNamebat().equals(a) && table == -1) {
                        listTable.add(a);
                    }
                }
                initView(listTable);
            }
        }


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initView(ArrayList<String> listBat) {
        sqlHelper = new SQLHelper(this);

        RecyclerView recyclerView = findViewById(R.id.rvOrder);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listOrderAdapter = new ListBatAdapter(listBat, this, this);
        recyclerView.setAdapter(listOrderAdapter);
    }


    @Override
    public void onItemClick(int position, ArrayList<String> listBat) {
        Intent intent = new Intent(this, DetailOrder.class);
        intent.putExtra("item", listBat.get(position));
        startActivityForResult(intent, 55);
    }
}
