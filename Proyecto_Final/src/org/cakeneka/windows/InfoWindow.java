package org.cakeneka.windows;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.Timer;
import javax.swing.UIManager;
import org.cakeneka.login.Login;

public class InfoWindow extends javax.swing.JFrame  {
    
    private MainWindow parent;
    
    private Timer timer;
    
    private int progressBarValue;
    private double increment = 1;
    private double curValue = 0;
    
    private String text1 = "> Desarrollado por:";
    private String text2 = "> Martina López Quijada";
    private String text3 = "> José Leonardo Ortega Pinto";
    
    private Queue<Character> textQueue1;
    private Queue<Character> textQueue2;
    private Queue<Character> textQueue3;
    /**
     * Creates new form InfoWindow
     */
    public InfoWindow(MainWindow parent) {
        initComponents();
        progressBar.setMinimum(-1000);
        timer = new Timer(1000 / 60, (ActionEvent ae) -> {
            update();
        });
        clearLabels();
        this.parent = parent;
        setLocationRelativeTo(parent);
    }
    
    private void update() {
        progressBarValue = (int) Math.round(Math.sin(curValue*(Math.PI / 180)) * 1000) ;
        progressBar.setValue(progressBarValue);
        progressBar1.setValue(progressBarValue * -1);
        curValue += increment;
        
        if (curValue % 2 == 0){
            if (!textQueue1.isEmpty()){
                textLabel1.setText(textLabel1.getText() + textQueue1.poll());
            } 
            if (!textQueue2.isEmpty()){
                textLabel2.setText(textLabel2.getText() + textQueue2.poll());
            }
            if (!textQueue3.isEmpty()){
                textLabel3.setText(textLabel3.getText() + textQueue3.poll());
            }
        }
        repaint();
    }
    
    public static Queue<Character> stringToCharacterQueue(String text) {
        Queue<Character> charQueue = new LinkedList<>();
        
        for (int i = 0; i < text.length(); i++) {
            charQueue.offer(text.charAt(i));
        }
        
        return charQueue;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jSeparator1 = new javax.swing.JSeparator();
        progressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        textLabel1 = new javax.swing.JLabel();
        textLabel2 = new javax.swing.JLabel();
        textLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PROYECTO D.U.A.");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("D o c u m e n t o  U l t r a  A b u r r i d o");

        progressBar.setForeground(new java.awt.Color(204, 255, 255));
        progressBar.setMaximum(1000);
        progressBar.setMinimum(-1000);
        progressBar.setToolTipText("Documento Ultra Aburrido");

        progressBar1.setForeground(new java.awt.Color(255, 204, 255));
        progressBar1.setMaximum(1000);
        progressBar1.setMinimum(-1000);
        progressBar1.setToolTipText("Documento Ultra Aburrido");

        jPanel1.setBackground(new java.awt.Color(11, 11, 11));
        jPanel1.setForeground(new java.awt.Color(241, 241, 241));

        textLabel1.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textLabel1.setForeground(new java.awt.Color(238, 238, 238));
        textLabel1.setText(">");

        textLabel2.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textLabel2.setForeground(new java.awt.Color(238, 238, 238));
        textLabel2.setText(">");

        textLabel3.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textLabel3.setForeground(new java.awt.Color(238, 238, 238));
        textLabel3.setText(">");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textLabel1)
                    .addComponent(textLabel2)
                    .addComponent(textLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Reiniciar");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 212, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(212, 212, 212)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        timer.stop();
    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        clearLabels();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        clearLabels();
        timer.start();
    }//GEN-LAST:event_formWindowActivated
    
    private void clearLabels(){
        textLabel1.setText("");
        textLabel2.setText("");
        textLabel3.setText("");
        
        textQueue1 = stringToCharacterQueue(text1);
        textQueue2 = stringToCharacterQueue(text2);
        textQueue3 = stringToCharacterQueue(text3);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JProgressBar progressBar1;
    private javax.swing.JLabel textLabel1;
    private javax.swing.JLabel textLabel2;
    private javax.swing.JLabel textLabel3;
    // End of variables declaration//GEN-END:variables
}
