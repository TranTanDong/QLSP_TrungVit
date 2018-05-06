package com.example.woo.qlsp_trungvit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.qlsp_trungvit.Model.ListKhachHang;
import com.example.woo.qlsp_trungvit.Model.ListSanPham;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddGiaoDich extends AppCompatActivity {

    private TextView tv_tinhTien, tv_thoiGian;
    private EditText et_soLuong, et_donGia;
    private Button btn_luuGiaDich, btn_huyGiaoDich, btn_tinhTien;
    private Spinner spn_loai, spn_khachHang;
    private ImageView img_loaiGiaoDich;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat dcf = new DecimalFormat("#,###,###,###");

    private ArrayList<String> arrayList = new ArrayList<String>();
    private String[] arrLoai = {"Cồ", "Lạc", "So", "Lộn", "Giữa", "Ngang", "Dập", "Dạc", "Thúi", "Xác", "Lỡ"};

    private int lastesSelected = -1; //Vị trí click Spinner
    private int codeType;

    private Intent mIntent;

    Database database;


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
        img_loaiGiaoDich = findViewById(R.id.img_loaiGiaoDich);

        //Set et_thoiGian
        tv_thoiGian.setText(sdf.format(calendar.getTime()));

        //Spinner Loai
        spn_loai = findViewById(R.id.spn_loai);
        ArrayAdapter<String> adapterLoai = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrLoai
        );
        adapterLoai.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_loai.setAdapter(adapterLoai);

        //Spinner khachHang
        addItemSpinnerKH();

        spn_khachHang = findViewById(R.id.spn_khachHang);
        ArrayAdapter<String> adapterKhachHang = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrayList
        );
        adapterKhachHang.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_khachHang.setAdapter(adapterKhachHang);



        mIntent = getIntent();
        codeType = mIntent.getIntExtra("CodeMua", -1);
        if (codeType == MuaVao.CODE_REQUEST_ADDSP){
            img_loaiGiaoDich.setImageResource(R.drawable.ic_muavao);
        }else img_loaiGiaoDich.setImageResource(R.drawable.ic_banra);


    }

    private void addItemSpinnerKH() {
        arrayList.clear();
        database = new Database(AddGiaoDich.this);
        Cursor data = database.GetData("SELECT * FROM KhachHang");
        while (data.moveToNext()){
            String TenKH = data.getString(1);
            arrayList.add(TenKH);
        }
        data.close();

        Collections.sort(arrayList);
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

        //Xử lý click Spinner spn_khachHang
        spn_khachHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(AddGiaoDich.this, "Bạn vừa chọn "+arrayList.get(i), Toast.LENGTH_LONG).show();
                lastesSelected = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Xử lý click Spinner spn_loai
        spn_loai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(AddGiaoDich.this, "Bạn vừa chọn "+arrLoai[i].toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Xử lý hủy thêm Giao Dịch
        btn_huyGiaoDich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Xử lý click Lưu
        btn_luuGiaDich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLuuGiaoDich();
                //Toast.makeText(AddGiaoDich.this, "Click làm tao đau rồi đó!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void xuLyLuuGiaoDich() {
        mIntent.putExtra("SL", et_soLuong.getText().toString());
        mIntent.putExtra("DG", et_donGia.getText().toString());
        mIntent.putExtra("L", spn_loai.getSelectedItem().toString());
        mIntent.putExtra("TG", tv_thoiGian.getText().toString());
        mIntent.putExtra("KH", spn_khachHang.getSelectedItem().toString());
        if (codeType==MuaVao.CODE_REQUEST_ADDSP){
            setResult(MuaVao.CODE_RESULT_ADDSP, mIntent);
        }else setResult(BanRa.CODE_RESULT_ADDSPB, mIntent);

        finish();
    }

    private void xuLyTinhTien() {
        if (TextUtils.isEmpty(et_soLuong.getText())){
            Toast.makeText(this, "Hãy nhập Số lượng!", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(et_donGia.getText())){
            Toast.makeText(this, "Hãy nhập đơn giá!", Toast.LENGTH_LONG).show();
        }else {
            long s = 0;
            long donGia, soLuong;
            donGia = Long.parseLong(et_donGia.getText().toString());
            soLuong = Long.parseLong(et_soLuong.getText().toString());
            s = soLuong*donGia;
            tv_tinhTien.setText(dcf.format(s)+"đ  ");

            //Hiện btn_luuGiaoDich
            btn_luuGiaDich.setVisibility(View.VISIBLE);
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
