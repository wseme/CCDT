package edu.monmouth.ccdt.data;

import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class Version {

	private String name;
	private int number;
	private DateTime dateUploaded;
	private ArrayList<File> files;
	private ArrayList<Change> changes;
	
	public Version(String name, int number, java.io.File fileFolder){
		
		if(name == null){
			System.err.println("Inputted version name can not be null");
			return;
		}
		
		if(number <=0){
			System.err.println("Inputted version number needs to be greater than 0.");
			return;			
		}
		
		if(fileFolder == null){
			System.err.println("Inputted file can not be null.");
			return;	
		}
		
		this.name = name;
		this.number = number;
		this.dateUploaded = new DateTime();
		
		String directory = getFileDirectory(fileFolder);
		File file = new File(new java.io.File(directory));
		traverseDirectoriesToAdd(file);
		
	}
	
	/**
	 * get inputted root directory, ensure that it is a directory inputed
	 * @param fileFolder
	 * @return
	 */
	private String getFileDirectory(java.io.File fileFolder){

		String directory = null;
		try {
			directory = fileFolder.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!fileFolder.isDirectory()){
			directory = fileFolder.getParent();
		}
		
		return directory;
	}
	
	/**
	 * Traverse File structure and add all files to current version
	 * @param node
	 */
	private void traverseDirectoriesToAdd(File node){
	
		if(node == null){
			return;
		}
		
		java.io.File file = new java.io.File(node.getFileName());
		
		if (!file.isDirectory()){
			files.add(node);
		}
		
		if(file.isDirectory()){
			String[] subNote = file.list();
			for(String filename : subNote){
				traverseDirectoriesToAdd(new File(new java.io.File(filename)));
			}
		}
	}
	
	
	public String getName(){
		return name;
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
}
