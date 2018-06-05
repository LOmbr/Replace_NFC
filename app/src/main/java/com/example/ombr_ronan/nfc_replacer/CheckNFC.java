package com.example.ombr_ronan.nfc_replacer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CheckNFC extends AppCompatActivity {

    BDD bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_nfc);
    }

    public void onBackPressed() {
        this.finish();
    }
}
