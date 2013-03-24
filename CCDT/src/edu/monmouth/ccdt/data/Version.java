package edu.monmouth.ccdt.data;

import edu.monmouth.ccdt.data.File;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class Version {

	private int number;
	private DateTime dateUploaded;
	private java.io.File directory;
	private ArrayList<File> files = new ArrayList<File>();
	private ArrayList<Change> changes;
	
	public Version(int number, java.io.File fileFolder){		
		
		if(number <=0){
			System.err.println("Inputted version number needs to be greater than 0.");
			return;			
		}
		
		if(fileFolder == null){
			System.err.println("Inputted file can not be null.");
			return;	
		}
		
		if (!fileFolder.isDirectory()){
			System.err.println("Inputted file must be a directory.");
			return;	
		}
		this.number = number;
		this.dateUploaded = new DateTime();
		this.directory = fileFolder;
		traverseFiles(fileFolder.listFiles());
		
	}
	
	/**
	 * Traverse File structure and add all files to current version
	 * @param node
	 */
	private void traverseFiles(java.io.File[] files){
	
		for (java.io.File file : files) {
	        if (file.isDirectory()) {
	            System.out.println("Directory: " + file.getName());
	            traverseFiles(file.listFiles()); // Calls same method again.
	        } else {
	            System.out.println("File: " + file.getName());
	            File fileModel = new File(file);
	            this.files.add(fileModel);
	        }
	    }
	}
	
	
	public String getName(){
		return "Version " + number;
	}
	
	public int getNumber(){
		return number;
	}
	
	public DateTime getDateUploaded(){
		return dateUploaded;
	}
	
	public ArrayList<File> getFiles(){
		return files;
	}
	public ArrayList<Change> getChange(){
		return changes;
	}

	public java.io.File getDirectory() {
		return directory;
	}
}
