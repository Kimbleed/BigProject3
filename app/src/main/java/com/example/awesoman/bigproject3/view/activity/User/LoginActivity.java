package com.example.awesoman.bigproject3.view.activity.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awesoman.bigproject3.view.activity.MainActivity;
import com.example.awesoman.bigproject3.MyApplication;
import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.UserEntity;
import com.example.awesoman.bigproject3.presenter.identify.user.LoginPresenter;
import com.example.awesoman.bigproject3.presenter.identify.user.LoginView;
import com.example.awesoman.bigproject3.view.activity.BaseActivity;

import butterknife.Bind;

/**
 * Created by Awesome on 2016/11/24.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginView,View.OnFocusChangeListener{

    @Bind(R.id.icon_username)
    TextView icon_username;
    @Bind(R.id.icon_password)
    TextView icon_password;
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.confirm_login)
    Button btn;


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    LoginPresenter loginPresenter;

    @Override
    public void setIsToolbar() {
        super.isBackHome = true;
    }

    @Override
    public int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(){

        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
    }


    @Override
    public void setListener(){


        //设置EditText的文字改变监听
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_username.getText().toString().length()>0&&et_password.getText().toString().length()>0){
                    btn.setBackgroundResource(R.color.orange);
                }
                else
                    btn.setBackgroundResource(R.color.orangeAlpha);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        et_username.addTextChangedListener(textWatcher);
        et_password.addTextChangedListener(textWatcher);

        //设置EditText的焦点效果
        et_username.setOnFocusChangeListener(this);
        et_password.setOnFocusChangeListener(this);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.confirm_login:
                loginPresenter.requestLogin(et_username.getText().toString(),et_password.getText().toString());
                break;

        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch(view.getId()) {
            case R.id.et_username:
                icon_username.setSelected(true);
                icon_password.setSelected(false);
                break;
            case R.id.et_password:
                icon_password.setSelected(true);
                icon_username.setSelected(false);
                break;
        }
    }

    @Override
    public void afterLogin(UserEntity entity) {
        if(entity!=null) {
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            ((MyApplication)getApplication()).setUser(entity);

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
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
