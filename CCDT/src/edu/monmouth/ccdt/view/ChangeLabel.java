package edu.monmouth.ccdt.view;

import javax.swing.JDialog;

import org.joda.time.DateTime;

import com.toedter.calendar.JDateChooser;

import edu.monmouth.ccdt.data.ChangeComment;

public class ChangeLabel extends javax.swing.JPanel {
	private ChangeComment changeComment;
    /**
     * Creates new form ChangeLabel
     */
	public ChangeLabel(ChangeComment changeComment) {
        initComponents();
        this.changeComment = changeComment;
        calendar.setDate(this.changeComment.date.toDate());
        name.setText(this.changeComment.name);
        description.setText(this.changeComment.description);
        addedLines.setText("" + this.changeComment.linesAdded);
        changedLines.setText("" + this.changeComment.linesChanged);
        deletedLines.setText("" + this.changeComment.linesDeleted);
        unchanged.setText("" + this.changeComment.unchangedLines);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblCalendar = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        lblAddedLines = new javax.swing.JLabel();
        lblChangedLines = new javax.swing.JLabel();
        lblunchangedLines = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        name = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        addedLines = new javax.swing.JLabel();
        changedLines = new javax.swing.JLabel();
        unchanged = new javax.swing.JLabel();
        calendar = new JDateChooser();
        lblDeletedLines = new javax.swing.JLabel();
        deletedLines = new javax.swing.JLabel();

        lblCalendar.setText("Date Changed:");

        lblName.setText("Changed By:");

        lblDescription.setText("Description:");

        lblAddedLines.setText("Added Lines:");

        lblChangedLines.setText("Changed Lines:");

        lblDeletedLines.setText("Deleted Lines:");
        
        lblunchangedLines.setText("Unchanged Lines:");
        
        
        btnSave.setLabel("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setLabel("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        description.setColumns(20);
        description.setRows(5);
        jScrollPane1.setViewportView(description);

        
        
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(60, 60, 60)
                .add(btnSave)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btnCancel)
                .add(60, 60, 60))
            .add(layout.createSequentialGroup()
                .add(5, 5, 5)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(lblDeletedLines)
                    .add(lblunchangedLines)
                    .add(lblChangedLines)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(lblCalendar)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, lblName)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, lblDescription)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, lblAddedLines)))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(jScrollPane1)
                        .add(name)
                        .add(addedLines)
                        .add(changedLines)
                        .add(unchanged)
                        .add(calendar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(deletedLines))
                .add(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(5, 5, 5)
                        .add(lblCalendar))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(calendar)))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblName)
                    .add(name, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblDescription)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblAddedLines)
                    .add(addedLines))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblChangedLines)
                    .add(changedLines))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblDeletedLines)
                    .add(deletedLines))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblunchangedLines)
                    .add(unchanged))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnSave)
                    .add(btnCancel))
                .add(5, 5, 5))
        );
    }// </editor-fold>                        

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {                                         
    	((JDialog)this.getParent().getParent().getParent()).dispose();
    	this.changeComment.date = new DateTime(this.calendar.getDate());
    	this.changeComment.name = name.getText();
    	this.changeComment.description = description.getText();
    	
    }                                        

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {                                         
    	((JDialog)this.getParent().getParent().getParent()).dispose();
    }                                        

    // Variables declaration - do not modify                     
    private javax.swing.JLabel addedLines;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private JDateChooser calendar;
    private javax.swing.JLabel changedLines;
    private javax.swing.JLabel deletedLines;
    private javax.swing.JTextArea description;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddedLines;
    private javax.swing.JLabel lblCalendar;
    private javax.swing.JLabel lblChangedLines;
    private javax.swing.JLabel lblDeletedLines;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblunchangedLines;
    private javax.swing.JTextField name;
    private javax.swing.JLabel unchanged;
    // End of variables declaration                   
}
