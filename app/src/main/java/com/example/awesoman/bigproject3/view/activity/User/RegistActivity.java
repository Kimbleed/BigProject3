package com.example.awesoman.bigproject3.view.activity.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awesoman.bigproject3.view.activity.MainActivity;
import com.example.awesoman.bigproject3.MyApplication;
import com.example.awesoman.bigproject3.view.PopupWindow.IconPopupWindow;
import com.example.awesoman.bigproject3.view.PopupWindow.PicWindow;
import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.UserResult;
import com.example.awesoman.bigproject3.model.entity.UserEntity;
import com.example.awesoman.bigproject3.presenter.identify.user.LoginPresenter;
import com.example.awesoman.bigproject3.presenter.identify.user.LoginView;
import com.example.awesoman.bigproject3.presenter.identify.user.RegistPresenter;
import com.example.awesoman.bigproject3.presenter.identify.user.RegistView;
import com.example.awesoman.bigproject3.presenter.identify.user.UpdatePresenter;
import com.example.awesoman.bigproject3.presenter.identify.user.UpdateView;
import com.example.awesoman.bigproject3.view.activity.BaseActivity;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;

import butterknife.Bind;

/**
 * Created by Awesome on 2016/11/25.
 */

public class RegistActivity extends BaseActivity implements View.OnClickListener,View.OnFocusChangeListener,RegistView,LoginView,UpdateView{
    @Bind(R.id.et_nickname)
    EditText et_nickname;
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_passwordRe)
    EditText et_passwordRe;
    @Bind(R.id.my_icon)
    ImageView my_icon;

    @Bind({R.id.icon_username,R.id.icon_password,R.id.icon_passwordRe})
    TextView[] icon;

    @Bind(R.id.confirm_regist)
    Button btnRegist;

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    RegistPresenter registPresenter;
    LoginPresenter loginPresenter;
    UpdatePresenter updatePresenter;


    PopupWindow popupWindow;


    @Override
    public void setIsToolbar() {

    }

    @Override
    public int setContentView() {
        return R.layout.activity_regist;
    }

    @Override
    public void initView(){
        registPresenter = new RegistPresenter();
        registPresenter.attachView(this);
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
        updatePresenter = new UpdatePresenter();
        updatePresenter.attachView(this);
    }

    @Override
    public void setListener(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //判定输入表单
                if(et_username.getText().toString().length()>0&&et_password.getText().toString().length()>0&&et_passwordRe.getText().length()>0){
                    btnRegist.setBackgroundResource(R.color.orange);
                }
                else
                    btnRegist.setBackgroundResource(R.color.orangeAlpha);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        et_username.addTextChangedListener(textWatcher);
        et_password.addTextChangedListener(textWatcher);
        et_passwordRe.addTextChangedListener(textWatcher);

        et_username.setOnFocusChangeListener(this);
        et_passwordRe.setOnFocusChangeListener(this);
        et_password.setOnFocusChangeListener(this);
        btnRegist.setOnClickListener(this);
        my_icon.setOnClickListener(this);

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirm_regist:
                registPresenter.requestRegist(et_username.getText().toString(),et_password.getText().toString());
                break;
            case R.id.my_icon:
                getPopupWindow();
                popupWindow.showAtLocation(findViewById(R.id.regist_container), Gravity.BOTTOM,0,0);
                break;
        }
    }


    @Override
    public void onFocusChange(View view, boolean b) {
        switch(view.getId()) {
            case R.id.et_username:
                focusWhich(0);
                break;
            case R.id.et_password:
                focusWhich(1);
                break;
            case R.id.et_passwordRe:
                focusWhich(2);
                break;
        }
    }
    public void focusWhich(int c) {
        for(int i = 0;i<icon.length;i++) {
            icon[i].setSelected(false);
        }
        icon[c].setSelected(true);
    }

    @Override
    public void afterRegist(UserResult userResult) {
        if(userResult.getCode().equals("1")) {
            UserEntity userData = userResult.getData();
            //请求更新
            updatePresenter.requestUpdate(new UserEntity(et_username.getText().toString(),et_nickname.getText().toString(),userData.getUuid(),userData.getUsername()),new File(cropHandler.getCropParams().uri.getPath()));
        }
        else
            Toast.makeText(this, userResult.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterUpdate(UserEntity entity) {
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        //请求登陆
        loginPresenter.requestLogin(et_username.getText().toString(),et_password.getText().toString());
    }

    @Override
    public void afterLogin(UserEntity entity) {


        //默认模式为0，即MODE_PRIVATE,有对应文件则读取，无则创建
        SharedPreferences sp =this.getSharedPreferences("login", Context.MODE_PRIVATE);
        //2.获取SharedPreferences的编辑器
        SharedPreferences.Editor editor = sp.edit();
        //3.向SharePreferences添加一个键值对
        editor.putBoolean("isLogin",true);
        editor.putString("username",et_username.getText().toString());
        editor.putString("password",et_password.getText().toString());
        //4.提交数据
        editor.commit();

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
            my_icon.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
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
            return RegistActivity.this;
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


