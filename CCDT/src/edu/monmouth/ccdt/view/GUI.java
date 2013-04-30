package edu.monmouth.ccdt.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.monmouth.ccdt.controller.Controller;
import edu.monmouth.ccdt.data.Change;
import edu.monmouth.ccdt.data.ChangeType;
import edu.monmouth.ccdt.data.File;
import edu.monmouth.ccdt.data.Line;
import edu.monmouth.ccdt.data.Program;
import edu.monmouth.ccdt.data.Version;

public class GUI extends javax.swing.JFrame implements View {

	private static final long serialVersionUID = -7471359688824283973L;
	private Program program;
	private Controller controller;
	private JPopupMenu popupMenu;
	private JMenuItem jMenuItem3;
	
	@SuppressWarnings("unused")
	private GUI() {
		
	}
	
	public GUI(Controller controller) {
		initComponents();
		
		this.controller = controller;
		
		createAndSetTreeSelectionListeners();
		
		initializeHTMLTextPanes();
	}

	private void initializeHTMLTextPanes() {
		textPaneCurrentVersion.setContentType("text/html");
		textPanePreviousVersion.setContentType("text/html");
		this.textPaneCurrentVersion.setEditable(false);
		this.textPanePreviousVersion.setEditable(false);
		
		StyleSheet ss = ((HTMLEditorKit)textPaneCurrentVersion.getEditorKit()).getStyleSheet();
		ss.addRule(".added {color:green}");
		ss.addRule(".deleted {color:red}");
		ss.addRule(".changed {color:blue}");
		HTMLEditorKit kit = (HTMLEditorKit) textPaneCurrentVersion.getEditorKit();
		kit.setStyleSheet(ss);

		textPaneCurrentVersion.setEditorKit(kit);
	}

