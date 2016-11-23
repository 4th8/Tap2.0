/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor..
 */
package GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import tap2.pkg0.dbQuery;

/**
 *
 * @author marc
 */
public class dataPage extends javax.swing.JFrame {

    static class InvalidFilenameException extends Exception {
    };

    static class NotCSVException extends Exception {
    };
    private dbQuery query;
    private String importDefaultLocation;
    private String exportDefaultLocation;
    private javax.swing.AbstractListModel<String> strings;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private long dateDiff=0;
    private int timeDiff=24;
    private String checkText;
    private String sDate = "";
    private String eDate = "";
    
    private ArrayList<String> locationsToShow ;
  

    /**
     * Creates new form dataPage
     *
     * @param query
     */
    public dataPage(dbQuery query) {
        this.query = query;
        this.checkText = "";
        try {
            this.importDefaultLocation = query.getImportLocation();
            this.exportDefaultLocation = query.getExportLocation();
            this.strings = new javax.swing.AbstractListModel<String>() {
                String[] locations = query.getAllLocations();

                public int getSize() {
                    return locations.length;
                }

                public String getElementAt(int i) {
                    return locations[i];
                }
            };
        } catch (dbQuery.noImportLocationException ex) {
            this.importDefaultLocation = null;
        } catch (dbQuery.noExportLocationException ex) {
            this.exportDefaultLocation = null;
        }
        startTime = "";
        endTime = "";
        startDate = "";
        endDate = "";
        locationsToShow  = new ArrayList<String>();
        updateResults();
        initComponents();
    }

