
package GUI;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConnectDB {
    public Connection getConnection(){
        Connection conn;
        try{
            String user = "root";
            String pass = "duchuy2003";
            Class.forName("com.mysql.cj.jdbc.Driver");
            String URL = "jdbc:mysql://localhost:3306/connectdb";
            conn = DriverManager.getConnection(URL, user, pass);
            if(conn != null){
                return conn;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
}
