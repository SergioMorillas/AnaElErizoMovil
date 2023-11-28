package com.iescomercio.menuprincipal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button[] botones = new Button[4];
    Button botonConfiguracion, botonEstadisticas, botonIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonConfiguracion = findViewById(R.id.botonMando);
        botonEstadisticas = findViewById(R.id.botonEstadisticas);
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);

        if (Configuracion.getIp().equals("ERROR") || !Configuracion.estaConectado()) {
            botonConfiguracion.setClickable(false);
            botonEstadisticas.setClickable(false);
            botonIniciarSesion.setClickable(false);
        } else {
            botonConfiguracion.setClickable(true);
            botonEstadisticas.setClickable(true);
            botonIniciarSesion.setClickable(true);
        }
    }

    public void lanzarMando(View view) {
        Intent i = new Intent(this, Mando.class);
        if (InicioSesion.getUsuario()==null) {
            Toast.makeText(MainActivity.this, "Debes iniciar sesion primero",
                    Toast.LENGTH_SHORT);
            return;
        }
        startActivity(i);
    }

    public void lanzarInicioSesión(View view) {
        Intent i = new Intent(this, InicioSesion.class);
        i.putExtra("ip", Configuracion.getIp());
        startActivity(i);
    }

    public void lanzarEstadisticas(View view) {
        Intent i = new Intent(this, Estadisticas.class);
        i.putExtra("ip", Configuracion.getIp());
        startActivity(i);
    }

    public void lanzarConfiguracion(View view) {
        Intent i = new Intent(this, Configuracion.class);
        Bundle b = new Bundle();
        switch (Configuracion.getStatus()) {
            case 0:
                b.putBoolean("configurado", false);
                break;
            case 1:
                b.putBoolean("configurado", true);
                b.putString("ip", Configuracion.getIp());
                b.putInt("puerto", Configuracion.getPuerto());
                i.putExtras(b);
                break;
            case 2:
                b.putBoolean("configurado", true);
                b.putBoolean("desconectado", true);
                b.putString("ip", Configuracion.getIp());
                b.putInt("puerto", Configuracion.getPuerto());
                i.putExtras(b);
                break;
            default:
        }
        startActivity(i);
    }

    public void lanzarInformacion(View view) {
        Intent i = new Intent(this, Informacion.class);
        startActivity(i);
    }
}