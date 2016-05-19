/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author FabianGM
 */
public class GestionarTablero implements ActionListener{
    private JButton[][] botones;
    private char[][] imagenes;
        private ImageIcon avatarIcon, cajaIcon, caminoIcon, muroIcon, llegadaIcon;
        private int a, b, x, y;
        private boolean avatar=false;

    public GestionarTablero() {
        this.botones=new JButton[20][20];
        this.imagenes=new char[20][20];
        avatarIcon = new ImageIcon(getClass().getResource("/recursos/avatarIcon.png"));
        muroIcon = new ImageIcon(getClass().getResource("/recursos/muroIcon.png"));
        cajaIcon = new ImageIcon(getClass().getResource("/recursos/cajaIcon.png"));
        llegadaIcon = new ImageIcon(getClass().getResource("/recursos/llegadaIcon.png"));
        caminoIcon = new ImageIcon(getClass().getResource("/recursos/caminoIcon.png"));
        
    }
        public void matrizDeBotonesBloqueado(JPanel panel) {
        for (int i = 0; i < 20; i++) {
            a = i * 30;
            for (int j = 0; j < 20; j++) {
                b = j * 30;
                botones[i][j] = new JButton();
                botones[i][j].setBounds(a, b, 30, 30);
                botones[i][j].setEnabled(true);
                botones[i][j].addActionListener(this);
                panel.add(botones[i][j]);
            }
            
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<20;i++){
        for(int j=0;j<20;j++){
            if(botones[i][j]==e.getSource()){
                if(botones[i][j].getIcon()==null){
                    botones[i][j].setIcon(muroIcon);
                }else if(botones[i][j].getIcon()==muroIcon){
                    botones[i][j].setIcon(cajaIcon);
                }else  if(botones[i][j].getIcon()==cajaIcon){
                    botones[i][j].setIcon(llegadaIcon);
                }else if(botones[i][j].getIcon()==llegadaIcon && avatar==false){
                    botones[i][j].setIcon(avatarIcon);
                    avatar=true;
                }else if(botones[i][j].getIcon()==llegadaIcon && avatar==true){
                    botones[i][j].setIcon(muroIcon);
                }else if(botones[i][j].getIcon()==avatarIcon){
                    botones[i][j].setIcon(muroIcon);
                    avatar=false;
                    
                }
            }
        }
        }
    }

    public char[][] llenarMatrizConImagenes() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {

                if (botones[i][j].getIcon() == null) {
                    imagenes[i][j]='C';
                } else if (botones[i][j].getIcon() == muroIcon) {
                    imagenes[i][j]='M';
                } else if (botones[i][j].getIcon() == cajaIcon) {
                    imagenes[i][j]='B';
                } else if (botones[i][j].getIcon() == llegadaIcon) {
                    imagenes[i][j]='L';
                
                } else if (botones[i][j].getIcon() == avatarIcon) {
                    imagenes[i][j]='A';

                }

            }
        }
        return imagenes;
    }
    
}
