package com.iescomercio.menuprincipal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.charts.*;
import com.iescomercio.menuprincipal.persistencia.BaseDatos;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity {

    HorizontalBarChart stats_individuales, stats_generales;
    int[] datosIndividuales, datosGenerales;
    EditText nombreUsuario;
    String usuario;
    BaseDatos objBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.estadisticas);
        stats_individuales = findViewById(R.id.statsIndividuales);
        stats_generales = findViewById(R.id.statsGenerales);
        String ip = this.getIntent().getExtras().getString("ip");
        objBD = new BaseDatos("MD5", ip, "sa", "P@ssw0rd", "quillquest");
        nombreUsuario = (EditText) findViewById(R.id.nombreUsuarioStats);
        nombreUsuario.setOnEditorActionListener(editorListener);
        datosGenerales = objBD.muestraEstadisticasGenerales();
        iniciarCharts(setDatos(datosGenerales), false);

    }

    public void lanzarMenu(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean ret = true;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                usuario = nombreUsuario.getText().toString();
                if (!objBD.compruebaNombre(usuario)) {
                    Toast.makeText(
                            Estadisticas.this,
                            String.format("El usuario %s no existe", usuario),
                            Toast.LENGTH_SHORT).show();
                    limpiarEstadisticas();
                    ret = false;
                } else {
                    // Lanzamos un hilo que actualiza las stats individuales y generales
                    new Thread(() -> {
                        datosIndividuales = objBD.muestraEstadisticasNombre(usuario);
                        runOnUiThread(() -> iniciarCharts(setDatos(datosIndividuales), true));
                        datosGenerales = objBD.muestraEstadisticasGenerales();
                        runOnUiThread(() -> iniciarCharts(setDatos(datosGenerales), false));
                    }).start();

                    Toast.makeText(
                            Estadisticas.this,
                            "Mostrando las estadisticas del usuario " + usuario,
                            Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(nombreUsuario.getWindowToken(), 0);
                }
            }
            return ret;
        }
    };

    private void iniciarCharts(BarData bd, boolean esIndividual) {
        if (esIndividual) {
            stats_individuales.getAxisLeft().setEnabled(false);
            stats_individuales.getXAxis().setEnabled(false);
            stats_individuales.getAxisRight().setEnabled(false);
            stats_individuales.getLegend().setTextColor(Color.WHITE);
            stats_individuales.getDescription().setEnabled(false);
            stats_individuales.setScaleEnabled(false);
            stats_individuales.setData(bd);
            stats_individuales.invalidate();
            stats_individuales.setVisibility(View.VISIBLE);
            ponerValores("individuales");
        } else {
            stats_generales.getAxisLeft().setEnabled(false);
            stats_generales.getXAxis().setEnabled(false);
            stats_generales.getAxisRight().setEnabled(false);
            stats_generales.getLegend().setTextColor(Color.WHITE);
            stats_generales.getDescription().setEnabled(false);
            stats_generales.setScaleEnabled(false);
            stats_generales.setData(bd);
            stats_generales.invalidate();
            stats_generales.setVisibility(View.VISIBLE);
            ponerValores("generales");
        }
    }

    private void limpiarEstadisticas(){
        TextView tMuertes = findViewById(R.id.textoMuertes1);
        TextView tResurrec = findViewById(R.id.textoResur1);
        TextView tVictorias = findViewById(R.id.textoVictorias1);

        stats_individuales.clear();
        stats_individuales.invalidate();
            stats_individuales.setVisibility(View.GONE);
        tMuertes.setText("");
        tResurrec.setText("");
        tVictorias.setText("");
    }

    private void ponerValores(String estadisticas){
        TextView tMuertes1 = findViewById(R.id.textoMuertes1);
        TextView tResurrec1 = findViewById(R.id.textoResur1);
        TextView tVictorias1 = findViewById(R.id.textoVictorias1);
        TextView tMuertes2 = findViewById(R.id.textoMuertes2);
        TextView tResurrec2 = findViewById(R.id.textoResur2);
        TextView tVictorias2 = findViewById(R.id.textoVictorias2);
        if(estadisticas.equals("individuales")){
            int muertes, resurrecciones, victorias;

            muertes = datosIndividuales[0];
            resurrecciones = datosIndividuales[1];
            victorias = datosIndividuales[2];

            tMuertes1.setText(Integer.toString(muertes));
            tResurrec1.setText(Integer.toString(resurrecciones));
            tVictorias1.setText(Integer.toString(victorias));
        }else if(estadisticas.equals("generales")){
            int muertes, resurrecciones, victorias;

            muertes = datosGenerales[0];
            resurrecciones = datosGenerales[1];
            victorias = datosGenerales[2];

            tMuertes2.setText(Integer.toString(muertes));
            tResurrec2.setText(Integer.toString(resurrecciones));
            tVictorias2.setText(Integer.toString(victorias));
        }
    }

    private BarData setDatos(int[] array) {
        ArrayList<BarEntry> lMuertes = new ArrayList<>();
        ArrayList<BarEntry> lResurrec = new ArrayList<>();
        ArrayList<BarEntry> lVictorias = new ArrayList<>();
        int muertes, resurrecciones, victorias;

        muertes = array[0];
        resurrecciones = array[1];
        victorias = array[2];

        lMuertes.add(new BarEntry(0, muertes));
        lResurrec.add(new BarEntry(2, resurrecciones));
        lVictorias.add(new BarEntry(4, victorias));

        BarDataSet bds_muertes = new BarDataSet(lMuertes, "Muertes");
        bds_muertes.setColor(Color.BLACK);
        bds_muertes.setValueTextColor(Color.WHITE);
        bds_muertes.setValueTextSize(20);

        BarDataSet bds_resurrec = new BarDataSet(lResurrec, "Resurrecciones");
        bds_resurrec.setColor(Color.parseColor("#62B6CB"));
        bds_resurrec.setValueTextColor(Color.BLACK);
        bds_resurrec.setValueTextSize(20);

        BarDataSet bds_victorias = new BarDataSet(lVictorias, "Victorias");
        bds_victorias.setColor(Color.GREEN);
        bds_victorias.setValueTextColor(Color.BLACK);
        bds_victorias.setValueTextSize(20);

        BarData bd = new BarData();
        bd.addDataSet(bds_muertes);
        bd.addDataSet(bds_resurrec);
        bd.addDataSet(bds_victorias);
        bd.setDrawValues(false);

        return bd;
    }
}