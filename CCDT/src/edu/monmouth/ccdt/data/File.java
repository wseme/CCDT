package edu.monmouth.ccdt.data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class File {
	
	private String fileName;
	private ArrayList<Line> lines;
	
	public File(java.io.File file){
		if(file == null){
			System.err.println("Inputted file can not be null");
			return;
		}
		try {
			this.fileName = file.getCanonicalPath();
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
	
	public ArrayList<Line> getLines(){
		return lines;
	}
	
	public int getNumberOfLines(){
		//TODO implement getNumberOfLines(changetype) if that is correct
		return lines.size();
	}
}
