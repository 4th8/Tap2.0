/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import tap2.pkg0.dbQuery;

/**
 *
 * @author marc
 */
public class dataPage extends javax.swing.JFrame {
    static class InvalidFilenameException extends Exception{};
    static class NotCSVException extends Exception{};
    private dbQuery query;
    /**
     * Creates new form dataPage
     * @param query
     */
    public dataPage(dbQuery query) {
        this.query = query;
        updateResults();
        initComponents();
    }
    /*
    This should allow the user to sort the results shown on the screen.
    It will 
    */
    private void updateResults(){
        try {
            
            
            /*
            import praram here
            */
            /*
            Code snippet adaped from http://technojeeves.com/index.php/22-resultset-to-tablemodel
            
            */
            ResultSet set = query.getAllTemp();// use the select query
            ResultSetMetaData metaData = set.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();

            while (set.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(set.getObject(i));
                }

                rows.addElement(newRow);
            }
            /*
            End Snippet
            */
            results = new DefaultTableModel(rows, columnNames);
        } catch (SQLException e) {
        }
        try{
        jTable1.setModel(results);
        }catch(NullPointerException ex){
            ;
        }
        
    }
    //Location GUI=new Location();
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tapPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("tapPU").createEntityManager();
        locationQuery = java.beans.Beans.isDesignTime() ? null : tapPUEntityManager.createQuery("SELECT l FROM Location l");
        locationList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : locationQuery.getResultList();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        rawPanel2 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        qrdYears1 = new javax.swing.JLabel();
        qrdMonths1 = new javax.swing.JLabel();
        qrdDays1 = new javax.swing.JLabel();
        qrdHours1 = new javax.swing.JLabel();
        importButton = new javax.swing.JButton();
        statusField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel22.setText("Raw Data");

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel24.setText("Month(s)");

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel26.setText("Year(s)");

        jLabel72.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel72.setText("Day(s)");

        jLabel73.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel73.setText("Hour(s)");

        qrdYears1.setText("N/A");

        qrdMonths1.setText("N/A");

        qrdDays1.setText("N/A");

        qrdHours1.setText("N/A");

        importButton.setText("Import");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        statusField.setEditable(false);
        statusField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusFieldActionPerformed(evt);
            }
        });

        jButton1.setText("Export");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(results);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getAccessibleContext().setAccessibleParent(jTable1);

        jButton2.setText("Add Location/Sensor");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("CLEAR DATABASE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Remove Sensor");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rawPanel2Layout = new javax.swing.GroupLayout(rawPanel2);
        rawPanel2.setLayout(rawPanel2Layout);
        rawPanel2Layout.setHorizontalGroup(
            rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rawPanel2Layout.createSequentialGroup()
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rawPanel2Layout.createSequentialGroup()
                        .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(rawPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(importButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(rawPanel2Layout.createSequentialGroup()
                                .addGap(204, 204, 204)
                                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(rawPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addGap(84, 84, 84)
                                        .addComponent(jLabel24))
                                    .addGroup(rawPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(qrdYears1)
                                        .addGap(124, 124, 124)
                                        .addComponent(qrdMonths1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel72)
                                    .addComponent(qrdDays1))))
                        .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rawPanel2Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel73)
                                    .addGroup(rawPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(qrdHours1))))
                            .addGroup(rawPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(statusField, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rawPanel2Layout.createSequentialGroup()
                        .addContainerGap(103, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(215, 215, 215))
            .addGroup(rawPanel2Layout.createSequentialGroup()
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rawPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jButton3))
                    .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(365, 365, 365)
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        rawPanel2Layout.setVerticalGroup(
            rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rawPanel2Layout.createSequentialGroup()
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rawPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel26)
                            .addComponent(jLabel72)
                            .addComponent(jLabel73)))
                    .addGroup(rawPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(qrdYears1)
                    .addComponent(qrdMonths1)
                    .addComponent(qrdDays1)
                    .addComponent(qrdHours1)
                    .addComponent(jButton3))
                .addGap(35, 35, 35)
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importButton)
                    .addComponent(statusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Raw Data", rawPanel2);
        jTabbedPane2.addTab("Graph", jTabbedPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1149, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //private static String fileLocation;
    private void statusFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusFieldActionPerformed

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        try {
            importFile();
        } catch (InvalidFilenameException ex) {
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Name must include serial number. \n Ex: 2015_<<serialNumber>>_temp.csv", "Invalid Filename", JOptionPane.ERROR_MESSAGE);            
        }catch (NotCSVException ex) {
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "File must be a .csv format.", "Invalid Filetype", JOptionPane.ERROR_MESSAGE);            
        }
    }//GEN-LAST:event_importButtonActionPerformed
    private void importFile()throws InvalidFilenameException, NotCSVException{
        JFileChooser chooser = new JFileChooser();
        String serialNumber = "";
        String locationID = "";
        int num = 0;
        int chooserValue = chooser.showOpenDialog(this);
        if (chooserValue == JFileChooser.APPROVE_OPTION) {
            try {
                Scanner fin;
                fin = new Scanner(chooser.getSelectedFile());
                String filename = chooser.getSelectedFile().getName();
                System.out.println(chooser.getSelectedFile().getPath());
                if(!filename.endsWith(".csv")){
                    throw new NotCSVException();
                }
                Pattern pattern = Pattern.compile("([0-9]{6})");
                Matcher matcher = pattern.matcher(filename);
                if (matcher.find()){
                    serialNumber = (matcher.group(1));
                    while (locationID.equals("")){
                        try {
                            locationID = query.getLocationIdBySerialNumber(serialNumber);
                        } catch (dbQuery.NoLocationException ex) {
                            addSensor(serialNumber);
                        }
                    }
                }
                else{
                    throw new InvalidFilenameException();
                }
                if(fin.hasNextLine()){
                    fin.nextLine();
                }
                else{
                    System.out.println("You meesed up.\n"+filename);
                }
               while(fin.hasNext()){
                    String rawline = fin.nextLine();
                    String [] line = rawline.split(",");
                    String timestamp = line[0];
                    double temp = Double.parseDouble(line[1]);
                    try{
                    query.insertTemperatureData(timestamp, temp, serialNumber, locationID);
                    num++;
                    }catch(SQLException ex){
                        continue;
                    }
                }
                //textArea.setText(buffer);
                fin.close();
                statusField.setText("Load " + chooser.getSelectedFile().getAbsolutePath());
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "File not found!");
            }
        }
        String message = Integer.toString(num) + " temperatures added.";
        if(num == 0){
            message = "File has already been added to the database.";
        }
        JOptionPane.showMessageDialog(this, message);
        updateResults();
    }
    private void addSensor(){    
        JTextField fullName = new JTextField(5);
        JTextField abb = new JTextField(3);
        JTextField sensorSerialNumber = new JTextField(6);
        
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Location Name:"));
        myPanel.add(fullName);
        myPanel.add(new JLabel("Location abbreviation:"));
        myPanel.add(abb);
        myPanel.add(new JLabel("Sensor Serial Number:"));
        myPanel.add(sensorSerialNumber);
        int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Please fill out the form.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            query.insertLocation(sensorSerialNumber.getText(),fullName.getText(), abb.getText());
      }
    }
    private void addSensor(String serialNumber){        
        JTextField fullName = new JTextField(5);
        JTextField abb = new JTextField(3);
        
        
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Location Name:"));
        myPanel.add(fullName);
        myPanel.add(new JLabel("Location abbreviation:"));
        myPanel.add(abb);
        int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Please fill out the form.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            query.insertLocation(serialNumber,fullName.getText(), abb.getText());
        }
        updateResults();
    }
    private void removeSensor(){
       JTextField serialNumberInput = new JTextField(8);
       
       JPanel serialPanel = new JPanel();
       serialPanel.add(new JLabel("Serial Number"));
       serialPanel.add(serialNumberInput);
       
       int result = JOptionPane.showConfirmDialog(null, serialPanel, 
               "Please fill out the form.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            query.deleteSensor(serialNumberInput.getText());
        }
       
       

    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        int chooserValue = chooser.showSaveDialog(this);
        if (chooserValue == JFileChooser.APPROVE_OPTION) {
            try {
                PrintWriter fout = new PrintWriter(chooser.getSelectedFile());
                //fout.print(textArea.getText());
                fout.close();

                statusField.setText("Saved " + chooser.getSelectedFile().getAbsolutePath());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(dataPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        addSensor();
        updateResults();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        query.CLEAR();
        updateResults();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        removeSensor();
        
    }//GEN-LAST:event_jButton4ActionPerformed
    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // textArea.setText("");
        // statusField.setText("New file");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton importButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private java.util.List<GUI.Location> locationList;
    private javax.persistence.Query locationQuery;
    private javax.swing.JLabel qrdDays1;
    private javax.swing.JLabel qrdHours1;
    private javax.swing.JLabel qrdMonths1;
    private javax.swing.JLabel qrdYears1;
    private javax.swing.JPanel rawPanel2;
    private javax.swing.JTextField statusField;
    private javax.persistence.EntityManager tapPUEntityManager;
    // End of variables declaration//GEN-END:variables
    private TableModel results;
}
