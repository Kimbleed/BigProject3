package com.example.awesoman.bigproject3.view.activity.Goods.ImageLibrary;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ImageLibraryChoseAdapter extends RecyclerView.Adapter<ImageLibraryChoseAdapter.ImageLibraryChoseViewHolder> {

    ArrayList<String> mData;
    Listener listener;
    RecyclerView recyclerView;

    public void setmData(ArrayList<String> mData) {
        this.mData = mData;
    }

    public ImageLibraryChoseAdapter(Listener listener ,RecyclerView bind) {
        this.listener = listener;
        mData = new ArrayList<>();
        recyclerView = bind;
    }

    public interface Listener{
        void clickOnBtnX(String uri);
        void clickOnImg(String uri);
    }

    public ArrayList<String> getmData() {
        return mData;
    }

    public void addmData(String uri){
        Log.i("ILCAdapter","addmData");
        mData.add(uri);
        recyclerView.scrollToPosition(0);
        notifyItemInserted(0);
    }

    /**
     *
     * @param uri 要添加作为封面的Uri
     * @return 返回choose中是否在添加前就存在该uri图片
     */
    public boolean addSurface(String uri){
        boolean isExist =  mData.remove(uri);
        mData.add(0,uri);
        notifyDataSetChanged();
        return isExist;
    }

    public void removeData(String uri){
        for(int i=0;i<mData.size();i++){
            if(mData.get(i).equals(uri)) {
                notifyItemRemoved(mData.size()-1-i);
                mData.remove(i);
                break;
            }
        }
    }

    @Override
    public ImageLibraryChoseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageLibraryChoseViewHolder holder =new ImageLibraryChoseViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_lirbrary_chose_recycle,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageLibraryChoseViewHolder holder,final int position) {
        ImageLoader.getInstance().displayImage("file:///"+mData.get(mData.size()-1-position),holder.imageView);
        final String img = mData.get(mData.size()-1-position);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickOnBtnX(img);
                notifyDataSetChanged();
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickOnImg(mData.get(mData.size()-1-position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ImageLibraryChoseViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        Button button;
        public ImageLibraryChoseViewHolder(View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.item_image_library_chose_img);
            button = (Button) itemView.findViewById(R.id.item_image_library_chose_btn);
        }
    }

}
