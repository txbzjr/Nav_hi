package com.zjr.nav_hi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdaper extends RecyclerView.Adapter<RecyclerViewAdaper.ViewHolder> implements Filterable {
    private OnItemClickLitener mItemClickListener;
    private List<Map<String,Object>> mSourceList;
    private List<Map<String,Object>> mFilterList;
    List<Map<String,Object>> getmFilterList(){
        return mFilterList;
    }
    public interface OnItemClickLitener{
        void onItemClick(View view, int position);
    }

    void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mItemClickListener = mOnItemClickLitener;
    }
    //布局解析器
    private LayoutInflater mInflater;
    RecyclerViewAdaper(Context context, List<Map<String, Object>> list){
        mSourceList = list;
        //这里需要初始化filterList
        mFilterList = list;
        this.mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv.setText(mFilterList.get(i).get("AppName").toString());
        viewHolder.imageView.setImageDrawable((Drawable) mFilterList.get(i).get("AppIcon"));
        if (mItemClickListener != null) {
            viewHolder.itemlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, i);
                }
            });
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            //执行过滤操作
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    //没有过滤的内容，则使用源数据
                    mFilterList = mSourceList;
                } else {
                    List<Map<String,Object>> filteredList = new ArrayList<>();
                    for (int i=0;i<mSourceList.size();i++) {
                        //这里根据需求，添加匹配规则
                        if (mSourceList.get(i).get("AppName").toString().contains(charString)) {
                            filteredList.add(mSourceList.get(i));
                        }
                    }

                    mFilterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }
            //把过滤后的值返回出来
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (ArrayList<Map<String,Object>>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount(){
        return mFilterList==null ? 0 : mFilterList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        private ImageView imageView;
        private LinearLayout itemlayout;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.AppImage);
            tv = (TextView) view.findViewById(R.id.AppName);
            itemlayout = (LinearLayout) view.findViewById(R.id.item);
        }
    }
}
