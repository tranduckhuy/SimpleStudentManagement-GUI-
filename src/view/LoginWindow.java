
package view;

import dao.ConnectDB;
import dao.LoginDAO;
import java.sql.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.Account;

/**
 *
 * @author ADMIN
 */
public class LoginWindow extends javax.swing.JFrame {

    public LoginWindow() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/user.png")));
        addPlaceHolderStyle(tfUser);
        addPlaceHolderStyle(tfPass);
    }

    private void addPlaceHolderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.gray);

    }

    private void removePlaceHolderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.PLAIN);
        textField.setFont(font);
        textField.setForeground(Color.black);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginPanel = new javax.swing.JPanel();
        txtUser = new javax.swing.JLabel();
        txtPass = new javax.swing.JLabel();
        loginBtn = new javax.swing.JButton();
        tfPass = new javax.swing.JPasswordField();
        tfUser = new javax.swing.JTextField();
        txtSignIn = new javax.swing.JLabel();
        changePwLabel = new javax.swing.JLabel();
        backgroundImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setName("LoginFrame"); // NOI18N
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        loginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtUser.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        txtUser.setForeground(new java.awt.Color(51, 51, 0));
        txtUser.setText("Username");
        loginPanel.add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, -1, 30));

        txtPass.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        txtPass.setForeground(new java.awt.Color(51, 51, 0));
        txtPass.setText("Password");
        loginPanel.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 211, -1, -1));

        loginBtn.setBackground(new java.awt.Color(255, 204, 102));
        loginBtn.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        loginBtn.setForeground(new java.awt.Color(102, 102, 0));
        loginBtn.setText("Login");
        loginBtn.setBorder(null);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusable(false);
        loginBtn.setOpaque(true);
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });
        loginPanel.add(loginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 150, 30));

        tfPass.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tfPass.setText("Password");
        tfPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfPassFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfPassFocusLost(evt);
            }
        });
        tfPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfPassKeyReleased(evt);
            }
        });
        loginPanel.add(tfPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 202, 150, 30));

        tfUser.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tfUser.setText("Username");
        tfUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfUserFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfUserFocusLost(evt);
            }
        });
        loginPanel.add(tfUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 150, 30));

        txtSignIn.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        txtSignIn.setForeground(new java.awt.Color(153, 102, 0));
        txtSignIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user-data.png"))); // NOI18N
        txtSignIn.setText("SIGN IN");
        loginPanel.add(txtSignIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 190, 60));

        changePwLabel.setFont(new java.awt.Font("Serif", 2, 11)); // NOI18N
        changePwLabel.setForeground(new java.awt.Color(255, 0, 51));
        changePwLabel.setText("Change your password?");
        changePwLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changePwLabelMouseClicked(evt);
            }
        });
        loginPanel.add(changePwLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 260, 112, -1));

        backgroundImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/student.jpg"))); // NOI18N
        loginPanel.add(backgroundImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 400));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        String username = tfUser.getText();
        String password = String.valueOf(tfPass.getPassword());
        if (username.equals("Username") || password.equals("Password")) {
            JOptionPane.showMessageDialog(null, "Username or password cannot be empty");
            return;
        }
        try {
            LoginDAO ld = new LoginDAO();
            Account a = (Account) ld.checkLogin(username, password);
            if (a != null) {
                new MainWindow().setVisible(true);
                dispose();
            } else {
                {
                    JOptionPane.showMessageDialog(null,
                            "Username or password is incorrect",
                            "Announcement",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }//GEN-LAST:event_loginBtnActionPerformed

    private void changePwLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changePwLabelMouseClicked
        new ChangePassWindow().setVisible(true);
    }//GEN-LAST:event_changePwLabelMouseClicked

    private void tfUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUserFocusGained
        if (tfUser.getText().equals("Username")) {
            tfUser.setText(null);
            tfUser.requestFocus();
            //remove placeholder style
            removePlaceHolderStyle(tfUser);
        }

    }//GEN-LAST:event_tfUserFocusGained

    private void tfPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfPassFocusGained
        if (String.valueOf(tfPass.getPassword()).equals("Password")) {
            tfPass.setText(null);
            tfPass.requestFocus();
            tfPass.setEchoChar('*');
            //remove placeholder style
            removePlaceHolderStyle(tfPass);
        }
    }//GEN-LAST:event_tfPassFocusGained

    private void tfUserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUserFocusLost
        if (tfUser.getText().length() == 0) {
            //add placeholder style
            addPlaceHolderStyle(tfUser);
            tfUser.setText("Username");
        }
    }//GEN-LAST:event_tfUserFocusLost

    private void tfPassFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfPassFocusLost
        if (String.valueOf(tfPass.getPassword()).length() == 0) {
            //add placeholder style
            addPlaceHolderStyle(tfPass);
            tfPass.setText("Password");
            tfPass.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_tfPassFocusLost

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.requestFocusInWindow();
    }//GEN-LAST:event_formWindowGainedFocus

    private void tfPassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPassKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String username = tfUser.getText();
            String password = String.valueOf(tfPass.getPassword());
            if (username.equals("Username") || password.equals("Password")) {
                JOptionPane.showMessageDialog(null, "Username or password cannot be empty");
                return;
            }
            try {
                LoginDAO ld = new LoginDAO();
                Account a = (Account) ld.checkLogin(username, password);
                if (a != null) {
                    new MainWindow().setVisible(true);
                    dispose();
                } else {
                    {
                        JOptionPane.showMessageDialog(null,
                                "Username or password is incorrect",
                                "Announcement",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_tfPassKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundImage;
    private javax.swing.JLabel changePwLabel;
    private javax.swing.JButton loginBtn;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPasswordField tfPass;
    private javax.swing.JTextField tfUser;
    private javax.swing.JLabel txtPass;
    private javax.swing.JLabel txtSignIn;
    private javax.swing.JLabel txtUser;
    // End of variables declaration//GEN-END:variables
}
