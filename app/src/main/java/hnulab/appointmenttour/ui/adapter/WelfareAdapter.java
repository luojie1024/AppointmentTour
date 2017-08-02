package hnulab.appointmenttour.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import hnulab.appointmenttour.MyApplication;
import hnulab.appointmenttour.R;
import hnulab.appointmenttour.bean.GankResultWelfare;
import hnulab.appointmenttour.listener.WelfareItemOnClickListener;

/**
 * Created by mouren on 2017/3/12.
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.ViewHolder> {
    private List<GankResultWelfare> mList = new ArrayList<>();
    private WelfareItemOnClickListener mListener;
    public void addList(List<GankResultWelfare> list){
        this.mList.addAll(list);
    }
    public void setItemOnClickListener(WelfareItemOnClickListener listener){
        this.mListener = listener;
    }
    public void clearList(){
        this.mList.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.applicationContext).inflate(R.layout.item_welfare,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mList.get(position),position);
        holder.mImg.setOnClickListener(v -> {
            if (mListener != null){
                mListener.onClick(holder.mImg,mList.get(position).getUrl(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView mImg;
        public ViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.img_welfare);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
        }

        public void bindData(GankResultWelfare bean,int position){
            Glide.with(MyApplication.applicationContext).load(bean.getUrl())
                    .crossFade(500)
                    .placeholder(R.mipmap.item_bg)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mImg);
        }
    }
}
