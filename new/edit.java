package com.example.piggybank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class edit extends AppCompatActivity {

    EditText edt;
    TextView txv;
    Button save,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent it = getIntent();
        //int no = it.getIntExtra("編號", 0);

        String s = it.getStringExtra("備忘");
        txv = (TextView) findViewById(R.id.txv);
        txv.setText(s.substring(0,2));
        edt = (EditText) findViewById(R.id.editText);
        if(s.length()>3){
            edt.setText(s.substring(3));
        }
    }

    public void onsave(View v){
        Intent it2 = new Intent();
        it2.putExtra("備忘", txv.getText() + " "+ edt.getText());
        setResult(RESULT_OK, it2);
        finish();
    }
    public void oncancel(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
}