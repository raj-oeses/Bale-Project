package com.bhawani.bhawanitraders;

public class Details {
    String item,barcode;
    String cpperpiece,sppercarton,spperpiece;

    public Details(String item, String cpperpiece, String sppercarton, String spperpiece,String barcode) {
        this.item = item;
        this.cpperpiece = cpperpiece;
        this.sppercarton = sppercarton;
        this.spperpiece = spperpiece;
        this.barcode = barcode;
    }

    public String getItem() {
        return item;
    }

    public String getCpperpiece() {
        return cpperpiece;
    }

    public String getSppercarton() {
        return sppercarton;
    }

    public String getSpperpiece() {
        return spperpiece;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setCpperpiece(String cpperpiece) {
        this.cpperpiece = cpperpiece;
    }

    public void setSppercarton(String sppercarton) {
        this.sppercarton = sppercarton;
    }

    public void setSpperpiece(String spperpiece) {
        this.spperpiece = spperpiece;
    }
}
