/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author sokobanUQ
 */
public class GestionarTablero implements ActionListener {

    private final JButton[][] botones;
    private final char[][] imagenes;
    private final ImageIcon avatarIcon, cajaIcon, caminoIcon, muroIcon, llegadaIcon;
    private int a, b, x, y;
    private boolean avatar = false;

    /**
     * Metodo contructor de la clase GestionarTablero
     * @param imagenes 
     */
    public GestionarTablero(char[][] imagenes) {
        this.botones = new JButton[20][20];
        this.imagenes = imagenes;
        
        avatarIcon = new ImageIcon(getClass().getResource("/recursos/avatarIcon.png"));
        muroIcon = new ImageIcon(getClass().getResource("/recursos/muroIcon.png"));
        cajaIcon = new ImageIcon(getClass().getResource("/recursos/cajaIcon.png"));
        llegadaIcon = new ImageIcon(getClass().getResource("/recursos/llegadaIcon.png"));
        caminoIcon = new ImageIcon(getClass().getResource("/recursos/caminoIcon.png"));
        
    }

    /**
     * Metodo que genera el tablero donde vamos a crear los niveles del sokoban
     * @param panel 
     */
    public void matrizDeBotones(JPanel panel) {
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
        llenarBotonesDeImagenes(); 

    }

    /**
     * Metodo que nos va a permitir poner un elemento en el tablero de acuerdo el numero
     * de clicks sobre el boton
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (botones[i][j] == e.getSource()) {
                    if (botones[i][j].getIcon() == null) {
                        botones[i][j].setIcon(muroIcon);
                    } else if (botones[i][j].getIcon() == muroIcon) {
                        botones[i][j].setIcon(cajaIcon);
                    } else if (botones[i][j].getIcon() == cajaIcon) {
                        botones[i][j].setIcon(llegadaIcon);
                    } else if (botones[i][j].getIcon() == llegadaIcon && avatar == false) {
                        botones[i][j].setIcon(avatarIcon);
                        avatar = true;
                    } else if (botones[i][j].getIcon() == llegadaIcon && avatar == true) {
                        botones[i][j].setIcon(muroIcon);
                    } else if (botones[i][j].getIcon() == avatarIcon) {
                        botones[i][j].setIcon(muroIcon);
                        avatar = false;

                    }
                }
            }
        }
    }
    
/**
 * Este metodo nos va a retornar un array de caracteres de acuerdo a las imagenes encontradas
 * @return 
 */
    public char[][] llenarMatrizConImagenes() {
        
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (botones[i][j].getIcon() == null) {
                    imagenes[i][j] = 'C';
                } else if (botones[i][j].getIcon() == muroIcon) {
                    imagenes[i][j] = 'M';
                } else if (botones[i][j].getIcon() == cajaIcon) {
                    imagenes[i][j] = 'B';
                } else if (botones[i][j].getIcon() == llegadaIcon) {
                    imagenes[i][j] = 'L';

                } else if (botones[i][j].getIcon() == avatarIcon) {
                    imagenes[i][j] = 'A';

                }

            }
        }
        return imagenes;
    }

    /**
     * Metodo que nos va a retornar la matriz del tablero para ser guardada en el archivo 
     * @return 
     */
    public char[][] retonarMatrizJuegoActual() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {

                if (botones[i][j].getIcon() == caminoIcon) {
                    imagenes[i][j] = 'C';
                } else if (botones[i][j].getIcon() == muroIcon) {
                    imagenes[i][j] = 'M';
                } else if (botones[i][j].getIcon() == cajaIcon) {
                    imagenes[i][j] = 'B';
                } else if (botones[i][j].getIcon() == llegadaIcon) {
                    imagenes[i][j] = 'L';

                } else if (botones[i][j].getIcon() == avatarIcon) {
                    imagenes[i][j] = 'A';

                }

            }
        }
        return imagenes;
    }
/**
 * Metodo que recorre la matriz de imagenes y de acuerdo a cada caracter se le asigna
 * una imagen diferente 
 */
    public void llenarBotonesDeImagenes(){
 
        for(int i=0;i<20;i++){
          for(int j=0;j<20;j++){
            if(imagenes[i][j]=='M'){
                botones[i][j].setIcon(muroIcon);
            }else if(imagenes[i][j]=='L'){
                botones[i][j].setIcon(llegadaIcon);
            }else if(imagenes[i][j]=='B'){
                botones[i][j].setIcon(cajaIcon);
            }else if(imagenes[i][j]=='A'){
                botones[i][j].setIcon(avatarIcon);
            }
        }  
        
    }
}
    /**
     * Metodo que nos verifica si es posible crear o no el tablero de acuerdo a ciertas validaciones
     * @return 
     */
    public boolean verificarTablero() {
        llenarMatrizConImagenes();
        int numCajas = 0, numLLegadas = 0, numAvatar = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (imagenes[i][j] == 'B') {
                    numCajas++;
                } else if (imagenes[i][j] == 'L') {
                    numLLegadas++;

                } else if (imagenes[i][j] == 'A') {
                    numAvatar++;
                }
            }

        }
        if(numAvatar>1){
            JOptionPane.showMessageDialog(null, "No puede crear mas de un avatar por tablero");
            return false;
        }else if(numAvatar==0){
            JOptionPane.showMessageDialog(null, "Debe posicionar el avatar en el tablero");
            return false;
        }else if(numLLegadas>numCajas){
            JOptionPane.showMessageDialog(null, "No puede crear mas llegadas que monedas");
            return false;
        }else if(numLLegadas<numCajas){
            JOptionPane.showMessageDialog(null, "No puede crear mas de monedas que llegadas");
            return false;
        }else if(numCajas==0){
            JOptionPane.showMessageDialog(null, "Debe haber por lo menos una moneda en el mapa");
            return false;
        }
        
return true;
    
    }
}
