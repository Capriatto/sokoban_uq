/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import code.Board;


/**
 *
 * @author FabianGM
 */
public class BoardFrame extends javax.swing.JFrame {
    private Board board;
    String nombreArchivo;
    /**
     * Creates new form BoardFrame
     */
<<<<<<< HEAD
    public BoardFrame(String nombreArchivo) {
=======
    public BoardFrame(String nombre,Login login) {
>>>>>>> df58bae117144a57c8dce6028d0442d8e2a2d77a
        initComponents();
        this.nombreArchivo=nombreArchivo;
        board = new Board(nombreArchivo);
        this.board.matrizDeBotonesBloqueado(Panel);
<<<<<<< HEAD
        
    }

    private BoardFrame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        board.leerArchivo(0);
        setLocationRelativeTo(this);
>>>>>>> df58bae117144a57c8dce6028d0442d8e2a2d77a
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        actionMenuItem2 = new org.jfree.ui.action.ActionMenuItem();
        actionMenuItem3 = new org.jfree.ui.action.ActionMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 626, Short.MAX_VALUE)
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 618, Short.MAX_VALUE)
        );

        jMenu1.setText("Opciones");

        actionMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        actionMenuItem2.setText("Deshacer paso");
        actionMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(actionMenuItem2);

        actionMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        actionMenuItem3.setText("Rehacer paso");
        actionMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(actionMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actionMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionMenuItem2ActionPerformed
        board.setAgregar(false);
        board.deshacerPaso();

    }//GEN-LAST:event_actionMenuItem2ActionPerformed

    private void actionMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionMenuItem3ActionPerformed
        board.setAgregar(true);
        board.rehacerPaso();
    }//GEN-LAST:event_actionMenuItem3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private org.jfree.ui.action.ActionMenuItem actionMenuItem2;
    private org.jfree.ui.action.ActionMenuItem actionMenuItem3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
