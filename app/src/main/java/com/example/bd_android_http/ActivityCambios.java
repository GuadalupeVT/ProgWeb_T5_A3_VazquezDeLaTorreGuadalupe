package com.example.bd_android_http;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ActivityCambios extends Activity {

    ListView listviewAlumnos;

    ArrayList<String> datos = new ArrayList<>();
    Intent i = new Intent(this, ActivityModificar.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);

        new MostrarAlumnos().execute();


        listviewAlumnos = findViewById(R.id.listview_alumnos_cambios);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        listviewAlumnos.setAdapter(adapter);

        listviewAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                Object o = listviewAlumnos.getItemAtPosition(posicion);
                String cad=o.toString();
                String alumno[]=cad.split("|");

                i.putExtra("nc",alumno[0]);
                i.putExtra("n",alumno[1]);
                i.putExtra("pa",alumno[1]);
                i.putExtra("sa",alumno[1]);
                i.putExtra("e",alumno[1]);
                i.putExtra("s",alumno[1]);
                i.putExtra("c",alumno[1]);
                startActivity(i);
            }
        });
    }


    class MostrarAlumnos extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            String url = "http://192.168.0.6:80/ago_dic_2020/Aplicacion_ABCC/API_REST_Android/api_consultas_alumnos.php";
            String metodo = "POST";

            AnalizadorJSON analizadorJSON = new AnalizadorJSON();
            JSONObject jsonObject = analizadorJSON.consultaHTTP(url);


            try {
                JSONArray jsonArray = jsonObject.getJSONArray("alumnos");
                // Log.i("MSJ->", String.valueOf(jsonArray));


                String cadena = "";
                for (int i=0; i<jsonArray.length();i++){

                    cadena = jsonArray.getJSONObject(i).getString("nc") + "|" +
                            jsonArray.getJSONObject(i).getString("n") + "|" +
                            jsonArray.getJSONObject(i).getString("pa") + "|" +
                            jsonArray.getJSONObject(i).getString("sa") + "|" +
                            jsonArray.getJSONObject(i).getString("e") + "|" +
                            jsonArray.getJSONObject(i).getString("s") + "|" +
                            jsonArray.getJSONObject(i).getString("c");

                    datos.add(cadena);
                    Log.i("MSJ->", cadena);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }



}//class ActivityBajas
