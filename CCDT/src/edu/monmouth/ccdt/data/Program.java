package edu.monmouth.ccdt.data;

import java.util.ArrayList;


public class Program {
	private ArrayList<Version> versions;

	public Program(){
		versions = new ArrayList<Version>();
	}
	
	public void addVersion(java.io.File fileFolder){
		int version = versions.size();
		if (version == 0) version = 1;
		
		versions.add(new Version(version, fileFolder));
	}
	
	public ArrayList<Version> getVersions(){
		return versions;
	}
}
