package com.example.awesoman.bigproject3.view.activity.Goods.UpLoadgoods;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.awesoman.bigproject3.MyApplication;
import com.example.awesoman.bigproject3.view.activity.MainActivity;
import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;
import com.example.awesoman.bigproject3.presenter.identify.good.UploadGoodsPresenter;
import com.example.awesoman.bigproject3.presenter.identify.good.UploadGoodsView;
import com.example.awesoman.bigproject3.view.activity.BaseActivity;
import com.example.awesoman.bigproject3.view.activity.Goods.ImageLibrary.ImageLibraryActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Awesome on 2016/11/26.
 */

public class UpLoadGoodsAcitivity extends BaseActivity implements UploadGoodsView, View.OnClickListener,UpLoadGoodsAdapter.PicListener {
    @Bind(R.id.et_goods_name)
    EditText et_goods_name;
    @Bind(R.id.et_goods_price)
    EditText et_goods_price;
    @Bind(R.id.et_goods_master)
    EditText et_goods_master;
    @Bind(R.id.et_goods_type)
    EditText et_goods_type;
    @Bind(R.id.et_goods_description)
    EditText et_goods_description;
    @Bind(R.id.confirm_upload)
    Button confirm_upload;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.update_goods_recyclerview)
    RecyclerView recyclerView;

    UploadGoodsPresenter uploadGoodsPresenter;
    UpLoadGoodsAdapter adapter;
    List<File> files;


    /**implement BaseActivity
     *
     */
    @Override
    public void setIsToolbar() {
        super.isBackHome = true;

    }

    @Override
    public int setContentView() {
        return R.layout.activity_upload_goods;
    }
    @Override
    public void initView(){
        files = new ArrayList<>();
        uploadGoodsPresenter = new UploadGoodsPresenter();
        uploadGoodsPresenter.attachView(this);
        adapter = new UpLoadGoodsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void setListener(){
        confirm_upload.setOnClickListener(this);
    }


    /**
     * implement UpLoadGoodsAdapter.PicListener
     */
    @Override
    public void picPhoto() {
        Intent intent = new Intent().setClass(this,ImageLibraryActivity.class);
        intent.putParcelableArrayListExtra("hasChose",adapter.getmData());
        startActivityForResult(intent,RESULT_IMG_PATHS);
    }

    /**
     * implement UploadGoodsView
     */
    @Override
    public void afterUploadGoodsRequest() {
        Toast.makeText(this,"上传成功",Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * implement  View.OnClickListener
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirm_upload:
                GoodsEntity entity = new GoodsEntity();
                entity.setName(et_goods_name.getText().toString());
                entity.setPrice(et_goods_price.getText().toString());
                entity.setMaster(((MyApplication)getApplication()).getUser().getName());
                entity.setType(et_goods_type.getText().toString());
                entity.setDescription(et_goods_description.getText().toString());
                uploadGoodsPresenter.requestUpload(entity,files);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_IMG_PATHS) {
            if(data!=null &&data.hasExtra("img_paths")){
                ArrayList<String> list = data.getStringArrayListExtra("img_paths");
                files.clear();
                for(int i = 0;i<list.size();i++){
                    Log.i("onActivityResult_"+i,list.get(i));
                    files.add(new File(list.get(i)));
                }
                adapter.setmData(list);


            }
        }
    }
}
