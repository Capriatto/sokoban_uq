
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author FabianGM
 */
public class Board implements KeyListener {

    private JButton[][] botones;
    private char[][] imagenes;
    private ImageIcon avatarIcon, cajaIcon, caminoIcon, muroIcon, llegadaIcon, estrellaIcon, llegadaAvatarIcon;
    private int a, b, x, y;

    public Board() {
        this.botones = new JButton[20][20];
        this.a = 0;
        this.b = 0;
        this.imagenes=new char[20][20];
        avatarIcon = new ImageIcon(getClass().getResource("/recursos/avatarIcon.png"));
        muroIcon = new ImageIcon(getClass().getResource("/recursos/muroIcon.png"));
        cajaIcon = new ImageIcon(getClass().getResource("/recursos/cajaIcon.png"));
        llegadaIcon = new ImageIcon(getClass().getResource("/recursos/llegadaIcon.png"));
        estrellaIcon = new ImageIcon(getClass().getResource("/recursos/estrellaIcon.png"));
        llegadaAvatarIcon = new ImageIcon(getClass().getResource("/recursos/llegadaAvatarIcon.png"));
        caminoIcon = new ImageIcon(getClass().getResource("/recursos/caminoIcon.png"));
    }

        // se parametrizan las imágenes


    public void matrizDeBotonesBloqueado(JPanel panel) {
        for (int i = 0; i < 20; i++) {
            a = i * 30;
            for (int j = 0; j < 20; j++) {
                b = j * 30;
                botones[i][j] = new JButton();
                botones[i][j].setBounds(a, b, 30, 30);
                botones[i][j].setEnabled(true);
                botones[i][j].addKeyListener(this);
                panel.add(botones[i][j]);
            }
            
        }
        leerArchivo(0);
cambiarIconos();
    }

