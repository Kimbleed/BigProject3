package com.example.awesoman.bigproject3.view.activity.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.awesoman.bigproject3.view.activity.MainActivity;
import com.example.awesoman.bigproject3.MyApplication;
import com.example.awesoman.bigproject3.view.PopupWindow.IconPopupWindow;
import com.example.awesoman.bigproject3.view.PopupWindow.PicWindow;
import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.UserEntity;
import com.example.awesoman.bigproject3.network.ShopAPI;
import com.example.awesoman.bigproject3.presenter.identify.user.UpdatePresenter;
import com.example.awesoman.bigproject3.presenter.identify.user.UpdateView;
import com.example.awesoman.bigproject3.view.activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;

import butterknife.Bind;

/**
 * Created by Awesome on 2016/11/25.
 */

public class PersonalActicity extends BaseActivity implements View.OnClickListener,UpdateView{

    @Bind(R.id.img_icon)
    ImageView img_icon;
    @Bind(R.id.unregist)
    TextView unregist;
    @Bind(R.id.tv_nickname)
    TextView tv_nickname;
    @Bind(R.id.tv_username)
    TextView tv_username;
    @Bind(R.id.personal_icon)
    View personal_icon;
    @Bind(R.id.personal_name)
    View personal_name;
    @Bind(R.id.personal_nickname)
    View personal_nickname;
    @Bind(R.id.personal_username)
    View getPersonal_nickname;

    UserEntity userEntity;

    PopupWindow popupWindow;

    UpdatePresenter updatePresenter;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.unregist:
                unRegist();
                break;
            case R.id.personal_icon:
                getPopupWindow();
                popupWindow.showAtLocation(findViewById(R.id.container_personal), Gravity.BOTTOM,0,0);
                break;
            case R.id.personal_nickname:
                Intent intent = new Intent();
                intent.putExtra("nickname",userEntity.getNickname());
                startActivity(intent.setClass(this,ReNameActivity.class));
                break;
        }
    }




    @Override
    public void setIsToolbar() {
        super.isBackHome = true;
    }

    @Override
    public int setContentView() {
        return R.layout.activity_personal;
    }

    @Override
    public void initView() {

        userEntity = ((MyApplication)getApplication()).getUser();
        updatePresenter = new UpdatePresenter();
        updatePresenter.attachView(this);

        tv_username.setText(userEntity.getUsername());
        tv_nickname.setText(userEntity.getNickname());
        ImageLoader.getInstance().displayImage(ShopAPI.IMAGE_URL+userEntity.getOther(),img_icon);
    }

    @Override
    public void setListener(){
        personal_icon.setOnClickListener(this);
        personal_nickname.setOnClickListener(this);
        unregist.setOnClickListener(this);
    }


    public void unRegist(){
        ((MyApplication) getApplication()).setUser(null);
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", null);
        editor.putString("password", null);
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void afterUpdate(UserEntity entity) {
        ((MyApplication)getApplication()).setUser(entity);
    }

    /**
     * 显示PopupWindow
     */
    public void getPopupWindow(){
        Log.i("getPopupWindow","start");
        if(popupWindow!=null){
            popupWindow.dismiss();
            return ;
        }
        else{
            initPopuptWindow();
        }
    }


    /**
     * 实例化PopupWindow
     */
    protected void initPopuptWindow() {
        popupWindow = new IconPopupWindow(this,picListener).getIconPopupWindow();
    }

    CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            Log.i("onPhotoCropped",uri.getPath());
            File file = new File(uri.getPath());
            img_icon.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            //发送更新请求
            updatePresenter.requestUpdate(((MyApplication)getApplication()).getUser(),file);
            popupWindow.dismiss();
        }

        @Override
        public void onCropCancel() {

        }

        @Override
        public void onCropFailed(String message) {

        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 400;
            cropParams.aspectY = 400;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return PersonalActicity.this;
        }
    };

    private PicWindow.Listener picListener = new PicWindow.Listener(){
        @Override
        public void toGallery() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent =CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent,CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent =CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent,CropHelper.REQUEST_CAMERA);
        }
    };

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
    }
}
