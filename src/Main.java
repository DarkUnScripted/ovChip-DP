import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:49154/ovchip?user=postgres&password=postgrespw";

        try{
            Connection conn = DriverManager.getConnection(url);

            Statement st = conn.createStatement();
            String query = "SELECT * FROM reiziger";

            ResultSet rs = st.executeQuery(query);

            String naam;
            Date geboortedatum;
            int id;

            System.out.println("Alle reizigers:");
            while(rs.next()){
                if(rs.getString("tussenvoegsel") != null){
                    naam = rs.getString("voorletters") + ". " + rs.getString("tussenvoegsel") + " " + rs.getString("achternaam");
                }else {
                    naam = rs.getString("voorletters") + ". " + rs.getString("achternaam");
                }
                geboortedatum = rs.getDate("geboortedatum");
                id = rs.getInt("reiziger_id");
                System.out.println("      #" + id + ": " + naam + " (" + geboortedatum + ")");
            }

        } catch (SQLException sqlex) {
            System.err.println("[SQLERROR] Something went wrong! Error: " + sqlex);
        }

    }
}
