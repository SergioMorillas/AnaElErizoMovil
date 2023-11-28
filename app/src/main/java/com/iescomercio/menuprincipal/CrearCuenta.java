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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        creaCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rContrasena.getText().toString().equals(contrasena.getText().toString())) {
                    Toast.makeText(CrearCuenta.this, "Las contraseñas no coinciden",
                            Toast.LENGTH_SHORT).show();
                    contrasena.setText("");
                    rContrasena.setText("");
                } else {
                    String ip = CrearCuenta.this.getIntent().getStringExtra("ip");
                    BaseDatos bd = new BaseDatos("MD5", ip, "sa", "P@ssw0rd", "quillquest");
                    boolean b = bd.anadeUsuario(usuario.getText().toString(), contrasena.getText().toString());
                    if (b) {
                        Toast.makeText(CrearCuenta.this, "Se ha podido agregar el usuario",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CrearCuenta.this, "No se ha podido agregar el usuario",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void lanzarMenu(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void lanzarInicioSesión(View view) {
        Intent i = new Intent(this, InicioSesion.class);
        i.putExtra("ip", Configuracion.getIp());
        startActivity(i);
    }
}
