
package GUI;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConnectDB {
    public Connection getConnection(){
        Connection conn = null;
        try{
            String user = "root";
            String pass = "duchuy2003";
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/connectdb";
            conn = DriverManager.getConnection(url, user, pass);
            if(conn != null){
                return conn;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
}
