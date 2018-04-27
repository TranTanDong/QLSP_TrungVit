package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddKhachHang extends AppCompatActivity {

    EditText et_tenkhachhang, et_sdt, et_diachi;
    Button btn_huy, btn_them;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_khach_hang);
        addControls();
        addEvents();
    }

    private void addControls() {
        et_tenkhachhang = (EditText)findViewById(R.id.et_tenKH);
        et_sdt = (EditText)findViewById(R.id.et_sdt);
        et_diachi = (EditText)findViewById(R.id.et_diachi);
        btn_huy = (Button)findViewById(R.id.btn_huy);
        btn_them = (Button)findViewById(R.id.btn_them);

    }

    private void addEvents() {
        //Xử Lý btn Thêm
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThemKH();
            }
        });

        //Xử lý btn Hủy
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void xuLyThemKH() {
        if (TextUtils.isEmpty(et_tenkhachhang.getText())){
            Toast.makeText(this, "Vui lòng nhập Tên Khách Hàng!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent mIntent = getIntent();
            mIntent.putExtra("TenKH", et_tenkhachhang.getText().toString());
            mIntent.putExtra("SDTKH", et_sdt.getText().toString());
            mIntent.putExtra("DiaChiKH", et_diachi.getText().toString());
            setResult(KhachHang.CODE_RESULT_KHACHHANG, mIntent);
            finish();
        }
    }


}
