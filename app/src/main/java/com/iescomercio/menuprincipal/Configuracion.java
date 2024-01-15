package com.iescomercio.menuprincipal;

import android.content.Intent;
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
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Configuracion extends AppCompatActivity {
    private EditText IPmod1, IPmod2, IPmod3, IPmod4, puerto;
    private Button conectar;
    private TextView textoOnOff;
    private ImageView botonRojo, botonVerde;
    private static boolean conectado = false;
    private static String ip = null;
    private static int elPuerto;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
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

        i = this.getIntent();
        boolean aux = i.getBooleanExtra("configurado", false),
                dis = i.getBooleanExtra("desconectado", false);

        if (aux) configurado(dis);
        conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = creaIP(IPmod1, IPmod2, IPmod3, IPmod4);
                try {
                    elPuerto = Integer.parseInt(puerto.getText().toString());
                    conexion(ip, elPuerto);
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Ha habido un error en el número de puerto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String creaIP(EditText iPmod1, EditText iPmod2, EditText iPmod3, EditText iPmod4) {
        return iPmod1.getText().toString() + "." + iPmod2.getText().toString() + "."
                + iPmod3.getText().toString() + "." + iPmod4.getText().toString();
    }

    private void setIP(String ip) {
        String[] miIP = ip.split("\\.");
        IPmod1.setText(miIP[0]);
        IPmod2.setText(miIP[1]);
        IPmod3.setText(miIP[2]);
        IPmod4.setText(miIP[3]);
    }

    private void conexion(String ip, int puerto) {
        try {
            InetSocketAddress conectar = new InetSocketAddress(ip, puerto);
            Socket socket = new Socket();
            socket.connect(conectar, 2*1000);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            if (conectado) { //Si está conectado hace estas instrucciones
                desconectar(bw);
            } else { // Si está desconectado pues hace estas
                bw.write("0\n");
                bw.flush();
                String recibido = br.readLine();
                if (recibido != null && recibido.equals("1")) {
                    conectar();
                } else if (recibido == null) {
                    Toast.makeText(getApplicationContext(), "Se ha superado el límite de usuarios", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NoRouteToHostException e) {
            Toast.makeText(getApplicationContext(), "El servidor no es alcanzable", Toast.LENGTH_SHORT).show();
        } catch (UnknownHostException e) {
            Toast.makeText(getApplicationContext(), "Ha habido un error en la conexión", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Ha habido un error en la conexión", Toast.LENGTH_SHORT).show();
        }
    }

    public void lanzarMenu(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public static String getIp() {
        return (ip == null) ? "ERROR" : ip;
    }

    public static int getPuerto() {
        return (elPuerto == 0) ? Integer.MIN_VALUE : elPuerto;
    }

    private void configurado(boolean dis) {
        String extra = i.getStringExtra("ip");
        setIP(extra);
        int valor = i.getIntExtra("puerto", 0);
        puerto.setText(String.valueOf(valor));
        if (!dis) conectar();
    }

    private void conectar() {
        botonRojo.setImageResource(R.drawable.boton_rojo_apagado);
        botonVerde.setImageResource(R.drawable.boton_verde_encendido);
        textoOnOff.setText(getApplicationContext().getResources().getString(R.string.conectado));
        conectar.setText("Desconectar");
        conectado = true;

    }

    private void desconectar(BufferedWriter bw) {
        try {
            bw.write("1\n");
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        botonRojo.setImageResource(R.drawable.boton_rojo_encendido);
        botonVerde.setImageResource(R.drawable.boton_verde_apagado);
        textoOnOff.setText(getApplicationContext().getResources().getString(R.string.desconectado));
        conectar.setText("Conectar");
        conectado = false;
    }

    public static boolean estaConectado() {
        return conectado;
    }

    public static int getStatus() {
        if (getIp().equals("ERROR")) return 0;
        if (conectado) {
            return 1;
        } else {
            return 2;
        }
    }
}
