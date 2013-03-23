package edu.monmouth.ccdt.data;

public class Line {
	
	private ChangeType type = ChangeType.NO_CHANGE;
	private int lineNumber;
	private String line;
	
	public Line(String line, int lineNumber){
		
		if(line == null){
			System.err.println("Inputted line can not be null");
			return;
		}
		
		if(lineNumber <=0){
			System.err.println("Inputted line number needs to be greater than 0.");
			return;			
		}
		
		this.line = line;
		this.lineNumber = lineNumber;
	}

	public ChangeType getType() {
		return type;
	}

	public void setType(ChangeType type) {
		this.type = type;
	}
	
	public int getLineNumber(){
		return this.lineNumber;
	}
	
	public String getLine(){
		return this.line;
	}
}
