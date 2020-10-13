package datnt.com.orders.Products;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import datnt.com.orders.Order.Pro;
import datnt.com.orders.QuanLy;
import datnt.com.orders.R;
import datnt.com.orders.SQLHelper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddProduct extends AppCompatActivity {

    ImageView ivBack, ivPr, ivCam, ivPic, ivDel;
    EditText etNamePr, etPricePr, etDiscountPr, etQuantity;
    Button btnOk;
    RadioButton rbCon, rbHet;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
    File file, destFile;
    Uri takePhotoUri;
    String[] pathImage = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ivBack = findViewById(R.id.ivBack);
        ivPr = findViewById(R.id.imImage);
        ivCam = findViewById(R.id.imCamera);
        ivPic = findViewById(R.id.imPic);
        etNamePr = findViewById(R.id.etNamePr);
        etPricePr = findViewById(R.id.etPricePr);
        etDiscountPr = findViewById(R.id.etdiscountPr);
        etQuantity = findViewById(R.id.etQuantity);
        btnOk = findViewById(R.id.btnAdd);
        rbCon = findViewById(R.id.rbStatus1);
        rbHet = findViewById(R.id.rbStatus2);
        ivDel = findViewById(R.id.ivDel);


        final SQLHelper sqlHelper = new SQLHelper(getBaseContext());

        Intent revercedIntent = getIntent();
        final Product product = (Product) revercedIntent.getSerializableExtra("item");

        if (product != null) {
            btnOk.setText("Xác nhận");
            etNamePr.setText(product.getNamePr());
            etPricePr.setText(String.valueOf(product.getPricePr()));
            etDiscountPr.setText(String.valueOf(product.getDiscountPr()));
            etQuantity.setText(String.valueOf(product.getQuantityPr()));
            if (product.getStatusPr().equals("Còn hàng")) {
                rbCon.setChecked(true);
            }
            if (product.getStatusPr().equals("Hết hàng")) {
                rbHet.setChecked(true);
            }
            if (product.getImagePr() != null) {
                Glide.with(this)
                        .load(product.getImagePr())
                        .into(ivPr);
                pathImage[0] = product.getImagePr();
            }
        }

        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddProduct.this);
                builder.setTitle("Bạn có chắc muốn xóa!?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sqlHelper.deleteProduct(product.getIdPr());
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

        ivCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file = new File(getFilesDir(), "images");
                if (!file.exists()) {
                    file.mkdir();
                }
                destFile = new File(file, "img_" + dateFormatter.format(new Date()).toString() + ".png");
                takePhotoUri = FileProvider.getUriForFile(AddProduct.this, "datnt.com.fileprovider", destFile);
                pathImage[0] = takePhotoUri.toString();
                if (ContextCompat.checkSelfPermission(AddProduct.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddProduct.this, Manifest.permission.CAMERA)) {
                        Toast.makeText(AddProduct.this, "Camera can't open!", Toast.LENGTH_SHORT).show();
                    } else {
                        ActivityCompat.requestPermissions(AddProduct.this, new String[]{Manifest.permission.CAMERA}, 13);
                    }
                } else {
                    Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePhoto.resolveActivity(getPackageManager()) != null) {
                        takePhoto.putExtra(MediaStore.EXTRA_OUTPUT, takePhotoUri);
                        startActivityForResult(takePhoto, 8);
                    }
                }
            }
        });

        ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AddProduct.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddProduct.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(AddProduct.this, "Can't access", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ActivityCompat.requestPermissions(AddProduct.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);
                        }
                    }
                } else {
                    Intent intentCamera = new Intent(Intent.ACTION_PICK);
                    intentCamera.setType("image/*");
                    startActivityForResult(intentCamera, 9);
                }
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namePr = etNamePr.getText().toString();
                String pricePr = etPricePr.getText().toString();
                String discountPr = etDiscountPr.getText().toString();
                String quantity = etQuantity.getText().toString();
                String conHang = rbCon.getText().toString();
                String hetHang = rbHet.getText().toString();


                if (namePr.equals("") || pricePr.equals("") || discountPr.equals("") || (!rbCon.isChecked() && !rbHet.isChecked())) {
                    Toast.makeText(AddProduct.this, "Hãy điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                if (product != null) {
                    Intent intent;
                    if (!rbCon.isChecked() && !rbHet.isChecked()) {
                        Toast.makeText(AddProduct.this, "Hãy cập nhật trạng thái!", Toast.LENGTH_SHORT).show();
                    }
                    if (rbCon.isChecked()) {
                        sqlHelper.updateProduct(product.getIdPr(), namePr, Long.parseLong(pricePr), pathImage[0], Long.parseLong(discountPr), Long.parseLong(quantity), conHang, null, null, null, null);

                        Toast.makeText(AddProduct.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getBaseContext(), QuanLy.class);
                        startActivity(intent);
                    }
                    if (rbHet.isChecked()) {
                        sqlHelper.updateProduct(product.getIdPr(), namePr, Long.parseLong(pricePr), pathImage[0], Long.parseLong(discountPr), Long.parseLong(quantity), hetHang, null, null, null, null);

                        Toast.makeText(AddProduct.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getBaseContext(), QuanLy.class);
                        startActivity(intent);
                    }
                } else {
                    ArrayList<Product> p = sqlHelper.getPr(namePr);
                    if (p.size() != 0) {
                        Toast.makeText(AddProduct.this, "Đã có tên món!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (rbCon.isChecked()) {
                            sqlHelper.insertProduct(namePr, Long.parseLong(pricePr), pathImage[0], Long.parseLong(discountPr), Long.parseLong(quantity), conHang, null, null, null, null);

                            Toast.makeText(AddProduct.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), QuanLy.class);
                            startActivity(intent);
                        }
                        if (rbHet.isChecked()) {
                            sqlHelper.insertProduct(namePr, Long.parseLong(pricePr), pathImage[0], Long.parseLong(discountPr), Long.parseLong(quantity), hetHang, null, null, null, null);

                            Toast.makeText(AddProduct.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), QuanLy.class);
                            startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //set image to ImageView
            switch (requestCode) {
                case 8:
                    Log.d("TakeCamera: ", "Selected image uri path :" + takePhotoUri);
                    ivPr.setImageURI(takePhotoUri);
                    break;
                case 9:
                    Uri uriPhoto = data.getData();
                    if (uriPhoto != null) {
                        Log.d("CameraSelected: ", "Selected image uri path :" + uriPhoto.toString());
                        pathImage = new String[]{uriPhoto.toString()};
                    }
                    ivPr.setImageURI(uriPhoto);
            }
        }

    }
}
