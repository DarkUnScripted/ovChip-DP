package nl.hu.dp;

import nl.hu.dp.data.AdresDAO;
import nl.hu.dp.data.AdresDAOPsql;
import nl.hu.dp.data.ReizigerDAO;
import nl.hu.dp.data.ReizigerDAOPsql;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.Reiziger;

import java.sql.*;
import java.util.List;

public class Main {

    private static Connection connection;
    private static ReizigerDAO RDAO;
    private static AdresDAO ADAO;

    public static void main(String[] args) {

        try {
            getConnection();

            //Initializing DAO's
            RDAO = new ReizigerDAOPsql(connection);
            ADAO = new AdresDAOPsql(connection);

            //Testing
            testReizigerDAO(RDAO);
            testAdresDAO(ADAO);

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
        sietske.setAdres(adres);
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

    public static ReizigerDAO getRDAO() {
        return RDAO;
    }

    public static AdresDAO getADAO() {
        return ADAO;
    }
}
