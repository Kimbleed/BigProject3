package com.example.awesoman.bigproject3.view.activity.Goods.ImageLibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.event.ImageChooseEvent;
import com.example.awesoman.bigproject3.view.activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * Created by Awesome on 2016/12/1.
 */

public class ImageViewPagerActivity extends BaseActivity {
    @Bind(R.id.image_library_viewpager)
    ViewPager viewPager;
    @Bind(R.id.image_library_viewpager_checkbox)
    CheckBox checkBox;
    @Bind(R.id.choose_finish)
    Button btn_finish;
    @Bind(R.id.choose_surface)
    Button btn_surface;

    ArrayList<Boolean> flags =new ArrayList<>();
    ArrayList<String> list ;
    ArrayList<String> choseInViewPager = new ArrayList<>();
    int count = 0;

    private MyPagerAdapter myPagerAdapter;

    @Override
    public void setIsToolbar() {
        isBackHome = true;
    }

    @Override
    public int setContentView() {
        return R.layout.activity_image_library_viewpager;
    }

    @Override
    public void initView() {
        //获取从ImageLibraryActivity中传过来的图片库uri集,
        // 也可以用ImageLibraryContentProvider,但怕别的程序对本地图片库进行增删
        //故采用上一个Activity(ImageLibraryActivity)中拥有的uri集
        list = getIntent().getStringArrayListExtra("imageLibrary");
        //获取在ImageLibraryActivity中点击的图片,在本Activity下,定位Viewpager
        int position = getIntent().getIntExtra("position",-1);
        //获取已选择的图片的index数组
        int[] hasChose = getIntent().getIntArrayExtra("hasChose");

        //实例化ViewPager 的适配器myPagerAdapter
        myPagerAdapter = new MyPagerAdapter();
        //装载本地图片库的uri集到myPagerAdapter
        myPagerAdapter.setImgPaths(list);

        //对图片是否已选择分配flag
        for(int i = 0;i<list.size();i++){
            flags.add(false);
        }
        for(int i =0;i<hasChose.length;i++){
            flags.set(hasChose[i],true);
            count++;
        }

        //为ViewPager配置viewPagerAdapter
        viewPager.setAdapter(myPagerAdapter);
        //ViewPager定位
        viewPager.setCurrentItem(position);
        //ViewPager设置监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("flags.size",flags.size()+"");
                checkBox.setChecked(flags.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //checkbox根据定位，设置
        checkBox.setChecked(flags.get(position));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setListener() {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    flags.set(viewPager.getCurrentItem(),checkBox.isChecked());
//                choseInViewPager.add(list.get(viewPager.getCurrentItem()));
                    EventBus.getDefault().post(new ImageChooseEvent(list.get(viewPager.getCurrentItem()), checkBox.isChecked(),false));
                    if (checkBox.isChecked()) {
                        if (count >= 8) {
                            checkBox.setChecked(!checkBox.isChecked());
                            Toast.makeText(ImageViewPagerActivity.this, "只能选取8张图片", Toast.LENGTH_LONG).show();
                        }
                        else {
                            count++;
                        }
                    }
                    else
                        count--;
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_surface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count >= 8) {
                    Toast.makeText(ImageViewPagerActivity.this, "只能选取8张图片", Toast.LENGTH_LONG).show();
                }
                else{
                    checkBox.setChecked(true);
                    EventBus.getDefault().post(new ImageChooseEvent(list.get(viewPager.getCurrentItem()), checkBox.isChecked(), true));
                }
            }
        });
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
            Log.i("ImageViewPagerActivity",imgPaths.get(position));
            imageLoader.displayImage("file:///"+imgPaths.get(position),v);
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
            container.removeView((View)object);
        }
    }

}
