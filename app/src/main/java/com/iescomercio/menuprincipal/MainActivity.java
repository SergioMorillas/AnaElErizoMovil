package com.iescomercio.menuprincipal;

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

    public void lanzarMInicioSesión(View view){
        Intent i=new Intent(this, login.class);
        startActivity(i);
    }

    public void lanzarConfig(View view){
        Intent i=new Intent(this, conexion.class);
        startActivity(i);
    }
}