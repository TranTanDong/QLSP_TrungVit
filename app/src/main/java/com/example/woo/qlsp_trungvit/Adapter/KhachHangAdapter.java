package com.example.woo.qlsp_trungvit.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woo.qlsp_trungvit.Interface.IKhachHang;
import com.example.woo.qlsp_trungvit.KhachHang;
import com.example.woo.qlsp_trungvit.Model.ListKhachHang;
import com.example.woo.qlsp_trungvit.R;

import java.util.ArrayList;

/**
 * Created by Woo on 3/22/2018.
 */

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.KhachHangViewHolder> {

    KhachHang context;
    ArrayList<ListKhachHang> khachHangs;
    IKhachHang iKhachHang;


    public KhachHangAdapter(KhachHang context, ArrayList<ListKhachHang> khachHangs, IKhachHang iKhachHang){
        this.context = context;
        this.khachHangs = khachHangs;
        this.iKhachHang = iKhachHang;
    }

    @Override
    public KhachHangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View khachHangView = LayoutInflater.from(context).inflate(R.layout.item_khachhang, parent, false);
            return new KhachHangViewHolder(khachHangView);
    }


    @Override
    public void onBindViewHolder(KhachHangViewHolder holder, final int position) {

        final ListKhachHang khachHang = khachHangs.get(position);
        holder.tv_tenKH.setText(khachHang.getTenKH());
        holder.tv_sdtKH.setText(khachHang.getSdtKH());
        holder.tv_diachiKH.setText(khachHang.getDiachiKH());


        //Xử lý Click Xóa, Gọi, ClickItem
        holder.btn_Goi.setTag(position);
        holder.btn_Xoa.setTag(position);
        holder.itemView.setTag(position);
        holder.btn_Sua.setTag(position);
        holder.btn_Goi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = Integer.parseInt(view.getTag().toString());
                iKhachHang.CallKhachHang(i);
            }
        });

        holder.btn_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j = Integer.parseInt(view.getTag().toString());
                iKhachHang.DelKhachHang(j);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t = Integer.parseInt(view.getTag().toString());
                iKhachHang.ClickItemKhachHang(t);
            }
        });

        holder.btn_Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int k = Integer.parseInt(view.getTag().toString());
                iKhachHang.EditKhachHang(k);
            }
        });

        if(khachHang.getTenKH().equals("GUEST")){
            holder.btn_Sua.setVisibility(View.INVISIBLE);
            holder.btn_Goi.setVisibility(View.INVISIBLE);
            holder.btn_Xoa.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return khachHangs.size();
    }

    public class KhachHangViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenKH;
        TextView tv_sdtKH;
        TextView tv_diachiKH;
        ImageView btn_Goi;
        ImageView btn_Xoa;
        ImageView btn_Sua;

        public KhachHangViewHolder(View itemView) {
            super(itemView);

            tv_tenKH = (TextView)itemView.findViewById(R.id.tv_tenKH);
            tv_sdtKH = (TextView)itemView.findViewById(R.id.tv_sdtKH);
            tv_diachiKH = (TextView)itemView.findViewById(R.id.tv_diachiKH);
            btn_Goi = (ImageView)itemView.findViewById(R.id.btn_goi);
            btn_Xoa = (ImageView)itemView.findViewById(R.id.btn_xoa);
            btn_Sua = (ImageView)itemView.findViewById(R.id.btn_sua);
        }
    }

}
