package com.example.ombr_ronan.nfc_replacer;

/**
 * Created by Ombr-Ronan on 05/06/2018.
 */

public class NFC {
    int id;
    String name;
    String tag;


    //Constructeur par défaut
    NFC() {
        this(0, "default", "default");
    }
    //Constructeur par paramètres
    NFC(int id, String name, String tag) {
        this.id = id;
        this.name = name;
        this.tag = tag;
    }

    //Constructeur par recopie
    NFC(NFC nfc) {
        this(nfc.id, nfc.name, nfc.tag);
    }


    /********************* GETTER & SETTER *********************/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    /***********************************************************/
}
