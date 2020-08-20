package com.kouxuan.bit_demo_android.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kouxuan.bit_demo_android.R;

/**
 * Created by KouxuanNB on 2016/8/29.
 */
public class main_screen extends Activity {
    ImageButton search;
    ImageButton setting;
    ImageButton alarm;
    ImageButton history;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_layout);
        context=this;
        search=(ImageButton)findViewById(R.id.search_button);
        setting=(ImageButton)findViewById(R.id.setting_button);
        alarm=(ImageButton)findViewById(R.id.alarm_button);
        history=(ImageButton)findViewById(R.id.history_button);

       search.setOnClickListener(listener1);
       setting.setOnClickListener(listener3);
        alarm.setOnClickListener(listener2);
        history.setOnClickListener(listener4);
    }

    private Button.OnClickListener listener1 = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            Intent intent3 = new Intent();
            intent3.setClass(context,com.kouxuan.bit_demo_android.main.search_activity.class);
            startActivity(intent3);
            finish();
        }
    };
    private Button.OnClickListener listener2 = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            Intent intent3 = new Intent();
            intent3.setClass(context,com.kouxuan.bit_demo_android.bluetoothchat.MainActivity.class);
            startActivity(intent3);
            finish();
        }
    };

    private Button.OnClickListener listener3 = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            Intent intent3 = new Intent();
            intent3.setClass(context,com.kouxuan.bit_demo_android.main.alarm_activity.class);
            startActivity(intent3);
            finish();
        }
    };
    private Button.OnClickListener listener4 = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            Intent intent3 = new Intent();
            intent3.setClass(context,com.kouxuan.bit_demo_android.main.history_activity.class);
            startActivity(intent3);
            finish();
        }
    };
}

