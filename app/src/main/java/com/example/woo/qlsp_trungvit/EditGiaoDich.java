package com.example.woo.qlsp_trungvit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class EditGiaoDich extends AppCompatActivity {

    private TextView tv_tinhTien_E, tv_thoiGian_E;
    private EditText et_soLuong_E, et_donGia_E;
    private Button btn_luuGiaDich_E, btn_huyGiaoDich_E, btn_tinhTien_E;
    private Spinner spn_loai_E, spn_khachHang_E;
    private ImageView img_loaiGiaoDich_E;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat dcf = new DecimalFormat("#,###,###,###");

    private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayList<String> arrayL = new ArrayList<String>();
    private String[] arrLoai = {"Cồ", "Lạc", "So", "Lộn", "Giữa", "Ngang", "Dập", "Dạc", "Thúi", "Xác", "Lỡ"};

    int C, M;
    Intent aIntent;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_giao_dich);
        addControls();
        addEvents();
    }

    private void addEvents() {
        //Xử lý thời gian
        tv_thoiGian_E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThoiGian();
            }
        });

        //Xử lý tính tiền
        btn_tinhTien_E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyTinhTien();
            }
        });

        //Xử lý hủy thêm Giao Dịch
        btn_huyGiaoDich_E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Xử lý click Lưu
        btn_luuGiaDich_E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLySuaGiaoDich();
            }
        });
    }

    private void xuLySuaGiaoDich() {
        aIntent.putExtra("ESL", et_soLuong_E.getText().toString());
        aIntent.putExtra("EDG", et_donGia_E.getText().toString());
        aIntent.putExtra("EL", spn_loai_E.getSelectedItem().toString());
        aIntent.putExtra("ETG", tv_thoiGian_E.getText().toString());
        aIntent.putExtra("EKH", spn_khachHang_E.getSelectedItem().toString());
        aIntent.putExtra("EM", M);
        //Log.i("EditInf", et_soLuong_E.getText().toString()+ spn_loai_E.getSelectedItem().toString()+ et_donGia_E.getText().toString()+tv_thoiGian_E.getText().toString()+spn_khachHang_E.getSelectedItem().toString()+M);
        if (C==MuaVao.CODE_REQUEST_DETAIL){
            setResult(Details.RESULT_EDITMV, aIntent);
        }else setResult(Details.RESULT_EDITBR, aIntent);

        finish();
    }

    private void xuLyTinhTien() {
        if (TextUtils.isEmpty(et_soLuong_E.getText())){
            Toast.makeText(this, "Hãy nhập Số lượng!!!", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(et_donGia_E.getText())){
            Toast.makeText(this, "Hãy nhập Đơn giá!!!", Toast.LENGTH_LONG).show();
        }else {
            long s = 0;
            int donGia, soLuong;
            donGia = Integer.parseInt(et_donGia_E.getText().toString());
            soLuong = Integer.parseInt(et_soLuong_E.getText().toString());
            s = soLuong*donGia;
            tv_tinhTien_E.setText(dcf.format(s)+"đ  ");

            //Hiện btn_luuGiaoDich
            btn_luuGiaDich_E.setVisibility(View.VISIBLE);
        }
    }

    private void xuLyThoiGian() {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(calendar.YEAR, year);
                calendar.set(calendar.MONTH, month);
                calendar.set(calendar.DAY_OF_MONTH, dayOfMonth);
                tv_thoiGian_E.setText(sdf.format(calendar.getTime()));
            }
        };

        DatePickerDialog datePickerDialog=new DatePickerDialog(
                EditGiaoDich.this,
                callback,
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void addControls() {
        tv_tinhTien_E = findViewById(R.id.tv_tinhTien_E);
        et_soLuong_E = findViewById(R.id.et_soLuong_E);
        et_donGia_E = findViewById(R.id.et_donGia_E);
        tv_thoiGian_E = findViewById(R.id.tv_thoiGian_E);
        btn_tinhTien_E = findViewById(R.id.btn_tinhTien_E);
        btn_huyGiaoDich_E = findViewById(R.id.btn_huyGiaoDich_E);
        btn_luuGiaDich_E = findViewById(R.id.btn_luuGiaoDich_E);
        img_loaiGiaoDich_E = findViewById(R.id.img_loaiGiaoDich_E);

        //Spinner Loai
        for (String i : arrLoai){
            arrayL.add(i);
        }
        spn_loai_E = findViewById(R.id.spn_loai_E);
        ArrayAdapter<String> adapterLoai = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrayL
        );
        adapterLoai.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_loai_E.setAdapter(adapterLoai);

        //Spinner khachHang
        addItemSpinnerKH();

        spn_khachHang_E = findViewById(R.id.spn_khachHang_E);
        ArrayAdapter<String> adapterKhachHang = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrayList
        );
        adapterKhachHang.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_khachHang_E.setAdapter(adapterKhachHang);


        //Xử lý thông tin từ Details gửi qua
        aIntent = getIntent();
        int SL = aIntent.getIntExtra("SLE", -1);
        int DG = aIntent.getIntExtra("DGE", -1);
        String L = aIntent.getStringExtra("LE");
        String TG = aIntent.getStringExtra("TGE");
        String KH = aIntent.getStringExtra("KHE");
        M = aIntent.getIntExtra("ME", -1);
        C = aIntent.getIntExtra("CE", -1);
        int CD = aIntent.getIntExtra("CDE", -1);

        if (C == BanRa.CODE_REQUEST_DETAILBR){
            img_loaiGiaoDich_E.setImageResource(R.drawable.ic_banra);
        }else img_loaiGiaoDich_E.setImageResource(R.drawable.ic_muavao);

        if (CD == Details.REQUEST_EDIT){
            et_soLuong_E.setText(String.valueOf(SL));
            et_donGia_E.setText(String.valueOf(DG));
            tv_thoiGian_E.setText(TG);
            spn_khachHang_E.setSelection(arrayList.indexOf(KH));
            spn_loai_E.setSelection(arrayL.indexOf(L));
            tv_tinhTien_E.setText(dcf.format(SL*DG)+"đ  ");
        }
    }

    private void addItemSpinnerKH() {
        database = new Database(EditGiaoDich.this);
        Cursor data = database.GetData("SELECT * FROM KhachHang");
        arrayList.clear();
        while (data.moveToNext()){
            String TenKH = data.getString(1);
            arrayList.add(TenKH);
        }
        data.close();

        Collections.sort(arrayList);
    }
}
