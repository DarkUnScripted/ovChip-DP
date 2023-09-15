package nl.hu.dp.data;

import nl.hu.dp.Main;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{
    private static Connection conn;

    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try{
            String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum)" +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement pst = ReizigerDAOPsql.conn.prepareStatement(query);
            pst.setInt(1, reiziger.getId());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());
            pst.executeUpdate();

            if(reiziger.getAdres() != null) {
                Main.getADAO().save(reiziger.getAdres());
            }

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }

    }

    @Override
    public boolean update(Reiziger reiziger) {
        try{
            if(reiziger.getAdres() != null) Main.getADAO().update(reiziger.getAdres());

            String query = "UPDATE reiziger SET reiziger_id=?, voorletters=?, tussenvoegsel=?, achternaam=?, geboortedatum=? WHERE reiziger_id=?";
            PreparedStatement pst = ReizigerDAOPsql.conn.prepareStatement(query);
            pst.setInt(1, reiziger.getId());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());
            pst.setInt(6, reiziger.getId());
            pst.executeUpdate();

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{

            if(reiziger.getAdres() != null) Main.getADAO().delete(reiziger.getAdres());

            String query = "DELETE FROM reiziger WHERE reiziger_id=?";
            PreparedStatement pst = ReizigerDAOPsql.conn.prepareStatement(query);
            pst.setInt(1, reiziger.getId());
            pst.executeUpdate();

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try{
            String query = "SELECT * FROM reiziger WHERE reiziger_id=?";
            PreparedStatement pst = ReizigerDAOPsql.conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            Reiziger reiziger = new Reiziger();
            while(rs.next()) {
                reiziger.setId(rs.getInt("reiziger_id"));
                reiziger.setVoorletters(rs.getString("voorletters"));
                reiziger.setTussenvoegsel(rs.getString("tussenvoegsel"));
                reiziger.setAchternaam(rs.getString("achternaam"));
                reiziger.setGeboortedatum(rs.getDate("geboortedatum"));
            }
            Adres adres = Main.getADAO().findByReiziger(reiziger);
            reiziger.setAdres(adres);

            return reiziger;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> reizigers = new ArrayList<Reiziger>();
        try{
            String query = "SELECT * FROM reiziger WHERE geboortedatum=?";
            PreparedStatement pst = ReizigerDAOPsql.conn.prepareStatement(query);
            pst.setString(1, datum);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reiziger reiziger = new Reiziger();
                reiziger.setId(rs.getInt("reiziger_id"));
                reiziger.setVoorletters(rs.getString("voorletters"));
                reiziger.setTussenvoegsel(rs.getString("tussenvoegsel"));
                reiziger.setAchternaam(rs.getString("achternaam"));
                reiziger.setGeboortedatum(rs.getDate("geboortedatum"));

                Adres adres = Main.getADAO().findByReiziger(reiziger);
                reiziger.setAdres(adres);

                reizigers.add(reiziger);
            }

            return reizigers;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<Reiziger>();
        try{
            String query = "SELECT * FROM reiziger";
            PreparedStatement pst = ReizigerDAOPsql.conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reiziger reiziger = new Reiziger();
                reiziger.setId(rs.getInt("reiziger_id"));
                reiziger.setVoorletters(rs.getString("voorletters"));
                reiziger.setTussenvoegsel(rs.getString("tussenvoegsel"));
                reiziger.setAchternaam(rs.getString("achternaam"));
                reiziger.setGeboortedatum(rs.getDate("geboortedatum"));

                Adres adres = Main.getADAO().findByReiziger(reiziger);
                reiziger.setAdres(adres);

                reizigers.add(reiziger);
            }

            return reizigers;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }
}
