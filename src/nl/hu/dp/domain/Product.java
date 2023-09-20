package nl.hu.dp.domain;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int product_nummer;
    private String naam;
    private String beschrijving;
    private int prijs;
    private List<Integer> ovKaartNummers;

    public Product(){
        this.ovKaartNummers = new ArrayList<>();
    }

    public Product(int product_nummer, String naam, String beschrijving, int prijs){
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovKaartNummers = new ArrayList<>();
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public int getPrijs() {
        return prijs;
    }

    public List<Integer> getOvKaartNummers() {
        return ovKaartNummers;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

    public void setOvKaartNummers(List<Integer> ovKaartNummers) {
        this.ovKaartNummers = ovKaartNummers;
    }

    public void addChipkaart(OVChipkaart ovChipkaart){
        this.ovKaartNummers.add(ovChipkaart.getKaart_nummer());
    }

    public void removeChipkaart(OVChipkaart ovChipkaart) {
        this.ovKaartNummers.remove(Integer.valueOf(ovChipkaart.getKaart_nummer()));
    }

    @Override
    public String toString() {
        return "Product: #" + product_nummer + " { Naam: " + naam + ", Prijs: " + prijs + ", OV-Kaarten:\n    " + ovKaartNummers.toString() +"\n}";
    }
}
