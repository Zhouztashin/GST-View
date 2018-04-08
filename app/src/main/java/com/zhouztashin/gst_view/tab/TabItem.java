package com.zhouztashin.gst_view.tab;

/**
 * Created by zhouztashin on 2018/3/26.
 * 底部导航栏项
 */

public class TabItem {
    private int checkIcon;
    private int icon;
    private String text;
    private int id;


    public int getId() {
        return id;
    }
;
    public void setId(int id){
        this.id = id;
    }
    public int getCheckIcon() {
        return checkIcon;
    }

    public void setCheckIcon(int checkIcon) {
        this.checkIcon = checkIcon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "TabItem{" +
                "checkIcon=" + checkIcon +
                ", icon=" + icon +
                ", text='" + text + '\'' +
                ", id=" + id +
                '}';
    }
}
