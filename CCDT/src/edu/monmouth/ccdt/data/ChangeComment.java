package edu.monmouth.ccdt.data;

import org.joda.time.DateTime;

public class ChangeComment {
	public String name;
	public String description;
	public DateTime date;
	public int linesAdded;
	public int linesChanged;
	public int linesDeleted;
	public int unchangedLines;
	
	public ChangeComment(Version version) {
		//TODO make helper methods in version to iterate over changes and add them.
		this.name = "";
		this.description = "";
		this.date = new DateTime();
		
		this.linesAdded = 0;
		this.linesChanged = 0;
		this.linesDeleted = 0;
		this.unchangedLines = 0;
		
		for(Change change : version.getChanges()){
			this.linesAdded      += change.getLineAmountAdded();
			this.linesChanged    += change.getLineAmountChanged();
			this.linesDeleted    += change.getLineAmountDeleted();
			this.unchangedLines += change.getLineAmountNoChange();
		
		}
	}

}
