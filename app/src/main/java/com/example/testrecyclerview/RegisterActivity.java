package com.example.testrecyclerview;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
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
import models.NguoiDung;

public class RegisterActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        EditText edtEmail=findViewById(R.id.edtEmail);
        EditText edtName=findViewById(R.id.edtTen);
        EditText edtSDT=findViewById(R.id.edtSDT);
        EditText edtPass=findViewById(R.id.edtPass);
        EditText edtRePass=findViewById(R.id.edtRePass);
        EditText edtDiaChi=findViewById(R.id.edtDiaChi);
        Button btnRegister= findViewById(R.id.btnRegister);
        TextView gotoLogin=findViewById(R.id.goLogin);

        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=edtPass.getText().toString();
                String repass=edtRePass.getText().toString();
                if(!pass.equals(repass))
                {
                    Toast.makeText(RegisterActivity.this, "Mat khau khong trung khop", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String email=edtEmail.getText().toString();
                    String name=edtName.getText().toString();
                    String sdt=edtSDT.getText().toString();
                    String diachi=edtDiaChi.getText().toString();
                    if(nguoiDungDAO.KiemTraEmailDaTonTai(email))
                    {
                        Toast.makeText(RegisterActivity.this, "Tai khoan da ton tai", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    NguoiDung nguoiDung= new NguoiDung(name,email,sdt,diachi,pass,1);
                    boolean check=nguoiDungDAO.DangKyTaiKhoan(nguoiDung);
                    if(check)
                    {
                        Toast.makeText(RegisterActivity.this, "Dang ki thanh cong", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Dang ki that bai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}