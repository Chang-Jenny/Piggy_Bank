package com.example.piggybank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.Calendar;

public class record extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    AdapterView.OnItemClickListener,
                    View.OnClickListener,
                    DatePickerDialog.OnDateSetListener{

    DrawerLayout drawerLayout;
    NavigationView navigation;
    Toolbar toolbar;

    static final String DB_NAME = "recordDB"; //資料庫名稱
    static final String TB_NAME = "recordlist"; //資料表名稱
    static final String[] FROM = new String[] {"day","name","type","category","price"};
    static final int MAX=100;
    SQLiteDatabase db;
    Cursor cur;
    SimpleCursorAdapter adapter;

    //畫面元件
    TextView txv_day, txv_time, txv_type;
    EditText et_name,et_price;
    RadioGroup rad_type;
    RadioButton input,output;
    Button btn_insert,btn_update,btn_delete;
    Spinner category;

    ListView LV;
    Calendar c;
    String T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation = (NavigationView) findViewById(R.id.navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Navigation
        navigation.setNavigationItemSelectedListener(this);
        //toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //畫面元件
        txv_day = (TextView) findViewById(R.id.txv_day);
        txv_time = (TextView) findViewById(R.id.txv_time);
        et_name = (EditText) findViewById(R.id.et_name);
        et_price = (EditText) findViewById(R.id.et_price);
        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        rad_type = (RadioGroup) findViewById(R.id.rad_type);
        input = (RadioButton) findViewById(R.id.input);
        output = (RadioButton) findViewById(R.id.output);
        txv_type = (TextView) findViewById(R.id.txv_type);
        category = (Spinner) findViewById(R.id.category);

        txv_time.setText("請點選以選擇日期");
        c = Calendar.getInstance();
        txv_time.setOnClickListener(this);

        //txv_type.setOnClickListener(this);

        //開啟或建立資料庫
        db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);

        //建立資料表
        String createTable = "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "day VARCHAR(32)," +
                "name VARCHAR(32)," +
                "type VARCHAR(32)," +
                "category VARCHAR(32),"+
                "price VARCHAR(32))";
        db.execSQL(createTable);

        //查詢資料
        cur = db.rawQuery("SELECT * FROM "+TB_NAME,null);

        //空的則寫入測試資料
        if(cur.getCount()==0){
            addData("2022/1/10","productA","收入","薪資","1200");
            addData("2022/1/10","productB","支出","食","350");
        }

        //建立adapter物件
        adapter=new SimpleCursorAdapter(this,
                R.layout.item,cur,FROM,
                new int[] {R.id.txvDay,R.id.txvName,R.id.txvType,R.id.txvCategory,R.id.txvPrice},0);

        LV = (ListView) findViewById(R.id.LV);
        LV.setAdapter(adapter);
        LV.setOnItemClickListener(this);
        requery();

        txv_time.setText("請點選以選擇日期");

    }
    @Override
    public void onClick(View v) {
        if(v==txv_time){
            new DatePickerDialog(this,this,
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    @Override
    public void onDateSet(DatePicker v, int y, int m, int d) {
        txv_time.setText(y+"/"+(m+1)+"/"+d);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                Intent it1 = new Intent(this, Home.class);
                startActivity(it1);
                finish();
                break;
            case R.id.menu_record:
                Toast.makeText(this, "每日記帳", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_calculate:
                Toast.makeText(this, "每日查詢", Toast.LENGTH_SHORT).show();
                Intent it2 = new Intent(this, calculate.class);
                startActivity(it2);
                finish();
                break;
            case R.id.menu_remind:
                Toast.makeText(this, "繳費備忘錄", Toast.LENGTH_SHORT).show();
                Intent it3 = new Intent(this, remind.class);
                startActivity(it3);
                finish();
                break;
            case R.id.menu_pig:
                Toast.makeText(this, "養小豬", Toast.LENGTH_SHORT).show();
                Intent it4 = new Intent(this, pig.class);
                startActivity(it4);
                finish();
                break;
            case R.id.menu_search:
                Toast.makeText(this, "結算總表", Toast.LENGTH_SHORT).show();
                Intent it6 = new Intent(this, search.class);
                startActivity(it6);
                finish();
                break;
            case R.id.menu_copyright:
                Toast.makeText(this, "CopyRight", Toast.LENGTH_SHORT).show();
                Intent it5 = new Intent(this, copyright.class);
                startActivity(it5);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                Intent it1 = new Intent(this, Home.class);
                startActivity(it1);
                finish();
                break;
            case R.id.menu_record:
                Toast.makeText(this, "每日記帳", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_calculate:
                Toast.makeText(this, "每日查詢", Toast.LENGTH_SHORT).show();
                Intent it2 = new Intent(this, calculate.class);
                startActivity(it2);
                finish();
                break;
            case R.id.menu_remind:
                Toast.makeText(this, "繳費備忘錄", Toast.LENGTH_SHORT).show();
                Intent it3 = new Intent(this, remind.class);
                startActivity(it3);
                finish();
                break;
            case R.id.menu_pig:
                Toast.makeText(this, "養小豬", Toast.LENGTH_SHORT).show();
                Intent it4 = new Intent(this, pig.class);
                startActivity(it4);
                finish();
                break;
            case R.id.menu_search:
                Toast.makeText(this, "結算總表", Toast.LENGTH_SHORT).show();
                Intent it6 = new Intent(this, search.class);
                startActivity(it6);
                finish();
                break;
            case R.id.menu_copyright:
                Toast.makeText(this, "CopyRight", Toast.LENGTH_SHORT).show();
                Intent it5 = new Intent(this, copyright.class);
                startActivity(it5);
                finish();
                break;
        }
        return true;
    }

    // spinner判斷
    public String enterJudge(){
        String[] categories = getResources().getStringArray(R.array.catrgory);
        int index = category.getSelectedItemPosition();
        return categories[index];
    }

    //新增
    private void addData(String day, String name, String type,String cat, String price) {
        ContentValues cv = new ContentValues(5);
        cv.put(FROM[0],day);
        cv.put(FROM[1],name);
        cv.put(FROM[2],type);
        cv.put(FROM[3],cat);
        cv.put(FROM[4],price);
        clean();
        db.insert(TB_NAME,null,cv);
    }

    //更新
    private void update(String day, String name, String type, String price,String cat, int id){
        ContentValues cv = new ContentValues(5);
        cv.put(FROM[0],day);
        cv.put(FROM[1],name);
        cv.put(FROM[2],type);
        cv.put(FROM[3],cat);
        cv.put(FROM[4],price);
        clean();
        db.update(TB_NAME,cv,"_id="+id,null);
    }

    //重新查詢
    private void requery() {
        cur=db.rawQuery("SELECT * FROM "+ TB_NAME,null);
        adapter.changeCursor(cur);
        if(cur.getCount()==MAX){
            btn_insert.setEnabled(false);
        }else{
            btn_insert.setEnabled(true);
        }

        btn_update.setEnabled(false);
        btn_delete.setEnabled(false);
    }

    //clean editText data
    public void clean(){
        txv_time.setText("請點選以選擇日期");
        //txv_time.setText("");
        et_name.setText("");
        et_price.setText("");
        T="";
        rad_type.clearCheck();
        txv_type.setText("");
    }

    @SuppressLint("Range")
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        cur.moveToPosition(position);
        txv_time.setText(cur.getString(cur.getColumnIndex(FROM[0])));
        et_name.setText(cur.getString(cur.getColumnIndex(FROM[1])));
        T=cur.getString(cur.getColumnIndex(FROM[2]));
        txv_type.setText(T);
        et_price.setText(cur.getString(cur.getColumnIndex(FROM[4])));

        String whichCategory = cur.getString(cur.getColumnIndex(FROM[3]));
        String[] categories = getResources().getStringArray(R.array.catrgory);
        int index = Arrays.asList(categories).indexOf(whichCategory);
        category.setSelection(index);

        if(T=="收入"){
            rad_type.addView(input);
            input.setChecked(true);
        }
        if(T=="支出"){
            rad_type.check(R.id.output);
        }
        btn_update.setEnabled(true);
        btn_delete.setEnabled(true);
    }

    public void oninsert (View v){
        switch(rad_type.getCheckedRadioButtonId()){
            case R.id.input:
                T="收入";
                break;
            case R.id.output:
                T="支出";
                break;
        }
        rad_type.clearCheck();
        String dayStr = txv_time.getText().toString().trim();
        String nameStr = et_name.getText().toString().trim();
        String typeStr = T;
        String cat = enterJudge();
        String priceStr = et_price.getText().toString().trim();
        //|| typeStr.length()==0
        if(dayStr.length()==0 || nameStr.length()==0 || typeStr.length()==0 || priceStr.length()==0)
            return;

        if(v.getId()==R.id.btn_update)
            update(dayStr,nameStr,typeStr,cat,priceStr,cur.getInt(0));
        else
            addData(dayStr,nameStr,typeStr,cat,priceStr);

        requery();
    }
    public void ondelete(View v){
        db.delete(TB_NAME, "_id="+cur.getInt(0),null);
        clean();
        requery();
    }


}