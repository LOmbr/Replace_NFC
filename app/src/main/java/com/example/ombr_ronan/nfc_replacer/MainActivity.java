package com.example.ombr_ronan.nfc_replacer;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.nio.charset.Charset;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openEmetteur(View v) {
        Intent intent = new Intent(this, Emetteur.class);
        startActivity(intent);
    }

    public void openRecepteur(View v) {
        Intent intent = new Intent(this, Recepteur.class);
        startActivity(intent);
    }
}
