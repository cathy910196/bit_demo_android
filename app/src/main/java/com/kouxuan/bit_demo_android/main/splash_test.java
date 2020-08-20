package com.kouxuan.bit_demo_android.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kouxuan.bit_demo_android.bluetoothchat.R;

/**
 * Created by KouxuanNB on 2016/8/29.
 */
public class splash_test  extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 1000; // 延遲1秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(splash_test.this,
                        main_screen.class);
                splash_test.this.startActivity(mainIntent);
                splash_test.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);

    }
}
