package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.woo.qlsp_trungvit.Adapter.SanPhamAdapter;
import com.example.woo.qlsp_trungvit.Interface.ISanPham;
import com.example.woo.qlsp_trungvit.Model.ListSanPham;

import java.util.ArrayList;

public class BanRa extends AppCompatActivity implements ISanPham {

    public static final int CODE_REQUEST_ADDSPB = 7;

    private RecyclerView rcv_banRa;
    ArrayList<ListSanPham> sPBanRas = new ArrayList<>();
    SanPhamAdapter sPBanRaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_ra);
        addControls();
        addEvents();
    }

    private void addControls() {
        //Thêm dữ liệu vào listSanPham để test
        sPBanRas.clear();
        sPBanRas.add(new ListSanPham(1, 1000, 1500, "Cồ", "22/12/2018", "NVA"));
        sPBanRas.add(new ListSanPham(2, 1100, 1600, "So", "23/12/2018", "NVB"));
        sPBanRas.add(new ListSanPham(3, 1200, 1700, "Cồ", "21/12/2018", "NVD"));
        sPBanRas.add(new ListSanPham(4, 1300, 1800, "Lạc", "24/12/2018", "NVE"));
        sPBanRas.add(new ListSanPham(5, 1400, 1900, "Cồ", "25/12/2018", "NVT"));
        sPBanRas.add(new ListSanPham(6, 1500, 1200, "Dạc", "26/12/2018", "NVS"));
        sPBanRas.add(new ListSanPham(7, 1600, 1300, "Ngang", "27/12/2018", "NVX"));
        sPBanRas.add(new ListSanPham(8, 1700, 1400, "Cồ", "28/12/2018", "NVZ"));
        sPBanRas.add(new ListSanPham(9, 1800, 1100, "Giữa", "29/12/2018", "NVV"));
        sPBanRas.add(new ListSanPham(10, 1900, 1950, "Cồ", "20/12/2018", "NVN"));


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
        if(item.getItemId() == R.id.add){
            Intent intent = new Intent(BanRa.this, AddGiaoDich.class);
            intent.putExtra("CodeMua", CODE_REQUEST_ADDSPB);
            startActivityForResult(intent, CODE_REQUEST_ADDSPB);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void ClickItemSanPham(int pos) {

    }
}
