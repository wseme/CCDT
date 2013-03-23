package edu.monmouth.ccdt.data;

public class Change {

	private Version previousVersion;
	private Version currentVersion;
	private File file;
	
	public Change(Version previousVersion, Version currentVersion, File file){
		this.previousVersion = previousVersion;
		this.currentVersion = currentVersion;
		this.file = file;
	}
	
}
