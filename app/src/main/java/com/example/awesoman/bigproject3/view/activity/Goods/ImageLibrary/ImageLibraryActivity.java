package com.example.awesoman.bigproject3.view.activity.Goods.ImageLibrary;


import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.awesoman.bigproject3.ImageLibraryContentProvider;
import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.event.ImageChooseEvent;
import com.example.awesoman.bigproject3.view.activity.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class ImageLibraryActivity extends BaseActivity implements ImageLibraryAdapter.ImageLibraryListener,ImageLibraryChoseAdapter.Listener{

    @Bind(R.id.image_library_container)
    RecyclerView il_container;
    @Bind(R.id.choose_finish)
    Button btn_choose_finish;
    @Bind(R.id.image_library_chose_container)
    RecyclerView il_chose_container;


    ImageLibraryContentProvider contentProvider;
    ImageLibraryAdapter imageLibraryAdapter;
    ImageLibraryChoseAdapter imageLibraryChoseAdapter;


    /**
     * implement ImageLibraryChoseAdapter.Listener
     * 实现 ImageLibraryChoseAdapter.Listener
     */
    @Override
    public void clickOnBtnX(String uri) {
        imageLibraryChoseAdapter.removeData(uri);
        Log.i("clickOnBtnX",uri);
        imageLibraryAdapter.cancelChose(uri);
    }

    /**
     * implement ImageLibraryAdapter.ImageLibraryListener
     *实现  ImageLibraryAdapter.ImageLibraryListener
     * 在点击图库的图片的checkbox后,在下方recyclerView中显示或移除点击的图片
     */
    @Override
    public void clickOnCheck(String uri,boolean isCheck) {
        if(isCheck){
            imageLibraryChoseAdapter.addmData(uri);
        }
        else
            imageLibraryChoseAdapter.removeData(uri);
    }

    /**
     * implement ImageLibraryAdapter.ImageLibraryListener
     * @param position  点击位置
     * 实现 ImageLibraryAdapter.ImageLibraryListener
     * 点击图片，跳转到自定义的图片浏览器中，并直接定位到所点击的图片上，并能操作选择，移除
     * 如果点击到第一个，跳转到拍照
     *
     */
    @Override
    public void clickOnImg(int position) {
        if(position!=0) {
            Intent intent = new Intent();
            intent.setClass(this,ImageViewPagerActivity.class);
            //图片集Uri,List<String>
            intent.putStringArrayListExtra("imageLibrary",imageLibraryAdapter.getData());
            //已选择的图片,int[]
            intent.putExtra("hasChose",imageLibraryAdapter.getFlags());
            //定位,int
            intent.putExtra("position",position-1);
            startActivityForResult(intent,RESULT_IMG_PATHS);
        }
        else {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), RESULT_CAMERA);
        }
    }

    @Override
    public void clickOnCheckBeStoped() {
        Toast.makeText(this,"只能选取8张图片",Toast.LENGTH_LONG).show();
    }

    @Override
    public void clickOnImg(String uri) {

    }

    /**
     * implement BaseActivity
     */
    @Override
    public void setIsToolbar() {
        isBackHome = true;
        super.isMenu = false;
    }

    @Override
    public int setContentView() {
        return R.layout.activity_image_library;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        //获取在上传商品页面中(UpLoadGoodsActivity)已经选择过的图片集
        ArrayList<String> hasChose = getIntent().getStringArrayListExtra("hasChose");
        if(hasChose == null)
            hasChose = new ArrayList<>();

        //实例化本地图片库RecyclerView il_container 的适配器imageLibraryAdapter
        imageLibraryAdapter = new ImageLibraryAdapter(this,il_container);
        //实例化展示已选择图片栏RecyclerView il_chose_container的适配器imageLibraryChoseAdapter
        imageLibraryChoseAdapter = new ImageLibraryChoseAdapter(this,il_chose_container);

        //获取手机本地图片库中所有图片的Uri
        contentProvider = new ImageLibraryContentProvider(this);
        ArrayList<String> data = contentProvider.getImageStringPaths();

        //往本地图片库RecyclerView il_container的适配器imageLibraryAdapter装载手机本地所有图片的Uri的数据
        imageLibraryAdapter.setData(data);
        //根据已选择过的图片集,对本地图片库RecyclerView il_container的适配器imageLibraryAdapter的标记置true,并刷新
        imageLibraryAdapter.choose(hasChose);
        //根据已选择过的图片集，对展示已选择图片栏RecyclerView il_chose_container的适配器imageLibraryChoseAdapter装载图片uri集，并刷新
        imageLibraryChoseAdapter.setmData(hasChose);

        //配置本地图片库RecyclerView il_container
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        il_container.setLayoutManager(gridLayoutManager);
        il_container.setAdapter(imageLibraryAdapter);

        //配置展示已选择图片栏RecyclerView il_chose_container
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        il_chose_container.setLayoutManager(linearLayoutManager);
        il_chose_container.setAdapter(imageLibraryChoseAdapter);
        il_chose_container.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void setListener() {
        btn_choose_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //获取展示已选择图片栏RecyclerView il_chose_container的适配器中的图片集uri
                        ArrayList<String> data = imageLibraryChoseAdapter.getmData();
                        Intent intent = new Intent();
                        intent.putStringArrayListExtra("img_paths",data);
                        setResult(RESULT_IMG_PATHS, intent);
                        finish();
                    }
                }
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data !=null) {
          if (requestCode == RESULT_CAMERA) {
                //处理从相机返回来的结果
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public void onEventMainThread(ImageChooseEvent event) {
        String uri = event.getUri();
        boolean isAdd = event.isAdd();
        boolean isSurface = event.isSurface();
        Log.i("ILActivity_EventBus",uri+"|"+(isAdd?"Add":"Delete"));
        if(!isSurface) {
            imageLibraryAdapter.chooseOne(uri,isAdd);
            if (isAdd) {
                imageLibraryChoseAdapter.addmData(uri);
            } else {
                imageLibraryChoseAdapter.removeData(uri);
            }
        }
        else{
            if(!imageLibraryChoseAdapter.addSurface(uri)){
                imageLibraryAdapter.chooseOne(uri,true);
            }
        }
    }

}
