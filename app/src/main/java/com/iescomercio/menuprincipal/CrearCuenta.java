package com.iescomercio.menuprincipal;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iescomercio.menuprincipal.persistencia.BaseDatos;

public class CrearCuenta extends AppCompatActivity {
    EditText usuario, contrasena, rContrasena;
    Button creaCuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_cuenta);
        usuario = findViewById(R.id.editTextUsuario);
        contrasena = findViewById(R.id.editTextContraseña);
        rContrasena = findViewById(R.id.editTextRContraseña);
        creaCuenta = findViewById(R.id.btnCrearCuenta);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        creaCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rContrasena.getText().toString().equals(contrasena.getText().toString())){
                    Toast.makeText(CrearCuenta.this, "Las contraseñas no coinciden",
                            Toast.LENGTH_SHORT).show();
                    contrasena.setText("");
                    rContrasena.setText("");
                } else {
                    BaseDatos bd = new BaseDatos();
                    bd.anadeUsuario(usuario.getText().toString(), contrasena.getText().toString());
                }
            }
        });
    }

    public void lanzarMenu(View view){
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void lanzarInicioSesión(View view){
        Intent i=new Intent(this, InicioSesion.class);
        startActivity(i);
    }
}
