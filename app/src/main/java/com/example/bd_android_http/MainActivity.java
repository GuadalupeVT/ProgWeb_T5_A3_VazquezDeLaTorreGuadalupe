package com.example.bd_android_http;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirActivities(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btn_acceso:  //VALIDAR USUARIO Y CONTRASE{A EN BD DE MySQL
                                   i=new Intent(this, ActivityMenu.class);
                                   startActivity(i);
                                    break;
            case R.id.btn_registro:
                                    break;
        }
    }
}