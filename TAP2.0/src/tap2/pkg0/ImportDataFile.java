/*
 * Reads in the .csv file and adds the data to the database.
 * Will Automaticly find the S/N of the Sensor and use that to queary the
 * database to find the location of that sensor. If no location exists will queary
 * the user to input the location and update the database with that location / SN combo
 */
package tap2.pkg0;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(filename);
            XSSFSheet sheet = workbook.getSheetAt(0);
             
             
        } catch (IOException ex) {
            Logger.getLogger(ImportDataFile.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        
}
    
}
