package com.example.awesoman.bigproject3.view.fragment.shopping;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;

import java.util.List;

/**
 * Created by Awesome on 2016/11/22.
 */

public class GoodsHolder extends RecyclerView.ViewHolder
{

    ImageView iv;
    TextView tv;


    public GoodsHolder(View itemView, List<GoodsEntity> mData) {
        super(itemView);
        this.iv = (ImageView)itemView.findViewById(R.id.iv);
        this.tv = (TextView) itemView.findViewById(R.id.tv);
    }

}
