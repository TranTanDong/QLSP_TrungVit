package com.example.woo.qlsp_trungvit.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woo.qlsp_trungvit.BanRa;
import com.example.woo.qlsp_trungvit.Interface.ISanPham;
import com.example.woo.qlsp_trungvit.Model.ListSanPham;
import com.example.woo.qlsp_trungvit.MuaVao;
import com.example.woo.qlsp_trungvit.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Woo on 3/22/2018.
 */

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder>{

    private Context context;
    private ArrayList<ListSanPham> sanPhams;
    private ISanPham iSanPham;

    private DecimalFormat dcf = new DecimalFormat("#,###,###,###");

    public SanPhamAdapter(Context context, ArrayList<ListSanPham> sanPhams, ISanPham iSanPham) {
        this.context = context;
        this.sanPhams = sanPhams;
        this.iSanPham = iSanPham;
    }

    @Override
    public SanPhamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View sanPhamView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new SanPhamViewHolder(sanPhamView);
    }

    @Override
    public void onBindViewHolder(SanPhamViewHolder holder, final int position) {
        holder.tv_tenKHSP.setText(sanPhams.get(position).getTenKhachHang());
        holder.tv_soLuongSP.setText(String.valueOf(dcf.format(sanPhams.get(position).getSoLuong())));
        holder.tv_donGiaSP.setText(String.valueOf(dcf.format(sanPhams.get(position).getDonGia())));
        holder.tv_thoiGianSP.setText(sanPhams.get(position).getThoiGian());
        holder.tv_loaiSP.setText(sanPhams.get(position).getLoai());
        int sl = sanPhams.get(position).getSoLuong();
        int dg = sanPhams.get(position).getDonGia();
        holder.tv_tinhTienSP.setText(String.valueOf(dcf.format(sl*dg))+"đ");

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = Integer.parseInt(view.getTag().toString());
                iSanPham.ClickItemSanPham(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class SanPhamViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_soLuongSP, tv_donGiaSP, tv_thoiGianSP, tv_loaiSP, tv_tinhTienSP, tv_tenKHSP;


        public SanPhamViewHolder(View itemView) {
            super(itemView);

            tv_soLuongSP = itemView.findViewById(R.id.tv_soLuongSP);
            tv_donGiaSP = itemView.findViewById(R.id.tv_donGiaSP);
            tv_thoiGianSP = itemView.findViewById(R.id.tv_thoiGianSP);
            tv_loaiSP = itemView.findViewById(R.id.tv_loaiSP);
            tv_tinhTienSP = itemView.findViewById(R.id.tv_tinhTienSP);
            tv_tenKHSP = itemView.findViewById(R.id.tv_tenKHSP);

        }
    }
}
