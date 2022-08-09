package com.example.piggybank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class calculate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigation;
    Toolbar toolbar;

    static final String DB_NAME = "recordDB"; //資料庫名稱
    static final String TB_NAME = "recordlist"; //資料表名稱
    static final String[] FROM = new String[] {"day","name","type","price"};
    SQLiteDatabase db;
    Cursor cur;
    SimpleCursorAdapter adapter;
    
    ListView LV_cal;
    TextView in_2,out_2;
    int in=0;
    int out=0;
    String temp="";
    String pr="";
    String comp_1="收入";
    String comp_2="支出";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

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
        
        in_2 = (TextView) findViewById(R.id.in_2);
        out_2 = (TextView) findViewById(R.id.out_2);

        //開啟或建立資料庫
        db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);

        //建立資料表
        String createTable = "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "day VARCHAR(32)," +
                "name VARCHAR(32)," +
                "type VARCHAR(32)," +
                "price VARCHAR(32))";
        db.execSQL(createTable);

        //查詢資料
        cur = db.rawQuery("SELECT * FROM "+TB_NAME,null);
        //空的則寫入測試資料
        if(cur.getCount()==0){
            addData("2022/1/10","productA","收入","299");
            addData("2022/1/10","productB","支出","350");
        }

        //查詢資料
        cur = db.rawQuery("SELECT * FROM "+TB_NAME,null);

        //建立adapter物件
        adapter=new SimpleCursorAdapter(this,
                R.layout.item,cur,FROM,
                new int[] {R.id.txvDay,R.id.txvName,R.id.txvType,R.id.txvPrice},0);

        LV_cal = (ListView) findViewById(R.id.LV_cal);
        LV_cal.setAdapter(adapter);

        //cur.moveToPosition(0);

        //Cursor c = db.rawQuery("SELECT * FROM "+TB_NAME,null);

        cur.moveToFirst();

       do{
           temp=cur.getString(3);
            pr=cur.getString(4);
        if(temp.equals(comp_1)) {in+=Integer.parseInt(pr);}
        if(temp.equals(comp_2)) {out+=Integer.parseInt(pr);}
       } while(cur.moveToNext());

        in_2.setText(Integer.toString(in));
        out_2.setText(Integer.toString(out));

    }
    //新增
    private void addData(String day, String name, String type,String price) {
        ContentValues cv = new ContentValues(4);
        cv.put(FROM[0],day);
        cv.put(FROM[1],name);
        cv.put(FROM[2],type);
        cv.put(FROM[3],price);
        db.insert(TB_NAME,null,cv);
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
                Intent it2 = new Intent(this, record.class);
                startActivity(it2);
                finish();
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_calculate:
                Toast.makeText(this, "結算總表", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
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
                Intent it2 = new Intent(this, record.class);
                startActivity(it2);
                finish();
                break;
            case R.id.menu_calculate:
                Toast.makeText(this, "結算總表", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
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
            case R.id.menu_copyright:
                Toast.makeText(this, "CopyRight", Toast.LENGTH_SHORT).show();
                Intent it5 = new Intent(this, copyright.class);
                startActivity(it5);
                finish();
                break;
        }
        return true;
    }
}