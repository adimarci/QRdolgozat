package com.example.qrdolgozat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private TextView textResult;
    private Button btnScan,btnKiir;
    private ImageView imageResult;
    private boolean writePermission;
    private String qrText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnScan.setOnClickListener(v -> {

            IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            intentIntegrator.setPrompt("QR CODE SCAN");
            intentIntegrator.setCameraId(0);
            intentIntegrator.setBeepEnabled(false);
            intentIntegrator.setBarcodeImageEnabled(true);
            intentIntegrator.initiateScan();
        });
        btnKiir.setOnClickListener(v -> {

           qrText=textResult.getText().toString();

                try {
                    Naplozas.kiir(qrText);
                    Toast toast1 = Toast.makeText(getApplicationContext(), "sikeres kiírás",
                            Toast.LENGTH_SHORT);

                    toast1.show();
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_SHORT);

                    toast.show();
                    Log.d("Kiiras", e.getMessage());
                    e.printStackTrace();
                }

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result =
                IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Kiléptél a scanből", Toast.LENGTH_SHORT).show();
            } else {
                textResult.setText(result.getContents());

                try {
                    Uri url = Uri.parse(result.getContents());
                    Intent intent = new Intent(Intent.ACTION_VIEW, url);
                    startActivity(intent);
                } catch (Exception exception){
                    Log.d("URI ERROR", exception.toString());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        btnScan = findViewById(R.id.btn_scan);
        btnKiir=findViewById(R.id.btn_kiir);
        textResult = findViewById(R.id.text_Scan_Result);
        imageResult = findViewById(R.id.image_result);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            writePermission = false;
            String[] permissions =
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 1);
        }else{
            writePermission = true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == 0) {
            writePermission =
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_GRANTED;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




    }



