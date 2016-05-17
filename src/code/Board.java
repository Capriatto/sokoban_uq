/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author FabianGM
 */
public class Board implements KeyListener{
    private JButton[][] botones;
    private int a,b;

    public Board() {
        this.botones = new JButton[20][20];
        this.a = 0;
        this.b = 0;
    }
    
    public void matrizDeBotonesBloqueado(JPanel panel) {
        for (int i = 0; i < 20; i++) {
            a = i * 30;
            for (int j = 0; j < 20; j++) {
                b = j * 30;
                botones[i][j] = new JButton();
                botones[i][j].setBounds(a, b, 30,30);
                botones[i][j].setEnabled(true);
                panel.add(botones[i][j]);
            }
            
        }
        botones[10][19].setText("x");

    }

    @Override
    public void keyTyped(KeyEvent e) {
        

  }

    @Override
    public void keyPressed(KeyEvent e) {
    
//        if(e.VK_W==e.getKeyCode()){
//            
//        }
   }

    @Override
    public void keyReleased(KeyEvent e) {
   }


        
        

}
