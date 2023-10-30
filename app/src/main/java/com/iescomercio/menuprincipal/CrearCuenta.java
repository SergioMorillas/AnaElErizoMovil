package com.iescomercio.menuprincipal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CrearCuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_crearcuenta);
    }

    public void lanzarMenu(View view){
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
