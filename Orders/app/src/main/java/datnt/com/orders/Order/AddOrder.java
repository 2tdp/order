package datnt.com.orders.Order;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import datnt.com.orders.Products.Product;
import datnt.com.orders.R;
import datnt.com.orders.SQLHelper;
import datnt.com.orders.Users.ListUser;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddOrder extends AppCompatActivity {

    EditText etTable, etPeople;
    Button btnOk, btnDone;
    ImageView ivAdd, ivBack;

    SQLHelper sqlHelper;
    ArrayList<Pro> listPr;
    ArrayList<Product> listProduct;
    ArrayList<Order> listOrder, editOrder;
    OrderAdapter OrderAdapter;
    EditOrderAdapter editOrderAdapter;
    String table, people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);

        etTable = findViewById(R.id.etTable);
        etPeople = findViewById(R.id.etNumPeople);
        btnOk = findViewById(R.id.btnOk);
        ivAdd = findViewById(R.id.ivMore);
        ivBack = findViewById(R.id.imBack);
        btnDone = findViewById(R.id.btnDone);

        btnDone.setVisibility(View.GONE);

        sqlHelper = new SQLHelper(getBaseContext());
        listOrder = new ArrayList<>();

        Intent revercedIntent = getIntent();
        table = revercedIntent.getStringExtra("bat");

        if (table != null) {
            ivAdd.setImageResource(R.drawable.add);
            listOrder.addAll(sqlHelper.getOrder(table));
            etTable.setText(table);
            etPeople.setText(listOrder.get(0).getPeople());
            people = listOrder.get(0).getPeople();
            initViewEdit();
        } else {
            initViewAdd();
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String table1 = etTable.getText().toString();
                final String people = etPeople.getText().toString();

                if (table1.equals("") || people.equals("")) {
                    Toast.makeText(AddOrder.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    if (listPr != null && listOrder.size() == 0) {
                        ArrayList<String> bat = sqlHelper.getBat(table1);
                        if (bat.size() != 0) {
                            Toast.makeText(AddOrder.this, "Bàn đã được đặt", Toast.LENGTH_SHORT).show();
                        } else {
                            for (Pro pro : listPr) {
                                long idBat = sqlHelper.insertBat(table1, Long.parseLong(people), null, null, null, null);
                                long idPr = sqlHelper.insertDetailOrder(pro.getIdPr(), idBat, pro.getName(), Long.parseLong(pro.getPrice()), Long.parseLong(pro.getQuantity()), null, null, null, null);
                                sqlHelper.insertOder(idBat, idPr, null, null, null, null);
                                sqlHelper.insertStatusBat(table1, "New");
                            }
                            Toast.makeText(AddOrder.this, "Thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String table1 = etTable.getText().toString();
                final String people = etPeople.getText().toString();
                if (editOrder.size() != 0) {
                    for (Order o : listOrder) {
                        for (Order order : editOrder) {
                            if (o.getNamePr().equals(order.getNamePr())) {
                                //update
                                String quantity = (String.valueOf(Long.parseLong(o.getQuantityPr()) + Long.parseLong(order.getQuantityPr())));
                                String price = (String.valueOf(Long.parseLong(o.getPricePr()) + (Long.parseLong(order.getQuantityPr()) * Long.parseLong(order.getPricePr()))));

                                o.setPricePr(price);
                                o.setQuantityPr(quantity);
                                sqlHelper.updateDetailOrder(o.getIdPro(), o.getIdBat(), o.getNamePr(), Long.parseLong(price), Long.parseLong(quantity), null, null, null, null);
                                sqlHelper.updatetBat(o.getIdBat(), table1, Long.parseLong(people), null, null, null, null);
                            } else {
                                //insert
                                listOrder.add(order);
                                long idBat = sqlHelper.insertBat(table1, Long.parseLong(people), null, null, null, null);
                                long idPr = sqlHelper.insertDetailOrder(order.getIdPro(), idBat, order.getNamePr(), Long.parseLong(order.getPricePr()), Long.parseLong(order.getQuantityPr()), null, null, null, null);
                                sqlHelper.insertOder(idBat, idPr, null, null, null, null);
                                sqlHelper.insertStatusBat(table1, "New");
                            }
                        }
                    }
                    Toast.makeText(AddOrder.this, "Chỉnh sửa đơn thành công!", Toast.LENGTH_SHORT).show();
                    initViewEdit();
                    btnDone.setVisibility(View.GONE);
                    btnOk.setVisibility(View.VISIBLE);
                    finish();
                }
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initViewAdd();
                btnOk.setVisibility(View.GONE);
                btnDone.setVisibility(View.VISIBLE);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initViewEdit() {
        sqlHelper = new SQLHelper(this);

        RecyclerView recyclerView = findViewById(R.id.rvOrder);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        editOrderAdapter = new EditOrderAdapter(listOrder, this);

        editOrderAdapter.setListener(new EditOrderAdapter.OnListenerChecked() {
            @Override
            public void onChecked(boolean isSelected, Order order) {
                if (!isSelected) {
                    sqlHelper.deleteDetailOrder(order.getIdPro());
                    sqlHelper.deleteBat(order.getIdBat());
                    sqlHelper.deleteOrders(order.getIdPro());
                }
            }
        });
        recyclerView.setAdapter(editOrderAdapter);
    }

    public void initViewAdd() {
        sqlHelper = new SQLHelper(this);

        RecyclerView recyclerView = findViewById(R.id.rvOrder);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listProduct = new ArrayList<>();
        listPr = new ArrayList<>();
        editOrder = new ArrayList<>();
        listProduct.addAll(sqlHelper.getAllProduct());

        OrderAdapter = new OrderAdapter(listProduct, this);

        OrderAdapter.setListener(new OrderAdapter.OnListenerChecked() {
            @Override
            public void onChecked(boolean isSelected, long idPr, String namePr, String pricePr, String quantityPr) {
                Pro pro = new Pro(idPr, namePr, pricePr, quantityPr);
                Order order = new Order(0, idPr, "", "", namePr, pricePr, quantityPr);
                if (isSelected) {
                    listPr.add(pro);

                    if (listOrder.size() != 0) {
                        editOrder.add(order);
                    }
                }

                if (!isSelected) {
                    for (Pro p : listPr) {
                        if (p.equals(pro)) {
                            listPr.remove(p);
                        }
                    }
                    for (Order o : editOrder) {
                        if (o.equals(order)) {
                            editOrder.remove(o);
                        }
                    }
                }
            }
        });

        recyclerView.setAdapter(OrderAdapter);
    }
}
