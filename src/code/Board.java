
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author FabianGM
 */
public class Board implements KeyListener {

    private JButton[][] botones;
    private ImageIcon avatarIcon, cajaIcon, caminoIcon, muroIcon, llegadaIcon, estrellaIcon, llegadaAvatarIcon;
    private int a, b, x, y;

    public Board() {
        this.botones = new JButton[20][20];
        this.a = 0;
        this.b = 0;
        avatarIcon = new ImageIcon(getClass().getResource("/recursos/avatarIcon.png"));
        muroIcon = new ImageIcon(getClass().getResource("/recursos/muroIcon.png"));
        cajaIcon = new ImageIcon(getClass().getResource("/recursos/cajaIcon.png"));
        llegadaIcon = new ImageIcon(getClass().getResource("/recursos/llegadaIcon.png"));
        estrellaIcon = new ImageIcon(getClass().getResource("/recursos/estrellaIcon.png"));
        llegadaAvatarIcon = new ImageIcon(getClass().getResource("/recursos/llegadaAvatarIcon.png"));
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
                botones[i][j].addKeyListener(this);
                botones[i][j].setIcon(caminoIcon);
                panel.add(botones[i][j]);
            }

        }
        botones[3][13].setIcon(avatarIcon);
        botones[8][12].setIcon(muroIcon);
        botones[13][9].setIcon(cajaIcon);
        botones[9][14].setIcon(muroIcon);
        botones[9][15].setIcon(llegadaIcon);
        botones[9][12].setIcon(llegadaIcon);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        posicionAvatar();
        if (e.VK_W == e.getKeyCode() && y >= 1 && botones[x][y - 1].getIcon() != muroIcon) {
            if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() != muroIcon && botones[x][y - 2].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon) {
                botones[x][y - 1].setIcon(caminoIcon);
                botones[x][y - 2].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            } else if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() == llegadaIcon) {
                botones[x][y - 1].setIcon(caminoIcon);
                botones[x][y - 2].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            } else if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() != muroIcon && botones[x][y - 2].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon) {
                botones[x][y - 1].setIcon(caminoIcon);
                botones[x][y - 2].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            } else if (botones[x][y - 1].getIcon() == cajaIcon) {

            } else if (botones[x][y - 1].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(llegadaAvatarIcon);
            } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y-1].getIcon() != cajaIcon && botones[x][y - 1].getIcon() != muroIcon && botones[x][y - 1].getIcon() != estrellaIcon) {
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
                    if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() != muroIcon && botones[x][y + 2].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon) {
                botones[x][y + 1].setIcon(caminoIcon);
                botones[x][y + 2].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            } else if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() == llegadaIcon) {
                botones[x][y + 1].setIcon(caminoIcon);
                botones[x][y + 2].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            } else if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() != muroIcon && botones[x][y + 2].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon) {
                botones[x][y + 1].setIcon(caminoIcon);
                botones[x][y + 2].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            } else if (botones[x][y + 1].getIcon() == cajaIcon) {

            } else if (botones[x][y + 1].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(llegadaAvatarIcon);
            } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y+1].getIcon() != cajaIcon && botones[x][y + 1].getIcon() != muroIcon && botones[x][y + 1].getIcon() != estrellaIcon) {
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
            if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() != muroIcon && botones[x - 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon) {
                botones[x - 1][y].setIcon(caminoIcon);
                botones[x - 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            } else if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() == llegadaIcon) {
                botones[x - 1][y].setIcon(caminoIcon);
                botones[x - 2][y].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            }else if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() != muroIcon && botones[x - 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon) {
                botones[x - 1][y].setIcon(caminoIcon);
                botones[x - 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            } else if (botones[x - 1][y].getIcon() == cajaIcon) {

            } else if (botones[x - 1][y].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(llegadaAvatarIcon);
            }  else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x - 1][y].getIcon() != cajaIcon && botones[x - 1][y].getIcon() != muroIcon && botones[x - 1][y].getIcon() != estrellaIcon) {
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
            if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() != muroIcon && botones[x + 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon) {
                botones[x + 1][y].setIcon(caminoIcon);
                botones[x + 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            } else if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() == llegadaIcon) {
                botones[x + 1][y].setIcon(caminoIcon);
                botones[x + 2][y].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            }else if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() != muroIcon && botones[x + 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon) {
                botones[x + 1][y].setIcon(caminoIcon);
                botones[x + 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            } else if (botones[x + 1][y].getIcon() == cajaIcon) {

            } else if (botones[x + 1][y].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(llegadaAvatarIcon);
            }  else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x + 1][y].getIcon() != cajaIcon && botones[x + 1][y].getIcon() != muroIcon && botones[x + 1][y].getIcon() != estrellaIcon) {
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
}
