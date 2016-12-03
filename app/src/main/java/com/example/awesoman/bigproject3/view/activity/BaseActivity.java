package com.example.awesoman.bigproject3.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.awesoman.bigproject3.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Awesome on 2016/11/28.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    public int RESULT_IMG_PATHS = 0x1000;
    public int RESULT_CAMERA = 0x2000;


    public Menu menu;

    protected Boolean isBackHome;
    protected Boolean isMenu = false;

    @Override
    public void onContentChanged() {
        ButterKnife.bind(this);
        super.onContentChanged();
        //设置toolbar
        setIsToolbar();
        initToolbar(isBackHome);
        //初始化
        initView();
        //设置监听器
        setListener();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
    }

    public void initToolbar(boolean isBackHome){
        setSupportActionBar(toolbar);
        Log.i("initToolbar",getSupportActionBar()==null?"null":"notNull");
        getSupportActionBar().setTitle("");
        if(isBackHome){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.type_menu:
                popup();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(isMenu)
            inflater.inflate(R.menu.menu_personal_goods,menu);
        else{
            inflater.inflate(R.menu.menu_null,menu);
        }
        this .menu =menu;
        Log.i("1menu",menu==null?"null":"notNull");

        return true;
    }


    abstract  public void setIsToolbar();
    abstract public int setContentView();
    abstract public void initView();
    abstract public void setListener();
    public void popup(){

    }
}
