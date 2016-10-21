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
    
    public void insertLocation(String id, String name, String abb) {
        String insertIntoLocation = String.format("INSERT INTO location (location_id,full_name,abbreviation)VALUES ('%s', '%s', '%s');", id, name, abb);
        try {
            database.executeInsert(insertIntoLocation);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void insertTemperatureData(String timeStamp, double temp, String sn, String locationId){
        String insertTemperature = String.format("INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id)"
                + " VALUES ('%s','%f','%s','%s');",timeStamp,temp,sn,locationId);
        try {
            database.executeInsert(insertTemperature);
        } catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public String getLocationIdBySerialNumber(String SN) throws NoLocationException {
        String ans = "";
        try {
            String getLocationId = String.format("select location.location_id from location INNER JOIN sensor ON (sensor.sensor_serial='%s' and sensor.abbreviation=location.abbreviation);", SN);
            ResultSet result = database.executeSelect(getLocationId);
            result.next();
            ans = result.getString(1);
        }catch (SQLException ex) {
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        if("".equals(ans)){
            throw new NoLocationException();
        }
        return ans;
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
        
            String All_Location_Field = "SELECT * FROM temperature;";
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
