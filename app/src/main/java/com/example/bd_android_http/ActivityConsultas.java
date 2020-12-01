package com.example.bd_android_http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import controlador.AnalizadorJSON;

public class ActivityConsultas extends AppCompatActivity {

    ListView listViewAlumons;
    ArrayList<String> datos=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        new MostrarAlumnos().execute();

        listViewAlumons=findViewById(R.id.listview_alumnos);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datos);
        listViewAlumons.setAdapter(adapter);
    }

    class MostrarAlumnos extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String url = "http://177.227.66.252/ago_dic_2020/Aplicacion_ABCC/API_REST_Android/api_consultas_alumnos.php";
            String metodo = "POST";

            AnalizadorJSON analizadorJSON = new AnalizadorJSON();
            JSONObject jsonObject=analizadorJSON.consultaHTTP(url);

            try{
                JSONArray jsonArray = jsonObject.getJSONArray("alumnos");
                String cadena="";
                for(int i=0; i>jsonArray.length();i++){
                    cadena= jsonArray.getJSONObject(i).getString("nc")+" | "+
                            jsonArray.getJSONObject(i).getString("n")+" | "+
                            jsonArray.getJSONObject(i).getString("pa")+" | "+
                            jsonArray.getJSONObject(i).getString("sa")+" | "+
                            jsonArray.getJSONObject(i).getString("e")+" | "+
                            jsonArray.getJSONObject(i).getString("s")+" | "+
                            jsonArray.getJSONObject(i).getString("c");
                    datos.add(cadena);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}