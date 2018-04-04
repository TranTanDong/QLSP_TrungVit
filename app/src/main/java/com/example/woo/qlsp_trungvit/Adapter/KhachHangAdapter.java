package com.example.woo.qlsp_trungvit.Adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.woo.qlsp_trungvit.Holder.KhachHangViewHolder;
import com.example.woo.qlsp_trungvit.Holder.LoadingViewHolder;
import com.example.woo.qlsp_trungvit.Interface.OnLoadMoreListener;
import com.example.woo.qlsp_trungvit.KhachHang;
import com.example.woo.qlsp_trungvit.Model.ListKhachHang;
import com.example.woo.qlsp_trungvit.R;

import java.util.ArrayList;

/**
 * Created by Woo on 3/22/2018.
 */

public class KhachHangAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    OnLoadMoreListener onLoadMoreListener;

    private boolean isLoading = false;
    int visibleThrehold = 5;
    int lastVisibleItem;
    int totalItemCount;
    RecyclerView recyclerView;
    KhachHang context;
    ArrayList<ListKhachHang> khachHangs;

    public void setLoaded(){
        isLoading = false;
    }

    public OnLoadMoreListener getOnLoadMoreListener(){
        return onLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public KhachHangAdapter(KhachHang context, RecyclerView recyclerView, ArrayList<ListKhachHang> khachHangs){
        this.context = context;
        this.recyclerView = recyclerView;
        this.khachHangs = khachHangs;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount<=(lastVisibleItem+visibleThrehold)){
                    if (onLoadMoreListener != null){
                        onLoadMoreListener.onLoadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM){
            View khachHangView = LayoutInflater.from(context).inflate(R.layout.item_khachhang, parent, false);
            return new KhachHangViewHolder(khachHangView);
        }

        if (viewType == VIEW_TYPE_LOADING){
            View LoandingView = LayoutInflater.from(context).inflate(R.layout.loadingitem, parent, false);
            return new LoadingViewHolder(LoandingView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof KhachHangViewHolder){
            final ListKhachHang khachHang = khachHangs.get(position);
            final KhachHangViewHolder khachHangViewHolder = (KhachHangViewHolder) holder;
            khachHangViewHolder.tv_tenKH.setText(khachHang.getTenKH());
            khachHangViewHolder.tv_sdtKH.setText(khachHang.getSdtKH());
            khachHangViewHolder.tv_diachiKH.setText(khachHang.getDiachiKH());

//            khachHangViewHolder.btn_Goi.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Uri uri = Uri.parse("tel:"+ khachHangViewHolder.tv_sdtKH.getText().toString());
//                    Intent nIntent = new Intent(Intent.ACTION_CALL);
//                    nIntent.setData(uri);
//                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//
//                }
//            });
        }
        else if (holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return khachHangs == null ? 0 : khachHangs.size();
    }

    @Override
    public int getItemViewType(int position) {
        return khachHangs.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
}