    public void leerArchivo(int nivel) {
        String levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "src/niveles" + java.io.File.separator;
        String filename = levelDirectory + "nivel" + nivel + ".txt";

        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file \"" + filename + "\".");
            return;
        }

        try {
            for (int i = 0; i <20; i++) {
                
                for (int j = 0; j <20; j++) {
                    char letra=(char) in.read();
                    imagenes[j][i]=letra;
                }
                in.readLine();
            }

        } catch (IOException e) {
            System.out.println("File improperly formatted, quitting");
            return;
        }
        return;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        posicionAvatar();
       
        if (e.VK_W == e.getKeyCode() && y >= 1 && botones[x][y - 1].getIcon() != muroIcon) {
            if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() != muroIcon && botones[x][y - 2].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon && botones[x][y - 2].getIcon() != cajaIcon && botones[x][y - 2].getIcon() != estrellaIcon) {
                botones[x][y - 1].setIcon(caminoIcon);
                botones[x][y - 2].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            } else if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() == llegadaIcon) {
                botones[x][y - 1].setIcon(caminoIcon);
                botones[x][y - 2].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(avatarIcon);
                validarSiGano();
            } else if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() != muroIcon && botones[x][y - 2].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y - 2].getIcon() != cajaIcon && botones[x][y - 2].getIcon() != estrellaIcon) {
                botones[x][y - 1].setIcon(caminoIcon);
                botones[x][y - 2].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            } else if (botones[x][y - 1].getIcon() == cajaIcon) {

            } else if (botones[x][y - 1].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(llegadaAvatarIcon);
            } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y - 1].getIcon() != cajaIcon && botones[x][y - 1].getIcon() != muroIcon && botones[x][y - 1].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(llegadaIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            } else if(botones[x][y - 1].getIcon() == estrellaIcon && y>=2&& botones[x][y-2].getIcon() != cajaIcon && botones[x][y - 2].getIcon() != muroIcon  &&botones[x][y-2].getIcon() !=estrellaIcon){
                botones[x][y - 1].setIcon(llegadaAvatarIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y-2].setIcon(cajaIcon);
            }else if (botones[x][y - 1].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            }
        } else if (e.VK_S == e.getKeyCode() && y < 19 && botones[x][y + 1].getIcon() != muroIcon) {
            if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() != muroIcon && botones[x][y + 2].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon && botones[x][y + 2].getIcon() != cajaIcon && botones[x][y + 2].getIcon() != estrellaIcon) {
                botones[x][y + 1].setIcon(caminoIcon);
                botones[x][y + 2].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            } else if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() == llegadaIcon) {
                botones[x][y + 1].setIcon(caminoIcon);
                botones[x][y + 2].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(avatarIcon);
                validarSiGano();
            } else if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() != muroIcon && botones[x][y + 2].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y + 2].getIcon() != cajaIcon && botones[x][y + 2].getIcon() != estrellaIcon) {
                botones[x][y + 1].setIcon(caminoIcon);
                botones[x][y + 2].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            } else if (botones[x][y + 1].getIcon() == cajaIcon) {

            } else if (botones[x][y + 1].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(llegadaAvatarIcon);
            } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y + 1].getIcon() != cajaIcon && botones[x][y + 1].getIcon() != muroIcon && botones[x][y + 1].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(llegadaIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            }  else if(botones[x][y + 1].getIcon() == estrellaIcon && y<=17&& botones[x][y+2].getIcon() != cajaIcon && botones[x][y + 2].getIcon() != muroIcon  &&botones[x][y+2].getIcon() !=estrellaIcon){
                botones[x][y + 1].setIcon(llegadaAvatarIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y+2].setIcon(cajaIcon);
            }else if (botones[x][y + 1].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            }
        } else if (KeyEvent.VK_A == e.getKeyCode() && x >= 1 && botones[x - 1][y].getIcon() != muroIcon) {
            if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() != muroIcon && botones[x - 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon&& botones[x-2][y].getIcon() != cajaIcon && botones[x-2][y].getIcon() != estrellaIcon) {
                botones[x - 1][y].setIcon(caminoIcon);
                botones[x - 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            } else if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() == llegadaIcon) {
                botones[x - 1][y].setIcon(caminoIcon);
                botones[x - 2][y].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(avatarIcon);
                validarSiGano();
            } else if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() != muroIcon && botones[x - 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon&& botones[x-2][y].getIcon() != cajaIcon && botones[x-2][y].getIcon() != estrellaIcon) {
                botones[x - 1][y].setIcon(caminoIcon);
                botones[x - 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            } else if (botones[x - 1][y].getIcon() == cajaIcon) {

            } else if (botones[x - 1][y].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(llegadaAvatarIcon);
            } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x - 1][y].getIcon() != cajaIcon && botones[x - 1][y].getIcon() != muroIcon && botones[x - 1][y].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(llegadaIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            }  else if(botones[x-1][y].getIcon() == estrellaIcon && x>=2&& botones[x-2][y].getIcon() != cajaIcon && botones[x-2][y].getIcon() != muroIcon  &&botones[x-2][y].getIcon() !=estrellaIcon){
                botones[x-1][y].setIcon(llegadaAvatarIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x-2][y].setIcon(cajaIcon);
            }else if (botones[x - 1][y].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            }
        } else if (e.VK_D == e.getKeyCode() && x < 19 && botones[x + 1][y].getIcon() != muroIcon) {
            if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() != muroIcon && botones[x + 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon&& botones[x+2][y].getIcon() != cajaIcon && botones[x+2][y].getIcon() != estrellaIcon) {
                botones[x + 1][y].setIcon(caminoIcon);
                botones[x + 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            } else if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() == llegadaIcon) {
                botones[x + 1][y].setIcon(caminoIcon);
                botones[x + 2][y].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(avatarIcon);
                validarSiGano();
            } else if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() != muroIcon && botones[x + 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon&& botones[x+2][y].getIcon() != cajaIcon && botones[x+2][y].getIcon() != estrellaIcon) {
                botones[x + 1][y].setIcon(caminoIcon);
                botones[x + 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            } else if (botones[x + 1][y].getIcon() == cajaIcon) {

            } else if (botones[x + 1][y].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(llegadaAvatarIcon);
            } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x + 1][y].getIcon() != cajaIcon && botones[x + 1][y].getIcon() != muroIcon && botones[x + 1][y].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(llegadaIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            } else if(botones[x+1][y].getIcon() == estrellaIcon && x<=17&& botones[x+2][y].getIcon() != cajaIcon && botones[x+2][y].getIcon() != muroIcon  &&botones[x+2][y].getIcon() !=estrellaIcon){
                botones[x+1][y].setIcon(llegadaAvatarIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x+2][y].setIcon(cajaIcon);
                
            } else if (botones[x + 1][y].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void posicionAvatar() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (botones[i][j].getIcon() == avatarIcon || botones[i][j].getIcon() == llegadaAvatarIcon) {
                    x = i;
                    y = j;
                    return;
                }
            }
        }

    }
    public void cambiarIconos(){
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                if(imagenes[i][j]=='C'){
                    botones[i][j].setIcon(caminoIcon);
                }else if(imagenes[i][j]=='M'){
                    botones[i][j].setIcon(muroIcon);
                }else if(imagenes[i][j]=='B'){
                    botones[i][j].setIcon(cajaIcon);
                }else if(imagenes[i][j]=='L'){
                    botones[i][j].setIcon(llegadaIcon);
                }else if(imagenes[i][j]=='A'){
                    botones[i][j].setIcon(avatarIcon);
                }
            }
        }
    }

    public void validarSiGano() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (botones[i][j].getIcon() == llegadaIcon || botones[i][j].getIcon() == llegadaAvatarIcon) {
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Ganaste putooo!");
        cambiarIconos();
        return;
    }
}
