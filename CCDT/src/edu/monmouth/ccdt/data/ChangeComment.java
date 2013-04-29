package edu.monmouth.ccdt.data;

import org.joda.time.DateTime;

public class ChangeComment {
	public String name;
	public String description;
	public DateTime date;
	public int linesAdded;
	public int linesChanged;
	public int linesDeleted;
	public int totalLOC;
	
	public ChangeComment(Version version) {
		//TODO make helper methods in version to iterate over changes and add them.
		this.name = "";
		this.description = "";
		this.date = new DateTime();
		this.linesAdded = -1;
		this.linesChanged = -1;
		this.linesDeleted = -1;
		this.totalLOC = -3;
	}

}
