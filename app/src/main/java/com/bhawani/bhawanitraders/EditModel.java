package com.bhawani.bhawanitraders;

public class EditModel {
    String Item,CPPerPiece,SPPerPiece,SPPerCarton,BarCode,Description;

    public EditModel() {
    }

    public EditModel(String item, String CPPerPiece, String SPPerPiece, String SPPerCarton, String barCode, String description) {
        Item = item;
        this.CPPerPiece = CPPerPiece;
        this.SPPerPiece = SPPerPiece;
        this.SPPerCarton = SPPerCarton;
        BarCode = barCode;
        Description = description;
    }

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

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
