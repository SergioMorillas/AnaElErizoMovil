package com.iescomercio.menuprincipal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Configuracion extends AppCompatActivity {
    EditText IPmod1, IPmod2, IPmod3, IPmod4, puerto;
    Button conectar;
    TextView textoOnOff;
    ImageView botonRojo, botonVerde;
    boolean conectado = false;
    SharedPreferences preferencias = getPreferences(this.MODE_PRIVATE);
    SharedPreferences.Editor editor = preferencias.edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        IPmod1 = findViewById(R.id.editTextIP192);
        IPmod2 = findViewById(R.id.editTextIP168);
        IPmod3 = findViewById(R.id.editTextIP0);
        IPmod4 = findViewById(R.id.editTextIP1);
        puerto = findViewById(R.id.editTextPort);
        conectar = findViewById(R.id.botonConectar);
        textoOnOff = findViewById(R.id.editTextOnOfF);
        botonRojo = findViewById(R.id.botonRojo);
        botonVerde = findViewById(R.id.botonVerde);
        conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = creaIP(IPmod1, IPmod2, IPmod3, IPmod4);
                try {
                    int port12 = Integer.parseInt(puerto.getText().toString());
                    conexion(ip, port12);
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Ha habido un error en el numero de puerto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String creaIP(EditText iPmod1, EditText iPmod2, EditText iPmod3, EditText iPmod4) {
        return iPmod1.getText().toString() + "." + iPmod2.getText().toString() + "."
                + iPmod3.getText().toString() + "." + iPmod4.getText().toString();
    }

    private void conexion(String ip, int puerto) {
        try (Socket socket = new Socket(ip, puerto);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            editor.putString("ip", ip);
            editor.putInt("puerto", puerto);
            if (conectado) { //Si esta conectado hace estas instrucciones
                botonRojo.setImageResource(R.drawable.boton_rojo_encendido);
                botonVerde.setImageResource(R.drawable.boton_verde_apagado);
                textoOnOff.setText(getApplicationContext().getResources().getString(R.string.desconectado));
                conectar.setText("Conectar");
                conectado = false;
                bw.write("0");
                bw.flush();
            } else {
                if (br.readLine().equals("1")) {
                    botonRojo.setImageResource(R.drawable.boton_rojo_apagado);
                    botonVerde.setImageResource(R.drawable.boton_verde_encendido);
                    textoOnOff.setText(getApplicationContext().getResources().getString(R.string.conectado));
                    conectar.setText("Desconectar");
                    conectado = true;
                    bw.write("1");
                    bw.flush();
                } else {
                    Toast.makeText(getApplicationContext(), "Se ha superado el limite de usuarios", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NoRouteToHostException e){
            Toast.makeText(getApplicationContext(), "El servidor no es alcanzable", Toast.LENGTH_SHORT).show();
        }catch (UnknownHostException e){
            Toast.makeText(getApplicationContext(), "Ha habido un error en la conexión", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void lanzarMenu(View view){
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
