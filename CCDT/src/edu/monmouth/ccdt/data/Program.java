package edu.monmouth.ccdt.data;

import java.util.ArrayList;


public class Program {
	private ArrayList<Version> versions;

	public Program(){
		versions = new ArrayList<Version>();
	}
	
	public void addVersion(java.io.File fileFolder){
		int version = versions.size() + 1;
		
		Version versionToAdd = new Version(version, fileFolder);
		//TODO check for deleted files in a version here, (see teambox)
		
		if (versions.size() > 0) {
			Version previousVersion = versions.get(versions.size() - 1);
			
			ArrayList<File> updatedFiles = versionToAdd.getFiles();
			ArrayList<File> previousFiles = previousVersion.getFiles();
			
			//Check for previous deleted file, if found, remove it from current version
			for(File oldFile: previousFiles){
//				String fileName = oldFile.getFileName();
				boolean isDeletedFile = true;
				
				for(Line line:oldFile.getLines()){
					if(line.getType() !=ChangeType.DELETED){
						isDeletedFile = false;
					}
				}
				
				if(isDeletedFile){
					versionToAdd.removeFile(oldFile);
				}
					
			}
			

			// create change for new version added
			for (File file : updatedFiles) {
				
				//if file is marked as ALL DELETED, do not create change because file does not exist
//				boolean isFileDeleted = true;
//				for(Line line:file.getLines()){
//					if(line.getType() !=ChangeType.DELETED){
//						isFileDeleted = false;
//					}
//					
//				}
				
//				if(!isFileDeleted){
					Change change = new Change(previousVersion, versionToAdd, file);
					versionToAdd.addChange(change);
//				}else{
//					//remove file from current version
//					updatedFiles.remove(file);
//				}
				
			}

			for(File oldFile: previousFiles){
				String fileName = oldFile.getFileName();
				boolean isFound = false;
				
				for(File updatedFile: updatedFiles){
					String newFile = updatedFile.getFileName();
					
					if(fileName.equals(newFile)){
						
//						//check if file is already deleted, if not it, mark is found
//						boolean isDeleted = true;
//						for(Line checkLine:updatedFile.getLines()){
//							if(checkLine.getType() != ChangeType.DELETED){
//								isDeleted = false;
//								break;
//							}
//						}
//						
//						if(!isDeleted){
							isFound=true;//prevent code below from being executed
							break;
//						}
							
					}
				}
				
				//if updated file does NOT exist and previous EXISTS
				if(!isFound){
					File deletedFile = new File(new java.io.File(oldFile.getFilePath()), versionToAdd);
					
					//mark as deleted
					for(Line line: deletedFile.getLines()){
						line.setType(ChangeType.DELETED);
					}
					
					//add file to Version
					versionToAdd.addFile(deletedFile);
					
					//create change
					Change change = new Change(previousVersion, versionToAdd, deletedFile);
					versionToAdd.addChange(change);
				}
			}
			
			
		}else{
			//set all lines to added for initial version
			for(File file: versionToAdd.getFiles()){
				for(Line line: file.getLines()){
					line.setType(ChangeType.ADDED);
				}
			}
		}
		
		versions.add(versionToAdd);
		
		
	}
	
	public ArrayList<Version> getVersions(){
		return versions;
	}
	
	
	
//	public static void main(String[] args){
//		Program program = new Program();
//		program.addVersion( new java.io.File("//Users//wsloth514//Desktop//testFolder"));
//
//		program.addVersion( new java.io.File("//Users//wsloth514//Desktop//testFolder2"));
//		
//		for(Version version: program.getVersions()){
//			for(File file: version.getFiles()){
//				for(Line line: file.getLines()){
//				System.out.println(version.getName() + ":" +file.getFileName() + " - " + line.getLineNumber() + ") " + line.getType().name() + " > " + line.getLine() );
//				}
//			}
//		}
//	}
	
}
