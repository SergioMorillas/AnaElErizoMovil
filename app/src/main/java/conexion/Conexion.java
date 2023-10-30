package conexion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.iescomercio.menuprincipal.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Conexion extends AppCompatActivity {
    Button conectar;
    EditText ip, puerto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conexion);
        conectar = findViewById(R.id.conectar);
        ip = findViewById(R.id.editTextIP);
        puerto = findViewById(R.id.editTextPort);
        conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipCon = String.valueOf(ip.getText());
                int puertoCon = Integer.parseInt(String.valueOf(ip.getText()));
                if (conectar(ipCon, puertoCon)){
                    conectar.setText("Conectado");
                    conectar.setBackgroundColor(234);
                }
            }
        });
    }

    public boolean conectar(String ip, int port) {
        boolean sw = false;
        try (Socket socket = new Socket(ip, port);
             BufferedReader bin = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {
            String str = bin.readLine();
            if (str.equals("1"))
                sw = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sw;
    }
}
