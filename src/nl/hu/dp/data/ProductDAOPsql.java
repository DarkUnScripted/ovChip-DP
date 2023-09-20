package nl.hu.dp.data;

import nl.hu.dp.Main;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private static Connection conn;

    public ProductDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) {
        try{
            String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs)" +
                    "VALUES (?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, product.getProduct_nummer());
            pst.setString(2, product.getNaam());
            pst.setString(3, product.getBeschrijving());
            pst.setInt(4, product.getPrijs());
            pst.executeUpdate();

            for(Integer ovKaartnummer : product.getOvKaartNummers()){
                String query2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer)" +
                        "VALUES (?,?)";
                PreparedStatement pst2 = conn.prepareStatement(query2);
                pst2.setInt(1, ovKaartnummer);
                pst2.setInt(2, product.getProduct_nummer());
                pst2.executeUpdate();
            }

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try{
            String query = "UPDATE product SET product_nummer=?, naam=?, beschrijving=?, prijs=? WHERE product_nummer=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, product.getProduct_nummer());
            pst.setString(2, product.getNaam());
            pst.setString(3, product.getBeschrijving());
            pst.setInt(4, product.getPrijs());
            pst.setInt(5, product.getProduct_nummer());
            pst.executeUpdate();

            String queryDelete = "DELETE FROM ov_chipkaart_product WHERE product_nummer=?";
            PreparedStatement pstDelete = conn.prepareStatement(queryDelete);
            pstDelete.setInt(1, product.getProduct_nummer());
            pstDelete.executeUpdate();

            for(Integer ovKaartnummer : product.getOvKaartNummers()){
                String query2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer)" +
                        "VALUES (?,?)";
                PreparedStatement pst2 = conn.prepareStatement(query2);
                pst2.setInt(1, ovKaartnummer);
                pst2.setInt(2, product.getProduct_nummer());
                pst2.executeUpdate();
            }

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try{
            String query2 = "DELETE FROM ov_chipkaart_product WHERE product_nummer=?";
            PreparedStatement pst2 = conn.prepareStatement(query2);
            pst2.setInt(1, product.getProduct_nummer());
            pst2.executeUpdate();

            String query = "DELETE FROM product WHERE product_nummer=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, product.getProduct_nummer());
            pst.executeUpdate();

            return true;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return false;
        }
    }

    @Override
    public Product findById(int id) {
        try{
            String query = "SELECT * FROM product WHERE product_nummer=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProduct_nummer(rs.getInt("product_nummer"));
                product.setNaam(rs.getString("naam"));
                product.setBeschrijving(rs.getString("beschrijving"));
                product.setPrijs(rs.getInt("prijs"));
                product.setOvKaartNummers(new ArrayList<>());

                String query2 = "SELECT * FROM ov_chipkaart_product op INNER JOIN ov_chipkaart ov ON ov.kaart_nummer=op.kaart_nummer AND op.product_nummer=?";
                PreparedStatement pst2 = conn.prepareStatement(query2);
                pst2.setInt(1, id);
                ResultSet rs2 = pst2.executeQuery();
                while (rs2.next()) {
                    OVChipkaart ovkaart = new OVChipkaart();
                    ovkaart.setKaart_nummer(rs2.getInt("kaart_nummer"));
                    ovkaart.setGeldigTot(rs2.getDate("geldig_tot"));
                    ovkaart.setKlasse(rs2.getInt("kaart_nummer"));
                    ovkaart.setSaldo(rs2.getInt("kaart_nummer"));
                    product.addChipkaart(ovkaart);
                }

                return product;
            }
            return null;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }

    @Override
    public List<Product> findByOvChipkaart(int id) {
        try{
            String query = "SELECT * FROM ov_chipkaart_product WHERE kaart_nummer=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            List<Product> producten = new ArrayList<>();
            while (rs.next()) {
                Integer productId = rs.getInt("product_nummer");
                Product product = findById(productId);
                producten.add(product);
            }

            return producten;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        try{
            String query = "SELECT * FROM product";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<Product> producten = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProduct_nummer(rs.getInt("product_nummer"));
                product.setNaam(rs.getString("naam"));
                product.setBeschrijving(rs.getString("beschrijving"));
                product.setPrijs(rs.getInt("prijs"));
                product.setOvKaartNummers(new ArrayList<>());

                String query2 = "SELECT * FROM ov_chipkaart_product op INNER JOIN ov_chipkaart ov ON ov.kaart_nummer=op.kaart_nummer AND op.product_nummer=?";
                PreparedStatement pst2 = conn.prepareStatement(query2);
                pst2.setInt(1, product.getProduct_nummer());
                ResultSet rs2 = pst2.executeQuery();
                while (rs2.next()) {
                    OVChipkaart ovkaart = new OVChipkaart();
                    ovkaart.setKaart_nummer(rs2.getInt("kaart_nummer"));
                    ovkaart.setGeldigTot(rs2.getDate("geldig_tot"));
                    ovkaart.setKlasse(rs2.getInt("kaart_nummer"));
                    ovkaart.setSaldo(rs2.getInt("kaart_nummer"));
                    product.addChipkaart(ovkaart);
                }

                producten.add(product);
            }
            return producten;

        }catch (SQLException sqlex){
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
            return null;
        }
    }
}
