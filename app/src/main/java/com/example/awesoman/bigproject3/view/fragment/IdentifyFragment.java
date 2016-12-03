package com.example.awesoman.bigproject3.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.awesoman.bigproject3.view.activity.User.LoginActivity;
import com.example.awesoman.bigproject3.MyApplication;
import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.UserEntity;
import com.example.awesoman.bigproject3.network.ShopAPI;
import com.example.awesoman.bigproject3.view.activity.User.PersonalActicity;
import com.example.awesoman.bigproject3.view.activity.Goods.PersonalGoods.PersonalGoodsActivity;
import com.example.awesoman.bigproject3.view.activity.User.RegistActivity;
import com.example.awesoman.bigproject3.view.activity.Goods.UpLoadgoods.UpLoadGoodsAcitivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Awesome on 2016/11/16.
 */

public class IdentifyFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.login)
    TextView tv_login;
    @Bind(R.id.regist)
    TextView tv_regist;
    @Bind(R.id.personal_msg)
    View personal_msg;
    @Bind(R.id.my_icon)
    ImageView my_icon;
    @Bind(R.id.before_login)
    View before_login;
    @Bind(R.id.after_login)
    TextView after_login;
    @Bind(R.id.upload_goods)
    View upload_goods;
    @Bind(R.id.personal_goods)
    View personal_goods;


    MyApplication myApplication;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identity,null);
//        CircleImageView = (MyImageView)view.findViewById(R.id.my_icon);
        ButterKnife.bind(this,view);
        initView();
        setListenrer();
        return view;
    }
    public void initView(){
        myApplication = (MyApplication)getActivity().getApplication();
    }


    public void setListenrer(){
        tv_login.setOnClickListener(this);
        my_icon.setOnClickListener(this);
        personal_msg.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
        upload_goods.setOnClickListener(this);
        personal_goods.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login:
            case R.id.personal_msg:
            case R.id.my_icon:
                if(myApplication.getUser()==null)
                    startActivity(new Intent().setClass(getContext(), LoginActivity.class));
                else
                    startActivity(new Intent().setClass(getContext(), PersonalActicity.class));
                break;
            case R.id.regist:
                startActivity(new Intent().setClass(getContext(),RegistActivity.class));
                break;
            case R.id.upload_goods:
                startActivity(new Intent().setClass(getContext(), UpLoadGoodsAcitivity.class));
                break;
            case R.id.personal_goods:
                startActivity(new Intent().setClass(getContext(), PersonalGoodsActivity.class));
                break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserEntity user =null;
        if(((user =((MyApplication)getActivity().getApplication()).getUser()))!=null) {
            ImageLoader.getInstance().displayImage(ShopAPI.IMAGE_URL + user.getOther(), my_icon);
//            my_icon.setImageBitmap(BitmapFactory.decodeFile(((MyApplication)getActivity().getApplication()).getIcon_path()));
            after_login.setText(user.getNickname());
            after_login.setVisibility(View.VISIBLE);
            before_login.setVisibility(View.GONE);
        }
        else{
            my_icon.setImageResource(R.drawable.person_icon);
            after_login.setVisibility(View.GONE);
            before_login.setVisibility(View.VISIBLE);
        }
    }
}
