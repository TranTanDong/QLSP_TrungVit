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
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.qlsp_trungvit.Adapter.SanPhamAdapter;
import com.example.woo.qlsp_trungvit.Interface.ISanPham;
import com.example.woo.qlsp_trungvit.Model.ListSanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BanRa extends AppCompatActivity implements ISanPham {

    public static final int CODE_REQUEST_ADDSPB = 1;
    public static final int CODE_RESULT_ADDSPB = 2;
    public static final int CODE_REQUEST_DETAILBR = 3;
    public static final int CODE_RESULT_DETAILBR = 4;
    public static final int CODE_RESULT_DETAILBRE = 5;

    private RecyclerView rcv_banRa;
    ArrayList<ListSanPham> sPBanRas = new ArrayList<>();
    SanPhamAdapter sPBanRaAdapter;

    Database database;

    private TextView tv_tongSoLuongB, tv_giaTrungBinhB, tv_tongTienB, tv_demItem;
    private DecimalFormat dcf = new DecimalFormat("#,###,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_ra);
        addControls();
        addEvents();

        showAllListBanRa();
    }

    private void showAllListBanRa() {
        Cursor data = database.GetData("SELECT * FROM BanRa ORDER BY BR_MA DESC");
        sPBanRas.clear();
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

            sPBanRas.add(new ListSanPham(Ma, SL, DG, L, TG, tenKH));
        }
        data.close();
        sPBanRaAdapter.notifyDataSetChanged();

        tv_tongTienB.setText(dcf.format(sumTien)+"đ");
        tv_tongSoLuongB.setText(dcf.format(sumSL)+"");
        if (sPBanRas.size() != 0){
            avgDG = sumTien/sumSL;
            tv_giaTrungBinhB.setText(dcf.format(avgDG)+"");
        } else tv_giaTrungBinhB.setText("0");

        tv_demItem.setText("Số giao dịch: "+sPBanRas.size());

    }

    private void addControls() {
        database = new Database(BanRa.this);
        String sql = "CREATE TABLE IF NOT EXISTS BanRa( " +
                "BR_MA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "BR_SOLG INTEGER NOT NULL, " +
                "BR_DONGIA INTEGER NOT NULL, " +
                "BR_LOAI VARCHAR(10) NOT NULL, " +
                "BR_THOIGIAN DATE NOT NULL, " +
                "BR_MAKH INTEGER, " +
                "CONSTRAINT FK_BAN_KHACHHANG FOREIGN KEY (BR_MAKH) REFERENCES KhachHang (KH_MA) ON UPDATE CASCADE)";
        database.QueryData(sql);

        //Thêm dữ liệu vào listSanPham để test
//        sPBanRas.clear();
//        sPBanRas.add(new ListSanPham(1, 1000, 1500, "Cồ", "22/12/2018", "NVA"));
//        sPBanRas.add(new ListSanPham(2, 1100, 1600, "So", "23/12/2018", "NVB"));
//        sPBanRas.add(new ListSanPham(3, 1200, 1700, "Cồ", "21/12/2018", "NVD"));
//        sPBanRas.add(new ListSanPham(4, 1300, 1800, "Lạc", "24/12/2018", "NVE"));
//        sPBanRas.add(new ListSanPham(5, 1400, 1900, "Cồ", "25/12/2018", "NVT"));
//        sPBanRas.add(new ListSanPham(6, 1500, 1200, "Dạc", "26/12/2018", "NVS"));
//        sPBanRas.add(new ListSanPham(7, 1600, 1300, "Ngang", "27/12/2018", "NVX"));
//        sPBanRas.add(new ListSanPham(8, 1700, 1400, "Cồ", "28/12/2018", "NVZ"));
//        sPBanRas.add(new ListSanPham(9, 1800, 1100, "Giữa", "29/12/2018", "NVV"));
//        sPBanRas.add(new ListSanPham(10, 1900, 1950, "Cồ", "20/12/2018", "NVN"));

        tv_tongSoLuongB = findViewById(R.id.tv_tongSoLuongB);
        tv_giaTrungBinhB = findViewById(R.id.tv_giaTrungBinhB);
        tv_tongTienB = findViewById(R.id.tv_tongTienB);
        tv_demItem = findViewById(R.id.tv_demItem);


        //Xử lý RecyclerView rcv_muaVao
        rcv_banRa = findViewById(R.id.rcv_banRa);
        rcv_banRa.setLayoutManager(new LinearLayoutManager(this));
        sPBanRaAdapter = new SanPhamAdapter(BanRa.this, sPBanRas, this);
        rcv_banRa.setAdapter(sPBanRaAdapter);
    }

    private void addEvents() {

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
                Intent intent = new Intent(BanRa.this, AddGiaoDich.class);
                intent.putExtra("CodeMua", CODE_REQUEST_ADDSPB);
                startActivityForResult(intent, CODE_REQUEST_ADDSPB);
                break;
            case R.id.day:
                showListBanRaToDay();
                break;
            case R.id.month:
                showListBanRaToMonth();
                break;
            case R.id.all:
                showAllListBanRa();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showListBanRaToMonth() {
        Cursor data = database.GetData("SELECT * FROM BanRa ORDER BY BR_MA DESC");
        sPBanRas.clear();
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
                sPBanRas.add(new ListSanPham(Ma, SL, DG, L, TG, tenKH));
            }


        }
        data.close();
        sPBanRaAdapter.notifyDataSetChanged();

        tv_tongTienB.setText(dcf.format(sumTien)+"đ");
        tv_tongSoLuongB.setText(dcf.format(sumSL)+"");
        if (sPBanRas.size() != 0){
            avgDG = sumTien/sumSL;
            tv_giaTrungBinhB.setText(dcf.format(avgDG)+"");
        } else tv_giaTrungBinhB.setText("0");

        tv_demItem.setText("Số giao dịch: "+sPBanRas.size());
    }

    private void showListBanRaToDay() {
        Cursor data = database.GetData("SELECT * FROM BanRa WHERE BR_THOIGIAN=DATE('now') ORDER BY BR_MA DESC");
        sPBanRas.clear();
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

            sPBanRas.add(new ListSanPham(Ma, SL, DG, L, TG, tenKH));
        }
        data.close();
        sPBanRaAdapter.notifyDataSetChanged();

        tv_tongTienB.setText(dcf.format(sumTien)+"đ");
        tv_tongSoLuongB.setText(dcf.format(sumSL)+"");
        if (sPBanRas.size() != 0){
            avgDG = sumTien/sumSL;
            tv_giaTrungBinhB.setText(dcf.format(avgDG)+"");
        } else tv_giaTrungBinhB.setText("0");

        tv_demItem.setText("Số giao dịch: "+sPBanRas.size());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_REQUEST_ADDSPB && resultCode == CODE_RESULT_ADDSPB){
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

            String sql = "INSERT INTO BanRa VALUES(null, "+SL+", "+DG+", '"+L+"', '"+TG+"', "+MaKH+")";
//            Log.i("INSERT1", sql);
            database.QueryData(sql);
            showAllListBanRa();
            Toast.makeText(BanRa.this, "Đã thêm thành công!", Toast.LENGTH_SHORT).show();
        }

        if (requestCode==CODE_REQUEST_DETAILBR && resultCode==CODE_RESULT_DETAILBR){
            int MaGD = data.getIntExtra("MaDelete", -1);
            //Log.i("DL", String.valueOf(MaGD));
            String sql = "DELETE FROM BanRa WHERE BR_MA="+MaGD+"";
            database.QueryData(sql);
            showAllListBanRa();
            Toast.makeText(BanRa.this,"Đã xóa thành công!", Toast.LENGTH_LONG).show();
        }

        if (requestCode == CODE_REQUEST_DETAILBR && resultCode == CODE_RESULT_DETAILBRE){
            int SL = Integer.parseInt(data.getStringExtra("MBSL"));
            int DG = Integer.parseInt(data.getStringExtra("MBDG"));
            String L = data.getStringExtra("MBL");
            String KH = data.getStringExtra("MBKH");
            String TG = data.getStringExtra("MBTG");
            int M = data.getIntExtra("MBM", -1);

            Cursor dataMaKH = database.GetData("SELECT * FROM KhachHang WHERE KH_TEN='"+KH+"'");
            dataMaKH.moveToFirst();
            int MaKH = dataMaKH.getInt(0);

            String sql = "UPDATE BanRa SET BR_SOLG="+SL+", BR_DONGIA="+DG+", BR_LOAI='"+L+"', BR_THOIGIAN='"+TG+"', BR_MAKH="+MaKH+" WHERE BR_MA="+M;
            database.QueryData(sql);
            showAllListBanRa();
            Toast.makeText(BanRa.this, "Đã sửa thành công!", Toast.LENGTH_SHORT).show();
            Log.i("InfBR", SL+L+DG+KH+TG+M+"\n"+sql);
        }

    }

    @Override
    public void ClickItemSanPham(int pos) {
        Intent sIntent = new Intent(BanRa.this, Details.class);
        sIntent.putExtra("SLD", sPBanRas.get(pos).getSoLuong());
        sIntent.putExtra("DGD", sPBanRas.get(pos).getDonGia());
        sIntent.putExtra("LD", sPBanRas.get(pos).getLoai());
        sIntent.putExtra("TGD", sPBanRas.get(pos).getThoiGian());
        sIntent.putExtra("MD", sPBanRas.get(pos).getMaGiaoDich());
        sIntent.putExtra("KHD", sPBanRas.get(pos).getTenKhachHang());
        sIntent.putExtra("CodeDetail", CODE_REQUEST_DETAILBR);
        startActivityForResult(sIntent, CODE_REQUEST_DETAILBR);
    }
}
