package edu.monmouth.ccdt.data;

import edu.monmouth.ccdt.data.File;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class Version {

	private int number;
	private java.io.File directory;
	private ArrayList<File> files = new ArrayList<File>();
	private ArrayList<Change> changes;
	private ChangeComment versionComment;
	
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
		this.directory = fileFolder;
		this.changes = new ArrayList<Change>();
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
	            File fileModel = new File(file, this);
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
	public ArrayList<File> getFiles(){
		return files;
	}
	
	public void addFile(File file){
		if(file != null && !files.contains(file)){
			files.add(file);
		}
	}
	
	public void removeFile(File file){
		if(file != null && !files.contains(file)){
			//check by file name
			for(File checkFile: files){
				if(file.getFilePath().equals(checkFile.getFilePath())){
					files.remove(file);
					return;
				}
			}
			
		}
	}
	

	public String getVersionChangeComment(){
		int totalAdded     = 0;
		int totalChanged    = 0;
		int totalDeleted   = 0;
		int totalNotChanged= 0;
		
		for(Change change : changes){
			totalAdded      += change.getLineAmountAdded();
			totalChanged    += change.getLineAmountChanged();
			totalDeleted    += change.getLineAmountDeleted();
			totalNotChanged += change.getLineAmountNoChange();
		
		}
		
		return "//" + getName() + " date uploaded:" + versionComment.date + " | Total line counts - Added:"
				+ totalAdded + ", Deleted:" + totalDeleted + ", Changed:" + totalChanged + ", Not Changed:" + totalNotChanged;
	}
	
	public String createOverallVersionReport(){
		
		//TODO integrate with verisionComment
		int totalAdded     = 0;
		int totalChanged   = 0;
		int totalDeleted   = 0;
		int totalNotChanged= 0;
		
		for(Change change : getChanges()){
			totalAdded      += change.getLineAmountAdded();
			totalChanged    += change.getLineAmountChanged();
			totalDeleted    += change.getLineAmountDeleted();
			totalNotChanged += change.getLineAmountNoChange();
		}
		
		return getName() + " date uploaded:" + versionComment.date + " | Total line counts - Added:"
				+ totalAdded + ", Deleted:" + totalDeleted + ", Changed:" + totalChanged + ", Not Changed:" + totalNotChanged;
	
	}
	
	public void writeCommentsToFiles(java.io.File fileFolder){
		//get changes from each file
		//write version comment to file in the header
		//write comments to file about each change.
		
		if (!fileFolder.isDirectory()){
			System.err.println("Inputted file must be a directory.");
			return;	
		}
		
		for(File file: files){
			
			ArrayList<Line> lines = file.getLines();
			
			String fileName = fileFolder.getPath() + System.getProperty("file.separator") +file.getFileName();
			
			System.out.println("Writing file: " + fileName); 
			
			//write file
			java.io.File newFile = new java.io.File(fileName);
			
			try {
				// if file doesnt exists, then create it
				if (!newFile.exists()) {
					if(newFile.createNewFile()){

						FileOutputStream fstream = new FileOutputStream(newFile);
						DataOutputStream out = new DataOutputStream(fstream);
						BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out));
						
						//write version change label
						br.write(getVersionChangeComment());
						br.newLine();
						
						for (Line line : lines) {
							
							//TODO ADD change comment here
							br.write(line.getLine());
							br.newLine();
						}
						br.flush();
						
						br.close();
						out.close();
						
					}
				}else{
					System.out.println("File exists");
				}
			} catch (Exception e) {
				System.err
				.println("Could not parse file\nError: " + e.getMessage());
				return;
			}
			
		}
			
	}
	
	
	public ArrayList<Change> getChanges(){
		return changes;
	}
	
	public void addChange(Change change){
		this.changes.add(change);
	}

	public java.io.File getDirectory() {
		return directory;
	}
	
	@Override
	public String toString() {
		return "Version " +  number;
	}
	
	public ChangeComment getChangeComment(){
		if (this.versionComment == null){
			this.versionComment = new ChangeComment(this);
		}
		return this.versionComment;
	}
}
