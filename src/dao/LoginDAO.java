
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Account;

/**
 *
 * @author ADMIN
 */
public class LoginDAO {

    private final ConnectDB cn = new ConnectDB();
    private Connection conn = null;
    private PreparedStatement ps = null;

    public Account checkLogin(String username, String password) {
        try {
            conn = (Connection)cn.getConnection();
            if(conn != null) {
                String query = "SELECT * FROM user WHERE username = ? and password = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()) {
                    Account a = new Account(rs.getString(1), rs.getString(2));
                    return a;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
}
