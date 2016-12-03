package com.example.awesoman.bigproject3.view.fragment.shopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.event.TypeEvent;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.greenrobot.event.EventBus;

/**
 * Created by Awesome on 2016/11/22.
 */

public class GoodsTypeAdapter extends RecyclerView.Adapter<GoodsTypeAdapter.MyViewHolder>{
    String[] typeInCh = {"全部","家用","电子","服饰","玩具","图书","礼品","其它"};
    String[] typeS = {"","household","electron","dress","toy","book","gift","other"};
    ImageLoader imageLoader ;
    int[] imgRes = {R.drawable.img_all,R.drawable.img_household,R.drawable.img_electron,R.drawable.img_dress,R.drawable.img_book,R.drawable.img_book,R.drawable.img_gift,R.drawable.img_other};
    GoodsTypeListener goodsTypeListener;
    Context context;

    public GoodsTypeAdapter(Context context) {
        this.context = context;
        if(context instanceof GoodsTypeListener)
            this.goodsTypeListener = (GoodsTypeListener)context;
        imageLoader = ImageLoader.getInstance();
    }

    public interface GoodsTypeListener{
        void updateType(String type);
    }

    @Override
    public GoodsTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("onCreateViewHolder",viewType+"");
        MyViewHolder view = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_goods_type_recycle_view,null));
        return view;
    }

    @Override
    public void onBindViewHolder(GoodsTypeAdapter.MyViewHolder holder, final int position) {
        holder.tv.setText(typeInCh[position]);
        holder.iv.setBackground(context.getDrawable(imgRes[position]));
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("GoodsType","onClick");
                EventBus.getDefault().post(new TypeEvent(typeS[position]));
            }
        });
    }


    @Override
    public int getItemCount() {
        return typeInCh.length;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}


