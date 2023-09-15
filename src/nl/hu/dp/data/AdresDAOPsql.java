package nl.hu.dp.data;

import nl.hu.dp.Main;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private static Connection conn;

    public AdresDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) {

        try{

            String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id)" +
                    "VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, adres.getId());
            pst.setString(2, adres.getPostcode());
            pst.setString(3, adres.getHuisnummer());
            pst.setString(4, adres.getStraat());
            pst.setString(5, adres.getWoonplaats());
            pst.setInt(6, adres.getReizigerId());
            pst.executeUpdate();

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }

    }

    @Override
    public boolean update(Adres adres) {

        try{

            String query = "UPDATE adres SET adres_id=?, postcode=?, huisnummer=?, straat=?, woonplaats=?, reiziger_id=? WHERE adres_id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, adres.getId());
            pst.setString(2, adres.getPostcode());
            pst.setString(3, adres.getHuisnummer());
            pst.setString(4, adres.getStraat());
            pst.setString(5, adres.getWoonplaats());
            pst.setInt(6, adres.getReizigerId());
            pst.setInt(7, adres.getId());
            pst.executeUpdate();

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {

        try{


            String query = "DELETE FROM adres WHERE adres_id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, adres.getId());
            pst.executeUpdate();

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }
    }

    @Override
    public Adres findById(int id) {
        try{

            String query = "SELECT * FROM adres WHERE adres_id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Adres adres = new Adres();
                adres.setId(rs.getInt("adres_id"));
                adres.setHuisnummer(rs.getString("huisnummer"));
                adres.setPostcode(rs.getString("postcode"));
                adres.setStraat(rs.getString("straat"));
                adres.setWoonplaats(rs.getString("woonplaats"));
                adres.setReizigerId(rs.getInt("reiziger_id"));
                return adres;
            }
            return null;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try{

            String query = "SELECT * FROM adres WHERE reiziger_id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reiziger.getId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Adres adres = new Adres();
                adres.setId(rs.getInt("adres_id"));
                adres.setHuisnummer(rs.getString("huisnummer"));
                adres.setPostcode(rs.getString("postcode"));
                adres.setStraat(rs.getString("straat"));
                adres.setWoonplaats(rs.getString("woonplaats"));
                adres.setReizigerId(reiziger.getId());
                return adres;
            }
            return null;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adressen = new ArrayList<Adres>();
        try{

            String query = "SELECT * FROM adres";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Adres adres = new Adres();
                adres.setId(rs.getInt("adres_id"));
                adres.setHuisnummer(rs.getString("huisnummer"));
                adres.setPostcode(rs.getString("postcode"));
                adres.setStraat(rs.getString("straat"));
                adres.setWoonplaats(rs.getString("woonplaats"));
                adres.setReizigerId(rs.getInt("reiziger_id"));
                adressen.add(adres);
            }

            return adressen;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }
}