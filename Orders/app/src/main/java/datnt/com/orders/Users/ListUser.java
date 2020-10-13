package datnt.com.orders.Users;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import datnt.com.orders.Products.AddProduct;
import datnt.com.orders.Products.ListProducts;
import datnt.com.orders.Products.Product;
import datnt.com.orders.R;
import datnt.com.orders.SQLHelper;

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

public class ListUser extends AppCompatActivity implements UserAdapter.OnItemListener {

    ImageView ivBack, ivMore, ivTrash, ivSearch;
    EditText etSearch;

    ArrayList<User> listUser, listDelUser, listUserSearch;
    SQLHelper sqlHelper;

    UserAdapter userAdapter;
    DeleteUserAdapter deleteUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        ivBack = findViewById(R.id.ivBack);
        ivMore = findViewById(R.id.ivMore);
        ivTrash = findViewById(R.id.ivTrash);
        ivSearch = findViewById(R.id.ivSearch);
        etSearch = findViewById(R.id.etSearch);

        final SQLHelper sqlHelper = new SQLHelper(getBaseContext());
        listUser = new ArrayList<>();
        listUser.addAll(sqlHelper.getAllUserName());

        Intent reverdIntent = getIntent();
        final User user = (User) reverdIntent.getSerializableExtra("user");

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = etSearch.getText().toString();
                listUserSearch = new ArrayList<>();
                listUserSearch.addAll(sqlHelper.getUser(search));
                initView(listUserSearch);
            }
        });

        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ListUser.this, ivMore);
                popupMenu.getMenuInflater().inflate(R.menu.more_list, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.itAddUser:
                                Intent intent = new Intent(getBaseContext(), AddUser.class);
                                startActivityForResult(intent, 4);
                                break;
                            case R.id.itdelUser:
                                initViewDel();
                                ivMore.setImageResource(0);
                                ivTrash.setImageResource(R.drawable.trash);
                                ivTrash.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ListUser.this);
                                        builder.setTitle("Bạn có chắc chắn muốn xóa?")
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        for (User u : listDelUser) {
                                                            sqlHelper.deleteUser(u.getIdUser());
                                                            sqlHelper.deleteRole(u.getIdRole());
                                                            sqlHelper.deleteUser_Role(u.getIdRole());
                                                            Toast.makeText(ListUser.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
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

        initView(listUser);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initViewDel() {
        sqlHelper = new SQLHelper(this);

        RecyclerView recyclerView = findViewById(R.id.rvUser);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listUser = new ArrayList<>();
        listDelUser = new ArrayList<>();
        listUser.addAll(sqlHelper.getAllUserName());
        deleteUserAdapter = new DeleteUserAdapter(listUser, this);
        recyclerView.setAdapter(deleteUserAdapter);

        deleteUserAdapter.setListener(new DeleteUserAdapter.OnChecked() {
            @Override
            public void onCheck(boolean isCheck, int position) {
                User u = listUser.get(position);
                if (isCheck) {
                    listDelUser.add(u);
                }
                if (!isCheck) {
                    for (User user : listDelUser) {
                        if (user.equals(u)) {
                            listDelUser.remove(user);
                        }
                    }
                }
            }
        });
    }

    public void initView(ArrayList<User> listUser) {
        sqlHelper = new SQLHelper(this);

        RecyclerView recyclerView = findViewById(R.id.rvUser);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        userAdapter = new UserAdapter(listUser, this, this);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void onItemClick(int position, ArrayList<User> listUser) {
        Intent intent = new Intent(this, AddUser.class);
        intent.putExtra("item", listUser.get(position));
        startActivity(intent);
    }
}
