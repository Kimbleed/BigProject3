package com.example.awesoman.bigproject3.view.fragment.shopping;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;
import com.example.awesoman.bigproject3.network.ShopAPI;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awesome on 2016/11/24.
 */

public class OtherTypeAdapter extends RecyclerView.Adapter<OtherTypeAdapter.MyViewHodler> {
    List<GoodsEntity> data;
    ImageLoader imageLoader;

    public OtherTypeAdapter() {
        this.imageLoader = ImageLoader.getInstance();
        data = new ArrayList<>();
    }

    public void setData(List<GoodsEntity> data) {
        this.data = data;
    }

    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHodler viewHodler = new MyViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_type,parent,false));
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(MyViewHodler holder, int position) {
        imageLoader.displayImage(ShopAPI.IMAGE_URL+data.get(position).getPage(),holder.img);
        holder.name.setText(data.get(position).getName());
        holder.price.setText(data.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHodler extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        TextView price;
        public MyViewHodler(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            price =(TextView)itemView.findViewById(R.id.tv_price);
        }
    }
}
