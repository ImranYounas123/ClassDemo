package messenger_project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public abstract class DataManager <T> {
	 private String fileName;
	    private List<String> logs;

	    public DataManager(String fileName) {
	        this.fileName = fileName;
	        logs = new ArrayList<>();
	    }
	    
	    public List<String> readLog(){
	        try {
	            File file;
	            file = new File("logs/"+fileName);
	            if (file.exists()) {
	                Scanner scan = new Scanner(file);
	                while (scan.hasNext()) {
	                    logs.add(scan.nextLine());
	                }
	                scan.close();
	            }
	        } catch (FileNotFoundException ex) {
	            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return logs;
	    }
	    
	    public boolean writeLog(String data){
	        try {
	            File file;
	            file = new File("logs/"+fileName);
	            FileWriter fr = new FileWriter(file, true);
	            fr.write(data);
	            fr.close();
	        } catch (FileNotFoundException ex) {
	            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (IOException ex) {
	            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return true;
	    }
	    
	    public boolean clearLog(){
	        try {
	            File file;
	            file = new File("logs/"+fileName);
	            FileWriter fr = new FileWriter(file);
	            fr.write("");
	            fr.close();
	        } catch (FileNotFoundException ex) {
	            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (IOException ex) {
	            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return true;
	    }
	    
	    public abstract void fetchData();


}
