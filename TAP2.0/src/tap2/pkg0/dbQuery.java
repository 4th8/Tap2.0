/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tap2.pkg0;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usman
 */
public class dbQuery {

    //private String All_Location_Field;
    //private String insertIntoLocation;
    private db database;
    public static class NoLocationException extends Exception{};
    public static class noImportLocationException extends Exception{};
    public static class noExportLocationException extends Exception{};
    public dbQuery(db database) {
        this.database = database;
    }

    private String tableQuery;
    

    public void filterTable(String startDate, String endDate, String startTime, String endTime, ArrayList<String> locations){
              
        if(startDate.isEmpty()==true || startDate==null || startDate.equals("0000-00-00") ){
           
            startDate="2000-01-01";
          
        }
        if(endDate.isEmpty()==true || endDate==null || endDate.equals("0000-00-00")){
            
            endDate="3000-01-01";//This should be the lateist date in the database.
            
          
        }
        String query =  "SELECT temperature.time_stamp, temperature.degrees_c, location.abbreviation FROM temperature JOIN location on temperature.location_id = location.location_id";
        if(!startDate.equals(""))
            query += " AND temperature.time_stamp >= '" + startDate + "'";
        if(!endDate.equals(""))
            query += " AND temperature.time_stamp < '" + endDate + "'";
         if(!startTime.equals(""))
            query += " AND EXTRACT(hour from temperature.time_stamp) >= '" + startTime + "'";
        if(!endTime.equals(""))
            query +=  " AND EXTRACT(hour from temperature.time_stamp) <= '" + endTime +"'";
        if(!locations.isEmpty()){
            query += " AND location.full_name = '";
            int x = locations.size();
            for(int i = 0; i < x; i++){
                if(i == 0){
                    query += locations.get(i) + "'";
                }
                else{
                    query += " OR location.full_name = '" + locations.get(i) + "'";
                }
            }
        }        
        this.tableQuery = query +";";
    }
    
    public void insertLocation(String serialNumber, String name, String abb) {
        String insertIntoLocation = String.format("INSERT INTO location (full_name,abbreviation)VALUES ('%s', '%s');", name, abb);
        try {
            database.executeInsert(insertIntoLocation);
            insertSensor(serialNumber, abb);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void insertTemperatureData(String timeStamp, double temp, String sn, String locationId) throws SQLException{
        String insertTemperature = String.format("INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id)"
                + " VALUES ('%s','%f','%s','%s');",timeStamp,temp,sn,locationId);
            database.executeInsert(insertTemperature);
    }
    
    public void insertSensor(String sn, String abb){
        String insertSensor = String.format("INSERT INTO sensor (sensor_serial, abbreviation)"
                + "VALUES ('%s', '%s');",sn,abb);
        try {
            database.executeInsert(insertSensor);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateSensor (String oldSn, String newSn){
        String updateSen = String.format(" UPDATE sensor SET sensor_serial ='%s' WHERE"
                + " sensor_serial ='%s';",newSn,oldSn);
        try {
            database.executeInsert(updateSen);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    public void updateExportLocation(String location){
        String query = String.format("UPDATE defaultSave SET filepath = '%s' WHERE kind = 'export';",location);
        try {
            database.executeInsert(query);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String getExportLocation() throws noExportLocationException{
        String query = "SELECT filepath FROM defaultSave WHERE kind = 'export';";
        String ans = null;
        ResultSet result = null;
        try {
            result  = database.executeSelect(query);
            result.next();
            ans = result.getString(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(ans == null){
            throw new noExportLocationException();
        }
        return ans;
    }
    
    
    
    
    public void updateImportLocation(String location){
        String query = String.format("UPDATE defaultSave SET filepath = '%s' WHERE kind = 'import';",location);
        try {
            database.executeInsert(query);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String getImportLocation() throws noImportLocationException{
        String getimport = "SELECT filepath FROM defaultSave WHERE kind = 'import';";
        String ans = null;
        ResultSet result = null;
        try {
            result  = database.executeSelect(getimport);
            result.next();
            ans = result.getString(1);
            System.out.println("query ans: " + ans);
            
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(ans == null){
            throw new noImportLocationException();
        }
            
        return ans;
    }
    
     public void removeSensor (String sn){
        String updateSen = String.format("UPDATE sensor SET sensor_serial ='' WHERE "
                + "sensor_serial ='%s';",sn);
        try {
            database.executeInsert(updateSen);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     public ResultSet getSensorSerialNumber(){
         ResultSet results = null;
         String sn="SELECT sensor_serial FROM sensor;";
        try {
            results=database.executeSelect(sn);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
         return results;
           
         }
     public void deleteSensor(String sn){
         String delSensor = String.format("DELETE from sensor WHERE sensor_serial = '%s'",sn);
        try {
            database.executeInsert(delSensor);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     

    
    public String getLocationIdBySerialNumber(String SN) throws NoLocationException {
        String ans = "";
        try {
            String getLocationId = String.format("select location.location_id from location INNER JOIN sensor ON (sensor.sensor_serial='%s' and sensor.abbreviation=location.abbreviation);", SN);
            ResultSet result = database.executeSelect(getLocationId);
            result.next();
            ans = result.getString(1);
        }catch (SQLException ex) {
            throw new NoLocationException();
        }
        return ans;
    }
    public void CLEAR(){
        try {
            database.executeInsert("DELETE FROM temperature;");
            database.executeInsert("DELETE FROM location;");
            database.executeInsert("DELETE FROM sensor;");
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String[] getAllLocations() {
        String All_Location_Field = "SELECT full_name FROM location;";
        ResultSet result = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            database.executeSelect(All_Location_Field);
            result = database.executeSelect(All_Location_Field);
             while (result.next()){
                 list.add(result.getString(1));
        }
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        int size = list.size();
        String[] ans = new String[size];
        for(int i = 0; i < size; i++){
            ans[i] = list.get(i);
        }
       
        /*
        Thow Exception here.
        */
        return ans;
    }
    public ResultSet getAllTemp(){
            String All_Location_Field;
            if(this.tableQuery == null){
                All_Location_Field = "SELECT temperature.time_stamp, temperature.degrees_c, location.abbreviation FROM temperature JOIN location on temperature.location_id = location.location_id;";
            }
            else{
                All_Location_Field = tableQuery;
            }
            
            ResultSet result = null;
        try {    
            database.executeSelect(All_Location_Field);
            result = database.executeSelect(All_Location_Field);
            
            /*
            Thow Exception here.
            */
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public ResultSet getStats(){
        
            String All_Location_Field = "WITH temperature(value) "
                    + "AS (SELECT degrees_c from temperature)"
                    + "SELECT avg(value) AS \"Average\",min(value),max(value),"
                    + "stddev(value),percentile_cont(0.25) WITHIN GROUP (ORDER BY value) AS"
                    + " \"Q1\",percentile_cont(0.50) "
                    + "WITHIN GROUP (ORDER BY value) AS "
                    + "\"Q2\",percentile_cont(0.75) "
                    + "WITHIN GROUP (ORDER BY value) AS \"Q3\" "
                    + ""
                    + "FROM temperature;";
            ResultSet result = null;
        try {    
            database.executeSelect(All_Location_Field);
            result = database.executeSelect(All_Location_Field);
            
            /*
            Thow Exception here.
            */
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
        
    

}


