package edu.monmouth.ccdt.data;

import java.util.ArrayList;
import java.util.List;

import difflib.DiffRow;
import difflib.DiffRow.Tag;
import difflib.DiffRowGenerator;



public class Change {

	private Version previousVersion;
	private Version currentVersion;
	private File file;
	
	public Change(Version previousVersion, Version currentVersion, File file){
		this.previousVersion = previousVersion;
		this.currentVersion = currentVersion;
		this.file = file;//this is file from current version
	
		compare();
	}
	
	private File getPreviousFile(){
		ArrayList<File> files = this.previousVersion.getFiles();
		//TODO check in folder from root, atm only checks against names ( might run into problems if files contain same name in project)
//		String rootDir = this.previousVersion.getDirectory().getPath();
		
		for(File file: files){
			if(this.file.getFileName().equals(file.getFileName())){
				return file;
			}
		}
		return null;
	}
	
	private File getCurrentFile(){
		ArrayList<File> files = this.currentVersion.getFiles();
		for(File file: files){
			if(this.file.getFileName().equals(file.getFileName())){
				return file;
			}
		}
		return null;
	}
	
	public void compare(){
		File previousFile = getPreviousFile();
		File currentFile = getCurrentFile();
		
		//new file, had to also added in Program
		if(previousFile == null && currentFile != null){
			for(Line line: currentFile.getLines()){
				line.setType(ChangeType.ADDED);
			}
			return;
		}

		// current file is deleted if all lines are DELETED, remove file and do not perform change
		ArrayList<Line> lines = previousFile.getLines();
		boolean isDeletedFile = true;
		for (Line line : lines) {
			if (line.getType() != ChangeType.DELETED) {
				isDeletedFile = false;
				break;
			}
		}
		if (isDeletedFile) {
			// file is deleted in previous, delete current File
			if (currentFile != null){
				currentVersion.getFiles().remove(previousFile); //all deleted, remove file
				return;
			}
		}
		
		
		//deleted file
		if(currentFile == null || currentFile.getLines().size() <=0){
			
			//create new file, copy previous and mark all as removed.
			java.io.File temp = new java.io.File(previousFile.getFileName());
			File deletedFile = new File(temp, previousFile.getVersion());
			deletedFile.setLines(previousFile.getLines());
			
			for(Line line: deletedFile.getLines()){
				line.setType(ChangeType.DELETED);
			}
			currentFile = deletedFile;
			temp.delete();//remove created tmp file
			return;
		}
		
		
		diff();
		
	}
	
	private void diff(){;
	
		File previousFile = getPreviousFile();
		File currentFile = getCurrentFile();
	
		ArrayList<String> prevBuilder = new ArrayList<String>();
			for(Line prevLine: previousFile.getLines()){
				prevBuilder.add(prevLine.getLine());
			}
		
		
		ArrayList<String> currBuilder = new ArrayList<String>();
		ArrayList<Line> currentLines = currentFile.getLines();
		for(Line curLine: currentLines){
			currBuilder.add(curLine.getLine());
		}

		
		DiffRowGenerator.Builder diffBuilder = new DiffRowGenerator.Builder();
		DiffRowGenerator diffGen = diffBuilder.build();
		List<DiffRow> rows = diffGen.generateDiffRows(prevBuilder, currBuilder);

		int index = 0;
		for(DiffRow diffRow : rows){
			//XXX need to do this before getting the index, r else it will through array out of bounds
			if(diffRow.getTag() == Tag.DELETE){
				//Needs to be done because line doesn't exist in current file
				//create deleted line
				Line deletedLine = new Line(diffRow.getOldLine(), index+1);
				deletedLine.setType(ChangeType.DELETED);
				
				//add line to file
				currentFile.getLines().add(index, deletedLine);
				
			}else if( diffRow.getTag() == Tag.CHANGE && diffRow.getOldLine().length() == 0 && diffRow.getNewLine().length() > 0 ){
				//if old line is 0 and new line contains info, set it insert, not change
				diffRow.setTag(Tag.INSERT);//don't mark these change, mark insert
			}
			
			
			//insert at end
			if(index >= currentLines.size()){
				currentLines.add(new Line(diffRow.getNewLine(), index));
				continue;
			}
			Line line = currentLines.get(index);
			
			if(diffRow.getTag() == Tag.CHANGE){
				line.setType(ChangeType.CHANGED);
			}else if(diffRow.getTag() == Tag.INSERT){
				line.setType(ChangeType.ADDED);
			}
			index++;
		}
	}
	
	public File getFile(){
		return file;
	}
	
	public int getLineAmountAdded(){
		ArrayList<Line> lines = getCurrentFile().getLines();
		int lineCount = 0;
		
		for(Line line : lines){
			if (line.getType() == ChangeType.ADDED){
				lineCount++;
			}
		}
		return lineCount;
	}
	
	public int getLineAmountDeleted(){
		ArrayList<Line> lines = getCurrentFile().getLines();
		int lineCount = 0;
		
		for(Line line : lines){
			if (line.getType() == ChangeType.DELETED){
				lineCount++;
			}
		}
		return lineCount;
	}
	
	public int getLineAmountChanged(){
		ArrayList<Line> lines = getCurrentFile().getLines();
		int lineCount = 0;
		
		for(Line line : lines){
			if (line.getType() != ChangeType.NO_CHANGE){//add and deleted count as changed
				lineCount++;
			}
		}
		return lineCount;
	}
	
	public int getLineAmountNoChange(){
		ArrayList<Line> lines = getCurrentFile().getLines();
		int lineCount = 0;
		
		for(Line line : lines){
			if (line.getType() == ChangeType.NO_CHANGE){
				lineCount++;
			}
		}
		return lineCount;
	}
	
	@Override
	public String toString() {
		return "Change " + this.currentVersion.getChanges().indexOf(this);
	}
	
//	public static void main(String[] args){
//		
//		java.io.File testFolder = new java.io.File("//Users//wsloth514//Desktop//testFolder");
//		System.out.println(testFolder.getName());
//		Version version = new Version(1, testFolder);
////		for(File file: version.getFiles()){
////			System.out.println(file.getFileName());
////		}
//
//		java.io.File testFolder2 = new java.io.File("//Users//wsloth514//Desktop//testFolder2");
//		System.out.println(testFolder2.getName());
//		Version version2= new Version(2, testFolder2);
//		
//		File testFile = new File(new java.io.File("//Users//wsloth514//Desktop//testFolder2//testFile1.txt"), version2);
//		Change changeTest = new Change(version, version2, testFile);
//		
//		File v2File = changeTest.getCurrentFile();
//			for(Line line: v2File.getLines()){
//				System.out.println(line.getLineNumber() + ": " +line.getLine() +" - " + line.getType());
//			}
//	
//			
//			System.out.println("Lines added: " + changeTest.getLineAmountAdded());
//			System.out.println("Lines changed: " + changeTest.getLineAmountChanged());
//			System.out.println("Lines deleted: " + changeTest.getLineAmountDeleted());
//			System.out.println("Lines not changed: " + changeTest.getLineAmountNoChange());
//			
//	}
}
