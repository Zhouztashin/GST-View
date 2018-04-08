package com.zhouztashin.gst_view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zhouztashin.gst_view.tab.TabBuilder
import com.zhouztashin.gst_view.tab.TabCheckListener
import com.zhouztashin.gst_view.tab.TabGroupView

/**
 * Created by zhouztashin on 2018/3/26.
 */
class DebugActivity :AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_debug)
        var tabView = findViewById(R.id.tabView) as TabGroupView
        var tabItem = TabBuilder().text("附近").icon(R.drawable.ic_road_near).
                checkIcon(R.drawable.ic_road_near_select).build()
        tabView.addTabItem(tabItem)
         tabItem = TabBuilder().text("附近2").icon(R.drawable.ic_road_near).
                checkIcon(R.drawable.ic_road_near_select).build()
        tabView.addTabItem(tabItem)
         tabItem = TabBuilder().text("附近3").icon(R.drawable.ic_road_near).
                checkIcon(R.drawable.ic_road_near_select).build()
        tabView.addTabItem(tabItem)
         tabItem = TabBuilder().text("附近4").icon(R.drawable.ic_road_near).
                checkIcon(R.drawable.ic_road_near_select).build()
        tabView.addTabItem(tabItem)

        tabView.setTabCheckListener(TabCheckListener {
            index, tabView ->
            L.log("选择了 "+index)

        } )
      /*  var setting = TabGroupSetting()
        setting.space = 10
        setting.textCheckColor = R.color.notification_icon_bg_color
        setting.textColor = R.color.notification_icon_bg_color
        setting.textSize = 13
        tabView.setTabGroupSetting(setting)*/
        tabView.addTabItem(tabItem)
        tabView.setDefaultTab(0)

    }
}
