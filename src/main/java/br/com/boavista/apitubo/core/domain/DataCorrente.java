package br.com.boavista.apitubo.core.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataCorrente {

    public String getDataCorrente() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date a = new Date();
        String data = sdf.format(a);
        return data;
    }

    public String getHoraCorrente() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String hora = sdf.format(new Date());
        return hora;
    }

    public String getDataHoraCorrente() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date a = new Date();
        String data = sdf.format(a);
        return data;
    }
}

