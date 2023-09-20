package nl.hu.dp.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovKaarten = new ArrayList<>();


    public Reiziger(){

    }

    public Reiziger(int id, String initials, String infix, String surname, Date birthdate) {
        this.id = id;
        this.voorletters = initials;
        this.tussenvoegsel = infix;
        this.achternaam = surname;
        this.geboortedatum = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOvKaarten() {
        return ovKaarten;
    }

    public void setOvKaarten(List<OVChipkaart> ovKaarten) {
        this.ovKaarten = ovKaarten;
    }

    public String getNaam(){
        return this.voorletters + ". " + (this.tussenvoegsel != null ? this.tussenvoegsel + " " : "") + this.achternaam;
    }

    @Override
    public String toString() {
        return "     #" + this.getId() + " " + this.getNaam() + " (" + this.geboortedatum + ") " + adres.toString();
    }
}
