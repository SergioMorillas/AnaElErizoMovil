package com.iescomercio.menuprincipal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.charts.*;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity {

    HorizontalBarChart stats_individuales, stats_generales;
    BarData bd_individuales, bd_generales;
    int[] datosPrueba = {3,23,13};
    int[] datosPrueba2 = {74,34,84};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);

        bd_individuales = setDatos(datosPrueba);
        bd_generales = setDatos(datosPrueba2);

        stats_individuales = findViewById(R.id.statsIndividuales);
        stats_individuales.getAxisLeft().setEnabled(false);
        stats_individuales.getXAxis().setEnabled(false);
        stats_individuales.getAxisRight().setEnabled(false);
        stats_individuales.getLegend().setTextColor(Color.WHITE);
        stats_individuales.setBackgroundColor(Color.parseColor("#9C000000"));
        stats_individuales.getDescription().setEnabled(false);
        stats_individuales.setData(bd_individuales);
        stats_individuales.invalidate();

        stats_generales = findViewById(R.id.statsGenerales);
        stats_generales.getAxisLeft().setEnabled(false);
        stats_generales.getXAxis().setEnabled(false);
        stats_generales.getAxisRight().setEnabled(false);
        stats_generales.getLegend().setTextColor(Color.WHITE);
        stats_generales.setBackgroundColor(Color.parseColor("#9C000000"));
        stats_generales.getDescription().setEnabled(false);
        stats_generales.setData(bd_generales);
        stats_generales.invalidate();
    }

    public void lanzarMenu(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private BarData setDatos(int[] array){
        ArrayList<BarEntry> lMuertes = new ArrayList<>();
        ArrayList<BarEntry> lResurrec = new ArrayList<>();
        ArrayList<BarEntry> lVictorias = new ArrayList<>();

        lMuertes.add(new BarEntry(0,array[0]));
        lResurrec.add(new BarEntry(2,array[1]));
        lVictorias.add(new BarEntry(4,array[2]));

        BarDataSet bds_muertes = new BarDataSet(lMuertes, "Muertes");
        bds_muertes.setDrawValues(true);
        bds_muertes.setColor(Color.RED);
        bds_muertes.setValueTextColor(Color.WHITE);
        bds_muertes.setValueTextSize(20);

        BarDataSet bds_resurrec = new BarDataSet(lResurrec, "Resurrecciones");
        bds_resurrec.setDrawValues(true);
        bds_resurrec.setColor(Color.BLUE);
        bds_resurrec.setValueTextColor(Color.WHITE);
        bds_resurrec.setValueTextSize(20);

        BarDataSet bds_victorias = new BarDataSet(lVictorias, "Victorias");
        bds_victorias.setDrawValues(true);
        bds_victorias.setColor(Color.MAGENTA);
        bds_victorias.setValueTextColor(Color.WHITE);
        bds_victorias.setValueTextSize(20);

        BarData bardata_individuales = new BarData();
        bardata_individuales.addDataSet(bds_muertes);
        bardata_individuales.addDataSet(bds_resurrec);
        bardata_individuales.addDataSet(bds_victorias);
        bardata_individuales.setDrawValues(true);

        return bardata_individuales;
    }

    private BarDataSet obtenerMuertesIndividuales(){
        ArrayList<BarEntry> datos = new ArrayList<>();
        datos.add(new BarEntry(0,120));
        BarDataSet set = new BarDataSet(datos, "Muertes");
        set.setColor(Color.RED);
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(20);
        return set;
    }

    private BarDataSet obtenerResurreccionesIndividuales(){
        ArrayList<BarEntry> datos = new ArrayList<>();
        datos.add(new BarEntry(3,6));
        BarDataSet set = new BarDataSet(datos, "Resurrecciones");
        set.setColor(Color.BLUE);
        return set;
    }

    private BarDataSet obtenerVictoriasIndividuales(){
        ArrayList<BarEntry> datos = new ArrayList<>();
        datos.add(new BarEntry(6,3));
        BarDataSet set = new BarDataSet(datos, "Victorias");
        set.setColor(Color.MAGENTA);
        return set;
    }
}

