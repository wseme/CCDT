package edu.monmouth.ccdt.data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class File {
	
	private String filePath;
	private String fileName;
	private ArrayList<Line> lines;
	private Version version;
	public File(java.io.File file, Version version){
		
		if(file == null){
			System.err.println("Inputted file can not be null");
			return;
		}
		
		if(file.getName().endsWith(".jpg") ||file.getName().endsWith(".jpeg") ||file.getName().endsWith(".gif") ||file.getName().endsWith(".png") ){
			System.err.println("Inputted file may not be a .jpeg, .gif, or .png file");
			return;
		}
		
		this.fileName = file.getName();
		this.version = version;
		
		try {
			this.filePath = file.getCanonicalPath();			
		} catch (IOException e1) {
			System.err.println("Check if inputted file path is valid.");
			e1.printStackTrace();
			return;
		}
		lines = new ArrayList<Line>();
		int lineNumber = 1;
		String strLine;
		
		try{
			  FileInputStream fstream = new FileInputStream(file);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
	  
			  while ((strLine = br.readLine()) != null)   {
				  Line line = new Line(strLine, lineNumber);
				  lines.add(line);
				  lineNumber++;
			  }
			  
			  in.close();
		}catch (Exception e){
			  System.err.println("Could not parse file\nError: " + e.getMessage());
			  return;
		}
		
		
	}
	
	public String getFileName(){
		return this.fileName;
	}
	
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFilePath(){
		return this.filePath;
	}
	
	
	public ArrayList<Line> getLines(){
		return lines;
	}
	
	public int getNumberOfLines(){
		return lines.size();
	}
	
	public void setLines(ArrayList<Line> lines){
		this.lines  = lines;
	}

	public File getSameFileFromVersion(Version version) {
		for (File otherFile : version.getFiles()){
			if (otherFile.fileName.equals(this.fileName)){
				return otherFile;
			}
		}
		return null;
	}

	public Version getVersion() {
		return this.version;
	}
}
