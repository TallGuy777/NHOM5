package com.example.testrecyclerview;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import adapter.SanPhamAdapter;
import dao.SanPhamDao;
import models.SanPham;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerViewSanPham;
    EditText edtSearch;
    ArrayList<SanPham> list;
    ArrayList<SanPham> filteredList;
    SanPhamDao sanPhamDao;
    SanPhamAdapter sanPhamAdapter;
    FloatingActionButton floatingAdd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //full màn hình API 30+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                insetsController.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        }

        //full màn hình API < 30
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }

        // Initialize views
        recyclerViewSanPham = findViewById(R.id.RecyclerViewSanPham);
        floatingAdd = findViewById(R.id.floatingAdd);
        edtSearch = findViewById(R.id.edtSearch);

        // Get role and update visibility of floatingAdd
        SharedPreferences sharedPreferences = getSharedPreferences("dataUser", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", -1);
        if (role == 1) {
            floatingAdd.setVisibility(View.GONE);
        }

        floatingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThem();
            }
        });

        // Data
        sanPhamDao = new SanPhamDao(this);

        // Initialize data and adapter
        UpdateData();

        // Search functionality
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void UpdateData() {
        list = sanPhamDao.getDSloaiSach();
        filteredList = new ArrayList<>(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewSanPham.setLayoutManager(linearLayoutManager);

        // Get role and create adapter with appropriate visibility
        SharedPreferences sharedPreferences = getSharedPreferences("dataUser", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", -1);
        boolean isCaseOne = (role == 1);

        sanPhamAdapter = new SanPhamAdapter(this, filteredList, sanPhamDao, isCaseOne);
        recyclerViewSanPham.setAdapter(sanPhamAdapter);
    }

    private void filterData(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(list);
        } else {
            for (SanPham sanPham : list) {
                if (sanPham.getTen().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(sanPham);
                }
            }
        }
        sanPhamAdapter.notifyDataSetChanged();
    }

    private void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        EditText tenSanPham = view.findViewById(R.id.edtTenSP);
        EditText loaiSanPham = view.findViewById(R.id.edtLoaitSP);
        EditText giaSanPham = view.findViewById(R.id.edtGiaSP);
        EditText hinhSanPham = view.findViewById(R.id.edtHinhAnh);
        Button btnAdd = view.findViewById(R.id.addSP);
        Button btnCancle = view.findViewById(R.id.Cancle);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ các trường nhập liệu
                String tenSP = tenSanPham.getText().toString().trim();
                String loaiSP = loaiSanPham.getText().toString().trim();
                String giaSP = giaSanPham.getText().toString().trim();
                String hinhSP = hinhSanPham.getText().toString().trim();

                // Kiểm tra trường nhập liệu có bị trống không
                if (tenSP.isEmpty() || loaiSP.isEmpty() || giaSP.isEmpty()| hinhSP.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra giá trị loại sản phẩm
                if (!loaiSP.equals("Trái cây") && !loaiSP.equals("Rau xanh")) {
                    Toast.makeText(HomeActivity.this, "Loại sản phẩm phải là 'Trái cây' hoặc 'Rau xanh'!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thực hiện hành động thêm sản phẩm
                boolean check = sanPhamDao.themSanPham(tenSP, loaiSP, giaSP,hinhSP);
                if (check) {
                    Toast.makeText(HomeActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    UpdateData(); // Cập nhật dữ liệu nếu cần
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(HomeActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}

