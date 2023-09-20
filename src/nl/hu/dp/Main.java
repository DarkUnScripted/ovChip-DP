package nl.hu.dp;

import nl.hu.dp.data.*;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Connection connection;
    private static ReizigerDAO RDAO;
    private static AdresDAO ADAO;
    private static OVChipkaartDAO OVDAO;

    public static void main(String[] args) {

        try {
            getConnection();

            //Initializing DAO's
            RDAO = new ReizigerDAOPsql(connection);
            ADAO = new AdresDAOPsql(connection);
            OVDAO = new OVChipkaartDAOPsql(connection);

            //Testing
            testReizigerDAO(RDAO);
            testAdresDAO(ADAO);
            testOVChipkaartDAO(OVDAO);

            closeConnection();
        }catch (SQLException sqlex){
            System.err.println("Error: " + sqlex);
        }
    }

    public static void getConnection(){
        String url = "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=postgrespw";
        try {
            connection = DriverManager.getConnection(url);
        }catch (SQLException sqlex){
            System.err.println("Error: " + sqlex);
        }
    }

    public static void closeConnection(){
        try {
            connection.close();
        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] " + sqlex);
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger met adres aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres adres = new Adres(20, "3249HS", "5431", "Langelaan", "Utrecht", sietske.getId());
        List<OVChipkaart> ovKaarten = new ArrayList<>();
        OVChipkaart ovKaart = new OVChipkaart(22, java.sql.Date.valueOf("2029-04-10"), 1, 25, 77);
        ovKaarten.add(ovKaart);
        sietske.setAdres(adres);
        sietske.setOvKaarten(ovKaarten);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);

        // Haal alle reizigers op
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        sietske.setTussenvoegsel("van");
        rdao.update(sietske);

        // Haal een enkele reiziger op
        Reiziger r = rdao.findById(77);
        System.out.println(r.toString());

        // Delete een reiziger
        rdao.delete(sietske);
    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Retrieve all adresses from the database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a.toString());
        }
        System.out.println();

        // Create traveler to assign an adress
        Reiziger r = new Reiziger(78, "S", "", "Boers", java.sql.Date.valueOf("2012-04-20"));
        getRDAO().save(r);

        // Create a new adress and assign it to the traveler + persist it.
        Adres adres = new Adres(21, "3249HS", "5431", "Langelaan", "Utrecht", r.getId());
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(adres);

        // Retrieve all adresses
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        // Update the adres with a different value
        adres.setHuisnummer("3241");
        adao.update(adres);

        // Retrieve a single adress
        Adres a = adao.findById(21);
        System.out.println(a.toString());

        // Delete an adress
        getRDAO().delete(getRDAO().findById(78));
    }

    private static void testOVChipkaartDAO(OVChipkaartDAO ovdao) throws SQLException {
        System.out.println("\n---------- Test OVDAO -------------");

        // Retrieve all adresses from the database
        List<OVChipkaart> ovKaarten = ovdao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende adressen:");
        for (OVChipkaart ovKaart : ovKaarten) {
            System.out.println(ovKaart.toString());
        }
        System.out.println();

        // Create a new ov-card and assign it to the traveler + persist it.
        OVChipkaart ovKaart = new OVChipkaart(21, java.sql.Date.valueOf("2029-04-20"), 2, 10, 1);
        System.out.print("[Test] Eerst " + ovKaarten.size() + " adressen, na OVChipkaartDAO.save() ");
        ovdao.save(ovKaart);

        // Retrieve all adresses
        ovKaarten = ovdao.findAll();
        System.out.println(ovKaarten.size() + " adressen\n");

        // Update the adres with a different value
        ovKaart.setSaldo(12);
        ovdao.update(ovKaart);

        // Retrieve a single adress
        OVChipkaart ovChipkaart = ovdao.findById(21);
        System.out.println(ovChipkaart.toString());
    }

    public static ReizigerDAO getRDAO() {
        return RDAO;
    }

    public static AdresDAO getADAO() {
        return ADAO;
    }

    public static OVChipkaartDAO getOVDAO() {
        return OVDAO;
    }
}
