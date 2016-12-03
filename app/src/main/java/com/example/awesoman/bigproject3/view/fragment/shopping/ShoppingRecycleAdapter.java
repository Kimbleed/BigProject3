package com.example.awesoman.bigproject3.view.fragment.shopping;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.awesoman.bigproject3.view.activity.Goods.GoodsDetailActivity;
import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;
import com.example.awesoman.bigproject3.network.ShopAPI;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awesome on 2016/11/22.
 */

public class ShoppingRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ImageLoader imageLoader ;

    final int TITLE_TYPE = 1;
    final int ADV_TYPE = 2;


    private List<GoodsEntity> data ;
    Context context;

    public ShoppingRecycleAdapter(Context context) {
        data = new ArrayList<>();
        this.context = context;
        data = new ArrayList<>();
        imageLoader = ImageLoader.getInstance();
    }

    public void setData(List<GoodsEntity> goodsList){
        data = goodsList;

    }
    public void addData(List<GoodsEntity> goodsList){
        data.addAll(data.size(),goodsList);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return ADV_TYPE;
        if(position ==1)
            return TITLE_TYPE;
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if(viewType == ADV_TYPE){
            holder = new AdvHolder(LayoutInflater.from(context)
                    .inflate(R.layout.view_pager_adv,parent,false));
        }
        else if(viewType ==TITLE_TYPE){
            holder = new GoodsTypeHolder(LayoutInflater.from(context)
                    .inflate(R.layout.recycle_view_type,parent,false));
        }
        else {
             holder = new GoodsHolder(LayoutInflater.from(context)
                     .inflate(R.layout.item_goods_recycle_view, parent,false),data);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Log.i("onBindViewHolder",position+"");
        Log.i("onBindViewHolderCount",getItemCount()+"");
        if(position>1) {
            if (position < data.size()+2) {
                final GoodsEntity goodsEntity = data.get(position-2);
                ((GoodsHolder)holder).tv.setText(goodsEntity.getName());
                imageLoader.displayImage(ShopAPI.IMAGE_URL + goodsEntity.getPage(), ((GoodsHolder)holder).iv);
                ((GoodsHolder)holder).iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context =((GoodsHolder)holder).tv.getContext();
                        Intent intent =new Intent().setClass(context, GoodsDetailActivity.class);
                        intent.putExtra("good",goodsEntity);
                        context.startActivity(intent);
                    }
                });

            } else {
                ((GoodsHolder)holder).tv.setText("");
                ((GoodsHolder)holder).iv.setBackground(null);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(data.size()%2==0)
            return data.size()+2;
        else
            return data.size()+3;
    }

}
