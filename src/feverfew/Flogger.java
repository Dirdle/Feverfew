/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

import java.io.IOException;
import java.nio.file.Path;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 *
 * @author Oscar
 * 
 * Whip the X's, pinch the O's! What this class does, no-one knows!
 */


public class Flogger {
    
    Path logFileLoc;
    PrintWriter outputStream;
    Long startTime;
    
    public Flogger(Path logTo) {
        logFileLoc = logTo;
        startTime = System.currentTimeMillis();        
        
        try {
            // try to create a file writer at the location to which to log
            outputStream = new PrintWriter(new FileWriter(logFileLoc.toFile()));
            //outputStream.append("Opened log succesfully" + '\n');
        }
        catch (IOException x){
            // code to handle exception
            x.printStackTrace();
            throw new RuntimeException("Failed to write log");
        }       

    }
    
    public void log(String s){
        // TODO add code to include extra info with each log, eg time
               
        String time = Long.toString(System.currentTimeMillis() - startTime);
        outputStream.println(time + ": " + s + '\n');
        
        // Flush after each log, to ensure the log gets written even if 
        // feverfew crashes. Seems to work.
        outputStream.flush();
    }
    
    public void close(){
        // The log has to be closed, or it won't save properly
        // if the program exits due to an error, I suspect the logging will fail
        // which is a huge problem, but I have no idea what to do about it
        
        outputStream.close();
    }
    
}
