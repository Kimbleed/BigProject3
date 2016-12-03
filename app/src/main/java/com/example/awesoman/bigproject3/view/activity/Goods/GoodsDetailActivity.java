package com.example.awesoman.bigproject3.view.activity.Goods;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;
import com.example.awesoman.bigproject3.network.ShopAPI;
import com.example.awesoman.bigproject3.presenter.shop.GoodsDetailPresenter;
import com.example.awesoman.bigproject3.presenter.shop.GoodsDetailView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Awesome on 2016/11/18.
 */

public class GoodsDetailActivity extends AppCompatActivity implements GoodsDetailView{

    @Bind(R.id.detail_viewpager)
    ViewPager viewPager;




    private GoodsDetailPresenter goodsDetailPresenter;
    private GoodsEntity good;

    private MyPagerAdapter myPagerAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        initView();
    }
    public void initView(){
        myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);
        good = (GoodsEntity)getIntent().getSerializableExtra("good");
        goodsDetailPresenter = new GoodsDetailPresenter();
        goodsDetailPresenter.attachView(this);
        goodsDetailPresenter.getImgPaths(good.getUuid());
    }

    @Override
    public void showImg(List<String> bitmapPaths) {
        myPagerAdapter.setImgPaths(bitmapPaths);
        myPagerAdapter.notifyDataSetChanged();
    }

    class MyPagerAdapter extends PagerAdapter {
        ImageLoader imageLoader;
        List<String> imgPaths = new ArrayList<>();
        List<View> viewList = new ArrayList<>();


        public MyPagerAdapter() {
            imageLoader = ImageLoader.getInstance();
        }

        public List<String> getImgPaths() {
            return imgPaths;
        }

        public void setImgPaths(List<String> imgPaths) {
            this.imgPaths = imgPaths;
        }

        /**
         *  长度
         * @return
         */
        @Override
        public int getCount() {
            return imgPaths.size();
        }

        /**
         * 添加View
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView v = new ImageView(container.getContext());
            Log.i("GoodsDetailActivity",imgPaths.get(position));
            imageLoader.displayImage(ShopAPI.IMAGE_URL+imgPaths.get(position),v);
            container.addView(v);
            viewList.add(v);
            return v;
        }

        /**
         *
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;//判定地址是否相同
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }

}
