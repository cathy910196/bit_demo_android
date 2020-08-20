package com.kouxuan.bit_demo_android.main;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.kouxuan.bit_demo_android.R;
import com.kouxuan.bit_demo_android.bluetoothchat.BluetoothChatFragment;
import com.kouxuan.bit_demo_android.common.activities.SampleActivityBase;
import com.kouxuan.bit_demo_android.common.logger.Log;

import androidx.fragment.app.FragmentTransaction;

/**
 * Created by KouxuanNB on 2016/8/29.
 */
public class search_activity extends SampleActivityBase {

    private Button bb;
    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    private Context context;
    //搜尋用的
    private ListView lv;
    private Button bt1;
    private Button bt2;
    private EditText et;
    //資料庫
    SQLiteDatabase db;
    MyDBHelper DH;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        bb = (Button) findViewById(R.id.back_button);
        bb.setOnClickListener(listener1);

        lv = (ListView) findViewById(R.id.ListView01);
        bt1 = (Button) findViewById(R.id.btnSaerch);
        bt2 = (Button) findViewById(R.id.btnSearchAll);
        et = (EditText) findViewById(R.id.edtID);
        // 設定偵聽
        bt1.setOnClickListener(myListener);
        bt2.setOnClickListener(myListener);
        lv.setOnItemClickListener(listview01Listener);
        openDB();
        context = this;
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    //回前頁
    private Button.OnClickListener listener1 = new Button.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            /*Intent intent1 = new Intent();
            intent1.setClass(context, main_screen.class);
            startActivity(intent1);*/
            finish();
        }
    };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //資料庫

    private void openDB() {

        DH = new MyDBHelper(this);
        db = DH.getWritableDatabase();
        //  db.execSQL("ALTER  TABLE table01 ADD abc TEXT;");

        // db.execSQL(CREATE_TABLE);
    }

    private ListView.OnItemClickListener listview01Listener =
            new ListView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    cursor.moveToPosition(position);
                    Cursor c = get(id);
                    String s = "id=" + id + "\r\n" + c.getString(1) + "\r\n" + "姓名：" + c.getString(2) + "\r\n" + c.getString(3) + "\r\n";
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // db.close(); // 關閉資料庫
        DH.close();
    }

    private Button.OnClickListener myListener = new Button.OnClickListener() {
        public void onClick(View v) {
            Log.d("logtest", "search " + et.getText().toString());
            try {
                switch (v.getId()) {
                    case R.id.btnSaerch: {     // 查詢單筆
                        Log.d("logtest", "查詢單筆");
                        long id = Integer.parseInt(et.getText().toString());
                        cursor = get(id);
                        UpdateAdapter(cursor); // 載入資料表至 ListView 中
                        break;
                    }
                    case R.id.btnSearchAll: {    // 查詢全部
                        Log.d("logtest", "查詢全部");
                        cursor = getAll();       // 查詢所有資料
                        UpdateAdapter(cursor); // 載入資料表至 ListView 中
                        break;
                    }
                }
            } catch (Exception err) {
                Toast.makeText(getApplicationContext(), "查無此資料!", Toast.LENGTH_SHORT).show();
            }
        }
    };


    public void UpdateAdapter(Cursor cursor) {
        if (cursor != null && cursor.getCount() >= 0) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.data_layout_2, // 包含兩個資料項
                    cursor, // 資料庫的 Cursors 物件
                    new String[]{"_bed_numbers", "_patient_names", "_bit_components", "_doctor_name"},
                    new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4},
                    0); // adapter 行為最佳化
            lv.setAdapter(adapter); // 將adapter增加到listview01中
        }
    }

    public Cursor getAll() { // 查詢所有資料
        Cursor cursor = db.rawQuery("SELECT * FROM table01", null);
        Log.d("logtest", "search add" + cursor.getCount());
        return cursor; // 傳回欄位
    }

    public Cursor get(long rowId) throws SQLException { // 查詢指定 ID 的資料
        Cursor cursor = db.rawQuery("SELECT * FROM table01 WHERE _id=" + rowId, null);
        if (cursor.getCount() > 0)
            cursor.moveToFirst();
        else
            Toast.makeText(getApplicationContext(), "查無此筆資料!", Toast.LENGTH_SHORT).show();
        return cursor; // 傳回欄位
    }
}

