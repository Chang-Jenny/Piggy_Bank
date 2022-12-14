package com.example.piggybank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    View.OnClickListener{

    DrawerLayout drawerLayout;
    NavigationView navigation;
    Toolbar toolbar;

    LinearLayout layout_record;
    LinearLayout layout_calculate;
    LinearLayout layout_remind;
    LinearLayout layout_raise;
    LinearLayout layout_copyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        layout_record = (LinearLayout) findViewById(R.id.layout_record);
        layout_calculate = (LinearLayout) findViewById(R.id.layout_calculate);
        layout_remind = (LinearLayout) findViewById(R.id.layout_remind);
        layout_raise = (LinearLayout) findViewById(R.id.layout_raise);
        layout_copyright = (LinearLayout) findViewById(R.id.layout_copyright);

        layout_record.setOnClickListener(this);
        layout_calculate.setOnClickListener(this);
        layout_remind.setOnClickListener(this);
        layout_raise.setOnClickListener(this);
        layout_copyright.setOnClickListener(this);
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
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_record:
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                Intent it1 = new Intent(this, record.class);
                startActivity(it1);
                finish();
                break;
            case R.id.menu_calculate:
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                Intent it2 = new Intent(this, calculate.class);
                startActivity(it2);
                finish();
                break;
            case R.id.menu_remind:
                Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show();
                Intent it3 = new Intent(this, remind.class);
                startActivity(it3);
                finish();
                break;
            case R.id.menu_pig:
                Toast.makeText(this, "?????????", Toast.LENGTH_SHORT).show();
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
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_record:
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                Intent it1 = new Intent(this, record.class);
                startActivity(it1);
                finish();
                break;
            case R.id.menu_calculate:
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                Intent it2 = new Intent(this, calculate.class);
                startActivity(it2);
                finish();
                break;
            case R.id.menu_remind:
                Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show();
                Intent it3 = new Intent(this, remind.class);
                startActivity(it3);
                finish();
                break;
            case R.id.menu_pig:
                Toast.makeText(this, "?????????", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.layout_record){
            Toast.makeText(Home.this,"????????????",Toast.LENGTH_SHORT).show();
            Intent itHtoR = new Intent(this, record.class);
            startActivity(itHtoR);
        }
        if(view.getId()==R.id.layout_calculate){
            Toast.makeText(Home.this,"????????????",Toast.LENGTH_SHORT).show();
            Intent itHtoC = new Intent(this, calculate.class);
            startActivity(itHtoC);
        }
        if(view.getId()==R.id.layout_remind){
            Toast.makeText(Home.this,"???????????????",Toast.LENGTH_SHORT).show();
            Intent itHtoRM = new Intent(this, remind.class);
            startActivity(itHtoRM);
        }
        if(view.getId()==R.id.layout_raise){
            Toast.makeText(Home.this,"?????????",Toast.LENGTH_SHORT).show();
            Intent itHtoP = new Intent(this, pig.class);
            startActivity(itHtoP);
        }
        if(view.getId()==R.id.layout_copyright){
            Toast.makeText(Home.this,"????????????",Toast.LENGTH_SHORT).show();
            Intent itHtoCR = new Intent(this, copyright.class);
            startActivity(itHtoCR);
        }

    }
}