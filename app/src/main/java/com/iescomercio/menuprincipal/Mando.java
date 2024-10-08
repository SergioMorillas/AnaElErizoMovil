package com.iescomercio.menuprincipal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Mando extends AppCompatActivity {
    private AppCompatButton[] botones = new AppCompatButton[9];
    private Socket socket;
    private BufferedWriter bw;
    private String IP = Configuracion.getIp();
    private int miPuerto = Configuracion.getPuerto();
    private String usuario = InicioSesion.getUsuario().trim();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mando);
        if (IP.equals("ERROR")) {
            Toast.makeText(this, "Debes configurar una direccion IP",
                    Toast.LENGTH_SHORT);
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("error", true);
            startActivity(i);
        }
        try {
            socket = new Socket(IP, miPuerto);
            bw = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            bw.write("2\n");
            bw.flush();
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

    @SuppressLint("ClickableViewAccessibility")
    private void setClickListener(AppCompatButton b, int n) {
        b.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                try {
                    bw.write(usuario + ":" + n + "\n");
                    bw.flush();
                } catch (Exception ex) {
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                try {
                    bw.write(usuario + ":" + "-10\n");
                    bw.flush();
                } catch (Exception ex) {
                }
            }
            return false;
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
