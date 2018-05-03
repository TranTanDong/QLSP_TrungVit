package com.example.woo.qlsp_trungvit.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woo.qlsp_trungvit.Interface.IThongKeMV;
import com.example.woo.qlsp_trungvit.R;

import java.util.ArrayList;

public class ThongKeMVAdapter extends RecyclerView.Adapter<ThongKeMVAdapter.ThongKeMVViewHolder> {

    private Context context;
    private ArrayList<String> listThongKes;
    private IThongKeMV iThongKeMV;

    public ThongKeMVAdapter(Context context, ArrayList<String> listThongKes, IThongKeMV iThongKeMV) {
        this.context = context;
        this.listThongKes = listThongKes;
        this.iThongKeMV = iThongKeMV;
    }

    @Override
    public ThongKeMVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View timeView = LayoutInflater.from(context).inflate(R.layout.item_time, parent, false);
        return new ThongKeMVViewHolder(timeView);
    }

    @Override
    public void onBindViewHolder(ThongKeMVViewHolder holder, int position) {
        holder.tv_thoiGianTK.setText(listThongKes.get(position));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iThongKeMV.ClickItemThongKeMV(Integer.parseInt(view.getTag().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listThongKes.size();
    }

    public class ThongKeMVViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_thoiGianTK;

        public ThongKeMVViewHolder(View itemView) {
            super(itemView);
            tv_thoiGianTK = itemView.findViewById(R.id.tv_thoiGianTK);
        }
    }
}
