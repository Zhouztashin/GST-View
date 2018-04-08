package com.zhouztashin.gst_view.tab;

import android.content.Context;

import com.zhouztashin.gst_view.R;

/**
 * Created by zhouztashin on 2018/3/26.
 * Tab构建器
 */

public class TabBuilder {

    private TabItem tabItem;

    public TabBuilder(){
        tabItem = new TabItem();
    }
    public   TabBuilder text(String text){
        tabItem.setText(text);
        return this;
    }
    public TabBuilder text(Context c,int textId){
        tabItem.setText(c.getString(textId));
        return this;
    }
    public TabBuilder icon(int iconId){
        tabItem.setIcon(iconId);
       return this;
    }
    public TabBuilder checkIcon(int checkIconId){
        tabItem.setCheckIcon(checkIconId);
        return this;
    }
    public TabItem build(){
        return tabItem;
    }
}
