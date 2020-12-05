package com.example.bd_android_http;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class MainActivity extends AppCompatActivity {
    EditText usuario,contraseña;
    static boolean mensajeResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario=findViewById(R.id.caja_usuario);
        contraseña=findViewById(R.id.caja_contraseña);
    }

    public void abrirActivities(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btn_acceso:  //VALIDAR USUARIO Y CONTRASE{A EN BD DE MySQL
                String u = usuario.getText().toString();
                String p = contraseña.getText().toString();


                //Verificar que no vengan datos vacios
                if (u.isEmpty() || p.isEmpty() ) {
                    Toast.makeText(this, "CAMPOS VACIOS!!", Toast.LENGTH_LONG).show();
                } else {


                    //Verificar que la comunicación con WIFI funcione
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo ni = cm.getActiveNetworkInfo();

                    if (ni != null && ni.isConnected()) {
                        new ValidarUsuario().execute(u,p);
                        Toast.makeText(this, mensajeResultados ? "EXITO" : "ME CAMBIO DE CARRERA", Toast.LENGTH_LONG).show();
                        if(mensajeResultados){
                            i=new Intent(this, ActivityMenu.class);
                            startActivity(i);
                        }
                    } else {
                        Log.e("MSJ-->", "Error en el WIFI");
                    }

                    Toast.makeText(this, "Magia", Toast.LENGTH_LONG).show();
                }




                                    break;
            case R.id.btn_registro:
                                    i=new Intent(this, ActivityRegistroUsuarios.class);
                                    startActivity(i);
                                    break;
        }
    }
}
class ValidarUsuario extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... datos) {

        String url = "http://192.168.0.6:80/ago_dic_2020/Aplicacion_ABCC/API_REST_Android/api_validar_usuario.php";
        String metodo = "POST";

        Map<String, String> mapDatos = new HashMap<String, String>();
        mapDatos.put("u", datos[0]);
        mapDatos.put("p", datos[1]);

        AnalizadorJSON aj = new AnalizadorJSON();
        JSONObject resultado = aj.peticionHTTPUsuarios(url, metodo, mapDatos);

        boolean exito = false;
        try {
            exito = resultado.getBoolean("exito");
            ActivityRegistroUsuarios.mensajeResultados = exito;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return String.valueOf(exito);
    }


}