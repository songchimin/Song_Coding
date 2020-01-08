package com.study.android.wifi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.xmlpull.v1.XmlPullParserException;

public class PageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                try {
                    return new Fragment1();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            case 3:
                return new Fragment4();
            default:
                return null;


        }
    }

    public int getCount(){
        return mNumOfTabs;
    }
}
