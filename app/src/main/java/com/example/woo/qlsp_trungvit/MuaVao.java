package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.woo.qlsp_trungvit.Adapter.SanPhamAdapter;
import com.example.woo.qlsp_trungvit.Interface.ISanPham;
import com.example.woo.qlsp_trungvit.Model.ListSanPham;

import java.util.ArrayList;

public class MuaVao extends AppCompatActivity implements ISanPham {

    public static final int CODE_REQUEST_ADDSP = 3;
    public static final int CODE_RESULT_ADDSP = 4;

    private RecyclerView rcv_muaVao;
    ArrayList<ListSanPham> listSanPhams = new ArrayList<>();
    SanPhamAdapter sanPhamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_vao);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {

        //Thêm dữ liệu vào listSanPham để test
        listSanPhams.clear();
        listSanPhams.add(new ListSanPham(1, 1000, 1500, "Cồ", "22/12/2018", "NVA"));
        listSanPhams.add(new ListSanPham(2, 1100, 1600, "So", "23/12/2018", "NVB"));
        listSanPhams.add(new ListSanPham(3, 1200, 1700, "Cồ", "21/12/2018", "NVD"));
        listSanPhams.add(new ListSanPham(4, 1300, 1800, "Lạc", "24/12/2018", "NVE"));
        listSanPhams.add(new ListSanPham(5, 1400, 1900, "Cồ", "25/12/2018", "NVT"));
        listSanPhams.add(new ListSanPham(6, 1500, 1200, "Dạc", "26/12/2018", "NVS"));
        listSanPhams.add(new ListSanPham(7, 1600, 1300, "Ngang", "27/12/2018", "NVX"));
        listSanPhams.add(new ListSanPham(8, 1700, 1400, "Cồ", "28/12/2018", "NVZ"));
        listSanPhams.add(new ListSanPham(9, 1800, 1100, "Giữa", "29/12/2018", "NVV"));
        listSanPhams.add(new ListSanPham(10, 1900, 1950, "Cồ", "20/12/2018", "NVN"));

        //Xử lý RecyclerView rcv_muaVao
        rcv_muaVao = findViewById(R.id.rcv_muaVao);
        rcv_muaVao.setLayoutManager(new LinearLayoutManager(this));
        sanPhamAdapter = new SanPhamAdapter(this, listSanPhams, this);
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_REQUEST_ADDSP && resultCode == CODE_RESULT_ADDSP){
            Intent rIntent = getIntent();
            int SL = Integer.parseInt(data.getStringExtra("SL"));
            int DG = Integer.parseInt(data.getStringExtra("DG"));
            String L = data.getStringExtra("L");
            String TG = data.getStringExtra("TG");
            String KH = data.getStringExtra("KH");

            listSanPhams.add(new ListSanPham(listSanPhams.size()+1, SL, DG, L, TG, KH));
            sanPhamAdapter.notifyDataSetChanged();
            Toast.makeText(MuaVao.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void ClickItemSanPham(int pos) {
        Toast.makeText(MuaVao.this, "Cái này của "+listSanPhams.get(pos).getTenKhachHang().toString(), Toast.LENGTH_SHORT).show();
    }
}
