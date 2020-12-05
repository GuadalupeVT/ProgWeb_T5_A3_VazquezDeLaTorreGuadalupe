package com.example.bd_android_http;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ActivityConsultas extends Activity {

    RecyclerView recycler;

    ArrayList<String> datos = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);
        recycler=findViewById(R.id.recycler_view_consultas);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.0.6:80/ago_dic_2020/Aplicacion_ABCC/API_REST_Android/api_consultas_alumnos.php";
                String metodo = "POST";

                AnalizadorJSON analizadorJSON = new AnalizadorJSON();
                JSONObject jsonObject = analizadorJSON.consultaHTTP(url);


                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("alumnos");


                    String cadena = "";
                    for (int i=0; i<jsonArray.length();i++){

                        cadena = jsonArray.getJSONObject(i).getString("nc") + " | " +
                                jsonArray.getJSONObject(i).getString("n") + "|" +
                                jsonArray.getJSONObject(i).getString("pa") + "|" +
                                jsonArray.getJSONObject(i).getString("sa") + "|" +
                                jsonArray.getJSONObject(i).getString("e") + "|" +
                                jsonArray.getJSONObject(i).getString("s") + "|" +
                                jsonArray.getJSONObject(i).getString("c");

                        datos.add(cadena);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AdaptadorAlumno adapter=new AdaptadorAlumno(datos);
                        recycler.setAdapter(adapter);
                    }
                });
            }//run
        }).start();

    }
}//class Consultas
