package com.bhawani.bhawanitraders;

public class SearchModel {
    String Item;
    String CPPerPiece;
    String SPPerPiece;
    String SPPerCarton;
    String Description;
    String BarCode;
    int Image;
    public SearchModel() {
    }
    /*@Override
    public String toString() {
        return "RecycleViewGetterSetters{" +
                "Item='" + Item + '\'' +
                ", CPPerPiece='" + CPPerPiece + '\'' +
                ", SPPerPiece='" + SPPerPiece + '\'' +
                ", SPPerCarton='" + SPPerCarton + '\'' +
                ", Description='" + Description + '\'' +
                ", BarCode='" + BarCode + '\'' +
                '}';
    }*/

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getCPPerPiece() {
        return CPPerPiece;
    }

    public void setCPPerPiece(String CPPerPiece) {
        this.CPPerPiece = CPPerPiece;
    }

    public String getSPPerPiece() {
        return SPPerPiece;
    }

    public void setSPPerPiece(String SPPerPiece) {
        this.SPPerPiece = SPPerPiece;
    }

    public String getSPPerCarton() {
        return SPPerCarton;
    }

    public void setSPPerCarton(String SPPerCarton) {
        this.SPPerCarton = SPPerCarton;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }
}
