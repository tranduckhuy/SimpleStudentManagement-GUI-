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
        init_score("grade_semester1");
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
    
    private void init_score(String semester){   
        
        try{    
            String query;
            DefaultTableModel model = (DefaultTableModel)gradeInforTable.getModel();
            if(semester.equals("grade_semester1")){
                Course1.setText("PRF192");
                Course2.setText("CSI104");
                Course3.setText("CEA201");
                gradeInforTable.getColumnModel().getColumn(3).setHeaderValue("PRF192");
                gradeInforTable.getColumnModel().getColumn(4).setHeaderValue("CSI104");
                gradeInforTable.getColumnModel().getColumn(5).setHeaderValue("CEA201");
                query = "SELECT class, idStudent, name, prf192, csi104, cea201, average FROM grade_semester1";
            }else{
                Course1.setText("PRO192");
                Course2.setText("OSG202");
                Course3.setText("NWC203c");
                query = "SELECT class, idStudent, name, pro192, osg202, nwc203c, average FROM grade_semester2";
                gradeInforTable.getColumnModel().getColumn(3).setHeaderValue("PRO192");
                gradeInforTable.getColumnModel().getColumn(4).setHeaderValue("OSG202");
                gradeInforTable.getColumnModel().getColumn(5).setHeaderValue("NWC203c");
                gradeInforTable.getTableHeader().repaint();
            }
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            model.setRowCount(0);
            while(rs.next()){
                model.addRow(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)});
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
        txtCourse1 = new javax.swing.JLabel();
        txtScore1 = new javax.swing.JLabel();
        txtScore2 = new javax.swing.JLabel();
        txtCourse3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        tfScore2 = new javax.swing.JTextField();
        txtCourse2 = new javax.swing.JLabel();
        txtGrade3 = new javax.swing.JLabel();
        txtClass1 = new javax.swing.JLabel();
        classComboBox1 = new javax.swing.JComboBox<>();
        id = new javax.swing.JComboBox<>();
        addBtn1 = new javax.swing.JButton();
        editBtn1 = new javax.swing.JButton();
        deleteBtn1 = new javax.swing.JButton();
        printBtn1 = new javax.swing.JButton();
        clearBtn1 = new javax.swing.JButton();
        tfScore1 = new javax.swing.JTextField();
        tfScore3 = new javax.swing.JTextField();
        Course1 = new javax.swing.JLabel();
        Course2 = new javax.swing.JLabel();
        Course3 = new javax.swing.JLabel();
        semesterChooser = new javax.swing.JLabel();
        searchPanel1 = new javax.swing.JPanel();
        refreshBtn1 = new javax.swing.JButton();
        tfSearch1 = new javax.swing.JTextField();
        searchLabel1 = new javax.swing.JLabel();
        searchIcon1 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        classList = new javax.swing.JComboBox<>();
        semesterList = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        gradeInforTable = new javax.swing.JTable();
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
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
                .addGap(32, 32, 32))
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

        txtCourse1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtCourse1.setForeground(new java.awt.Color(0, 51, 255));
        txtCourse1.setText("Course:");

        txtScore1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtScore1.setForeground(new java.awt.Color(0, 51, 255));
        txtScore1.setText("Score");

        txtScore2.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtScore2.setForeground(new java.awt.Color(102, 102, 0));
        txtScore2.setText("Score");

        txtCourse3.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtCourse3.setForeground(new java.awt.Color(51, 0, 0));
        txtCourse3.setText("Course:");

        jSeparator3.setForeground(new java.awt.Color(51, 0, 0));

        tfScore2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfScore2.setForeground(new java.awt.Color(102, 102, 0));
        tfScore2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        txtCourse2.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtCourse2.setForeground(new java.awt.Color(102, 102, 0));
        txtCourse2.setText("Course:");

        txtGrade3.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtGrade3.setForeground(new java.awt.Color(51, 0, 0));
        txtGrade3.setText("Score");

        txtClass1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        txtClass1.setForeground(new java.awt.Color(0, 51, 255));
        txtClass1.setText("Class");

        classComboBox1.setBackground(new java.awt.Color(0, 204, 153));
        classComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        classComboBox1.setForeground(new java.awt.Color(51, 51, 255));
        classComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SE1", "SE2", "SE3", "SE4" }));
        classComboBox1.setSelectedIndex(-1);
        classComboBox1.setSelectedItem(null);
        classComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        classComboBox1.setFocusable(false);
        classComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classComboBox1ActionPerformed(evt);
            }
        });

        id.setBackground(new java.awt.Color(0, 204, 153));
        id.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        id.setForeground(new java.awt.Color(51, 51, 255));
        id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        id.setFocusable(false);

        addBtn1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addBtn1.setForeground(new java.awt.Color(102, 102, 0));
        addBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        addBtn1.setText(" Add");
        addBtn1.setFocusable(false);
        addBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtn1ActionPerformed(evt);
            }
        });

        editBtn1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        editBtn1.setForeground(new java.awt.Color(102, 102, 0));
        editBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit.png"))); // NOI18N
        editBtn1.setText("Edit  ");
        editBtn1.setEnabled(false);
        editBtn1.setFocusable(false);
        editBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtn1ActionPerformed(evt);
            }
        });

        deleteBtn1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        deleteBtn1.setForeground(new java.awt.Color(102, 102, 0));
        deleteBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        deleteBtn1.setText("Delete");
        deleteBtn1.setEnabled(false);
        deleteBtn1.setFocusable(false);
        deleteBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtn1ActionPerformed(evt);
            }
        });

        printBtn1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        printBtn1.setForeground(new java.awt.Color(102, 102, 0));
        printBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/print.png"))); // NOI18N
        printBtn1.setText(" Print ");
        printBtn1.setFocusable(false);
        printBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtn1ActionPerformed(evt);
            }
        });

        clearBtn1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clearBtn1.setForeground(new java.awt.Color(102, 102, 0));
        clearBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/clear.png"))); // NOI18N
        clearBtn1.setText("Clear");
        clearBtn1.setFocusable(false);
        clearBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtn1ActionPerformed(evt);
            }
        });

        tfScore1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfScore1.setForeground(new java.awt.Color(0, 51, 255));
        tfScore1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        tfScore3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfScore3.setForeground(new java.awt.Color(51, 0, 0));
        tfScore3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfScore3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfScore3KeyReleased(evt);
            }
        });

        Course1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        Course1.setForeground(new java.awt.Color(0, 51, 255));

        Course2.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        Course2.setForeground(new java.awt.Color(102, 102, 0));

        Course3.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        Course3.setForeground(new java.awt.Color(51, 0, 0));

        semesterChooser.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        semesterChooser.setForeground(new java.awt.Color(0, 0, 102));
        semesterChooser.setText("Semester 1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(clearBtn1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtClass1)
                                    .addComponent(txtId1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(classComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(deleteBtn1)
                                    .addComponent(addBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(editBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(printBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtScore2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfScore2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtScore1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCourse2)
                                    .addComponent(txtCourse1))
                                .addGap(17, 17, 17)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Course1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(tfScore1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(Course2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGrade3)
                                    .addComponent(txtCourse3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfScore3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Course3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(semesterChooser)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(semesterChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClass1)
                    .addComponent(classComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId1))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCourse1)
                    .addComponent(Course1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtScore1)
                    .addComponent(tfScore1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCourse2)
                    .addComponent(Course2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtScore2)
                    .addComponent(tfScore2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCourse3)
                    .addComponent(Course3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGrade3)
                    .addComponent(tfScore3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn1)
                    .addComponent(editBtn1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(printBtn1)
                    .addComponent(deleteBtn1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clearBtn1)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        searchPanel1.setBackground(new java.awt.Color(0, 153, 102));

        refreshBtn1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        refreshBtn1.setForeground(new java.awt.Color(0, 153, 51));
        refreshBtn1.setText("Refresh");
        refreshBtn1.setFocusable(false);
        refreshBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtn1ActionPerformed(evt);
            }
        });

        tfSearch1.setBackground(new java.awt.Color(0, 153, 102));
        tfSearch1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        tfSearch1.setForeground(new java.awt.Color(0, 255, 255));
        tfSearch1.setBorder(null);
        tfSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearch1KeyReleased(evt);
            }
        });

        searchLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        searchLabel1.setForeground(new java.awt.Color(255, 255, 255));
        searchLabel1.setText("Search ID Student");

        searchIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        searchIcon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchIcon1MouseClicked(evt);
            }
        });

        jSeparator4.setBackground(new java.awt.Color(0, 153, 102));
        jSeparator4.setForeground(new java.awt.Color(51, 0, 0));
        jSeparator4.setOpaque(true);

        classList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        classList.setForeground(new java.awt.Color(0, 153, 51));
        classList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SE1", "SE2", "SE3", "SE4" }));
        classList.setFocusable(false);
        classList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classListActionPerformed(evt);
            }
        });

        semesterList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        semesterList.setForeground(new java.awt.Color(0, 153, 51));
        semesterList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semester 1", "Semester 2" }));
        semesterList.setFocusable(false);
        semesterList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                semesterListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchPanel1Layout = new javax.swing.GroupLayout(searchPanel1);
        searchPanel1.setLayout(searchPanel1Layout);
        searchPanel1Layout.setHorizontalGroup(
            searchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(searchLabel1)
                .addGap(41, 41, 41)
                .addGroup(searchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(jSeparator4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchIcon1)
                .addGap(18, 18, 18)
                .addComponent(refreshBtn1)
                .addGap(35, 35, 35)
                .addComponent(semesterList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(classList, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        searchPanel1Layout.setVerticalGroup(
            searchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(searchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(searchIcon1)
                                .addGroup(searchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(refreshBtn1)
                                    .addComponent(classList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(semesterList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(3, 3, 3))
        );

        gradeInforTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        gradeInforTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Class", "ID", "Name", "PRF192", "CSI104", "CEA201", "Average Score"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        gradeInforTable.getTableHeader().setReorderingAllowed(false);
        gradeInforTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gradeInforTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(gradeInforTable);
        if (gradeInforTable.getColumnModel().getColumnCount() > 0) {
            gradeInforTable.getColumnModel().getColumn(0).setPreferredWidth(15);
            gradeInforTable.getColumnModel().getColumn(1).setPreferredWidth(35);
            gradeInforTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            gradeInforTable.getColumnModel().getColumn(3).setPreferredWidth(35);
            gradeInforTable.getColumnModel().getColumn(4).setPreferredWidth(35);
            gradeInforTable.getColumnModel().getColumn(5).setPreferredWidth(35);
            gradeInforTable.getColumnModel().getColumn(6).setPreferredWidth(110);
        }

        javax.swing.GroupLayout coursePanelLayout = new javax.swing.GroupLayout(coursePanel);
        coursePanel.setLayout(coursePanelLayout);
        coursePanelLayout.setHorizontalGroup(
            coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursePanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        coursePanelLayout.setVerticalGroup(
            coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2))
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
                    
            String query = "INSERT INTO student VALUE(null, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            JOptionPane.showMessageDialog(null, "    Added successfully!");
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
                editBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                JOptionPane.showMessageDialog(null, "   Edited successfully!");
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
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "You must select a row need to delete!");
            }
            JOptionPane.showMessageDialog(null, "   Deleted successfully!");
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

//  -----------------------------------------COURSE------------------------------------------------------

    private void refreshBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtn1ActionPerformed
        if(semesterList.getSelectedItem().equals("Semester 1")){
            init_score("grade_semester1");
        }else{
            init_score("grade_semester2");
        }
        tfSearch1.setText("");
    }//GEN-LAST:event_refreshBtn1ActionPerformed

    private void tfSearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearch1KeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String query;
            if(semesterList.getSelectedItem().equals("Semester 1")){
                query = "SELECT class, idStudent, name, prf192, csi104, cea201, average FROM grade_semester1 WHERE idStudent = ?";
            }else{
                query = "SELECT class, idStudent, name, pro192, osg202, nwc203c, average FROM grade_semester2 WHERE idStudent = ?";
            }
            try{
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, tfSearch1.getText());
                ResultSet rs = ps.executeQuery();
                DefaultTableModel model = (DefaultTableModel)gradeInforTable.getModel();
                model.setRowCount(0);
                if(rs.next()){
                    model.addRow(new String[]{rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_tfSearch1KeyReleased

    private void searchIcon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchIcon1MouseClicked
        
    }//GEN-LAST:event_searchIcon1MouseClicked

    private boolean validId(){
        String query;
        if(semesterList.getSelectedItem().equals("Semester 1")){
            query = "SELECT * FROM grade_semester1 WHERE idStudent = ?";
        }else{
            query = "SELECT * FROM grade_semester2 WHERE idStudent = ?";
        }
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "The student has been graded this semester");
                return false;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return true;
    }
    
    private boolean validGrade(){
        if(tfScore1.getText().equals("") || tfScore2.getText().equals("")|| tfScore3.getText().equals("")){
            JOptionPane.showMessageDialog(null, "    Empty score!");
            return false;
        }
        try{
            float score1 = Float.parseFloat(tfScore1.getText());
            float score2 = Float.parseFloat(tfScore2.getText());
            float score3 = Float.parseFloat(tfScore3.getText());
            if(score1 < 0 || score1 > 10){
                JOptionPane.showMessageDialog(null, "Invalid score");
            return false;
            }
            if(score2 < 0 || score2 > 10){
                JOptionPane.showMessageDialog(null, "Invalid score");
                return false;
            }
            if(score3 < 0 || score3 > 10){
                JOptionPane.showMessageDialog(null, "Invalid score");
                return false;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "  Please enter the score as a number!");
        }
        return true;
    }
    
    private String averageScore(){
        String average;
        float score1 = Float.parseFloat(tfScore1.getText());
        float score2 = Float.parseFloat(tfScore2.getText());
        float score3 = Float.parseFloat(tfScore3.getText());
        average = String.valueOf(Math.floor((score1 + score2 + score3)/3*10)/10);
        return average;
    }
    
    private String getNameFromId(){
        String name = null;
        String query = "SELECT name FROM student WHERE idStudent = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                name = rs.getString(1);   
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return name;
    }       
    
    private void addBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtn1ActionPerformed
        if(classComboBox1.getSelectedIndex() != -1){
            if(validId() && validGrade()){
                String query, semester;
                try{
                    if(semesterChooser.getText().equals("Semester 1")){
                        query = "INSERT INTO grade_semester1 VALUE(null, ?, ?, ?, ?, ?, ?, ?)";
                        semester = "grade_semester1";

                    }else{
                        query = "INSERT INTO grade_semester2 VALUE(null, ?, ?, ?, ?, ?, ?, ?)";
                        semester = "grade_semester2";
                    }
                    try{
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, classComboBox1.getSelectedItem().toString());
                        ps.setString(2, id.getSelectedItem().toString());
                        ps.setString(3, getNameFromId());
                        ps.setString(4, tfScore1.getText());
                        ps.setString(5, tfScore2.getText());
                        ps.setString(6, tfScore3.getText());
                        ps.setString(7, averageScore());
                        ps.executeUpdate();
                        init_score(semester);
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, e);
                    }       

                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                JOptionPane.showMessageDialog(null, "    Added successfully!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Please select the Student's class and ID");
        }
    }//GEN-LAST:event_addBtn1ActionPerformed

    private void editBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtn1ActionPerformed
        if(validGrade()){
            String query, semester;
            try{
                if(semesterChooser.getText().equals("Semester 1")){
                    query = "UPDATE grade_semester1 SET prf192 = ?, csi104 = ?, cea201 = ?, average = ? WHERE idStudent = ?";
                    semester = "grade_semester1";
                    
                }else{
                    query = "UPDATE grade_semester2 SET pro192 = ?, osg202 = ?, nwc203c = ?, average = ? WHERE idStudent = ?";
                    semester = "grade_semester2";
                }
                try{
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, tfScore1.getText());
                    ps.setString(2, tfScore2.getText());
                    ps.setString(3, tfScore3.getText());
                    ps.setString(4, averageScore());
                    ps.setString(5, id.getSelectedItem().toString());
                    ps.executeUpdate();
                    init_score(semester);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }                       
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            classComboBox1.setEnabled(true);
            id.setEnabled(true);
            tfScore1.setText("");
            tfScore2.setText("");
            tfScore3.setText("");
            addBtn1.setEnabled(true);
            editBtn1.setEnabled(false);
            deleteBtn1.setEnabled(false);
            JOptionPane.showMessageDialog(null, "    Edited successfully!");
        }
    }//GEN-LAST:event_editBtn1ActionPerformed

    private void deleteBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtn1ActionPerformed
        int choice = JOptionPane.showConfirmDialog(null, 
                "Do you seriously want to delete it?", 
                "Confirm", 
                JOptionPane.YES_NO_OPTION);
        if(choice == 0){
            String query, semester;
            if(semesterList.getSelectedItem().equals("Semester 1")){
                query = "DELETE FROM grade_semester1 WHERE idStudent = ?";
                semester = "grade_semester1";
            }else{
                query = "DELETE FROM grade_semester1 WHERE idStudent = ?";
                semester = "grade_semester2";
            }
            DefaultTableModel model = (DefaultTableModel)gradeInforTable.getModel();
            int selected = gradeInforTable.getSelectedRow();
            try{
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, model.getValueAt(selected, 1).toString());
                ps.executeUpdate();
                init_score(semester);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "You must select a row need to delete!");
            }
            JOptionPane.showMessageDialog(null, "   Deleted successfully!");
        } 
    }//GEN-LAST:event_deleteBtn1ActionPerformed

    private void printBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtn1ActionPerformed
        MessageFormat header;
        if(semesterList.getSelectedItem().equals("Semester 1")){
               header = new MessageFormat("Student's 1st semester grades"); 
        }else{
               header = new MessageFormat("Student's 2nd semester grades"); 
        }
        MessageFormat footer = new MessageFormat("Page{0, number, integer}");
        
        try{           
            gradeInforTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            JOptionPane.showMessageDialog(null, "    Printed successfully!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_printBtn1ActionPerformed

    private void clearBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtn1ActionPerformed
        gradeInforTable.getSelectionModel().clearSelection();
        classComboBox1.setSelectedIndex(0);
        classComboBox1.setEnabled(true);
        id.setSelectedIndex(0);
        id.setEnabled(true);
        tfScore1.setText("");
        tfScore2.setText("");
        tfScore3.setText("");
        addBtn1.setEnabled(true);
        editBtn1.setEnabled(false);
        deleteBtn1.setEnabled(false);
    }//GEN-LAST:event_clearBtn1ActionPerformed

    private void setIdStudentOfClass(){
        id.removeAllItems();
        String query = "SELECT idStudent FROM student WHERE class = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, classComboBox1.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id.addItem(rs.getString(1));
            }
        }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void classComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classComboBox1ActionPerformed
        setIdStudentOfClass();
    }//GEN-LAST:event_classComboBox1ActionPerformed

    private void gradeInforTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gradeInforTableMouseClicked
        DefaultTableModel model = (DefaultTableModel)gradeInforTable.getModel();
        int selected = gradeInforTable.getSelectedRow(); 
        classComboBox1.setSelectedItem(model.getValueAt(selected, 0));
        setIdStudentOfClass();
        id.setSelectedItem(model.getValueAt(selected, 1));
        tfScore1.setText(model.getValueAt(selected, 3).toString());
        tfScore2.setText(model.getValueAt(selected, 4).toString());
        tfScore3.setText(model.getValueAt(selected, 5).toString());
        classComboBox1.setEnabled(false);
        id.setEnabled(false);
        addBtn1.setEnabled(false);
        editBtn1.setEnabled(true);
        deleteBtn1.setEnabled(true);
    }//GEN-LAST:event_gradeInforTableMouseClicked

    private void semesterListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semesterListActionPerformed
        if(semesterList.getSelectedItem().equals("Semester 1")){
            semesterChooser.setText("Semester 1");
            init_score("grade_semester1");
             
        }else{
            semesterChooser.setText("Semester 2");
            init_score("grade_semester2");
        }
        tfSearch1.setText("");
    }//GEN-LAST:event_semesterListActionPerformed

    private void tfScore3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfScore3KeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(classComboBox1.getSelectedIndex() != -1){
                if(validGrade()){
                    String query, semester;
                    try{
                        if(semesterChooser.getText().equals("Semester 1")){
                            query = "INSERT INTO grade_semester1 VALUE(null, ?, ?, ?, ?, ?, ?, ?)";
                            semester = "grade_semester1";

                        }else{
                            query = "INSERT INTO grade_semester2 VALUE(null, ?, ?, ?, ?, ?, ?, ?)";
                            semester = "grade_semester2";
                        }
                        try{
                            PreparedStatement ps = conn.prepareStatement(query);
                            ps.setString(1, classComboBox1.getSelectedItem().toString());
                            ps.setString(2, id.getSelectedItem().toString());
                            ps.setString(3, getNameFromId());
                            ps.setString(4, tfScore1.getText());
                            ps.setString(5, tfScore2.getText());
                            ps.setString(6, tfScore3.getText());
                            ps.setString(7, averageScore());
                            ps.executeUpdate();
                            init_score(semester);
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, e);
                        }       

                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Please select the Student's class and ID");
            }
        }
    }//GEN-LAST:event_tfScore3KeyReleased

    private void classListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classListActionPerformed
        String query;
        if(semesterList.getSelectedItem().equals("Semester 1")){
            query = "SELECT class, idStudent, name, prf192, csi104, cea201, average FROM grade_semester1 WHERE class = ?";
        }else{
            query = "SELECT class, idStudent, name, pro192, osg202, nwc203c, average FROM grade_semester2 WHERE class = ?";
        }
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, classList.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)gradeInforTable.getModel();
            model.setRowCount(0);
            while(rs.next()){
                model.addRow(new String[]{rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        tfSearch1.setText("");
    }//GEN-LAST:event_classListActionPerformed

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
    private javax.swing.JLabel Course1;
    private javax.swing.JLabel Course2;
    private javax.swing.JLabel Course3;
    private javax.swing.JButton addBtn;
    private javax.swing.JButton addBtn1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> classComboBox;
    private javax.swing.JComboBox<String> classComboBox1;
    private javax.swing.JComboBox<String> classList;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton clearBtn1;
    private javax.swing.JPanel coursePanel;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton deleteBtn1;
    private javax.swing.JButton editBtn;
    private javax.swing.JButton editBtn1;
    private javax.swing.JRadioButton femaleBtn;
    private javax.swing.JTable gradeInforTable;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JComboBox<String> id;
    private javax.swing.JTable inforStudentTabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JRadioButton maleBtn;
    private javax.swing.JButton printBtn;
    private javax.swing.JButton printBtn1;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton refreshBtn1;
    private javax.swing.JLabel searchIcon;
    private javax.swing.JLabel searchIcon1;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JLabel searchLabel1;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JPanel searchPanel1;
    private javax.swing.JLabel semesterChooser;
    private javax.swing.JComboBox<String> semesterList;
    private javax.swing.JButton sortBtn;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextField tfAddress;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfId;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfPhone;
    private javax.swing.JTextField tfScore1;
    private javax.swing.JTextField tfScore2;
    private javax.swing.JTextField tfScore3;
    private javax.swing.JTextField tfSearch;
    private javax.swing.JTextField tfSearch1;
    private javax.swing.JLabel txtAddress;
    private javax.swing.JLabel txtClass;
    private javax.swing.JLabel txtClass1;
    private javax.swing.JLabel txtCourse1;
    private javax.swing.JLabel txtCourse2;
    private javax.swing.JLabel txtCourse3;
    private javax.swing.JLabel txtDob;
    private javax.swing.JLabel txtEmail;
    private javax.swing.JLabel txtGender;
    private javax.swing.JLabel txtGrade3;
    private javax.swing.JLabel txtId;
    private javax.swing.JLabel txtId1;
    private javax.swing.JLabel txtName;
    private javax.swing.JLabel txtPhone;
    private javax.swing.JLabel txtScore1;
    private javax.swing.JLabel txtScore2;
    // End of variables declaration//GEN-END:variables
}
