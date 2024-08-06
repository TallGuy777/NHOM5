package com.example.testrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dao.NguoiDungDAO;

public class LoginActivity extends AppCompatActivity {
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call this first
        setContentView(R.layout.activity_login); // Set the layout

        // Now you can find the views
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView GoToRegister = findViewById(R.id.goRegister);

        nguoiDungDAO = new NguoiDungDAO(this);

        // Khởi tạo EdgeToEdge nếu cần
        EdgeToEdge.enable(this);

        // Áp dụng các thuộc tính padding cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                boolean check = nguoiDungDAO.KiemTraDangNhap(email, pass);
                if (check) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Đặt OnClickListener cho TextView
        GoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}