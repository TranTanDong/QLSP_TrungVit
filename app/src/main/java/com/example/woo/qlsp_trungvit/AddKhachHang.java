package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

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

    }


}
