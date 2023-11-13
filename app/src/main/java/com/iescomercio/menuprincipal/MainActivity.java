package com.iescomercio.menuprincipal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button[] botones = new Button[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lanzarMando(View view){
        Intent i=new Intent(this, Mando.class);
        startActivity(i);
    }
    public void lanzarInicioSesión(View view){
        Intent i=new Intent(this, InicioSesion.class);
        startActivity(i);
    }
    public void lanzarEstadisticas(View view){
        Intent i=new Intent(this, Estadisticas.class);
        startActivity(i);
    }
    public void lanzarConfiguracion(View view){
        Intent i=new Intent(this, Configuracion.class);
        startActivity(i);
    }

    public void lanzarInformacion(View view){
        Intent i=new Intent(this, Informacion.class);
        startActivity(i);
    }
}