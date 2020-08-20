package com.kouxuan.bit_demo_android.main;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.kouxuan.bit_demo_android.R;
import com.kouxuan.bit_demo_android.common.logger.Log;

/**
 * Created by KouxuanNB on 2016/8/29.
 */
public class history_activity extends Activity {

    ListView hd;
    private Button bb;
    Context context;
    //資料庫
    SQLiteDatabase db;
    MyDBHelper DH;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        bb = (Button) findViewById(R.id.back_button);
        hd = (ListView) findViewById(R.id.history_data);
        context = this;
        bb.setOnClickListener(listener1);
        hd.setOnItemClickListener(listview01Listener);

       /* DH = new MyDBHelper(this.getBaseContext());
        db = DH.getWritableDatabase();*/
        openDB();

        // chechDBnull == false -> add new data
        if (chechDBnull()) {
            add("床號：101-A", "蔡茵雯", "點滴：生理食鹽水", "主治醫師：陳當歸");
            add("床號：101-B", "馬應酒", "點滴：葡萄糖水溶液", "主治醫師：徐大棗");
            add("床號：102-A", "王景平", "點滴：林格氏液", "主治醫師：黃耆");
            add("床號：102-B", "呂繡璉", "點滴：乳酸林格氏液", "主治醫師：王肉桂");
            add("床號：201-A", "陳小橘", "點滴：高張溶液", "主治醫師：黃柏");
            add("床號：201-B", "陳隨扁", "點滴：生理食鹽水", "主治醫師：蔡黨蔘");
            add("床號：202-A", "朱粒崙", "點滴：葡萄糖水溶液", "主治醫師：胡靈芝");
            add("床號：201-B", "賴沁徳", "點滴：林格氏液", "主治醫師：黃連之");
        }
        cursor = getAll();       // 查詢所有資料
        UpdateAdapter(cursor); // 載入資料表至 ListView 中
    }

    private boolean chechDBnull() {
        Cursor cursor = db.rawQuery("SELECT * FROM table01", null);
        Log.d("logtest", "cursor.getCount() = " + cursor.getCount());
        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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


    private void add(String _bed_numbers, String _patient_names, String _bit_components, String _docctor_name) {
        // db = DH.getWritableDatabase();
        Log.d("logtest", "add func");
        ContentValues values = new ContentValues();
        values.put("_bed_numbers", _bed_numbers.toString());
        values.put("_patient_names", _patient_names.toString());
        values.put("_bit_components", _bit_components.toString());
        values.put("_doctor_name", _docctor_name.toString());
        
        db.insert("table01", null, values);
    }

    private void openDB() {

        DH = new MyDBHelper(this);
        db = DH.getWritableDatabase();
/*       db.execSQL("ALTER TABLE table01 ADD alarm_message TEXT");
        db.execSQL("ALTER TABLE table01 ADD _patient_names TEXT");
       db.execSQL("ALTER TABLE table01 ADD _bit_components TEXT");
       db.execSQL("ALTER TABLE table01 ADD _doctor_name TEXT");*/

        // db.execSQL("ALTER TABLE table01 ADD only_test TEXT");
        //db.execSQL("ALTER TABLE table01 ADD test TEXT");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // db.close(); // 關閉資料庫
        DH.close();
    }


    public void UpdateAdapter(Cursor cursor) {
        if (cursor != null && cursor.getCount() >= 0) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.data_layout_2, // 包含兩個資料項
                    cursor, // 資料庫的 Cursors 物件
                    new String[]{"_bed_numbers", "_patient_names", "_bit_components", "_doctor_name"}, // pname、price 欄位
                    new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4}, // 與 pname、price對應的元件
                    0); // adapter 行為最佳化
            hd.setAdapter(adapter); // 將adapter增加到listview01中
        }
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


    public Cursor getAll() { // 查詢所有資料
        Cursor cursor = db.rawQuery("SELECT * FROM table01", null);
        return cursor; // 傳回_id、pname、price欄位
    }

    public Cursor get(long rowId) throws SQLException { // 查詢指定 ID 的資料
        Cursor cursor = db.rawQuery("SELECT * FROM table01 WHERE _id=" + rowId, null);
        if (cursor.getCount() > 0)
            cursor.moveToFirst();
        else
            Toast.makeText(getApplicationContext(), "查無此筆資料!", Toast.LENGTH_SHORT).show();
        return cursor; // 傳回_id、pname、price欄位
    }
}

