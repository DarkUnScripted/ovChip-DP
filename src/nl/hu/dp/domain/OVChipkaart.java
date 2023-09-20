package nl.hu.dp.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {

    private int kaart_nummer;
    private Date geldigTot;
    private int klasse;
    private int saldo;
    private int reizigerId;
    private List<Product> producten;

    public OVChipkaart(){
        this.producten = new ArrayList<>();
    }

    public OVChipkaart(int kaart_nummer, Date geldigTot, int klasse, int saldo, int reizigerId){
        this.kaart_nummer = kaart_nummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reizigerId = reizigerId;
        this.producten = new ArrayList<>();
    }


    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public int getSaldo() {
        return saldo;
    }

    public int getReizigerId() {
        return reizigerId;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void setReizigerId(int reizigerId) {
        this.reizigerId = reizigerId;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    public void addProduct(Product product) {
        this.producten.add(product);
        product.addChipkaart(this);
    }

    public void removeProduct(Product product){
        product.removeChipkaart(this);
        this.producten.remove(product);
    }

    @Override
    public String toString() {
        return "OVKaart: #" + this.kaart_nummer + " { Saldo: " + this.saldo + ", Geldig tot: " + this.geldigTot + ", Klasse: " + this.klasse + " Producten: \n    " + producten.toString() + "\n}";
    }
}