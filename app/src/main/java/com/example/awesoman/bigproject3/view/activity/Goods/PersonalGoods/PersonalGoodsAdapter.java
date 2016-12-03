package com.example.awesoman.bigproject3.view.activity.Goods.PersonalGoods;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;
import com.example.awesoman.bigproject3.network.ShopAPI;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awesome on 2016/11/29.
 */

public class PersonalGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<GoodsEntity> data;
    List<Boolean> flags;
    ImageLoader imageLoader;
    int count = 0;
    DeleteListener deleteListener;
    Boolean isAll =false;

    public interface DeleteListener{
        void canDelete(int count);
        void popupBtn();
    }

    //点击全选  或  取消全选
    public boolean setAll() {
        isAll = !isAll;
        putAllFlag(isAll);
        return isAll;
    }
    public void putAllFlag(boolean all){
        for(int i = 0;i<flags.size();i++){
            Log.i("putAllFlag",i+"");
            flags.set(i,all);
        }
        if(all == true)
            count = flags.size();
        else
            count = 0;
        deleteListener.canDelete(count);
        notifyDataSetChanged();
    }


    public PersonalGoodsAdapter(DeleteListener listener) {
        this.imageLoader = ImageLoader.getInstance();
        data = new ArrayList<>();
        flags = new ArrayList<>();
        deleteListener = listener;
    }


    public void setData(List<GoodsEntity> data) {
        this.data = data;
        flags.clear();
        for(int i = 0;i<data.size();i++){
            flags.add(false);
        }
    }



    public void removeData(){
        for(int i =0;i<data.size();i++){
            if(flags.get(i)==true){
                data.remove(i);
                flags.remove(i);
                Log.i("i","--");
                i--;
            }
        }
        notifyDataSetChanged();
    }

    public int getSelectedCount(){
       return count;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHodler viewHodler = new MyViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_good,parent,false));
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHodler myViewHodler = (MyViewHodler) holder;
        imageLoader.displayImage(ShopAPI.IMAGE_URL+data.get(position).getPage(),myViewHodler.img);
        myViewHodler.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myViewHodler.flag.isChecked()){
                    count--;
                }
                else
                    count++;
                myViewHodler.flag.performClick();
                flags.set(position,myViewHodler.flag.isChecked());
                deleteListener.canDelete(count);
            }
        });

        myViewHodler.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteListener.popupBtn();
                return true;
            }
        });
        myViewHodler.name.setText(data.get(position).getName());
        myViewHodler.price.setText(data.get(position).getPrice());
        myViewHodler.description.setText(data.get(position).getDescription());
        myViewHodler.flag.setChecked(flags.get(position));
        myViewHodler.flag.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHodler extends RecyclerView.ViewHolder{
        View container;
        ImageView img;
        TextView name;
        TextView description;
        TextView price;
        CheckBox flag;
        public MyViewHodler(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.item_personal_good_container);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            price =(TextView)itemView.findViewById(R.id.tv_price);
            flag = (CheckBox)itemView.findViewById(R.id.checkbox);
        }
    }
}
