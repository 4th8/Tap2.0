/*
 * Reads in the .csv file and adds the data to the database.
 * Will Automaticly find the S/N of the Sensor and use that to queary the
 * database to find the location of that sensor. If no location exists will queary
 * the user to input the location and update the database with that location / SN combo
 */
package tap2.pkg0;
import org.apache.poi.hssf.eventusermodel;

/**
 *
 * @author i4th8
 */
public class ImportDataFile {
    private final String filename; //Filename for the input file
    private final dbQuery db;
    ImportDataFile(String filename, dbQuery db){
        this.filename = filename;
        this.db = db;
        execute();
    }
    private void execute(){
        XSSFWorkbook workbook = new XSSFWorkbook(filename);
}
    
}
