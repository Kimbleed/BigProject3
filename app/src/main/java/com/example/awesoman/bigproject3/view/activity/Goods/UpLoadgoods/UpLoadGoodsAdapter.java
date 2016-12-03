package com.example.awesoman.bigproject3.view.activity.Goods.UpLoadgoods;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.awesoman.bigproject3.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Awesome on 2016/11/30.
 */

public class UpLoadGoodsAdapter extends RecyclerView.Adapter<UpLoadGoodsAdapter.UpdateLoadGoodsViewHolder> {

    ArrayList mData ;

    PicListener listener;

    public UpLoadGoodsAdapter(PicListener listener) {
        this.listener = listener;
        mData = new ArrayList();
    }

    public interface PicListener{
        void picPhoto();
    }

    public ArrayList getmData() {
        return mData;
    }

    public void setmData(ArrayList mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public UpdateLoadGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UpdateLoadGoodsViewHolder holder = new UpdateLoadGoodsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_update_goods_recyclerview,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(UpdateLoadGoodsViewHolder holder, int position) {
        if(position<mData.size()){
            holder.button.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage("file:///"+mData.get(position),holder.imageView);
        }
        else if(position == mData.size()){
            holder.button.setVisibility(View.GONE);
            holder.imageView.setImageResource(R.drawable.take_pt);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.picPhoto();
                }
            });
        }
        else {
            holder.button.setVisibility(View.GONE);
            holder.imageView.setImageBitmap(null);
            holder.imageView.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class UpdateLoadGoodsViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        Button button;
        public UpdateLoadGoodsViewHolder(View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.item_update_goods_imageview);
            button = (Button) itemView.findViewById(R.id.item_update_goods_btn);
        }
    }
}
