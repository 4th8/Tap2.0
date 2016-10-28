/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tap2.pkg0;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.PSQLException;

/**
 *
 * @author Usman
 */
public class dbQuery {

    //private String All_Location_Field;
    //private String insertIntoLocation;
    private db database;
    public static class NoLocationException extends Exception{};
    public dbQuery(db database) {
        this.database = database;
    }

    ;
    
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
    public ResultSet get_All_Location_Field() {
        String All_Location_Field = "SELECT * FROM location;";
        ResultSet result = null;
        try {
            database.executeSelect(All_Location_Field);
            result = database.executeSelect(All_Location_Field);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        Thow Exception here.
        */
        return result;
    }
    public ResultSet getAllTemp(){
        
            String All_Location_Field = "SELECT temperature.time_stamp, temperature.degrees_c, location.abbreviation FROM temperature JOIN location on temperature.location_id = location.location_id;";
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


