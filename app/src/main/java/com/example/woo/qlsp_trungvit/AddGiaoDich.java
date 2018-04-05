package com.example.woo.qlsp_trungvit;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddGiaoDich extends AppCompatActivity {

    TextView tv_tinhTien, tv_thoiGian;
    EditText et_soLuong, et_donGia;
    Button btn_luuGiaDich, btn_huyGiaoDich, btn_tinhTien;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd - MM - yyyy");
    DecimalFormat dcf = new DecimalFormat("#,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_giao_dich);
        addControls();
        addEvents();
    }

    private void addControls() {
        tv_tinhTien = findViewById(R.id.tv_tinhTien);
        et_soLuong = findViewById(R.id.et_soLuong);
        et_donGia = findViewById(R.id.et_donGia);
        tv_thoiGian = findViewById(R.id.tv_thoiGian);
        btn_tinhTien = findViewById(R.id.btn_tinhTien);
        btn_huyGiaoDich = findViewById(R.id.btn_huyGiaoDich);
        btn_luuGiaDich = findViewById(R.id.btn_luuGiaoDich);

        //Set et_thoiGian
        tv_thoiGian.setText(sdf.format(calendar.getTime()));
    }

    private void addEvents() {
        //Xử lý thời gian
        tv_thoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThoiGian();
            }
        });

        //Xử lý tính tiền
        btn_tinhTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyTinhTien();
            }
        });
    }

    private void xuLyTinhTien() {
        if (TextUtils.isEmpty(et_soLuong.getText())){
            Toast.makeText(this, "Hãy nhập Số lượng!!!", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(et_donGia.getText())){
            Toast.makeText(this, "Hãy nhập Đơn giá!!!", Toast.LENGTH_LONG).show();
        }else {
            int s = 0, donGia, soLuong;
            donGia = Integer.parseInt(et_donGia.getText().toString());
            soLuong = Integer.parseInt(et_soLuong.getText().toString());
            s = soLuong*donGia;
            tv_tinhTien.setText(dcf.format(s)+"đ  ");
        }
    }

    //Xử lý thời gian
    private void xuLyThoiGian() {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(calendar.YEAR, year);
                calendar.set(calendar.MONTH, month);
                calendar.set(calendar.DAY_OF_MONTH, dayOfMonth);
                tv_thoiGian.setText(sdf.format(calendar.getTime()));
            }
        };

        DatePickerDialog datePickerDialog=new DatePickerDialog(
                AddGiaoDich.this,
                callback,
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
