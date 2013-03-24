package edu.monmouth.ccdt.controller;

import java.io.File;

import edu.monmouth.ccdt.data.Program;
import edu.monmouth.ccdt.view.GUI;

public class Controller {
	Program program;
	GUI gui;
	
	public Controller() {
		this.program = new Program();
		
		gui = new GUI(this);
		
		gui.setVisible(true);
		
		gui.loadProgram(program);
	}
	
	public void addVersion(File fileFolder){
		program.addVersion(fileFolder);
		gui.loadProgram(program);
	}
}
