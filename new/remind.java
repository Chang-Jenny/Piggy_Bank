package com.example.piggybank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class remind extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    AdapterView.OnItemClickListener,
                    AdapterView.OnItemLongClickListener{

    DrawerLayout drawerLayout;
    NavigationView navigation;
    Toolbar toolbar;

    String[] Payment_Memorandum = {
      "1. 水電費",
      "2. 手機費","3.","4.","5.","6." };
    ListView listview;
    ArrayAdapter<String> aa;

    //畫面元件


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);

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

        listview = (ListView) findViewById(R.id.listview);
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Payment_Memorandum);
        listview.setAdapter(aa);

        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> a, View v, int pos, long id) {
        Intent it = new Intent(this,edit.class);
        it.putExtra("編號", pos+1);
        it.putExtra("備忘", Payment_Memorandum[pos]);
        startActivityForResult(it,pos);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent it) {
        super.onActivityResult(requestCode, resultCode, it);
        if(resultCode==RESULT_OK){
            Payment_Memorandum[requestCode]=it.getStringExtra("備忘");
            aa.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> a, View v, int pos, long id) {
        Payment_Memorandum[pos]=(pos+1)+".";
        aa.notifyDataSetChanged();
        return true;
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
                Toast.makeText(this, "每日查詢", Toast.LENGTH_SHORT).show();
                Intent it3 = new Intent(this, calculate.class);
                startActivity(it3);
                finish();
                break;
            case R.id.menu_remind:
                Toast.makeText(this, "繳費備忘錄", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
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
                Intent it2 = new Intent(this, record.class);
                startActivity(it2);
                finish();
                break;
            case R.id.menu_calculate:
                Toast.makeText(this, "每日查詢", Toast.LENGTH_SHORT).show();
                Intent it3 = new Intent(this, calculate.class);
                startActivity(it3);
                finish();
                break;
            case R.id.menu_remind:
                Toast.makeText(this, "繳費備忘錄", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
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


}