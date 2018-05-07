package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.qlsp_trungvit.Adapter.SanPhamAdapter;
import com.example.woo.qlsp_trungvit.Interface.ISanPham;
import com.example.woo.qlsp_trungvit.Model.ListKhachHang;
import com.example.woo.qlsp_trungvit.Model.ListSanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MuaVao extends AppCompatActivity implements ISanPham {

    public static final int CODE_REQUEST_ADDSP = 11;
    public static final int CODE_RESULT_ADDSP = 12;
    public static final int CODE_REQUEST_DETAIL = 13;
    public static final int CODE_RESULT_DETAIL = 14;
    public static final int CODE_RESULT_DETAILE = 15;

    private TextView tv_tongSoLuong, tv_giaTrungBinh, tv_tongTien, tv_demItemMV;

    private RecyclerView rcv_muaVao;
    ArrayList<ListSanPham> listSanPhams = new ArrayList<>();
    SanPhamAdapter sanPhamAdapter;

    Database database;

    private DecimalFormat dcf = new DecimalFormat("#,###,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_vao);
        addControls();
        addEvents();

        showListMuaVaoToDay();
    }

    private void showAllListMuaVao() {
        setTitle("Danh sách mua vào");
        Cursor data = database.GetData("SELECT * FROM MuaVao ORDER BY MV_MA DESC");
        listSanPhams.clear();
        long sumSL = 0;
        long sumTien = 0;
        double avgDG;
        while (data.moveToNext()){
            int Ma = data.getInt(0);
            int SL = data.getInt(1);
            int DG = data.getInt(2);
            String L = data.getString(3);
            String TG = data.getString(4);
            int MaKH = data.getInt(5);

            long sl = SL, dg = DG;
            sumTien += sl*dg;
            sumSL += sl;

            Cursor datatenKH = database.GetData("SELECT * FROM KhachHang WHERE KH_MA="+MaKH+"");
            String tenKH;
            if (datatenKH.moveToFirst()){
                tenKH = datatenKH.getString(1);
            } else tenKH = "GUEST";

            listSanPhams.add(new ListSanPham(Ma, SL, DG, L, TG, tenKH));
        }
        data.close();
        sanPhamAdapter.notifyDataSetChanged();

        tv_tongTien.setText(dcf.format(sumTien)+"đ");
        tv_tongSoLuong.setText(dcf.format(sumSL)+"");
        if (listSanPhams.size()!= 0){
            avgDG = sumTien/sumSL;
            tv_giaTrungBinh.setText(dcf.format(avgDG)+"");
        } else tv_giaTrungBinh.setText("0");
        tv_demItemMV.setText("Số giao dịch: "+listSanPhams.size());
    }

    private void addEvents() {

    }

    private void addControls() {
        database = new Database(MuaVao.this);
        String sql = "CREATE TABLE IF NOT EXISTS MuaVao( " +
                "MV_MA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MV_SOLG INTEGER NOT NULL, " +
                "MV_DONGIA INTEGER NOT NULL, " +
                "MV_LOAI VARCHAR(10) NOT NULL, " +
                "MV_THOIGIAN DATE NOT NULL, " +
                "MV_MAKH INTEGER, " +
                "CONSTRAINT FK_MUA_KHACHHANG FOREIGN KEY (MV_MAKH) REFERENCES KhachHang (KH_MA) ON UPDATE CASCADE)";
        database.QueryData(sql);


        tv_tongSoLuong = findViewById(R.id.tv_tongSoLuong);
        tv_giaTrungBinh = findViewById(R.id.tv_giaTrungBinh);
        tv_tongTien = findViewById(R.id.tv_tongTien);
        tv_demItemMV = findViewById(R.id.tv_demItemMV);

        //Xử lý RecyclerView rcv_muaVao
        rcv_muaVao = findViewById(R.id.rcv_muaVao);
        rcv_muaVao.setLayoutManager(new LinearLayoutManager(this));
        sanPhamAdapter = new SanPhamAdapter(MuaVao.this, listSanPhams, this);
        rcv_muaVao.setAdapter(sanPhamAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add:
                Intent intent = new Intent(MuaVao.this, AddGiaoDich.class);
                intent.putExtra("CodeMua", CODE_REQUEST_ADDSP);
                startActivityForResult(intent, CODE_REQUEST_ADDSP);
                break;
            case R.id.day:
                showListMuaVaoToDay();
                break;
            case R.id.month:
                showListMuaVaoToMonth();
                break;
            case R.id.all:
                showAllListMuaVao();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showListMuaVaoToMonth() {
        setTitle("Mua vào tháng này");
        Cursor data = database.GetData("SELECT * FROM MuaVao ORDER BY MV_MA DESC");
        listSanPhams.clear();
        long sumSL = 0;
        long sumTien = 0;
        double avgDG;
        while (data.moveToNext()){
            int Ma = data.getInt(0);
            int SL = data.getInt(1);
            int DG = data.getInt(2);
            String L = data.getString(3);
            String TG = data.getString(4);
            int MaKH = data.getInt(5);

            Cursor datatenKH = database.GetData("SELECT * FROM KhachHang WHERE KH_MA="+MaKH+"");
            String tenKH;
            if (datatenKH.moveToFirst()){
                tenKH = datatenKH.getString(1);
            } else tenKH = "GUEST";

            //Lấy tháng năm hiện tại để so sánh
            Cursor d = database.GetData("SELECT DATE('now')");
            d.moveToFirst();
            String day = d.getString(0);
            day = day.substring(0, 7);

            if (day.equals(TG.substring(0,7))){
                long sl = SL, dg = DG;
                sumTien += sl*dg;
                sumSL += sl;
                listSanPhams.add(new ListSanPham(Ma, SL, DG, L, TG, tenKH));
            }


        }
        data.close();
        sanPhamAdapter.notifyDataSetChanged();

        tv_tongTien.setText(dcf.format(sumTien)+"đ");
        tv_tongSoLuong.setText(dcf.format(sumSL)+"");
        if (listSanPhams.size() != 0){
            avgDG = sumTien/sumSL;
            tv_giaTrungBinh.setText(dcf.format(avgDG)+"");
        } else tv_giaTrungBinh.setText("0");

        tv_demItemMV.setText("Số giao dịch: "+listSanPhams.size());
    }

    private void showListMuaVaoToDay() {
        setTitle("Mua vào hôm nay");
        Cursor data = database.GetData("SELECT * FROM MuaVao WHERE MV_THOIGIAN=DATE('now') ORDER BY MV_MA DESC");
        listSanPhams.clear();
        long sumSL = 0;
        long sumTien = 0;
        double avgDG;
        while (data.moveToNext()){
            int Ma = data.getInt(0);
            int SL = data.getInt(1);
            int DG = data.getInt(2);
            String L = data.getString(3);
            String TG = data.getString(4);
            int MaKH = data.getInt(5);

            long sl = SL, dg = DG;
            sumTien += sl*dg;
            sumSL += sl;

            Cursor datatenKH = database.GetData("SELECT * FROM KhachHang WHERE KH_MA="+MaKH+"");
            String tenKH;
            if (datatenKH.moveToFirst()){
                tenKH = datatenKH.getString(1);
            } else tenKH = "GUEST";

            listSanPhams.add(new ListSanPham(Ma, SL, DG, L, TG, tenKH));
        }
        data.close();
        sanPhamAdapter.notifyDataSetChanged();

        tv_tongTien.setText(dcf.format(sumTien)+"đ");
        tv_tongSoLuong.setText(dcf.format(sumSL)+"");
        if (listSanPhams.size() != 0){
            avgDG = sumTien/sumSL;
            tv_giaTrungBinh.setText(dcf.format(avgDG)+"");
        } else tv_giaTrungBinh.setText("0");

        tv_demItemMV.setText("Số giao dịch: "+listSanPhams.size());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_REQUEST_ADDSP && resultCode == CODE_RESULT_ADDSP){
            int SL = Integer.parseInt(data.getStringExtra("SL"));
            int DG = Integer.parseInt(data.getStringExtra("DG"));
            String L = data.getStringExtra("L");
            String TG = data.getStringExtra("TG");
            String KH = data.getStringExtra("KH");

            Cursor dataMaKH = database.GetData("SELECT * FROM KhachHang WHERE KH_TEN='"+KH+"'");
            int MaKH;
            if (dataMaKH.moveToFirst()){
                MaKH = dataMaKH.getInt(0);
            } else MaKH = -1;

            String sql = "INSERT INTO MuaVao VALUES(null, "+SL+", "+DG+", '"+L+"', '"+TG+"', "+MaKH+")";
            Log.i("INSERT1", sql);
            database.QueryData(sql);
            showListMuaVaoToDay();
            Toast.makeText(MuaVao.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == CODE_REQUEST_DETAIL && resultCode == CODE_RESULT_DETAIL){
            int MaGD = data.getIntExtra("MaDelete", -1);
            Log.i("DL", String.valueOf(MaGD));
            String sql = "DELETE FROM MuaVao WHERE MV_MA="+MaGD+"";
            database.QueryData(sql);
            showListMuaVaoToDay();
            Toast.makeText(MuaVao.this,"Đã xóa thành công!", Toast.LENGTH_LONG).show();
        }

        if (requestCode == CODE_REQUEST_DETAIL && resultCode == CODE_RESULT_DETAILE){
            int SL = Integer.parseInt(data.getStringExtra("MBSL"));
            int DG = Integer.parseInt(data.getStringExtra("MBDG"));
            String L = data.getStringExtra("MBL");
            String KH = data.getStringExtra("MBKH");
            String TG = data.getStringExtra("MBTG");
            int M = data.getIntExtra("MBM", -1);

            Cursor dataMaKH = database.GetData("SELECT * FROM KhachHang WHERE KH_TEN='"+KH+"'");
            dataMaKH.moveToFirst();
            int MaKH = dataMaKH.getInt(0);

            String sql = "UPDATE MuaVao SET MV_SOLG="+SL+", MV_DONGIA="+DG+", MV_LOAI='"+L+"', MV_THOIGIAN='"+TG+"', MV_MAKH="+MaKH+" WHERE MV_MA="+M;
            database.QueryData(sql);
            showListMuaVaoToDay();
            Toast.makeText(MuaVao.this, "Đã sửa thành công!", Toast.LENGTH_SHORT).show();
            Log.i("InfMV", SL+L+DG+KH+TG+M+"\n"+sql);

        }

    }

    @Override
    public void ClickItemSanPham(int pos) {
        Intent sIntent = new Intent(MuaVao.this, Details.class);
        sIntent.putExtra("SLD", listSanPhams.get(pos).getSoLuong());
        sIntent.putExtra("DGD", listSanPhams.get(pos).getDonGia());
        sIntent.putExtra("LD", listSanPhams.get(pos).getLoai());
        sIntent.putExtra("TGD", listSanPhams.get(pos).getThoiGian());
        sIntent.putExtra("MD", listSanPhams.get(pos).getMaGiaoDich());
        sIntent.putExtra("KHD", listSanPhams.get(pos).getTenKhachHang());
        sIntent.putExtra("CodeDetail", CODE_REQUEST_DETAIL);
       // sIntent.putExtra("PD", pos);
        startActivityForResult(sIntent, CODE_REQUEST_DETAIL);

        //Toast.makeText(MuaVao.this, "Cái này của "+listSanPhams.get(pos).getTenKhachHang().toString(), Toast.LENGTH_SHORT).show();
    }
}
