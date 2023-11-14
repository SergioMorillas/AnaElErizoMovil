package com.iescomercio.menuprincipal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Mando extends AppCompatActivity {
    private AppCompatButton[] botones = new AppCompatButton[9];
    private Socket socket;
    private BufferedWriter bw;
    private String IP = Configuracion.getIp();
    private int miPuerto = Configuracion.getPuerto();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mando);
        if (IP.equals("ERROR")){
            Toast.makeText(this,"Debes configurar una direccion IP",
            Toast.LENGTH_SHORT);
            Intent i=new Intent(this, MainActivity.class);
            i.putExtra("error", true);
            startActivity(i);
        }
        try {
            socket = new Socket(IP, miPuerto);
            bw = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception exception) {
        }
        botones[0] = findViewById(R.id.flechaAbajo);    // Flecha abajo manda 1
        botones[1] = findViewById(R.id.flechaArriba);   // Flecha arriba manda 2
        botones[2] = findViewById(R.id.flechaIzq);      // Flecha izquierda manda 3
        botones[3] = findViewById(R.id.flechaDcha);     // Flecha derecha manda 4
        botones[4] = findViewById(R.id.botonStart);     // Boton start manda 5
        botones[5] = findViewById(R.id.botonSelect);    // Boton select manda 6
        botones[6] = findViewById(R.id.botonCasa);      // Boton casa manda 7
        botones[7] = findViewById(R.id.botonSaltar);    // Boton saltar manda 8
        botones[8] = findViewById(R.id.botonAgachar);   // Boton agachar manda 9

        for (int i = 0; i < botones.length; i++) setClickListener(botones[i], i + 1);
    }

    public void lanzarMenu(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void setClickListener(AppCompatButton b, int n) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    bw.write(n + "\n");
                    bw.flush();
                } catch (Exception ex) {
                }
            }
        });
    }

    private void setClickListenerView(View v, int n) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    bw.write(n + "\n");
                    bw.flush();
                    socket.close();
                } catch (Exception ex) {
                }
            }
        });
    }

}
