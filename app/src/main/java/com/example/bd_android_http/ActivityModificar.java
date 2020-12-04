package com.example.bd_android_http;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import controlador.AnalizadorJSON;

public class ActivityModificar extends AppCompatActivity {

    EditText cajaNumControl, cajaNombre, cajaPrimerAp,cajaSegundoAp, cajaEdad;
    Spinner spinnerSemestre, spinnerCarrera;
    static boolean mensajeResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        cajaNumControl = findViewById(R.id.caja_num_control_cambios);
        cajaNombre = findViewById(R.id.caja_nombre_cambios);
        cajaPrimerAp=findViewById(R.id.caja_primer_ap_cambios);
        cajaSegundoAp=findViewById(R.id.caja_segundo_ap_cambios);
        cajaEdad=findViewById(R.id.caja_edad_cambios);
        spinnerSemestre=findViewById(R.id.spinner_semestre_cambios);
        spinnerCarrera=findViewById(R.id.spinner_carrera_cambios);

        String nc=getIntent().getExtras().getString("nc");
        String n=getIntent().getExtras().getString("n");
        String pa=getIntent().getExtras().getString("pa");
        String sa=getIntent().getExtras().getString("sa");
        String e=getIntent().getExtras().getString("e");
        String s=getIntent().getExtras().getString("s");
        String c=getIntent().getExtras().getString("c");

        cajaNombre.setText(n);
        cajaPrimerAp.setText(pa);
        cajaSegundoAp.setText(sa);
        cajaEdad.setText(e);
        spinnerSemestre.setSelection (((ArrayAdapter) spinnerSemestre.getAdapter ()). getPosition (s));
        spinnerCarrera.setSelection (((ArrayAdapter) spinnerCarrera.getAdapter ()). getPosition (c));
    }

    public void modificarRegistro(View v) {
        String nc = cajaNumControl.getText().toString();
        String n = cajaNombre.getText().toString();
        String pa = cajaPrimerAp.getText().toString();
        String sa = cajaSegundoAp.getText().toString();
        String e = cajaEdad.getText().toString();
        String s = spinnerSemestre.getSelectedItem().toString();
        String c = spinnerCarrera.getSelectedItem().toString();

        //Verificar que no vengan datos vacios
        if (nc.isEmpty() || n.isEmpty() || pa.isEmpty() || e.isEmpty() || s.isEmpty() || c.isEmpty()) {
            Toast.makeText(this, "CAMPOS VACIOS!!", Toast.LENGTH_LONG).show();
        } else {


            //Verificar que la comunicación con WIFI funcione
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();

            if (ni != null && ni.isConnected()) {
                new ModificarAlumno().execute(nc, n, pa, sa, e, s, c);
                Toast.makeText(this, mensajeResultados ? "EXITO" : "ME CAMBIO DE CARRERA", Toast.LENGTH_LONG).show();
            } else {
                Log.e("MSJ-->", "Error en el WIFI");
            }

            Toast.makeText(this, "Magia", Toast.LENGTH_LONG).show();
        }
    }
}

class ModificarAlumno extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... datos) {

        String url = "http://192.168.0.6:80/ago_dic_2020/Aplicacion_ABCC/API_REST_Android/api_cambios_alumnos.php";
        String metodo = "POST";

        Map<String, String> mapDatos = new HashMap<String, String>();
        mapDatos.put("nc", datos[0]);
        mapDatos.put("n", datos[1]);
        mapDatos.put("pa", datos[2]);
        mapDatos.put("sa", datos[3]);
        mapDatos.put("e", datos[4]);
        mapDatos.put("s", datos[5]);
        mapDatos.put("c", datos[6]);

        AnalizadorJSON aj = new AnalizadorJSON();
        JSONObject resultado = aj.peticionHTTP(url, metodo, mapDatos);

        boolean exito = false;
        try {
            exito = resultado.getBoolean("exito");
            ActivityAltas.mensajeResultados = exito;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return String.valueOf(exito);
    }
}