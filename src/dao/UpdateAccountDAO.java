
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
public class UpdateAccountDAO {
    private final ConnectDB cn = new ConnectDB();
    private Connection conn = null;
    private PreparedStatement ps = null;

    public void updateAccount(String username, String newPassword) {
        try {
            conn = (Connection)cn.getConnection();
            if(conn != null) {
                String query = "UPDATE user SET password = ? WHERE username = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, newPassword);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
