package com.example.piggybank;

import static java.lang.System.out;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.text.format.DateUtils;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class search extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    View.OnClickListener,
                    DatePickerDialog.OnDateSetListener{

    DrawerLayout drawerLayout;
    NavigationView navigation;
    Toolbar toolbar;
    ViewGroup layout;

    static final String DB_NAME = "recordDB"; //資料庫名稱
    static final String TB_NAME = "recordlist"; //資料表名稱
    static final String[] FROM = new String[] {"day","name","type","category","price"};
    SQLiteDatabase db;
    Cursor cur;

    Button enter;
    Spinner chooseMonth;
    TextView show, year;
    Calendar date;


    String query;
    int y, m, d;
    double[] money = {0, 0, 0, 0, 0, 0, 0, 0, 0};


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
                Intent it6 = new Intent(this, Home.class);
                startActivity(it6);
                finish();
            case R.id.menu_record:
                Toast.makeText(this, "每日記帳", Toast.LENGTH_SHORT).show();
                Intent it1 = new Intent(this, record.class);
                startActivity(it1);
                finish();
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
                drawerLayout.closeDrawers();
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
                Intent it6 = new Intent(this, Home.class);
                startActivity(it6);
                finish();
                break;
            case R.id.menu_record:
                Toast.makeText(this, "每日記帳", Toast.LENGTH_SHORT).show();
                Intent it1 = new Intent(this, record.class);
                startActivity(it1);
                finish();
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
                drawerLayout.closeDrawers();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

        layout = (ViewGroup) findViewById(R.id.layout_search);


        date = Calendar.getInstance();
        chooseMonth = (Spinner) findViewById(R.id.chooseMonth);
        show = (TextView) findViewById(R.id.show);
        enter = (Button) findViewById(R.id.enter);
        year = (TextView) findViewById(R.id.txtYear);

        enter.setOnClickListener(this);
        year.setOnClickListener(this);
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
    }

    public String enterJudge(){
        String[] months = getResources().getStringArray(R.array.month);
        int index = chooseMonth.getSelectedItemPosition();
        show.setText(months[index]);
        return months[index];
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.txtYear){
            y = date.get(Calendar.YEAR);
            m = date.get(Calendar.MONTH);
            d = date.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(this,this, y,m,d).show();
//            new MonPickerDialog(this, this, y,m,d).show();
        }
        else if(view.getId()==R.id.enter){
            String which = "%" +query+ "%";
            String condition = "SELECT * FROM "+TB_NAME+" WHERE day LIKE ?";
            String[] selectionArgs = {which};
            cur = db.rawQuery(condition, selectionArgs);
            Double out=0.0;
            Double in=0.0;
            if(cur.getCount()!=0){
                cur.moveToFirst();
                do{
                    String temp=cur.getString(3); //收入支出
                    String cat = cur.getString(3); // 食衣住行育樂薪資其他
                    String pr=cur.getString(5); //金額
                    if(temp.equals("支出") && cat.equals("食")) { money[0]+=Integer.parseInt(pr);}
                    if(temp.equals("支出") && cat.equals("衣")) { money[1]+=Integer.parseInt(pr);}
                    if(temp.equals("支出") && cat.equals("住")) { money[2]+=Integer.parseInt(pr);}
                    if(temp.equals("支出") && cat.equals("行")) { money[3]+=Integer.parseInt(pr);}
                    if(temp.equals("支出") && cat.equals("育")) { money[4]+=Integer.parseInt(pr);}
                    if(temp.equals("支出") && cat.equals("樂")) { money[5]+=Integer.parseInt(pr);}
                    if(temp.equals("支出") && cat.equals("其他")) { money[6]+=Integer.parseInt(pr);}

                    if(temp.equals("收入") && cat.equals("薪資")) { money[7]+=Integer.parseInt(pr);}
                    if(temp.equals("收入") && cat.equals("其他")) { money[8]+=Integer.parseInt(pr);}
                    out = (money[0]+money[1]+money[2]+money[3]+money[4]+money[5]+money[6]);
                    in = (money[7]+money[8]);

                } while(cur.moveToNext());
                show.setText(String.valueOf(in));
                System.out.println(String.valueOf(out));
                System.out.println(String.valueOf(in));
            }
            else{
                System.out.println("空的");
            }

        }
    }
    @Override
    public void onDateSet(DatePicker v, int y, int m, int d) {
        show.setText(y+"/"+(m+1)+"/"+d);
        query = y+"/"+(m+1)+"/";
    }
}


class MonPickerDialog extends DatePickerDialog {
    public MonPickerDialog(Context context, OnDateSetListener listener, int y, int m, int d) {
        super(context, listener, y, m, d);
        this.setTitle(y+"年"+(m+1)+"月");

        ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day){
        super.onDateChanged(view, year, month, day);

        this.setTitle(year+"年"+(month+1)+"月");
    }

}