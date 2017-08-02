package hnulab.appointmenttour.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;

import java.util.List;

import hnulab.appointmenttour.R;
import hnulab.appointmenttour.listener.SearchItemOnClickListener;


/**
 * Created by dingmouren on 2017/2/28.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private List<PoiItem> mList;
    private SearchItemOnClickListener mListener;
    public void setList(List<PoiItem> list){
        this.mList = list;
    }
    private Context context;
    //传入context
    public SearchResultAdapter(Context context){
        this.context=context;
    }


    public void setOnItemClickListener(SearchItemOnClickListener listener){
        this.mListener = listener;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mList.get(position));
        holder.root.setOnClickListener(v -> {
            if (null != mListener ){
                mListener.onClick(holder.root,mList.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView root;
        TextView tvName,tvAddress;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            root = (CardView) itemView.findViewById(R.id.root);
        }
        private void bindData(PoiItem poiItem){
            if (null != poiItem){
                tvName.setText(poiItem.getTitle());
                tvAddress.setText(poiItem.getSnippet());
            }
        }
    }
}
