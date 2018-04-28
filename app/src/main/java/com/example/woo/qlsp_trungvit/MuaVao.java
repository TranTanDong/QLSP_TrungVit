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

    private TextView tv_tongSoLuong, tv_giaTrungBinh, tv_tongTien;

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

        showAllListMuaVao();
    }

    private void showAllListMuaVao() {
        Cursor data = database.GetData("SELECT * FROM MuaVao ORDER BY MV_THOIGIAN DESC");
        listSanPhams.clear();
        int sumSL = 0;
        int sumDG = 0;
        int sumTien = 0;
        int avgDG;
        while (data.moveToNext()){
            int Ma = data.getInt(0);
            int SL = data.getInt(1);
            int DG = data.getInt(2);
            String L = data.getString(3);
            String TG = data.getString(4);
            int MaKH = data.getInt(5);

            sumTien += SL*DG;
            sumSL += SL;
            sumDG += DG;

            Cursor datatenKH = database.GetData("SELECT * FROM KhachHang WHERE KH_MA="+MaKH+"");
            datatenKH.moveToFirst();
            String tenKH = datatenKH.getString(1);
            listSanPhams.add(new ListSanPham(Ma, SL, DG, L, TG, tenKH));
        }
        data.close();
        sanPhamAdapter.notifyDataSetChanged();

        tv_tongTien.setText(dcf.format(sumTien)+"đ");
        tv_tongSoLuong.setText(dcf.format(sumSL)+"");
        if (listSanPhams.size()!= 0){
            avgDG = sumDG/listSanPhams.size();
            tv_giaTrungBinh.setText(dcf.format(avgDG)+"");
        } else tv_giaTrungBinh.setText("0");

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
                "MV_THOIGIAN SMALLDATETIME NOT NULL, " +
                "MV_MAKH INTEGER NOT NULL, " +
                "CONSTRAINT FK_MUA_KHACHHANG FOREIGN KEY (MV_MAKH) REFERENCES KhachHang (KH_MA) ON UPDATE CASCADE)";
        database.QueryData(sql);

//        database.QueryData("INSERT INTO MuaVao VALUES(null, 1000, 1200, 'Cồ', '10/03/2018', 1)");
//        database.QueryData("INSERT INTO MuaVao VALUES(null, 3000, 1500, 'So', '11/03/2018', 2)");
//        database.QueryData("INSERT INTO MuaVao VALUES(null, 5000, 1000, 'Lạc', '12/03/2018', 9)");

        //Thêm dữ liệu vào listSanPham để test
//        listSanPhams.clear();
//        listSanPhams.add(new ListSanPham(1, 1000, 1500, "Cồ", "22/12/2018", "NVA"));
//        listSanPhams.add(new ListSanPham(2, 1100, 1600, "So", "23/12/2018", "NVB"));
//        listSanPhams.add(new ListSanPham(3, 1200, 1700, "Cồ", "21/12/2018", "NVD"));
//        listSanPhams.add(new ListSanPham(4, 1300, 1800, "Lạc", "24/12/2018", "NVE"));
//        listSanPhams.add(new ListSanPham(5, 1400, 1900, "Cồ", "25/12/2018", "NVT"));
//        listSanPhams.add(new ListSanPham(6, 1500, 1200, "Dạc", "26/12/2018", "NVS"));
//        listSanPhams.add(new ListSanPham(7, 1600, 1300, "Ngang", "27/12/2018", "NVX"));
//        listSanPhams.add(new ListSanPham(8, 1700, 1400, "Cồ", "28/12/2018", "NVZ"));
//        listSanPhams.add(new ListSanPham(9, 1800, 1100, "Giữa", "29/12/2018", "NVV"));
//        listSanPhams.add(new ListSanPham(10, 1900, 1950, "Cồ", "20/12/2018", "NVN"));

        tv_tongSoLuong = findViewById(R.id.tv_tongSoLuong);
        tv_giaTrungBinh = findViewById(R.id.tv_giaTrungBinh);
        tv_tongTien = findViewById(R.id.tv_tongTien);

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
        if(item.getItemId() == R.id.add){
            Intent intent = new Intent(MuaVao.this, AddGiaoDich.class);
            intent.putExtra("CodeMua", CODE_REQUEST_ADDSP);
            startActivityForResult(intent, CODE_REQUEST_ADDSP);
        }

        switch (item.getItemId()){
            case R.id.day:
                Cursor d = database.GetData("SELECT DATE('now')");
                d.moveToFirst();
                String day = d.getString(0);
                Toast.makeText(MuaVao.this, day, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
            dataMaKH.moveToFirst();
            int MaKH = dataMaKH.getInt(0);

            String sql = "INSERT INTO MuaVao VALUES(null, "+SL+", "+DG+", '"+L+"', '"+TG+"', "+MaKH+")";
            Log.i("INSERT1", sql);
            database.QueryData(sql);
            showAllListMuaVao();
            Toast.makeText(MuaVao.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == CODE_REQUEST_DETAIL && resultCode == CODE_RESULT_DETAIL){
            int MaGD = data.getIntExtra("MaDelete", -1);
            Log.i("DL", String.valueOf(MaGD));
            String sql = "DELETE FROM MuaVao WHERE MV_MA="+MaGD+"";
            database.QueryData(sql);
            showAllListMuaVao();
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
            showAllListMuaVao();
            Toast.makeText(MuaVao.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
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
