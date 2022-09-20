package com.example.piggybank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class search<pieData> extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    View.OnClickListener,
                    DatePickerDialog.OnDateSetListener{

    DrawerLayout drawerLayout;
    NavigationView navigation;
    Toolbar toolbar;
    ViewGroup layout;

    PieChart pie;
    List<PieEntry> listpie;




    static final String DB_NAME = "recordDB"; //資料庫名稱
    static final String TB_NAME = "recordlist"; //資料表名稱
    static final String[] FROM = new String[] {"day","name","type","category","price"};
    SQLiteDatabase db;
    Cursor cur;

    Button enter;
    TextView from, end;
    Calendar date;


    String query, from_query, end_query;
    int from_y, from_m, from_d, end_y, end_m, end_d;
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
        enter = (Button) findViewById(R.id.enter);
        from = (TextView) findViewById(R.id.from);
        end = (TextView) findViewById(R.id.end);

        enter.setOnClickListener(this);
        from.setOnClickListener(this);
        end.setOnClickListener(this);

        pie = (PieChart) findViewById(R.id.pie);
        listpie = new ArrayList<>();
        pie.setNoDataText("尚未輸入查詢期間");




        //開啟或建立資料庫
        db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);

        //建立資料表
        String createTable = "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "day DATE," +
                "name VARCHAR(32)," +
                "type VARCHAR(32)," +
                "category VARCHAR(32),"+
                "price VARCHAR(32))";
        db.execSQL(createTable);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.from){
            from_y = date.get(Calendar.YEAR);
            from_m = date.get(Calendar.MONTH);
            from_d = date.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(this,this, from_y,from_m,from_d).show();
//            new MonPickerDialog(this, this, y,m,d).show();
        }
        if(view.getId()==R.id.end){
            end_y = date.get(Calendar.YEAR);
            end_m = date.get(Calendar.MONTH);
            end_d = date.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(this,this, end_y,end_m,end_d).show();
//            new MonPickerDialog(this, this, y,m,d).show();
        }
        else if(view.getId()==R.id.enter){
//            String which = "%" +query+ "%";
//            String which ="%"+"-01-"+"%";
            String which = from_query;
            String queryDate = (String) from.getText();
            String condition = "SELECT * FROM "+TB_NAME+" WHERE day>=? AND day<=?";
            String[] selectionArgs = {from_query, end_query};
            cur = db.rawQuery(condition, selectionArgs);

            Double out=0.0;
            Double in=0.0;
            if(cur.getCount()!=0){
                double[] money = {0, 0, 0, 0, 0, 0, 0, 0, 0};
                cur.moveToFirst();
                do{
                    String temp = cur.getString(3); //收入支出
                    String cat = cur.getString(4); // 食衣住行育樂薪資其他
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

                pie = (PieChart) findViewById(R.id.pie);
                listpie = new ArrayList<>();
                if(money[0]!=0) listpie.add(new PieEntry(Math.round(money[0]),"食"));
                if(money[1]!=0) listpie.add(new PieEntry(Math.round(money[1]),"衣"));
                if(money[2]!=0) listpie.add(new PieEntry(Math.round(money[2]),"住"));
                if(money[3]!=0) listpie.add(new PieEntry(Math.round(money[3]),"行"));
                if(money[4]!=0) listpie.add(new PieEntry(Math.round(money[4]),"育"));
                if(money[5]!=0) listpie.add(new PieEntry(Math.round(money[5]),"樂"));
                if(money[6]!=0) listpie.add(new PieEntry(Math.round(money[6]),"其他支出"));
                if(money[7]!=0) listpie.add(new PieEntry(Math.round(money[7]),"薪資"));
                if(money[8]!=0) listpie.add(new PieEntry(Math.round(money[8]),"其他收入"));



                PieDataSet pieDataSet=new PieDataSet(listpie,"");
                PieData pieData=new PieData(pieDataSet);
                pie.setData(pieData);
                pieDataSet.setColors(Color.parseColor("#d08c60"),
                        Color.parseColor("#797d62"),
                        Color.parseColor("#b58463"),
                        Color.parseColor("#baa587"),
                        Color.parseColor("#9b9b7a"),
                        Color.parseColor("#d9ae94"),
                        Color.parseColor("#ffcb69"),
                        Color.parseColor("#f1dca7"));

//                pieDataSet.setColors(Color.YELLOW,Color.GREEN);


                //把右下邊的Description label 去掉，同學也可以設置成餅圖說明

                Description description = null;
                pie.setDescription(description);
                //去掉中心圓，此時中心圓半透明
                pie.setHoleRadius(0f);
                //去掉半透明
                pie.setTransparentCircleAlpha(0);
                pie.setDrawEntryLabels(true);
                pie.invalidate();


                System.out.println(String.valueOf(out));
                System.out.println(String.valueOf(in));
                listpie = new ArrayList<>();


            }
            else{
                System.out.println("空的");
                pie.setNoDataText("尚無紀錄");
            }

        }
    }
    int count=0;
    @Override
    public void onDateSet(DatePicker v, int y, int m, int d) {
        count+=1;
        if(m<10){
            query = y+"-0"+(m+1)+"-"+d;
        }
        else{
            query = y+"-"+(m+1)+"-"+d;
        }
        if(count%2!=0){
            from_query = query;
            from.setText(from_query);
        }
        else if(count%2==0){
            end_query = query;
            end.setText(end_query);
        }
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