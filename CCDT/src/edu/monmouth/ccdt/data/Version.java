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
		
		StringBuilder commentBuilder = new StringBuilder();
		
		int linesAdded = 0;
		int linesChanged = 0;
		int linesDeleted = 0;
		int linesUnchanged = 0;
		
		//update just incase changes are not saved
		for(Change change : getChanges()){
			linesAdded      += change.getLineAmountAdded();
			linesChanged    += change.getLineAmountChanged();
			linesDeleted    += change.getLineAmountDeleted();
			linesUnchanged  += change.getLineAmountNoChange();
		}
		
		
		commentBuilder.append("//").append(getName());
	
		if(versionComment == null){
			 getChangeComment();
		}
		
		versionComment.linesAdded = linesAdded;
		versionComment.linesChanged = linesChanged;
		versionComment.linesDeleted = linesDeleted;
		versionComment.linesUnchanged = linesUnchanged;
		
		if(versionComment.date != null){
			commentBuilder.append("\n//date uploaded: ").append(versionComment.date);
		}
		
		if(versionComment.name != null && !versionComment.name.equals("") ){
			commentBuilder.append("\n//developer name: ").append(versionComment.name);
		}
		
		if(versionComment.description != null && !versionComment.description.equals("")){
			commentBuilder.append("\n//reason: ").append(versionComment.description);
		}
		
		commentBuilder.append("\n//--------------Version line Counts--------------");
		commentBuilder.append("\n// ADDED     - ").append(versionComment.linesAdded);
		commentBuilder.append("\n// DELETED   - ").append(versionComment.linesDeleted);
		commentBuilder.append("\n// CHANGED   - ").append(versionComment.linesChanged);
		commentBuilder.append("\n// NO CHANGE - ").append(versionComment.linesUnchanged);
		commentBuilder.append("\n//-----------------------------------------------");
		
		return commentBuilder.toString();
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
