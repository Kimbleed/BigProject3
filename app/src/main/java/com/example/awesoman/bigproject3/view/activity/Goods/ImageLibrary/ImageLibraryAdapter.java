package com.example.awesoman.bigproject3.view.activity.Goods.ImageLibrary;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.awesoman.bigproject3.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awesome on 2016/11/30.
 */

public class ImageLibraryAdapter extends RecyclerView.Adapter<ImageLibraryAdapter.ImageLibraryViewHolder> {
    ArrayList<String> data;
    ArrayList<Boolean> flags;
    int count= 0;
    ImageLibraryListener listener;
    RecyclerView recyclerView;

    public interface ImageLibraryListener{
        void clickOnCheck(String uri,boolean isCheck);
        void clickOnImg(int positionOfData);
        void clickOnCheckBeStoped();
    }

    public ImageLibraryAdapter(ImageLibraryListener libraryListener,RecyclerView bind) {
        this.flags = new ArrayList<>();
        listener = libraryListener;
        recyclerView = bind;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> mData) {
        this.data = mData;
        //分配flag
        for(int i =0;i<mData.size();i++){
            flags.add(false);
        }
    }

    /**
     * 选一个,根据uri,对对应的flag置check
     * @param uri
     * @param check
     */
    public void chooseOne(String uri,boolean check){
        for(int i =0;i<data.size();i++){
            if(uri.equals(data.get(i))){
                flags.set(i,check);
                ((CheckBox)recyclerView.findViewWithTag(uri)).setChecked(check);
                if(check){
                    count++;
                }
                else
                    count--;
                break;
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 选择图片库中与list中存在的，其余一律不选
     * @param list
     */
    public void choose(List<String> list){
        for(int i = 0;i<data.size();i++){
            for(int j=0;j<list.size();j++) {
                if (data.get(i).equals(list.get(j))){
                    flags.set(i,true);
                    count++;
                    break;
                }
                if( j ==list.size()-1){
                    flags.set(i,false);
                }
            }
        }
    }

    /**
     * 获取标记数组(用于放到intent中传输用)
     * @return
     */
    public int[] getFlags() {
        int count = 0;
        for(int i = 0;i<flags.size();i++){
            if(flags.get(i)==true){
                count++;
            }
        }
        int[] flagsArr = new int[count];
        for(int i=0,j =0;i<flags.size();i++){
            if(flags.get(i)==true)
                flagsArr[j++] = i;
        }
        return flagsArr;
    }

    /**
     * 返回已经标记为true(已选)的图片的uri
     * @return
     */
    public ArrayList<String> getChose(){
        ArrayList<String> imgPaths = new ArrayList<>();
        for(int i = 0;i<data.size();i++){
            if(flags.get(i)==true){
                imgPaths.add(data.get(i));
            }
        }
        return imgPaths;
    }

    /**
     * 取消选择，同时刷新对应uri的该Adapter绑定的RecyclerView下的item中的checkbox
     * @param uri 取消的图片的uri
     */
    public void cancelChose(String uri){
        for(int i = 0;i<data.size();i++)
            if(data.get(i).equals(uri)){
                flags.set(i,false);
                count--;
                View view =recyclerView.findViewWithTag(uri);
                if(view!=null){
                    ((CheckBox)view).setChecked(false);
                }
                return ;
            }
    }

    @Override
    public ImageLibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageLibraryViewHolder viewHolder = new ImageLibraryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_library,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageLibraryViewHolder holder, final int position) {
        if(position == 0){
            holder.imageView.setImageResource(R.drawable.take_pt);
            holder.checkBox.setVisibility(View.GONE);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.clickOnImg(0);
                }
            });
        }
        else{
            ImageLoader.getInstance().displayImage("file:///" + data.get(position-1), holder.imageView);
            holder.checkBox.setChecked(flags.get(position-1));
            holder.checkBox.setTag(data.get(position-1));
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        if (holder.checkBox.isChecked()) {
                            if(count>=8) {
                                holder.checkBox.setChecked(!holder.checkBox.isChecked());
                                listener.clickOnCheckBeStoped();
                            }
                            else {
                                count++;
                            }
                        } else
                            count--;
                        flags.set(position - 1, holder.checkBox.isChecked());
                        listener.clickOnCheck(data.get(position - 1), holder.checkBox.isChecked());
                    }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.clickOnImg(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    class ImageLibraryViewHolder extends RecyclerView.ViewHolder{
        View container;
        ImageView imageView;
        CheckBox checkBox;
        public ImageLibraryViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.item_image_library_container);
            imageView = (ImageView)itemView.findViewById(R.id.item_image_library_imageview);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_image_library_checkbox);
        }
    }
}
