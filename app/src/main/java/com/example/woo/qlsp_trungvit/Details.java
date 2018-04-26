package com.example.woo.qlsp_trungvit;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    private TextView    tv_soLuong_detail,
                        tv_donGia_detail,
                        tv_loai_detail,
                        tv_thoiGian_detail,
                        tv_khachHang_detail,
                        tv_tinhTien_detail,
                        title_detail;
    private ImageView img_detail;

    private int SL, DG, M, C;
    private String L, TG, KH;
    private Intent dIntent;

    public static final int REQUEST_EDIT = 13;
    public static final int RESULT_EDIT = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        addControls();
        addEvents();
    }

    private void addControls() {
        tv_soLuong_detail = findViewById(R.id.tv_soLuong_detail);
        tv_donGia_detail = findViewById(R.id.tv_donGia_detail);
        tv_loai_detail = findViewById(R.id.tv_loai_detail);
        tv_thoiGian_detail = findViewById(R.id.tv_thoiGian_detail);
        tv_khachHang_detail = findViewById(R.id.tv_khachHang_detail);
        tv_tinhTien_detail = findViewById(R.id.tv_tinhTien_detail);
        title_detail = findViewById(R.id.title_detail);
        img_detail = findViewById(R.id.img_detail);

        //Xử lý thông tin từ MuaVao | BanRa gửi qua
        dIntent = getIntent();
        SL = dIntent.getIntExtra("SLD", -1);
        DG = dIntent.getIntExtra("DGD", -1);
        L = dIntent.getStringExtra("LD");
        TG = dIntent.getStringExtra("TGD");
        KH = dIntent.getStringExtra("KHD");
        M = dIntent.getIntExtra("MD", -1);
        //P = dIntent.getIntExtra("PD", -1);
        C = dIntent.getIntExtra("CodeDetail", -1);

        //Set giá trị
        tv_soLuong_detail.setText(String.valueOf(SL));
        tv_donGia_detail.setText(String.valueOf(DG));
        tv_loai_detail.setText(L);
        tv_thoiGian_detail.setText(TG);
        tv_khachHang_detail.setText(KH);
        tv_tinhTien_detail.setText(String.valueOf(SL*DG));

        if (C==BanRa.CODE_REQUEST_DETAILBR){
            title_detail.setText("Trứng Bán Ra");
            img_detail.setImageResource(R.drawable.ic_banra);
        }


       // Log.i("Detail", SL+" "+DG+" "+L+" "+TG+" "+KH+" "+M);
    }

    private void addEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.edit_detail){
            Intent sIntent = new Intent(Details.this, EditGiaoDich.class);
            sIntent.putExtra("SL", SL);
            sIntent.putExtra("DG", DG);
            sIntent.putExtra("TG", TG);
            sIntent.putExtra("L", L);
            sIntent.putExtra("KH", KH);
            sIntent.putExtra("M", M);
            sIntent.putExtra("C", C);
            sIntent.putExtra("CD", REQUEST_EDIT);
            startActivityForResult(sIntent, REQUEST_EDIT);
        }

        if (item.getItemId()==R.id.del_detail){
            AlertDialog.Builder builder = new AlertDialog.Builder(Details.this);
            builder.setTitle("Xóa Sản Phẩm");
            builder.setMessage("Bạn muốn xóa giao dịch này không?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dIntent.putExtra("MaDelete", M);
                    Log.i("DL", String.valueOf(M));
                    if (C==BanRa.CODE_REQUEST_DETAILBR){
                        setResult(BanRa.CODE_RESULT_DETAILBR, dIntent);
                    }else setResult(MuaVao.CODE_RESULT_DETAIL, dIntent);

                    dialogInterface.dismiss();
                    Details.this.finish();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