	private void createAndSetTreeSelectionListeners() {
		treeVersions.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeFiles.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		treeVersions.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				Object chosenObject = treeVersions.getLastSelectedPathComponent();
				if (chosenObject instanceof Version){
					treeFiles.setModel(new VersionTreeModel((Version)chosenObject));
					textPaneCurrentVersion.setText(null);
					textPanePreviousVersion.setText(null);
				}
				else{
					//TODO Display the change.
				}
			}
		});
		
		popupMenu = new JPopupMenu();
		JMenuItem comment = new JMenuItem("Comment");
		comment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				JDialog commentDialog = new JDialog(GUI.this);
				
				ChangeLabel changeLabel = new ChangeLabel(((Version)treeVersions.getLastSelectedPathComponent()).getChangeComment());
				commentDialog.setContentPane(changeLabel);
				commentDialog.pack();
				
				commentDialog.setLocationRelativeTo(GUI.this);
				commentDialog.setResizable(false);
				
				commentDialog.setVisible(true);
			}
		});
		popupMenu.add(comment);
		
		treeVersions.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isRightMouseButton(e)){
					int row = treeVersions.getClosestRowForLocation(e.getX(), e.getY());
			        treeVersions.setSelectionRow(row);
			        if (treeVersions.getLastSelectedPathComponent() instanceof Version){
				        popupMenu.show(e.getComponent(), e.getX(), e.getY());			        	
			        }

				}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		
		treeFiles.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				GUI.VersionTreeModel.TreeNode treeNode = (GUI.VersionTreeModel.TreeNode)treeFiles.getLastSelectedPathComponent();
				
				if (treeNode != null){
					int previousVersionIndex = treeNode.file.getVersion().getNumber()-2;//GUI.this.treeFiles.getSelectedIndex() - 1;
					if (previousVersionIndex >= 0){
						textPanePreviousVersion.setText(loadFileIntoHTML(treeNode.getFile().getSameFileFromVersion(GUI.this.program.getVersions().get(previousVersionIndex))));
					}else{
						textPanePreviousVersion.setText("");
					}
					
					textPaneCurrentVersion.setText(loadFileIntoHTML(treeNode.getFile()));
				}
			}
		});
		
		
	}
	
	@Override
	public void loadProgram(Program program) {
		this.program = program;
		textPaneCurrentVersion.setText(null);
		textPanePreviousVersion.setText(null);
		treeVersions.setModel(null);
		treeFiles.setModel(null);
		if (this.program.getVersions().size() > 0){
			treeVersions.setModel(new VersionListModel());
			jMenuItem3.setEnabled(true);
		}
		
	}

	private void exit(){
		this.dispose();
	}

	private void openVersion(){
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.controller.addVersion(chooser.getSelectedFile());
		}
	}
	
	
	private String loadFileIntoHTML(File file){
		if (file == null){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body><table>");
		int lineNum = 1;
		for (Line line : file.getLines()){
			String classString = "";
			if (line.getType() == ChangeType.ADDED){
				classString = "added";
			}else if (line.getType() == ChangeType.DELETED){
				classString = "deleted";
			}else if (line.getType() == ChangeType.CHANGED){
				classString = "changed";
			}
			
			sb.append("<tr><td><span>" + lineNum + "</span></td><td class=\"" + classString + "\">").append(line.getLine()).append("</td></tr>");
			lineNum++;
		}
		sb.append("</table></body></html>");
		

		return sb.toString();
	}
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")                   
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		textPanePreviousVersion = new javax.swing.JTextPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		textPaneCurrentVersion = new javax.swing.JTextPane();
		jScrollPane3 = new javax.swing.JScrollPane();
		treeFiles = new javax.swing.JTree();
		jScrollPane4 = new javax.swing.JScrollPane();
		treeVersions = new javax.swing.JTree();
		labelCurrentVersion = new javax.swing.JLabel();
		labelPreviousVersion1 = new javax.swing.JLabel();
		jMenuBar1 = new javax.swing.JMenuBar();
		menuFile = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem3 = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Change Counter Development Tool");
		setResizable(false);

		jScrollPane1.setViewportView(textPanePreviousVersion);

		jScrollPane2.setViewportView(textPaneCurrentVersion);

		jScrollPane3.setViewportView(treeFiles);

		jScrollPane4.setViewportView(treeVersions);

		labelCurrentVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelCurrentVersion.setText("Current Version");

		labelPreviousVersion1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelPreviousVersion1.setText("Previous Version");

		menuFile.setText("File");

		jMenuItem1.setText("Open Version Folder");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openVersion();
			}
		});
		menuFile.add(jMenuItem1);
		
		
		jMenuItem3.setText("Save With Comments");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openSaveWithComments();
			}
		});
		jMenuItem3.setEnabled(false);
		menuFile.add(jMenuItem3);
		
		jMenuItem2.setText("Exit");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exit();
			}
		});
		menuFile.add(jMenuItem2);

		jMenuBar1.add(menuFile);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
								.addComponent(jScrollPane4))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addComponent(labelPreviousVersion1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(labelCurrentVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(labelCurrentVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(labelPreviousVersion1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(layout.createSequentialGroup()
																.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
																.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		pack();
	}
	
	private void openSaveWithComments() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.controller.saveFileWithComments(chooser.getSelectedFile());
		}
		
	}
                   
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JLabel labelCurrentVersion;
	private javax.swing.JLabel labelPreviousVersion1;
	private JTree treeVersions;
	private javax.swing.JMenu menuFile;
	private javax.swing.JTextPane textPaneCurrentVersion;
	private javax.swing.JTextPane textPanePreviousVersion;
	private javax.swing.JTree treeFiles;    

	private class VersionListModel implements TreeModel{
		private Vector<TreeModelListener> listeners = new Vector<TreeModelListener>();
		
		@Override
		public void addTreeModelListener(TreeModelListener listener) {
			listeners.add(listener);
		}

		@Override
		public Object getChild(Object arg0, int arg1) {
			if (arg0 instanceof Version){
				return ((Version)arg0).getChanges().get(arg1);
			}
			else{
				return GUI.this.program.getVersions().get(arg1);
			}
		}

		@Override
		public int getChildCount(Object arg0) {
			if (arg0 instanceof Version)
				return ((Version)arg0).getChanges().size();
			else
				return GUI.this.program.getVersions().size();
		}

		@Override
		public int getIndexOfChild(Object arg0, Object arg1) {
			if (arg0 instanceof Version)
				return ((Version)arg0).getChanges().indexOf(arg1);
			else
				return GUI.this.program.getVersions().indexOf(arg1);
		}

		@Override
		public Object getRoot() {
			return "Versions";
		}

		@Override
		public boolean isLeaf(Object arg0) {
			return arg0 instanceof Version;
		}

		@Override
		public void removeTreeModelListener(TreeModelListener arg0) {
			listeners.remove(arg0);
			
		}

		@Override
		public void valueForPathChanged(TreePath arg0, Object arg1) {
			// TODO Auto-generated method stub
			
		}

	}
	private class VersionTreeModel implements TreeModel{

		private TreeNode root;
		private Vector<TreeModelListener> listeners = new Vector<TreeModelListener>();

		public VersionTreeModel(Version version) {
			root = new TreeNode(null, "/");
			
			int parentDirectoryCount = version.getDirectory().getPath().split("[\\\\/]").length;

			for (int fileI = 0; fileI < version.getFiles().size(); fileI++){
				File file = version.getFiles().get(fileI);
				String[] filePathArray = file.getFilePath().split("[\\\\/]");
				
				TreeNode currentDirectory = root;
				
	 FilePath: for (int i = parentDirectoryCount; i < filePathArray.length; i++){
		 			String filePathPart = filePathArray[i];
					
					for (TreeNode treeNode : currentDirectory.children){
						if (treeNode.toString().equals(filePathPart)){
							currentDirectory = treeNode;
							break FilePath;
						}
					}
	
					TreeNode treeNode;
					if (i == filePathArray.length-1)	
						treeNode = new TreeNode(file, filePathPart);
					else
						treeNode = new TreeNode(null, filePathPart);
					
					currentDirectory.children.add(treeNode);
					currentDirectory = treeNode;
				}
			}
		}

		public Object getRoot() {
			return root;
		}

		public Object getChild(Object parent, int index) {
			TreeNode treeNode = (TreeNode) parent;
			return treeNode.children.get(index);
		}

		public int getChildCount(Object parent) {
			TreeNode treeNode = (TreeNode) parent;
			return treeNode.children.size();
		}

		public boolean isLeaf(Object node) {
			TreeNode treeNode = (TreeNode) node;
			return treeNode.children.size() == 0;
		}

		public int getIndexOfChild(Object parent, Object child) {
			TreeNode parentNode = (TreeNode) parent;
			TreeNode childNode = (TreeNode) child;
			return parentNode.children.indexOf(childNode);
		}

		public void valueForPathChanged(TreePath path, Object value) {
		}


		public void addTreeModelListener(TreeModelListener listener) {
			listeners.add(listener);
		}

		public void removeTreeModelListener(TreeModelListener listener) {
			listeners.remove(listener);
		}

		public class TreeNode{
			ArrayList<TreeNode> children = new ArrayList<TreeNode>();
			File file;
			String title;
			public TreeNode(File file, String title) {
				this.file = file;
				this.title = title;
			}
			
			@Override
			public String toString() {
				if (title != null)
					return title;
				else{
					return file.getFileName();
				}
			}
			
			public File getFile(){
				return file;
			}
		}
	}
}