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

public class InicioSesion extends AppCompatActivity {
    private EditText usuario, contrasena;
    private Button btnComprobar;
    private BaseDatos bd;
    private int encontrado;
    private String sUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        bd = new BaseDatos("MD5", "172.16.10.122", "sa", "P@ssw0rd", "quillquest");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);
        usuario = findViewById(R.id.editTextUsuario);
        contrasena = findViewById(R.id.editTextRContraseña);
        btnComprobar = findViewById(R.id.btnInicioSesion);
        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUsuario = usuario.getText().toString();
                encontrado = bd.compruebaLogin(sUsuario, bd.cifraContrasena(contrasena.getText().toString()));
                switch (encontrado) {
                    case 1:
                        Toast.makeText(InicioSesion.this,
                                "El nombre no se ha encontrado",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(
                                InicioSesion.this,
                                "La contraseña no coincide con el usuario",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(InicioSesion.this,
                                "Gracias por conectarte " + sUsuario,
                                Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }

    public void lanzarMenu(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("usuario", sUsuario);
        startActivity(i);
    }

    public void lanzarCrearCuenta(View view) {
        Intent i = new Intent(this, CrearCuenta.class);
        startActivity(i);
    }
}

