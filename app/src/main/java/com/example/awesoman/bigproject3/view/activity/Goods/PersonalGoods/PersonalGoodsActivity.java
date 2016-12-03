package com.example.awesoman.bigproject3.view.activity.Goods.PersonalGoods;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;
import com.example.awesoman.bigproject3.model.event.PersonalGoodEvent;
import com.example.awesoman.bigproject3.presenter.shop.GoodsPresenter;
import com.example.awesoman.bigproject3.presenter.shop.GoodsView;
import com.example.awesoman.bigproject3.view.PopupWindow.PersonalGoodsPopupWindow;
import com.example.awesoman.bigproject3.view.activity.BaseActivity;

import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * Created by Awesome on 2016/11/28.
 */

public class PersonalGoodsActivity extends BaseActivity implements GoodsView,View.OnClickListener,PersonalGoodsAdapter.DeleteListener{

    PopupWindow popupWindow;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personal_goods_recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.select_all)
    Button selectAll;
    @Bind(R.id.delete_selected)
    Button deleteSelected;
    @Bind(R.id.btn_container)
    View btn_container;
    PersonalGoodsAdapter personalGoodsAdapter;
    GoodsPresenter goodsPresenter;
    Boolean isPopupBtn = false;
    float yPopupBtn = -1;
    float heightPopupBtn = -1;

    @Override
    protected void onResume() {
        super.onStart();
        Log.i("btn_containerGetY",btn_container.getY()+"");

    }

    @Override
    public void setIsToolbar() {
        super.isBackHome = true;
        super.isMenu = true;
    }

    @Override
    public int setContentView() {
        return R.layout.activity_goods_type;
    }
    @Override
    public void initView(){
        EventBus.getDefault().register(this);
        goodsPresenter = new GoodsPresenter();
        goodsPresenter .attachView(this);

        personalGoodsAdapter = new PersonalGoodsAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(personalGoodsAdapter);

//        过场动画
        Animation rightIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        LayoutAnimationController lc = new LayoutAnimationController(rightIn);
        //设置动画组延迟
        lc.setDelay(0.3f);
        //设置动画组播放次序
        lc.setOrder(LayoutAnimationController.ORDER_RANDOM);
        recyclerView.setLayoutAnimation(lc);
        yPopupBtn = btn_container.getY();
//        popupBtn();

    }

    @Override
    public void setListener() {
        selectAll.setOnClickListener(this);
        deleteSelected.setOnClickListener(this);
        deleteSelected.setClickable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_all:
                Log.i("selectAll","all");
                personalGoodsAdapter.setAll();
                break;
            case R.id.delete_selected:
                Log.i("delete_selected","delete");
                personalGoodsAdapter.removeData();
                break;
        }
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
    public void initPopuptWindow(){
        popupWindow = new PersonalGoodsPopupWindow(this).getIconPopupWindow();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void popup() {
        super.popup();
        getPopupWindow();
        popupWindow.showAtLocation(findViewById(R.id.container_goods_type), Gravity.BOTTOM,0,0);
    }

    @Override
    public void hideRefresh() {

    }

    @Override
    public void addRefreshData(List<GoodsEntity> data) {
        personalGoodsAdapter.setData(data);
        personalGoodsAdapter.notifyDataSetChanged();
    }

    @Override
    public void addData(List<GoodsEntity> data) {

    }

    @Override
    public void canDelete(int count) {
        Log.i("canDeleteImp",count+"");
        if(count>0){
            deleteSelected.setText("删除"+"("+ count +")");
            deleteSelected.setClickable(true);
            deleteSelected.setTextColor(Color.RED);
        }
        else {
            deleteSelected.setText("删除");
            deleteSelected.setClickable(false);
            deleteSelected.setTextColor(Color.GRAY);
        }

    }

    @Override
    public void popupBtn() {
        if(yPopupBtn <=0 && heightPopupBtn<=0) {
            yPopupBtn = btn_container.getTop();
            heightPopupBtn = btn_container.getHeight();
        }
        Log.i("y",yPopupBtn+"");
        Log.i("height",heightPopupBtn+"");
        ValueAnimator valueAnimator;
        Log.i("2menu",menu==null?"null":"notNull");
        if(isPopupBtn) {
            valueAnimator = ObjectAnimator.ofFloat(btn_container, "y", yPopupBtn, yPopupBtn+heightPopupBtn);
            menu.getItem(0).setActionView(null);
            yPopupBtn -=heightPopupBtn;
        }
        else {
            valueAnimator = ObjectAnimator.ofFloat(btn_container, "y", yPopupBtn, yPopupBtn-heightPopupBtn);
            menu.getItem(0).setActionView(R.layout.menu_item_cancle);
            yPopupBtn +=heightPopupBtn;
        }
        valueAnimator.setEvaluator(new FloatEvaluator());
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(200);
        valueAnimator.start();
        isPopupBtn = !isPopupBtn;
    }

    public void onEventMainThread(PersonalGoodEvent event) {
        Log.i("onEventMainThread",event.getMsg());
        goodsPresenter.refreshData(1,event.getMsg());
//        startActivity(new Intent().setClass(this, TypeActivity.class).putExtra("type",event.getType()));
    }
}
