package com.example.ombr_ronan.nfc_replacer;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Recepteur extends AppCompatActivity {



    NfcAdapter adapter;
    PendingIntent mPendingIntent;

    BDD bdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepteur);


        bdd = new BDD(this);
        bdd.open();

        Intent intent;
        intent = this.getIntent();

        NfcManager manager = (NfcManager) this.getSystemService(Context.NFC_SERVICE);
        adapter = manager.getDefaultAdapter();

        if (adapter != null) {

            if(adapter.isEnabled()) {
                if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
                    Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                }
            }
            else
            {
                this.finish();
                Intent intent2 = new Intent(this, CheckNFC.class);
                startActivity(intent2);
            }
        }
        else
        {
            this.finish();
            Intent intent2 = new Intent(this, CheckNFC.class);
            startActivity(intent2);
        }

        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onNewIntent(Intent intent){
        getTagInfo(intent);
    }

    private void getTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] uid = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);

        String s = new BigInteger(uid).toString(16);


        Intent i = new Intent(this, AjoutActivity.class);
        i.putExtra("TAG", s);
        startActivity(i);
        this.finish();


    }

    /********************* VIE *********************/
    @Override
    public void onBackPressed() {
        bdd.close();
        this.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bdd.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.disableForegroundDispatch(this);
        }
    }
    /***********************************************/


    public void afficherMessage(String s) { Toast.makeText(this, s ,Toast.LENGTH_LONG).show(); }
}
