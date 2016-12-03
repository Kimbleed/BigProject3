package com.example.awesoman.bigproject3;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awesome on 2016/11/30.
 */

public class ImageLibraryContentProvider{
    Context context;
    Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    String[] projection = new String[]{MediaStore.Images.Media._ID,MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
    String orderBy = MediaStore.Images.Media.DATE_MODIFIED+" desc";
    public ImageLibraryContentProvider(Context context) {
        this.context = context;
    }

    public ArrayList<String> getImageStringPaths(){
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(mImageUri,projection,null,null,orderBy);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                data.add(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            }while(cursor.moveToNext());
        }
        return data;
    }
}
