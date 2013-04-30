package edu.monmouth.ccdt.data;

import org.joda.time.DateTime;

public class ChangeComment {
	public String name;
	public String description;
	public DateTime date;
	public int linesAdded = 0;
	public int linesChanged = 0;
	public int linesDeleted = 0;
	public int linesUnchanged = 0;
	
	public ChangeComment(Version version) {
		//TODO make helper methods in version to iterate over changes and add them.
		this.name = "";
		this.description = "";
		this.date = new DateTime();
		
		for(Change change : version.getChanges()){
			this.linesAdded      += change.getLineAmountAdded();
			this.linesChanged    += change.getLineAmountChanged();
			this.linesDeleted    += change.getLineAmountDeleted();
			this.linesUnchanged  += change.getLineAmountNoChange();
		
		}
	}

}
