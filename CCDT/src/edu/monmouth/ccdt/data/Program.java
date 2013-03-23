package edu.monmouth.ccdt.data;

import java.util.ArrayList;


public class Program {
	private ArrayList<Version> versions;

	public Program(){
		versions = new ArrayList<Version>();
	}
	
	public void addVersion(Version version){
		versions.add(version);
	}
	
	public ArrayList<Version> getVersions(){
		return versions;
	}
}
