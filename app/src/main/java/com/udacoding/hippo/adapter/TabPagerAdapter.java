package com.udacoding.hippo.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.udacoding.hippo.fragment.AppFragment;
import com.udacoding.hippo.fragment.ContactFragment;
import com.udacoding.hippo.fragment.HomeFragment;
import com.udacoding.hippo.fragment.ProdukFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){

            case 0 : return new HomeFragment();
            case 1 : return new AppFragment();
            case 2 : return new ProdukFragment();
            case 3 : return new ContactFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){

            case 0 : return "Home";
            case 1 : return "App";
            case 2 : return "produk";
            case 3 :return "Contact";
            default:
                return null;


        }
    }
}
