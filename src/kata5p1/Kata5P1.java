package kata5p1;
import java.sql.*;
import java.util.List;

public class Kata5P1 {

    public static void main(String[] args) throws SQLException {
        connect();
        createNewTable();
        
        String fichero = "email.txt";
        List<String> lista = new MailListReader().read(fichero);

        String url = "jdbc:sqlite:Kata5.db";
        String sql= "INSERT INTO direcc_email(direccion) VALUES(?)";
        try (Connection conn = DriverManager.getConnection(url) ;
            PreparedStatement pstmt= conn.prepareStatement(sql)) {
            for (String list: lista) {
                pstmt.setString(1, list);
                pstmt.executeUpdate();
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:Kata5.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connexión a SQLite establecida");
            selectAll(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }
    
    private static void selectAll(Connection conn){
        String sql = "SELECT * FROM PEOPLE";
        try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                rs.getString("Name") + "\t" +
                rs.getString("Apellidos") + "\t" +
                rs.getString("Departamento") + "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createNewTable(){
        String url= "jdbc:sqlite:Kata5.db";
        String sql = "CREATE TABLE IF NOT EXISTS direcc_email (\n"
                + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " direccion text NOT NULL);";
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla creada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
}