    /*
    This should allow the user to sort the results shown on the screen.
    It will 
    */
    private void updateResults() {
        try {

            /*
            import praram here
             */
 /*
            Code snippet adaped from http://technojeeves.com/index.php/22-resultset-to-tablemodel
            
             */
            strings = new javax.swing.AbstractListModel<String>() {
                String[] locations = query.getAllLocations();

                public int getSize() {
                    return locations.length;
                }

                public String getElementAt(int i) {
                    return locations[i];
                }
            };
            query.filterTable(startDate, endDate, startTime, endTime, locationsToShow );
            ResultSet set = query.getAllTemp();// use the select query
            ResultSet copySet = set;//a copy so cursors dont get messed up.
            float sum = 0;
            float count;
            count = 0;
            ResultSetMetaData metaData = set.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();
            high = 0;
            low = 0;
            average = 0;
            
            ArrayList<Date> timeStampList = new ArrayList<Date>();
            while (set.next()) {                
                float value = set.getFloat("degrees_c");
                
             
                timeStampList.add(set.getDate("time_stamp"));
                count++;
                sum += value;
                average = sum / count;

                if (value > high) {
                    high = value;
                }
                if (value < low) {
                    low = value;
                }
                
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(set.getObject(i));
                }

                rows.addElement(newRow);
            }
            if(timeStampList.size() >0){
            sDate = timeStampList.get(0).toString();
            eDate = timeStampList.get(timeStampList.size()-1).toString(); 
            }
            System.out.println("Start Date: " + sDate);  
            System.out.println("End Date: " + eDate); 
            /*
            End Snippet
             */
            results = new DefaultTableModel(rows, columnNames);
            double expected = (double)dateDiff * timeDiff * this.locationsToShow.size();
            double percent = ((double)count / expected) * 100.0;
            checkText = Double.toString(percent) + "%";
//            endDate = query.getMaxDate(startDate, endDate, startTime, endTime, locationsToShow);
//            startDate = query.getMinDate(startDate, endDate, startTime, endTime, locationsToShow);
//            System.out.println("StartDate: "+ startDate);
//            System.out.println("EndDate: " + endDate);

        } catch (SQLException e) {
        }
        try {
            rawTable.setModel(results);
            jLabel4.setText(Float.toString(average));
            jLabel5.setText(Float.toString(low));
            jLabel6.setText(Float.toString(high));
            jLabel7.setText(checkText);
            int[] selected = jList1.getSelectedIndices();
            jList1.setModel(strings);
            jList1.setSelectedIndices(selected);
            List<String> values = jList1.getSelectedValuesList();
            ArrayList<String> newlist = new ArrayList<>();
            for(String location: values){
               newlist.add(location);
            }
            locationsToShow = newlist;
        } catch (NullPointerException ex) {
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

        jTabbedPane2 = new javax.swing.JTabbedPane();
        rawPanel2 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        importButton = new javax.swing.JButton();
        statusField = new javax.swing.JTextField();
        exportButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        rawTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane2.setBackground(new java.awt.Color(204, 255, 204));

        rawPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tap2/pkg0/resources/rsz_tap-portugal-logo.png"))); // NOI18N
        jLabel22.setText("Raw Data");

        importButton.setText("Import");
        importButton.setToolTipText("Import File");
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

        exportButton.setText("Export");
        exportButton.setToolTipText("Export File");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        rawTable.setAutoCreateRowSorter(true);
        rawTable.setBackground(new java.awt.Color(235, 229, 255));
        rawTable.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        rawTable.setModel(results);
        rawTable.setToolTipText("");
        jScrollPane1.setViewportView(rawTable);
        rawTable.getAccessibleContext().setAccessibleParent(rawTable);

        jButton2.setText("Add Location/Sensor");
        jButton2.setToolTipText("Add Location Name, Abbreviation, and Serial Number");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 102, 153));
        jButton3.setForeground(new java.awt.Color(0, 0, 51));
        jButton3.setText("CLEAR DATABASE");
        jButton3.setToolTipText("Pressing this button will clear everything in the Database");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Remove Sensor");
        jButton4.setToolTipText("Remove Sensor Serial Number ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setText("Average");

        jLabel2.setText("Low");

        jLabel3.setText("High");

        String avg  = Float.toString(average);
        jLabel4.setText(avg);
        jLabel4.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel4PropertyChange(evt);
            }
        });

        String lowstring = Float.toString(low);
        jLabel5.setText(lowstring);

        String highString = Float.toString(high);
        jLabel6.setText(highString);

        jList1.setModel(strings);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList1MouseReleased(evt);
            }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jButton1.setText("Update Sensor");
        jButton1.setToolTipText("Update Sensor Serial Number");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateSensorActionPerformed(evt);
            }
        });

        jButton6.setText("Date Filter");
        jButton6.setToolTipText("Select Start Date & End Date");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateFilterActionPerformed(evt);
            }
        });

        jButton7.setText("Time Filter");
        jButton7.setToolTipText("Select Start Time & End Time ");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeFilterActionPerformed(evt);
            }
        });

        jLabel7.setText(checkText);

        javax.swing.GroupLayout rawPanel2Layout = new javax.swing.GroupLayout(rawPanel2);
        rawPanel2.setLayout(rawPanel2Layout);
        rawPanel2Layout.setHorizontalGroup(
            rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rawPanel2Layout.createSequentialGroup()
                .addContainerGap(165, Short.MAX_VALUE)
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rawPanel2Layout.createSequentialGroup()
                        .addComponent(importButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exportButton)
                        .addGap(18, 18, 18)
                        .addComponent(statusField, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rawPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)))
                    .addGroup(rawPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rawPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton6)
                            .addComponent(jButton7))))
                .addContainerGap())
            .addGroup(rawPanel2Layout.createSequentialGroup()
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(233, 233, 233)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(134, 134, 134))
        );
        rawPanel2Layout.setVerticalGroup(
            rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rawPanel2Layout.createSequentialGroup()
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rawPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(rawPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rawPanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importButton)
                    .addComponent(statusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(rawPanel2Layout.createSequentialGroup()
                        .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(rawPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addGap(1, 1, 1)
                        .addComponent(jButton7)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addGap(18, 18, 18))
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
        } catch (NotCSVException ex) {
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "File must be a .csv format.", "Invalid Filetype", JOptionPane.ERROR_MESSAGE);
        }
        updateResults();
    }//GEN-LAST:event_importButtonActionPerformed
    private void importFile() throws InvalidFilenameException, NotCSVException {
        JFileChooser chooser = new JFileChooser();
        boolean fileChosen = false;
        if (this.importDefaultLocation != null) {
            File currentDir = new File(this.importDefaultLocation);
            chooser.setCurrentDirectory(currentDir);
        }
        String serialNumber = "";
        String locationID = "";
        int num = 0;
        boolean checkifopen=true;
        int chooserValue = chooser.showOpenDialog(this);
        if (chooserValue == JFileChooser.APPROVE_OPTION) {
            try {
                fileChosen = true;
                Scanner fin;
                fin = new Scanner(chooser.getSelectedFile());
                String filename = chooser.getSelectedFile().getName();
                if (!filename.endsWith(".csv")) {
                    throw new NotCSVException();
                }
                Pattern pattern = Pattern.compile("([0-9]{6})");
                Matcher matcher = pattern.matcher(filename);
                if (matcher.find()) {
                    serialNumber = (matcher.group(1));
                    while (locationID.equals("")) {
                        try {
                            locationID = query.getLocationIdBySerialNumber(serialNumber);
                        } catch (dbQuery.NoLocationException ex) {
                            
     
                            if(addSensor(serialNumber,checkifopen)==false){
                               // System.out.print(checkifopen);
                                return;
                            }
                            checkifopen=true;
        
                        }
                    }
                } else {
                    throw new InvalidFilenameException();
                }
                if (fin.hasNextLine()) {
                    fin.nextLine();
                } else {
                    System.out.println("You meesed up.\n" + filename);
                }
                
                while (fin.hasNext()) {
                    String rawline = fin.nextLine();
                    String[] line = rawline.split(",");
                    String timestamp = line[0];
                    double temp = Double.parseDouble(line[1]);
                    try {
                        query.insertTemperatureData(timestamp, temp, serialNumber, locationID);
                        num++;
                    } catch (SQLException ex) {
                        continue;
                    }
                }
                //textArea.setText(buffer);
                fin.close();
                this.importDefaultLocation = chooser.getSelectedFile().getParent();
                query.updateImportLocation(this.importDefaultLocation);
                statusField.setText("Load " + chooser.getSelectedFile().getAbsolutePath());
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "File not found!");
            }
        }
        String message;
        if (fileChosen && num > 0) {
            message = Integer.toString(num) + " temperatures added.";
            JOptionPane.showMessageDialog(this, message);
        }
        if (num == 0 && fileChosen) {
            message = "File has already been added to the database.";
            JOptionPane.showMessageDialog(this, message);
        }

        updateResults();
    }

    private void addSensor() {
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
            query.insertLocation(sensorSerialNumber.getText(), fullName.getText(), abb.getText());
        }
        updateResults();
    }

    private boolean addSensor(String serialNumber,boolean checkifopen) {
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
            if(fullName.getText().equals("") || fullName.getText().isEmpty()==true || fullName.getText()==null){
                JOptionPane.showMessageDialog(this, "Must Fill in All Blank Areas First");
                checkifopen=false;
            
            } 
            else if(abb.getText().equals("") || abb.getText().isEmpty()==true || abb.getText()==null){
                JOptionPane.showMessageDialog(this, "Must Fill in All Blank Areas First");
                checkifopen=false;
            }
            
            else{
                query.insertLocation(serialNumber, fullName.getText(), abb.getText());
            }
            
           
        }
        if (result == JOptionPane.CANCEL_OPTION) {
           checkifopen=false;
           
        }
         
        
        
        
        updateResults();
        //System.out.print(checkifopen);
        return checkifopen;
    }

    private void removeSensor() throws SQLException {

        JPanel serialPanel = new JPanel();
        serialPanel.add(new JLabel("Select Serial Number"));

        ResultSet set = query.getSensorSerialNumber();
        List<String> list = new ArrayList<String>();

        int count = 0;

        while (set.next()) {
            list.add(set.getString(1));

        }

        String[] a = new String[list.size()];
        while (count < list.size()) {
            a[count] = list.get(count);

            count++;
        }
        JComboBox<Object> jComboBox1 = new javax.swing.JComboBox<>();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(a));
        serialPanel.add(jComboBox1);

        int result = JOptionPane.showConfirmDialog(null, serialPanel,
                "Please fill out the form.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                System.out.print(jComboBox1.getSelectedItem());

                query.deleteSensor(jComboBox1.getSelectedItem().toString());
                JOptionPane.showMessageDialog(this, "Sensor Successfully Removed");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "No Sensors to Remove");
            }
        }
        updateResults();
    }

    private void updateSensor() throws SQLException {
        JTextField inputNewSerial = new JTextField(8);

        JPanel serialPanel = new JPanel();

        serialPanel.add(new JLabel("Select Serial Number"));

        ResultSet set = query.getSensorSerialNumber();
        List<String> list = new ArrayList<String>();

        int count = 0;

        while (set.next()) {
            list.add(set.getString(1));

        }

        String[] a = new String[list.size()];
        while (count < list.size()) {
            a[count] = list.get(count);

            count++;
        }
        JComboBox<Object> jComboBox1 = new javax.swing.JComboBox<>();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(a));
        serialPanel.add(jComboBox1);
        serialPanel.add(new JLabel("New Serial Number:"));
        serialPanel.add(inputNewSerial);

        int result = JOptionPane.showConfirmDialog(null, serialPanel,
                "Please fill out the form.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                System.out.print(jComboBox1.getSelectedItem());

                query.updateSensor(jComboBox1.getSelectedItem().toString(), inputNewSerial.getText());
                JOptionPane.showMessageDialog(this, "Sensor Successfully Updated");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "No Sensors to Update");
            }
        }
        updateResults();
    }

    private void dateFilter() throws ParseException {

        JPanel serialPanel = new JPanel();

        UtilDateModel modelStartDate = new UtilDateModel();
        UtilDateModel modelEndDate = new UtilDateModel();
        
       System.out.println(sDate.substring(5,7));
        modelStartDate.setDate(Integer.parseInt((sDate.substring(0, 4))),
                (Integer.parseInt((sDate.substring(5, 7)))-1), Integer.parseInt((sDate.substring(8, 10))));
        modelEndDate.setDate(Integer.parseInt((eDate.substring(0, 4))),
         (Integer.parseInt((eDate.substring(5, 7)))-1), Integer.parseInt((eDate.substring(8, 10))));
        // Need this...
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl dateStartPanel = new JDatePanelImpl(modelStartDate, p);
        JDatePanelImpl dateEndPanel = new JDatePanelImpl(modelEndDate, p);

        // Don't know about the formatter, but there it is...
        serialPanel.add(new JLabel("Select Start Date"));
        JDatePickerImpl dateStartPicker = new JDatePickerImpl(dateStartPanel, new DateLabelFormatter());
        serialPanel.add(dateStartPicker);

        serialPanel.add(new JLabel("Select End Date"));
        JDatePickerImpl dateEndPicker = new JDatePickerImpl(dateEndPanel, new DateLabelFormatter());
        serialPanel.add(dateEndPicker);

        int result = JOptionPane.showConfirmDialog(null, serialPanel,
                "Please fill out the form.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            startDate = dateStartPicker.getJFormattedTextField().getText(); // Global Variable
            endDate = dateEndPicker.getJFormattedTextField().getText(); //   Global Variable

            String date = String.format("Start Date: %s End Date: %s", startDate, endDate);
            System.out.println(date);
        }
        
        //String startDate1=startDate.substring(0,10);
        //String endDate1=endDate.substring(0,10);
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          
        Date d1 = null;
	Date d2 = null;
        
        if(startDate.isEmpty()==true || startDate==null ){
           
            startDate="0000-00-00";
          
        }
        if(endDate.isEmpty()==true || endDate==null){
            
            endDate="0000-00-00";
          
        }
         

        d1 = format.parse(startDate+" 00:00:00");
        d2 = format.parse(endDate+" 00:00:00");
        
			
        long diff = d2.getTime() - d1.getTime();
        
        //System.out.print(diff);
        
        //long diffSeconds = diff / 1000 % 60;
        //long diffMinutes = diff / (60 * 1000) % 60;
        //long diffHours = diff / (60 * 60 * 1000) % 24;
        dateDiff = diff / (24 * 60 * 60 * 1000);
        if(this.startDate.equals(this.endDate) && (this.endDate.isEmpty()!= true || this.endDate != null)&& (this.startDate.isEmpty()!= true || this.startDate != null)){
            dateDiff = 1;
        }
        
        
        
//        System.out.print(diffDays + " days, ");
//        System.out.print(diffHours + " hours, ");
//	System.out.print(diffMinutes + " minutes, ");
//	System.out.print(diffSeconds + " seconds.");
        updateResults();
        System.out.print(dateDiff + " days.");
    }

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed

        try {
            JFileChooser chooser = new JFileChooser();
            if (this.exportDefaultLocation != null) {
                try {
                    File file = new File(query.getExportLocation());
                    chooser.setCurrentDirectory(file);
                } catch (Exception e) {
                    ;
                }
            }
            int chooserValue = chooser.showSaveDialog(this);
            if (chooserValue == JFileChooser.APPROVE_OPTION) {

                statusField.setText("Saved " + chooser.getSelectedFile().getAbsolutePath());

            }
            Writer writer = null;
            DefaultTableModel dtm = (DefaultTableModel) rawTable.getModel();
            int nRow = dtm.getRowCount();
            int nCol = dtm.getColumnCount();
            File outputfile = chooser.getSelectedFile();
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputfile.getAbsolutePath() + ".csv"), "UTF-8"));

                //write the header information
                StringBuffer bufferHeader = new StringBuffer();
                for (int j = 0; j < nCol; j++) {
                    bufferHeader.append(dtm.getColumnName(j));
                    if (j != nCol) {
                        bufferHeader.append(", ");
                    }
                }
                writer.write(bufferHeader.toString() + "\r\n");

                //write row information
                for (int i = 0; i < nRow; i++) {
                    StringBuffer buffer = new StringBuffer();
                    for (int j = 0; j < nCol; j++) {
                        buffer.append(dtm.getValueAt(i, j));
                        if (j != nCol) {
                            buffer.append(", ");
                        }
                    }
                    writer.write(buffer.toString() + "\r\n");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(dataPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(dataPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(dataPage.class.getName()).log(Level.SEVERE, null, ex);
            }

            writer.close();
            JOptionPane.showMessageDialog(this, "File was Exported Correctly");
            this.exportDefaultLocation = outputfile.getParent();
            query.updateExportLocation(this.exportDefaultLocation);
            System.out.println(this.exportDefaultLocation);
        } catch (IOException ex) {
            Logger.getLogger(dataPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_exportButtonActionPerformed

    private void timeFilter() {
        JPanel timePanel = new JPanel();

        SpinnerModel startModel = new SpinnerDateModel();
        SpinnerModel endModel = new SpinnerDateModel();
        
        JSpinner timeStartSpinner = new JSpinner(startModel);
        JSpinner timeEndSpinner = new JSpinner(endModel);

        
        JComponent startEditor = new JSpinner.DateEditor(timeStartSpinner, "HH:00:00");
        timePanel.add(new JLabel("Start Time"));
        timeStartSpinner.setEditor(startEditor);
        timePanel.add(timeStartSpinner);

        
        JComponent endEditor = new JSpinner.DateEditor(timeEndSpinner,"HH:00:00");
        timePanel.add(new JLabel("End Time"));
        timeEndSpinner.setEditor(endEditor);
        timePanel.add(timeEndSpinner);


        int result = JOptionPane.showConfirmDialog(null, timePanel,
                "Please fill out the form.", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                timeStartSpinner.commitEdit();
                timeEndSpinner.commitEdit();            
            } catch (java.text.ParseException e) {
            }
            
            startTime  = new SimpleDateFormat("HH:00:00").format(timeStartSpinner.getValue());
            endTime = new SimpleDateFormat("HH:00:00").format(timeEndSpinner.getValue());
            
           String time = String.format("Start Time: %s End Time: %s", startTime, endTime);
           System.out.println(time);

        }
        
       startTime=startTime.substring(0,2);
       endTime=endTime.substring(0,2);
       
       timeDiff=abs(parseInt(endTime)-parseInt(startTime));
       
       System.out.print(timeDiff);
       System.out.print(startTime+endTime);
        
       
       updateResults();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        addSensor();
        updateResults();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        query.CLEAR();
        updateResults();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            removeSensor();
        } catch (SQLException ex) {
            Logger.getLogger(dataPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void updateSensorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateSensorButtonActionPerformed

    }//GEN-LAST:event_updateSensorButtonActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
       
    }//GEN-LAST:event_jList1ValueChanged

    private void jLabel4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel4PropertyChange
        updateResults();
    }//GEN-LAST:event_jLabel4PropertyChange

    private void updateSensorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateSensorActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            updateSensor();
        } catch (SQLException ex) {
            Logger.getLogger(dataPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateResults();
    }//GEN-LAST:event_updateSensorActionPerformed

    private void timeFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeFilterActionPerformed
        // TODO add your handling code here:
        timeFilter();
    }//GEN-LAST:event_timeFilterActionPerformed

    private void dateFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateFilterActionPerformed
        try {
            // TODO add your handling code here:
            dateFilter();
        } catch (ParseException ex) {
            Logger.getLogger(dataPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_dateFilterActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        updateResults();
    }//GEN-LAST:event_jList1MouseClicked

    private void jList1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseReleased
        updateResults();
    }//GEN-LAST:event_jList1MouseReleased
    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // textArea.setText("");
        // statusField.setText("New file");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exportButton;
    private javax.swing.JButton importButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPanel rawPanel2;
    private javax.swing.JTable rawTable;
    private javax.swing.JTextField statusField;
    // End of variables declaration//GEN-END:variables
    private TableModel results;
    private float average;
    private float low;
    private float high;

    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
}
