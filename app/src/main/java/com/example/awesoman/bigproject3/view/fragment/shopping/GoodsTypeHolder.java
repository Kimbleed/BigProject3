package com.example.awesoman.bigproject3.view.fragment.shopping;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.awesoman.bigproject3.R;

/**
 * Created by Awesome on 2016/11/22.
 */

public class GoodsTypeHolder extends RecyclerView.ViewHolder {
    RecyclerView v;
    GoodsTypeAdapter goodsTypeAdapter;
    public GoodsTypeHolder(View itemView) {
        super(itemView);
        v = (RecyclerView) itemView.findViewById(R.id.goods_type_recyleview);
        Log.i("GoodsTypeHolder","");
        v.setLayoutManager(new GridLayoutManager(itemView.getContext(),4));
        goodsTypeAdapter = new GoodsTypeAdapter(itemView.getContext());
        v.setAdapter(goodsTypeAdapter);

    }

}
