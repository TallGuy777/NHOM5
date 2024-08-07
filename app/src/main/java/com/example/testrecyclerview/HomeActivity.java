package com.example.testrecyclerview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        recyclerViewSanPham = findViewById(R.id.RecyclerViewSanPham);
        FloatingActionButton floatingAdd = findViewById(R.id.floatingAdd);
        edtSearch = findViewById(R.id.edtSearch); // Sửa lại id cho EditText

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
        sanPhamAdapter = new SanPhamAdapter(this, filteredList,sanPhamDao);
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
        Button btnAdd = view.findViewById(R.id.addSP);
        Button btnCancle = view.findViewById(R.id.Cancle);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ các trường nhập liệu
                String tenSP = tenSanPham.getText().toString().trim();
                String loaiSP = loaiSanPham.getText().toString().trim();
                String giaSP = giaSanPham.getText().toString().trim();

                // Kiểm tra trường nhập liệu có bị trống không
                if (tenSP.isEmpty() || loaiSP.isEmpty() || giaSP.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra giá trị loại sản phẩm
                if (!loaiSP.equals("Trái cây") && !loaiSP.equals("Rau xanh")) {
                    Toast.makeText(HomeActivity.this, "Loại sản phẩm phải là 'Trái cây' hoặc 'Rau xanh'!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thực hiện hành động thêm sản phẩm
                boolean check = sanPhamDao.themSanPham(tenSP, loaiSP, giaSP);
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
