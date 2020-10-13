package datnt.com.orders.Products;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import datnt.com.orders.Order.Pro;
import datnt.com.orders.QuanLy;
import datnt.com.orders.R;
import datnt.com.orders.SQLHelper;
import datnt.com.orders.Users.UserAdapter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class ListProducts extends AppCompatActivity implements ListPrAdapter.OnItemListener {

    ImageView ivBack, ivMore, ivTrash, ivSearch;
    EditText etSearch;

    SQLHelper sqlHelper;
    ArrayList<Product> listPr, listDelPr, listPrSearch;
    ListPrAdapter listPrAdapter;
    DeletePrAdapter deletePrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);

        ivBack = findViewById(R.id.ivBack);
        ivMore = findViewById(R.id.ivMore);
        ivTrash = findViewById(R.id.ivTrash);
        ivSearch = findViewById(R.id.ivSearch);
        etSearch = findViewById(R.id.etSearch);

        sqlHelper = new SQLHelper(getBaseContext());
        listPr = new ArrayList<>();
        listPr.addAll(sqlHelper.getAllProduct());

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = etSearch.getText().toString();
                listPrSearch = new ArrayList<>();
                listPrSearch.addAll(sqlHelper.getPr(search));
                initView(listPrSearch);
            }
        });

        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ListProducts.this, ivMore);
                popupMenu.getMenuInflater().inflate(R.menu.more_list_pr, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.itAddUser:
                                final Intent intent = new Intent(getBaseContext(), AddProduct.class);
                                startActivityForResult(intent, 6);
                                break;
                            case R.id.itdelUser:
                                initViewDel();
                                ivMore.setImageResource(0);
                                ivTrash.setImageResource(R.drawable.trash);
                                ivTrash.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ListProducts.this);
                                        builder.setTitle("Bạn có chắc chắn muốn xóa?")
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        for (Product p : listDelPr) {
                                                            sqlHelper.deleteProduct(p.getIdPr());
                                                            Toast.makeText(ListProducts.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                                            finish();
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
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        initView(listPr);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initViewDel() {
        sqlHelper = new SQLHelper(this);

        RecyclerView recyclerView = findViewById(R.id.rvPr);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listPr = new ArrayList<>();
        listDelPr = new ArrayList<>();
        listPr.addAll(sqlHelper.getAllProduct());
        deletePrAdapter = new DeletePrAdapter(listPr, this);
        recyclerView.setAdapter(deletePrAdapter);

        deletePrAdapter.setListener(new DeletePrAdapter.OnChecked() {
            @Override
            public void onCheck(boolean isCheck, int position) {
                Product p = listPr.get(position);
                if (isCheck) {
                    listDelPr.add(p);
                }
                if (!isCheck) {
                    for (Product product : listDelPr) {
                        if (product.equals(p)) {
                            listDelPr.remove(product);
                        }
                    }
                }
            }
        });
    }

    public void initView(ArrayList<Product> listPr) {
        sqlHelper = new SQLHelper(this);

        RecyclerView recyclerView = findViewById(R.id.rvPr);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listPrAdapter = new ListPrAdapter(listPr, this, this);
        recyclerView.setAdapter(listPrAdapter);
    }

    @Override
    public void onItemClick(int position, ArrayList<Product> listPr) {
        Intent intent = new Intent(this, AddProduct.class);
        intent.putExtra("item", listPr.get(position));
        startActivity(intent);
    }
}
