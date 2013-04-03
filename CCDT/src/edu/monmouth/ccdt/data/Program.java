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
		
		if (versions.size() > 0) {
			//create change
			for (File file : versionToAdd.getFiles()) {
				Change change = new Change(versions.get(versions.size() - 1),
						versionToAdd, file);
				versionToAdd.addChange(change);
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
