package com.example.awesoman.bigproject3.view.activity.User;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.awesoman.bigproject3.view.activity.MainActivity;
import com.example.awesoman.bigproject3.MyApplication;
import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.UserEntity;
import com.example.awesoman.bigproject3.presenter.identify.user.UpdatePresenter;
import com.example.awesoman.bigproject3.presenter.identify.user.UpdateView;
import com.example.awesoman.bigproject3.view.activity.BaseActivity;

import java.io.File;

import butterknife.Bind;

/**
 * Created by Awesome on 2016/11/28.
 */

public class ReNameActivity extends BaseActivity implements View.OnClickListener,UpdateView{

    @Bind(R.id.et_rename)
    EditText et_rename;
    @Bind(R.id.clear)
    Button btn_clear;
    @Bind(R.id.rename_finish)
    Button rename_finish;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    UpdatePresenter updatePresenter;


    @Override
    public void setIsToolbar() {
        super.isBackHome = true;
    }

    @Override
    public int setContentView() {
        return R.layout.activity_reaname;
    }

    public void initView(){
        updatePresenter = new UpdatePresenter();
        updatePresenter.attachView(this);

        String nickname = getIntent().getStringExtra("nickname");
        et_rename.setText(nickname);
    }

    public void setListener(){
        btn_clear.setOnClickListener(this);
        rename_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clear:
                et_rename.setText("");
                break;
            case R.id.rename_finish:
                UserEntity user = ((MyApplication)getApplication()).getUser();
                user.setNickname(et_rename.getText().toString());
                updatePresenter.requestUpdate(user,new File(((MyApplication)getApplication()).getIcon_path()));
                break;
        }
    }

    @Override
    public void afterUpdate(UserEntity entity) {
        Toast.makeText(this,"修改成功",Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ((MyApplication)getApplication()).setUser(entity);
    }
}
