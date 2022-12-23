/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends javax.swing.JFrame {

    private final ConnectDB cn = new ConnectDB();
    private final Connection conn = cn.getConnection();
    
    public MainWindow() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/stManager.png")));
        init();
    }
    
    private void init(){
        String query = "SELECT idStudent, name, gender, dob, class, email, phone, address FROM student";
        try{        
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)inforStudentTabel.getModel();
            while(rs.next()){
                model.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8)});
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private boolean validInfor(String act){
        if(tfId.getText().equals("")){
            JOptionPane.showMessageDialog(null, "     Student's id is empty");
            return false;
        }else{
            String query = "SELECT * FROM student WHERE idStudent = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, tfId.getText());
                if(ps.executeQuery().next() && act.equals("add")){
                    JOptionPane.showMessageDialog(null, "     The ID already existed");
                    return false;
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if(tfName.getText().equals("")){
            JOptionPane.showMessageDialog(null, "    Student's name is empty");
            return false;
        }
        if(tfEmail.getText().equals("")){
            JOptionPane.showMessageDialog(null, "    Student's email is empty");
            return false;
        }else{
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
            Pattern pat = Pattern.compile(emailRegex);
            if(pat.matcher(tfEmail.getText()).matches()){
                String query = "SELECT * FROM student WHERE email = ?";
                try {
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, tfEmail.getText());
                    if(ps.executeQuery().next() && act.equals("add")){
                        JOptionPane.showMessageDialog(null, "     The Email already existed");
                        return false;
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                } 
            }else{
                JOptionPane.showMessageDialog(null, "     Student's email is invalid");
                return false;
            }
        }
        if(tfPhone.getText().equals("")){
            JOptionPane.showMessageDialog(null, "    Student's phone is empty");
            return false;
        }else{
            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(tfPhone.getText()); 
            if(!matcher.matches()) {
                JOptionPane.showMessageDialog(null, " Student's phone number is invalid");
                return false;
            }
            String query = "SELECT * FROM student WHERE phone = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, tfPhone.getText());
                if(ps.executeQuery().next() && act.equals("add")){
                    JOptionPane.showMessageDialog(null, " The phone number already existed");
                    return false;
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if(tfAddress.getText().equals("")){
            JOptionPane.showMessageDialog(null, "    Student's address is empty");
            return false;
        }
        if(dateChooser.getDate() == null){
            JOptionPane.showMessageDialog(null, " Student's date of birth is empty");
            return false;
        }else if(dateChooser.getDate().compareTo(new Date()) > 0){
            JOptionPane.showMessageDialog(null, "   No student from the future are allowed!");
            return false;
        }        
        return true;    
    }
    
    private void table_load(){
        String query = "SELECT idStudent, name, gender, dob, class, email, phone, address FROM student";
        try{        
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)inforStudentTabel.getModel();
            model.setRowCount(0);
            while(rs.next()){
                model.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8)});
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        studentPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtId = new javax.swing.JLabel();
        tfId = new javax.swing.JTextField();
        txtName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        txtGender = new javax.swing.JLabel();
        maleBtn = new javax.swing.JRadioButton();
        femaleBtn = new javax.swing.JRadioButton();
        txtEmail = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        txtPhone = new javax.swing.JLabel();
        tfPhone = new javax.swing.JTextField();
        txtAddress = new javax.swing.JLabel();
        tfAddress = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        dateChooser = new com.toedter.calendar.JDateChooser();
        txtDob = new javax.swing.JLabel();
        txtClass = new javax.swing.JLabel();
        classComboBox = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        clearBtn = new javax.swing.JButton();
        searchPanel = new javax.swing.JPanel();
        sortBtn = new javax.swing.JButton();
        refreshBtn = new javax.swing.JButton();
        tfSearch = new javax.swing.JTextField();
        searchLabel = new javax.swing.JLabel();
        searchIcon = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        inforStudentTabel = new javax.swing.JTable();
        coursePanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtId1 = new javax.swing.JLabel();
        tfId1 = new javax.swing.JTextField();
        txtName1 = new javax.swing.JLabel();
        tfName1 = new javax.swing.JTextField();
        txtGender1 = new javax.swing.JLabel();
        tfEmail1 = new javax.swing.JTextField();
        txtPhone1 = new javax.swing.JLabel();
        tfPhone1 = new javax.swing.JTextField();
        txtAddress1 = new javax.swing.JLabel();
        addBtn1 = new javax.swing.JButton();
        editBtn1 = new javax.swing.JButton();
        deleteBtn1 = new javax.swing.JButton();
        printBtn1 = new javax.swing.JButton();
        txtClass1 = new javax.swing.JLabel();
        semester = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        tfName2 = new javax.swing.JTextField();
        txtGender2 = new javax.swing.JLabel();
        txtAddress2 = new javax.swing.JLabel();
        tfAddress2 = new javax.swing.JTextField();
        txtAddress3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        headerPanel = new javax.swing.JPanel();
        headerLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        tabbedPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tabbedPane.setFocusable(false);
        tabbedPane.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N

        studentPanel.setBackground(new java.awt.Color(255, 204, 153));

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        txtId.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtId.setForeground(new java.awt.Color(204, 255, 204));
        txtId.setText("ID");

        tfId.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        txtName.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtName.setForeground(new java.awt.Color(204, 255, 204));
        txtName.setText("Name");

        tfName.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tfName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        txtGender.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtGender.setForeground(new java.awt.Color(204, 255, 204));
        txtGender.setText("Gender");

        maleBtn.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(maleBtn);
        maleBtn.setSelected(true);
        maleBtn.setText("Male");
        maleBtn.setFocusable(false);

        femaleBtn.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(femaleBtn);
        femaleBtn.setText("Female");
        femaleBtn.setFocusable(false);

        txtEmail.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(204, 255, 204));
        txtEmail.setText("Email");

        tfEmail.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        txtPhone.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtPhone.setForeground(new java.awt.Color(204, 255, 204));
        txtPhone.setText("Phone");

        tfPhone.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        txtAddress.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtAddress.setForeground(new java.awt.Color(204, 255, 204));
        txtAddress.setText("Address");

        tfAddress.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addBtn.setForeground(new java.awt.Color(102, 102, 0));
        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        addBtn.setText(" Add");
        addBtn.setFocusable(false);
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        editBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        editBtn.setForeground(new java.awt.Color(102, 102, 0));
        editBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit.png"))); // NOI18N
        editBtn.setText("Edit  ");
        editBtn.setEnabled(false);
        editBtn.setFocusable(false);
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        deleteBtn.setForeground(new java.awt.Color(102, 102, 0));
        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        deleteBtn.setText("Delete");
        deleteBtn.setEnabled(false);
        deleteBtn.setFocusable(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        printBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        printBtn.setForeground(new java.awt.Color(102, 102, 0));
        printBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/print.png"))); // NOI18N
        printBtn.setText(" Print ");
        printBtn.setFocusable(false);
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
            }
        });

        logoutBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        logoutBtn.setForeground(new java.awt.Color(102, 102, 0));
        logoutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logout.png"))); // NOI18N
        logoutBtn.setText("Logout");
        logoutBtn.setFocusable(false);
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        txtDob.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtDob.setForeground(new java.awt.Color(204, 255, 204));
        txtDob.setText("Date of birth");

        txtClass.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtClass.setForeground(new java.awt.Color(204, 255, 204));
        txtClass.setText("Class");

        classComboBox.setBackground(new java.awt.Color(51, 153, 255));
        classComboBox.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        classComboBox.setForeground(new java.awt.Color(102, 153, 0));
        classComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SE1", "SE2", "SE3", "SE4" }));
        classComboBox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204)));
        classComboBox.setFocusable(false);

        jSeparator2.setBackground(new java.awt.Color(51, 153, 255));
        jSeparator2.setForeground(new java.awt.Color(51, 0, 0));

        clearBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clearBtn.setForeground(new java.awt.Color(102, 102, 0));
        clearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/clear.png"))); // NOI18N
        clearBtn.setText("Clear");
        clearBtn.setFocusable(false);
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGender)
                            .addComponent(txtEmail)
                            .addComponent(txtPhone)
                            .addComponent(txtAddress)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtClass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtDob)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(maleBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(femaleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
                            .addComponent(tfPhone, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfAddress, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfEmail, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tfId))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(logoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(printBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(7, 7, 7)))
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClass)
                    .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId)
                    .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGender)
                    .addComponent(maleBtn)
                    .addComponent(femaleBtn))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone)
                    .addComponent(tfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDob))
                .addGap(26, 26, 26)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editBtn)
                    .addComponent(addBtn))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBtn)
                    .addComponent(printBtn))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logoutBtn)
                    .addComponent(clearBtn))
                .addGap(26, 26, 26))
        );

        searchPanel.setBackground(new java.awt.Color(0, 153, 102));

        sortBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sortBtn.setForeground(new java.awt.Color(0, 153, 51));
        sortBtn.setText("Sort");
        sortBtn.setFocusable(false);
        sortBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortBtnActionPerformed(evt);
            }
        });

        refreshBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        refreshBtn.setForeground(new java.awt.Color(0, 153, 0));
        refreshBtn.setText("Refresh");
        refreshBtn.setFocusable(false);
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        tfSearch.setBackground(new java.awt.Color(0, 153, 102));
        tfSearch.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        tfSearch.setForeground(new java.awt.Color(0, 255, 255));
        tfSearch.setBorder(null);
        tfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchKeyReleased(evt);
            }
        });

        searchLabel.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(255, 255, 255));
        searchLabel.setText("Search ID Student");

        searchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        searchIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchIconMouseClicked(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 153, 102));
        jSeparator1.setForeground(new java.awt.Color(51, 0, 0));
        jSeparator1.setOpaque(true);

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(searchLabel)
                .addGap(41, 41, 41)
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchIcon)
                .addGap(45, 45, 45)
                .addComponent(sortBtn)
                .addGap(41, 41, 41)
                .addComponent(refreshBtn)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(searchIcon)
                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(sortBtn)
                                    .addComponent(refreshBtn)))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(3, 3, 3))
        );

        inforStudentTabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        inforStudentTabel.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        inforStudentTabel.setForeground(new java.awt.Color(51, 0, 0));
        inforStudentTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Gender", "Date of birth", "Class", "Email", "Phone", "Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        inforStudentTabel.setFocusable(false);
        inforStudentTabel.setRowHeight(25);
        inforStudentTabel.getTableHeader().setReorderingAllowed(false);
        inforStudentTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inforStudentTabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(inforStudentTabel);
        if (inforStudentTabel.getColumnModel().getColumnCount() > 0) {
            inforStudentTabel.getColumnModel().getColumn(0).setPreferredWidth(40);
            inforStudentTabel.getColumnModel().getColumn(1).setPreferredWidth(120);
            inforStudentTabel.getColumnModel().getColumn(2).setPreferredWidth(20);
            inforStudentTabel.getColumnModel().getColumn(3).setPreferredWidth(50);
            inforStudentTabel.getColumnModel().getColumn(4).setPreferredWidth(5);
            inforStudentTabel.getColumnModel().getColumn(5).setPreferredWidth(150);
            inforStudentTabel.getColumnModel().getColumn(6).setPreferredWidth(50);
            inforStudentTabel.getColumnModel().getColumn(7).setPreferredWidth(110);
        }

        javax.swing.GroupLayout studentPanelLayout = new javax.swing.GroupLayout(studentPanel);
        studentPanel.setLayout(studentPanelLayout);
        studentPanelLayout.setHorizontalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE))
                .addContainerGap())
        );
        studentPanelLayout.setVerticalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );

        tabbedPane.addTab("Student", new javax.swing.ImageIcon(getClass().getResource("/Images/graduation-hat.png")), studentPanel); // NOI18N

        jPanel2.setBackground(new java.awt.Color(0, 204, 153));

        txtId1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtId1.setForeground(new java.awt.Color(0, 51, 255));
        txtId1.setText("ID");

        txtName1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtName1.setForeground(new java.awt.Color(0, 51, 255));
        txtName1.setText("Name");

        tfName1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        txtGender1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtGender1.setForeground(new java.awt.Color(0, 51, 255));
        txtGender1.setText("Course 1");

        txtPhone1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtPhone1.setForeground(new java.awt.Color(0, 51, 255));
        txtPhone1.setText("Course 2");

        txtAddress1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtAddress1.setForeground(new java.awt.Color(0, 51, 255));
        txtAddress1.setText("Grade 2");

        addBtn1.setText("  Add  ");
        addBtn1.setFocusable(false);
        addBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtn1ActionPerformed(evt);
            }
        });

        editBtn1.setText(" Edit ");
        editBtn1.setFocusable(false);
        editBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtn1ActionPerformed(evt);
            }
        });

        deleteBtn1.setText("Delete");
        deleteBtn1.setFocusable(false);
        deleteBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtn1ActionPerformed(evt);
            }
        });

        printBtn1.setText("Print ");
        printBtn1.setFocusable(false);
        printBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtn1ActionPerformed(evt);
            }
        });

        txtClass1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtClass1.setForeground(new java.awt.Color(0, 51, 255));
        txtClass1.setText("Semester");

        semester.setBackground(new java.awt.Color(0, 204, 153));
        semester.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        semester.setForeground(new java.awt.Color(51, 51, 255));
        semester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        semester.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        semester.setFocusable(false);

        jSeparator3.setForeground(new java.awt.Color(51, 0, 0));

        tfName2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        txtGender2.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtGender2.setForeground(new java.awt.Color(0, 51, 255));
        txtGender2.setText("Grade 1");

        txtAddress2.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtAddress2.setForeground(new java.awt.Color(0, 51, 255));
        txtAddress2.setText("Start");

        txtAddress3.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtAddress3.setForeground(new java.awt.Color(0, 51, 255));
        txtAddress3.setText("End");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addBtn1)
                    .addComponent(deleteBtn1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(printBtn1)
                    .addComponent(editBtn1))
                .addGap(57, 57, 57))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtId1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress1)
                    .addComponent(txtName1)
                    .addComponent(txtGender1)
                    .addComponent(txtClass1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtPhone1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtGender2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtAddress3)
                    .addComponent(txtAddress2))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfPhone1)
                            .addComponent(tfName1)
                            .addComponent(tfEmail1)
                            .addComponent(tfId1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfName2)
                            .addComponent(tfAddress2))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(semester, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClass1)
                    .addComponent(semester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId1)
                    .addComponent(tfId1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName1)
                    .addComponent(tfName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGender1)
                    .addComponent(tfName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGender2))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone1)
                    .addComponent(tfPhone1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddress1)
                    .addComponent(tfAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtAddress2)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtAddress3)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editBtn1)
                    .addComponent(addBtn1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBtn1)
                    .addComponent(printBtn1))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout coursePanelLayout = new javax.swing.GroupLayout(coursePanel);
        coursePanel.setLayout(coursePanelLayout);
        coursePanelLayout.setHorizontalGroup(
            coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursePanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 791, Short.MAX_VALUE))
        );
        coursePanelLayout.setVerticalGroup(
            coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Course", new javax.swing.ImageIcon(getClass().getResource("/Images/homework.png")), coursePanel); // NOI18N

        headerPanel.setBackground(new java.awt.Color(0, 255, 255));

        headerLabel.setBackground(new java.awt.Color(0, 255, 255));
        headerLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(174, 174, 62));
        headerLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/apprentice.png"))); // NOI18N
        headerLabel.setText("STUDENT MANAGEMENT SYSTEM");
        headerLabel.setOpaque(true);

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerLabel)
                .addGap(333, 333, 333))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerLabel, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabbedPane)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabbedPane))
        );

        tabbedPane.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.requestFocusInWindow();
    }//GEN-LAST:event_formWindowGainedFocus

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        table_load();
        tfSearch.setText("");
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        
        if(validInfor("add")){
                    
            String query = "INSERT INTO student VALUE(null,?, ?, ?, ?, ?, ?, ?, ?)";
            try{
                PreparedStatement ps = conn.prepareStatement(query);
                
                ps.setString(1, tfId.getText());
                ps.setString(2, tfName.getText());
                // get Text from selected button in buttonGroup  
                String gender = null;
                Enumeration<AbstractButton> enu = buttonGroup1.getElements();
                while (enu.hasMoreElements()) {
                    AbstractButton button = enu.nextElement();
                    if(button.isSelected())                     
                        gender = button.getText();
                }
                ps.setString(3, gender);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String date = dateFormat.format(dateChooser.getDate());
                ps.setString(4, date);
                ps.setString(5, classComboBox.getSelectedItem().toString());
                ps.setString(6, tfEmail.getText());
                ps.setString(7, tfPhone.getText());
                ps.setString(8, tfAddress.getText());
                ps.executeUpdate();
                table_load();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            tfId.setText("");
            tfName.setText("");
            tfEmail.setText("");
            tfPhone.setText("");
            tfAddress.setText("");
            dateChooser.setDate(null);
        }                    
    }//GEN-LAST:event_addBtnActionPerformed

    private int getId(){
        int id = 0;
        String query = "SELECT id FROM student WHERE idStudent = ?";
        PreparedStatement ps;
        try {
            DefaultTableModel model = (DefaultTableModel)inforStudentTabel.getModel();
            int selected = inforStudentTabel.getSelectedRow();
            String idStudent = model.getValueAt(selected, 0).toString();
            ps = conn.prepareStatement(query);
            ps.setString(1, idStudent);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }    
    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        
        if(validInfor("edit")){
            int choice = JOptionPane.showConfirmDialog(null, 
                "Do you seriously want to modify it?", 
                "Confirm", 
                JOptionPane.YES_NO_OPTION);
            if(choice == 0){
                int id = getId();
                String query = "UPDATE student SET idStudent = ?, name = ?, gender = ?, dob = ?, class = ?, email = ?, phone = ?, address = ? "
                        + "where id = ?";
                try{
                    PreparedStatement ps = conn.prepareStatement(query);

                    ps.setString(1, tfId.getText());
                    ps.setString(2, tfName.getText());
                    // get Text from selected button in buttonGroup  
                    String gender = null;
                    Enumeration<AbstractButton> enu = buttonGroup1.getElements();
                    while (enu.hasMoreElements()) {
                        AbstractButton button = enu.nextElement();
                        if(button.isSelected())                     
                            gender = button.getText();
                    }
                    ps.setString(3, gender);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String date = dateFormat.format(dateChooser.getDate());
                    ps.setString(4, date);
                    ps.setString(5, classComboBox.getSelectedItem().toString());
                    ps.setString(6, tfEmail.getText());
                    ps.setString(7, tfPhone.getText());
                    ps.setString(8, tfAddress.getText());
                    ps.setInt(9, id);
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)inforStudentTabel.getModel();
                    model.setRowCount(0);
                    table_load();
                    JOptionPane.showMessageDialog(null, "   Edited successfully!");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                tfId.setText("");
                tfName.setText("");
                tfEmail.setText("");
                tfPhone.setText("");
                tfAddress.setText("");
                dateChooser.setDate(null);
                addBtn.setEnabled(true);
            }
        }      
    }//GEN-LAST:event_editBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        
        int choice = JOptionPane.showConfirmDialog(null, 
                "Do you seriously want to delete it?", 
                "Confirm", 
                JOptionPane.YES_NO_OPTION);
        if(choice == 0){
            String query = "DELETE FROM student WHERE idStudent = ?";
            DefaultTableModel model = (DefaultTableModel)inforStudentTabel.getModel();
            int selected = inforStudentTabel.getSelectedRow();
            try{
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, model.getValueAt(selected, 0).toString());
                ps.executeUpdate();
                table_load();
                JOptionPane.showMessageDialog(null, "   Deleted successfully!");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "You must select a row need to delete!");
            }
        }         
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed
        MessageFormat header = new MessageFormat("Students Information");
        MessageFormat footer = new MessageFormat("Page{0, number, integer}");
        
        try{           
            inforStudentTabel.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            JOptionPane.showMessageDialog(null, "    Printed successfully!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_printBtnActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        int select = JOptionPane.showConfirmDialog(null, 
                "Do you really want to logout?", "Message", JOptionPane.YES_NO_OPTION);
        if(select == 0){
            this.dispose();
            new LoginWindow().setVisible(true);
        }
    }//GEN-LAST:event_logoutBtnActionPerformed

    private void addBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addBtn1ActionPerformed

    private void editBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editBtn1ActionPerformed

    private void deleteBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteBtn1ActionPerformed

    private void printBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_printBtn1ActionPerformed

    private void sortBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortBtnActionPerformed
        String query = "SELECT idStudent, name, gender, dob, class, email, phone, address FROM student ORDER BY idStudent";
        try{        
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)inforStudentTabel.getModel();
            model.setRowCount(0);
            while(rs.next()){
                model.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8)});
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_sortBtnActionPerformed

    private void inforStudentTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inforStudentTabelMouseClicked
        try{           
            DefaultTableModel model = (DefaultTableModel)inforStudentTabel.getModel();
            int selected = inforStudentTabel.getSelectedRow();
            tfId.setText(model.getValueAt(selected, 0).toString());
            tfName.setText(model.getValueAt(selected, 1).toString());           
            if(model.getValueAt(selected, 2).toString().equals("Male")){
                maleBtn.setSelected(true);
            }else{
                femaleBtn.setSelected(true);
            }
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse((String)model.getValueAt(selected, 3));
            dateChooser.setDate(date);
            classComboBox.setSelectedItem(model.getValueAt(selected, 4));
            tfEmail.setText(model.getValueAt(selected, 5).toString());
            tfPhone.setText(model.getValueAt(selected, 6).toString());
            tfAddress.setText(model.getValueAt(selected, 7).toString());
        }catch(ParseException e){
            JOptionPane.showMessageDialog(null, e);
        }
        addBtn.setEnabled(false);
        editBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }//GEN-LAST:event_inforStudentTabelMouseClicked

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        
        editBtn.setEnabled(false);
        addBtn.setEnabled(true);
        inforStudentTabel.getSelectionModel().clearSelection();
        tfId.setText("");
        tfName.setText("");
        tfEmail.setText("");
        tfPhone.setText("");
        tfAddress.setText("");
        dateChooser.setDate(null);
        JOptionPane.showMessageDialog(null, "       Cleaned!");

    }//GEN-LAST:event_clearBtnActionPerformed

    private void tfSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String query = "SELECT idStudent, name, gender, dob, class, email, phone, address FROM student WHERE idStudent = ?";
            try{        
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, tfSearch.getText());
                ResultSet rs = ps.executeQuery();
                DefaultTableModel model = (DefaultTableModel)inforStudentTabel.getModel();
                model.setRowCount(0);
                while(rs.next()){
                    model.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)});
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_tfSearchKeyReleased

    private void searchIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchIconMouseClicked
        String query = "SELECT idStudent, name, gender, dob, class, email, phone, address FROM student WHERE idStudent = ?";
        try{        
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, tfSearch.getText());
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)inforStudentTabel.getModel();
            model.setRowCount(0);
            while(rs.next()){
                model.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)});
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_searchIconMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookanmodeleel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton addBtn1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> classComboBox;
    private javax.swing.JButton clearBtn;
    private javax.swing.JPanel coursePanel;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton deleteBtn1;
    private javax.swing.JButton editBtn;
    private javax.swing.JButton editBtn1;
    private javax.swing.JRadioButton femaleBtn;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTable inforStudentTabel;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JRadioButton maleBtn;
    private javax.swing.JButton printBtn;
    private javax.swing.JButton printBtn1;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JLabel searchIcon;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JComboBox<String> semester;
    private javax.swing.JButton sortBtn;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextField tfAddress;
    private javax.swing.JTextField tfAddress2;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfEmail1;
    private javax.swing.JTextField tfId;
    private javax.swing.JTextField tfId1;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfName1;
    private javax.swing.JTextField tfName2;
    private javax.swing.JTextField tfPhone;
    private javax.swing.JTextField tfPhone1;
    private javax.swing.JTextField tfSearch;
    private javax.swing.JLabel txtAddress;
    private javax.swing.JLabel txtAddress1;
    private javax.swing.JLabel txtAddress2;
    private javax.swing.JLabel txtAddress3;
    private javax.swing.JLabel txtClass;
    private javax.swing.JLabel txtClass1;
    private javax.swing.JLabel txtDob;
    private javax.swing.JLabel txtEmail;
    private javax.swing.JLabel txtGender;
    private javax.swing.JLabel txtGender1;
    private javax.swing.JLabel txtGender2;
    private javax.swing.JLabel txtId;
    private javax.swing.JLabel txtId1;
    private javax.swing.JLabel txtName;
    private javax.swing.JLabel txtName1;
    private javax.swing.JLabel txtPhone;
    private javax.swing.JLabel txtPhone1;
    // End of variables declaration//GEN-END:variables
}
