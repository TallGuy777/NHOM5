package com.example.testrecyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
    ArrayList<SanPham> list;
    SanPhamDao SanPhamDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_home);


            //thiet ke giao dien
            recyclerViewSanPham = findViewById(R.id.RecyclerViewSanPham);
            FloatingActionButton floatingAdd=findViewById(R.id.floatingAdd);


            floatingAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogThem();
                }
            });

            //data
            SanPhamDao = new SanPhamDao(this);


            //adapter
            UpdateData();

    }
    private  void UpdateData()
    {
        list= SanPhamDao.getDSloaiSach();
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerViewSanPham.setLayoutManager(linearLayoutManager);
        SanPhamAdapter sanPhamAdapter =new SanPhamAdapter(this,list);
        recyclerViewSanPham.setAdapter(sanPhamAdapter);
    }
    private  void showDialogThem()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_add,null);
        builder.setView(view);

        AlertDialog alertDialog=builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        EditText tenSanPham=view.findViewById(R.id.edtTenSP);
        EditText loaiSanPham=view.findViewById(R.id.edtLoaitSP);
        EditText giaSanPham=view.findViewById(R.id.edtGiaSP);
        Button btnAdd=view.findViewById(R.id.addSP);
        Button btnCancle=view.findViewById(R.id.Cancle);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ các trường nhập liệu
                String tenSP = tenSanPham.getText().toString().trim();
                String loaiSP = loaiSanPham.getText().toString().trim();
                String giaSP = giaSanPham.getText().toString().trim();

                // Kiểm tra trường nhập liệu có bị trống không
                if (tenSP.isEmpty() || loaiSP.isEmpty()||giaSP.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra giá trị loại sản phẩm
                if (!loaiSP.equals("Trái cây") && !loaiSP.equals("Rau xanh")) {
                    Toast.makeText(HomeActivity.this, "Loại sản phẩm phải là 'Trái cây' hoặc 'Rau xanh'!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thực hiện hành động thêm sản phẩm
                boolean check = SanPhamDao.themSanPham(tenSP, loaiSP,giaSP);
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