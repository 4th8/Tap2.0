/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tap2.pkg0;

/**
 *
 * @author Usman
 */
public class dbQuery {

    //private String All_Location_Field;
    //private String insertIntoLocation;

    dbQuery() {
    }

    ;
    
    public String insertLocation(String id, String name, String abb) {
        String insertIntoLocation = String.format("INSERT INTO location (location_id,full_name,abbreviation)VALUES ('%s', '%s', '%s');", id, name, abb);
        return insertIntoLocation;
    }
    
    public String insertTemperatureData(String timeStamp, int temp, String sn, String locationId){
        String insertTemperature = String.format("INSERT INTO temperature (time_stamp,degrees_c,sensor_serial,location_id)"
                + " VALUES ('%s','%d','%s','%s');",timeStamp,temp,sn,locationId);
        return insertTemperature;
    }
    
    public String insertSensor(String sn, String abb){
        String insertSensor=String.format("INSERT INTO sensor (sensor_serial, abbreviation)"
                + "VALUES ('%s', '%s');",sn,abb);
        return insertSensor;
    }

    public String getLocationIdBySerialNumber(String SN) {
        String getLocationId = String.format("select location.location_id from location INNER JOIN sensor ON (sensor.sensor_serial='%s' and sensor.abbreviation=location.abbreviation);", SN);
        return getLocationId;
    }

    public String get_All_Location_Field() {
        String All_Location_Field = "SELECT * FROM location;";
        return All_Location_Field;
    }

}
