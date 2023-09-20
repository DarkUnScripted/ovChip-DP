package nl.hu.dp.data;

import nl.hu.dp.Main;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    private static Connection conn;

    public OVChipkaartDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try{

            String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)" +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, ovChipkaart.getKaart_nummer());
            pst.setDate(2, ovChipkaart.getGeldigTot());
            pst.setInt(3, ovChipkaart.getKlasse());
            pst.setInt(4, ovChipkaart.getSaldo());
            pst.setInt(5, ovChipkaart.getReizigerId());
            pst.executeUpdate();

            for (Product product : ovChipkaart.getProducten()){
                Main.getPDAO().save(product);
            }
            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try{

            String query = "UPDATE ov_chipkaart SET kaart_nummer=?, geldig_tot=?, klasse=?, saldo=?, reiziger_id=? WHERE kaart_nummer=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, ovChipkaart.getKaart_nummer());
            pst.setDate(2, ovChipkaart.getGeldigTot());
            pst.setInt(3, ovChipkaart.getKlasse());
            pst.setInt(4, ovChipkaart.getSaldo());
            pst.setInt(5, ovChipkaart.getReizigerId());
            pst.setInt(6, ovChipkaart.getKaart_nummer());
            pst.executeUpdate();

            for (Product product : ovChipkaart.getProducten()){
                Main.getPDAO().update(product);
            }

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try{

            String query2 = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer=?";
            PreparedStatement pst2 = conn.prepareStatement(query2);
            pst2.setInt(1, ovChipkaart.getKaart_nummer());
            pst2.executeUpdate();

            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, ovChipkaart.getKaart_nummer());
            pst.executeUpdate();

            for (Product product : ovChipkaart.getProducten()){
                product.removeChipkaart(ovChipkaart);
                Main.getPDAO().update(product);
            }

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }
    }

    @Override
    public OVChipkaart findById(int id) {
        try{

            String query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart();
                ovChipkaart.setKaart_nummer(rs.getInt("kaart_nummer"));
                ovChipkaart.setGeldigTot(rs.getDate("geldig_tot"));
                ovChipkaart.setKlasse(rs.getInt("klasse"));
                ovChipkaart.setSaldo(rs.getInt("saldo"));
                ovChipkaart.setReizigerId(rs.getInt("reiziger_id"));
                ovChipkaart.setProducten(Main.getPDAO().findByOvChipkaart(id));
                return ovChipkaart;
            }
            return null;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try{

            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reiziger.getId());
            ResultSet rs = pst.executeQuery();
            List<OVChipkaart> ovKaarten = new ArrayList<>();
            while (rs.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart();
                ovChipkaart.setKaart_nummer(rs.getInt("kaart_nummer"));
                ovChipkaart.setGeldigTot(rs.getDate("geldig_tot"));
                ovChipkaart.setKlasse(rs.getInt("klasse"));
                ovChipkaart.setSaldo(rs.getInt("saldo"));
                ovChipkaart.setReizigerId(reiziger.getId());
                ovChipkaart.setProducten(Main.getPDAO().findByOvChipkaart(rs.getInt("kaart_nummer")));
                ovKaarten.add(ovChipkaart);
            }
            return ovKaarten;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        try{

            String query = "SELECT * FROM ov_chipkaart";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<OVChipkaart> ovKaarten = new ArrayList<>();
            while (rs.next()) {
                OVChipkaart ovKaart = new OVChipkaart();
                ovKaart.setKaart_nummer(rs.getInt("kaart_nummer"));
                ovKaart.setGeldigTot(rs.getDate("geldig_tot"));
                ovKaart.setKlasse(rs.getInt("klasse"));
                ovKaart.setSaldo(rs.getInt("saldo"));
                ovKaart.setReizigerId(rs.getInt("reiziger_id"));
                ovKaart.setProducten(Main.getPDAO().findByOvChipkaart(rs.getInt("kaart_nummer")));
                ovKaarten.add(ovKaart);
            }

            return ovKaarten;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }
}
