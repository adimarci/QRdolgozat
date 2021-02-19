package com.example.qrdolgozat;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UTFDataFormatException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class Naplozas {


    public static void kiir(String qrText) throws IOException {



        Date datum = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formazottDatum = df.format(datum);

        String sor = String.format("%s,%s",qrText,formazottDatum);

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            File file = new File(Environment.getExternalStorageDirectory(),"scannedCodes.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
          //  BufferedWriter bw = new BufferedWriter(new FileWriter(file, true)StandardCharsets.UTF_8);
            // nem tudtam hogyan kell utf8-á alakitani 16 os api levelen

            bw.append(sor);
            bw.append(System.getProperty("line.separator"));
            bw.close();
        }

    }
}