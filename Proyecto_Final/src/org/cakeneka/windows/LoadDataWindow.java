package org.cakeneka.windows;

import org.cakeneka.utilities.SaveState;
import org.cakeneka.utilities.DuaPersistenceManager;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class LoadDataWindow extends javax.swing.JFrame{
    
    private MainWindow parent;
    List<SaveState> saveStates;
    DuaPersistenceManager database;
    
    /**
     * Creates new form SaveLoadWindow
     * @param parent 
     */
    public LoadDataWindow(MainWindow parent) {
        initComponents();
        this.parent = parent;
        this.setLocationRelativeTo(parent);
        saveStates = new ArrayList<>();
        database = new DuaPersistenceManager();
    }
    
    /**
     * Recoge la información de la tabla SaveStates de la base de datos y actualiza
     * la lista de guardados.
     */
    private void updateList(){
        String[][] savesTable;
        try {
            savesTable = database.getSaveStatesTable();
            saveStates.clear();
            for (int i = 0; i < savesTable.length; i++) {
                SaveState saveState = createSaveState(savesTable[i]);
                saveStates.add(saveState);
            }
            saveStates.sort(Comparator.reverseOrder());
            
            DefaultListModel<String> model = new DefaultListModel<>();
            for (SaveState saveState : saveStates) {
                model.addElement(saveState.toString());
            }
            savesJList.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error durante la conexión, volviendo a la ventana principal","Error",JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
    
    
    private SaveState createSaveState(String[] params) {
        DateTimeFormatter mySqlTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        
        int id = Integer.parseInt(params[0]);
        LocalDateTime timeCreated = LocalDateTime.parse(params[1], mySqlTimeFormat);
        String duaFields = params[2];
        List<String> fields = Arrays.asList(duaFields.split("\\\\"));
        
        return new SaveState(timeCreated, id, fields);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        loadBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        deleteBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        savesJList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        loadBtn.setText("Cargar");
        loadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cargar datos guardados");

        deleteBtn.setText("Eliminar");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(savesJList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(loadBtn)
                        .addGap(18, 18, 18)
                        .addComponent(deleteBtn)
                        .addGap(0, 104, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadBtn)
                    .addComponent(deleteBtn))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBtnActionPerformed
        SaveState selectedSave = getSelectedSaveState();
        if (selectedSave != null){
            parent.updateFields(selectedSave.getFields()); // Actualiza los campos de la ventana principal a los campos del guardado seleccionado
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Antes debes seleccionar un guardado","Atención",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_loadBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        SaveState selectedSave = getSelectedSaveState();
        try {
            if (selectedSave != null){
                database.deleteSaveStateById(selectedSave.getId());
                updateList();
            } else {
                JOptionPane.showMessageDialog(this, "Antes debes seleccionar un guardado","Atención",JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Se ha producido un error al eliminar el guardado","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteBtnActionPerformed
    
    private SaveState getSelectedSaveState(){
        int selectedIndex = savesJList.getSelectedIndex();
        if (selectedIndex < 0) return null;
        return saveStates.get(selectedIndex);
    }
    
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        updateList();
    }//GEN-LAST:event_formWindowActivated

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loadBtn;
    private javax.swing.JList<String> savesJList;
    // End of variables declaration//GEN-END:variables
}
