package com.example.awesoman.bigproject3.view.fragment.shopping;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.awesoman.bigproject3.R;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Awesome on 2016/11/22.
 */

public class AdvHolder extends RecyclerView.ViewHolder {
    AdvViewPager advViewPager;
    CircleIndicator indicator;
    public AdvHolder(View itemView) {
        super(itemView);
        advViewPager = (AdvViewPager)itemView.findViewById(R.id.adv);
        indicator = (CircleIndicator)itemView.findViewById(R.id.indicator);
        indicator.setViewPager(advViewPager);

    }
}
