package com.kouxuan.bit_demo_android.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.kouxuan.bit_demo_android.R;
import com.kouxuan.bit_demo_android.bluetoothchat.BluetoothChatFragment;
import com.kouxuan.bit_demo_android.common.activities.SampleActivityBase;

import androidx.fragment.app.FragmentTransaction;


/**
 * Created by KouxuanNB on 2016/8/29.
 */
public class alarm_activity  extends SampleActivityBase {

    int color_green= Color.rgb(156,245,168);
    int color_yellow= Color.rgb(254,252,188);
    int color_red= Color.rgb(251,193,190);

    String text;

    String[] bed_numbers=new String[]{"床號：101-A","床號：101-B","床號：102-A","床號：102-B","床號：201-A","床號：201-B","床號：202-A","床號：201-B"};
    String[] patient_names=new String[]{"蔡茵雯","馬應酒","王景平","呂繡璉","陳小橘","陳隨扁","朱粒崙","賴沁徳"};
    String[] bit_components=new String[]{"點滴：生理食鹽水","點滴：葡萄糖水溶液","點滴：林格氏液","點滴：乳酸林格氏液","點滴：高張溶液","點滴：生理食鹽水","點滴：葡萄糖水溶液","點滴：林格氏液"};
    String[] doctor_names=new String[]{"主治醫師：陳當歸","主治醫師：徐大棗","主治醫師：黃耆","主治醫師：王肉桂","主治醫師：黃柏","主治醫師：蔡黨蔘","主治醫師：胡靈芝","主治醫師：黃連之"};
    int[] image_icon=new int[]{R.drawable.female_icon,R.drawable.male_icon,R.drawable.male_icon,R.drawable.female_icon,R.drawable.female_icon,R.drawable.male_icon,R.drawable.male_icon,R.drawable.male_icon};
    int color_back[] = {color_red,color_green,color_green,color_yellow,color_green,color_red,color_yellow,color_green};
    MyAdapter adapter=null;
    ListView ad;
    Button bb;
    private Context context;
    Bundle bundle;
    private boolean mLogShown;
    String name=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);
        context=this;
        ad=(ListView)findViewById(R.id.alarm_data);
        bb=(Button)findViewById(R.id.back_button);
        bb.setOnClickListener(listener1);
        context=this;
        adapter = new MyAdapter(this);


        bundle = getIntent().getExtras();
        if(bundle==null){

        }else {
            text = bundle.getString("read_message");

        }
        if (text != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("已達警戒值"+text)
                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog about_dialog = builder.create();
            about_dialog.show();
        }

        ad.setAdapter(adapter);
        ad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                openOptionsDialog(arg0.getItemAtPosition(arg2).toString());

            }
        });

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
//////////////////////////////////////////////////////////////////////////////////////////////////
    //    Intent intent = this.getIntent();
    //    name = intent.getStringExtra("name");
//////////////////////////////////////////////////////////////////////////////////////////////////

    /*    if(name!=null){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("已達警戒值")
                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog about_dialog = builder.create();
            about_dialog.show();
        }*/
    }
//////////////////////////////////////////////////////////////////////////////////////////////////

    private Button.OnClickListener listener1 = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            Intent intent1 = new Intent();
            intent1.setClass(context,main_screen.class);
            startActivity(intent1);
            finish();
        }
    };
    // 對話框所執行的 function
    private void openOptionsDialog(String xMessage) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("詳細資料");
        int number=0;
        //int number=Integer.valueOf(xMessage).intValue()-1;
        if(xMessage=="床號：101-A"){
            number=0;
        }else if(xMessage=="床號：101-B"){
            number=1;
        }else if(xMessage=="床號：102-A"){
            number=2;
        }else if(xMessage=="床號：102-B"){
            number=3;
        }else if(xMessage=="床號：201-A"){
            number=4;
        }else if(xMessage=="床號：201-B"){
            number=5;
        }else if(xMessage=="床號：202-A"){
            number=6;
        }else{
            number=7;
        }

        // 承接傳過來的字串，顯示在對話框之中
        dialog.setMessage(xMessage+"\n"+"病人:"+patient_names[number]+"\n"+bit_components[number]+"\n"+doctor_names[number]);
        // 設定 PositiveButton 也就是一般 確定 或 OK 的按鈕
        dialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
                // 當使用者按下確定鈕後所執行的動作
            }
        } );
        dialog.show();
    } //EOF openOptionsDialog





    public class MyAdapter extends BaseAdapter {
        private LayoutInflater myInflater;

        private class ViewHolder {
            TextView txtNumber,txtName,txtBit,txtDr;
            ImageView imv;
            // ListView lvv;
            public ViewHolder(TextView txtNumber, TextView txtName, TextView txtBit, TextView txtDr, ImageView imv){
                this.txtNumber = txtNumber;
                this.txtName = txtName;
                this.txtBit = txtBit;
                this.txtDr = txtDr;
                this.imv =  imv;
                //  this.lvv = listView;
            }
        }
        public  MyAdapter(Context c){
            myInflater= LayoutInflater.from(c);
        }
        @Override
        public int getCount() {
            return bed_numbers.length;
        }

        @Override
        public Object getItem(int i) {
            return bed_numbers[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if(convertView==null){
                convertView = myInflater.inflate(R.layout.data_layout, null);
                holder = new ViewHolder(
                        (TextView) convertView.findViewById(R.id.textView1),
                        (TextView) convertView.findViewById(R.id.textView2),
                        (TextView) convertView.findViewById(R.id.textView3),
                        (TextView) convertView.findViewById(R.id.textView4),
                        (ImageView) convertView.findViewById(R.id.imageView)
                );
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.txtName.setText(patient_names[position]);
            holder.txtNumber.setText(bed_numbers[position]);
            holder.txtBit.setText(bit_components[position]);
            holder.txtDr.setText(doctor_names[position]);
            holder.imv.setImageResource(image_icon[position]);

            if (position == 0) {
                convertView.setBackgroundColor(color_back[position]);
            }else if (position ==1){
                convertView.setBackgroundColor(color_back[position]);
            }else if (position ==2){
                convertView.setBackgroundColor(color_back[position]);
            }else if (position ==3){
                convertView.setBackgroundColor(color_back[position]);
            }else{
                convertView.setBackgroundColor(color_back[position]);
            }
            return convertView;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
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

}

