/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package feverfew;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

/**
*
* @author Oscar
*
* Whip the X's, pinch the O's! What this class does, no-one knows!
*/


public class FLogger {
    
    Path logFileLoc;
    PrintWriter outputStream;
    Long startTime;
    
    private StringBuilder logBuild;
    private State previousState = null;
    
    public FLogger(Path logTo) {
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
        // Maybe format into HTML, I dunno
        logBuild = new StringBuilder(0);
        String time = Long.toString(System.currentTimeMillis() - startTime);
        logBuild.append("LOG: ")
                .append(time)
                .append(": ")
                .append(s);
        outputStream.println(logBuild.toString());
        
        // Flush after each log, to ensure the log gets written even if
        // feverfew crashes. Seems to work.
        outputStream.flush();
    }
    
    public void stateSet(State s){
        // Log changes to the state of the game
        logBuild = new StringBuilder(0);
        String time = Long.toString(System.currentTimeMillis() - startTime);
        logBuild.append("STA: ")
                .append(time)
                .append(": ");
        if (!(previousState == null)){
            logBuild.append("FROM: ")
                .append(previousState.toString())
                .append(" TO: ")
                .append(s.toString());
        }
        else {
            logBuild.append("TO: ")
                    .append(s.toString());
        }
        this.previousState = s;
        outputStream.println(logBuild.toString());
        outputStream.flush();
    }
    
    public void input(String desc){
        // record a user input
        logBuild = new StringBuilder(0);
        String time = Long.toString(System.currentTimeMillis() - startTime);
        logBuild.append("INP: ")
                .append(time)
                .append(": ")
                .append(desc);
        outputStream.println(logBuild.toString());
        
        // Flush after each log, to ensure the log gets written even if
        // feverfew crashes. Seems to work.
        outputStream.flush();        
    }
    
    public void output(String desc){
        // record a program output
        logBuild = new StringBuilder(0);
        String time = Long.toString(System.currentTimeMillis() - startTime);
        logBuild.append("OUT: ")
                .append(time)
                .append(": ")
                .append(desc);
        outputStream.println(logBuild.toString());
        
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
