package com.iescomercio.menuprincipal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

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
        setContentView(R.layout.estadisticas);
        //objBD = new BaseDatos();
        nombreUsuario = (EditText) findViewById(R.id.nombreUsuarioStats);
        nombreUsuario.setOnEditorActionListener(editorListener);
        //datosGenerales = objBD.muestraEstadisticasGenerales();
        //iniciarCharts(setDatos(datosGenerales), false);
    }

    public void lanzarMenu(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                usuario = nombreUsuario.getText().toString();
                //datosIndividuales = objBD.muestraEstadisticasNombre(usuario);
                //iniciarCharts(setDatos(datosIndividuales), true);
                return true;
            }
            return false;
        }
    };

    private void iniciarCharts(BarData bd, boolean esIndividual){
        if(esIndividual){
            stats_individuales = findViewById(R.id.statsIndividuales);
            stats_individuales.getAxisLeft().setEnabled(false);
            stats_individuales.getXAxis().setEnabled(false);
            stats_individuales.getAxisRight().setEnabled(false);
            stats_individuales.getLegend().setTextColor(Color.WHITE);
            stats_individuales.getDescription().setEnabled(false);
            stats_individuales.setScaleEnabled(false);
            stats_individuales.setData(bd);
            stats_individuales.setDrawValueAboveBar(true);
            stats_individuales.invalidate();
        }else{
            stats_generales = findViewById(R.id.statsGenerales);
            stats_generales.getAxisLeft().setEnabled(false);
            stats_generales.getXAxis().setEnabled(false);
            stats_generales.getAxisRight().setEnabled(false);
            stats_generales.getLegend().setTextColor(Color.WHITE);
            stats_generales.getDescription().setEnabled(false);
            stats_generales.setScaleEnabled(false);
            stats_generales.setData(bd);
            stats_generales.setDrawValueAboveBar(false);
            stats_generales.invalidate();
        }
    }

    private BarData setDatos(int[] array){
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
        bds_muertes.setDrawValues(true);
        bds_muertes.setValueTextColor(Color.WHITE);
        bds_muertes.setValueTextSize(20);

        BarDataSet bds_resurrec = new BarDataSet(lResurrec, "Resurrecciones");
        bds_resurrec.setColor(Color.parseColor("#62B6CB"));
        bds_resurrec.setDrawValues(true);
        bds_resurrec.setValueTextColor(Color.BLACK);
        bds_resurrec.setValueTextSize(20);

        BarDataSet bds_victorias = new BarDataSet(lVictorias, "Victorias");
        bds_victorias.setColor(Color.GREEN);
        bds_victorias.setDrawValues(true);
        bds_victorias.setValueTextColor(Color.BLACK);
        bds_victorias.setValueTextSize(20);

        BarData bd = new BarData();
        bd.addDataSet(bds_muertes);
        bd.addDataSet(bds_resurrec);
        bd.addDataSet(bds_victorias);
        bd.setDrawValues(true);

        return bd;
    }
}

