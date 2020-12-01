package com.example.bd_android_http;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ActivityAltas extends Activity {

    static boolean mensajeResultados;

    EditText cajaNumControl, cajaNombre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        cajaNumControl = findViewById(R.id.caja_num_control_altas);
        cajaNombre = findViewById(R.id.caja_nombre_altas);

    }

    public void agregarRegistro(View v){

        String nc = cajaNumControl.getText().toString();
        String n = cajaNombre.getText().toString();

        String pa = "Skywalker";
        String sa = "-";
        String e = "50";
        String s = "5";
        String c = "ISC";

        //Verificar que la comunicacion con WIFI funcione
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if(ni != null && ni.isConnected()){

            new AgregarAlumno().execute(nc, n, pa, sa, e, s, c);

            Toast.makeText(getBaseContext(), mensajeResultados?"EXITO":"ME CAMBIO DE CARRERA", Toast.LENGTH_LONG).show();


        }else
            Log.e("MSJ-->", "Error en WIFI");


        Toast.makeText(getBaseContext(), "Magia", Toast.LENGTH_LONG).show();

    }//metodo AgregarRegistro

    //clase interna
    class AgregarAlumno extends AsyncTask<String, String, String> {

        @Override                       //VARARGS
        protected String doInBackground(String...datos) {

            String url = "http://177.227.66.252/ago_dic_2020/Aplicacion_ABCC/API_REST_Android/api_altas_alumnos.php";
            String metodo = "POST";

            Map<String, String> mapaDatos = new HashMap<>();
            mapaDatos.put("nc", datos[0]);
            mapaDatos.put("n", datos[1]);
            mapaDatos.put("pa", datos[2]);
            mapaDatos.put("sa", datos[3]);
            mapaDatos.put("e", datos[4]);
            mapaDatos.put("s", datos[5]);
            mapaDatos.put("c", datos[6]);

            AnalizadorJSON analizadorJSON = new AnalizadorJSON();

            JSONObject resultado = analizadorJSON.peticionHTTP(url, metodo, mapaDatos);

            boolean exito = false;
            try {
                exito = resultado.getBoolean("exito");
                ActivityAltas.mensajeResultados = exito;

                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), exito?"EXITO":"ME CAMBIO DE CARRERA", Toast.LENGTH_LONG).show();
                    }
                });*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return String.valueOf(exito);
        }
    }

}//class ActivityAltas
